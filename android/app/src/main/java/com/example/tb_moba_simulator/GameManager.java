package com.example.tb_moba_simulator;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.tb_moba_simulator.objects.Character;
import com.example.tb_moba_simulator.objects.Game;
import com.example.tb_moba_simulator.objects.Item;
import com.example.tb_moba_simulator.objects.Location;
import com.example.tb_moba_simulator.objects.Mob;
import com.example.tb_moba_simulator.objects.SoloGame;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

//Class to maintain static values for the game environment
public class GameManager {
    public static Game game;
    public static Character.CharacterClass selectedType = Character.CharacterClass.archer;
    public static Map<String, Character> allCharacterTypes;
    public static boolean playable = false;
    public static boolean loaded = false;
    public static Random seed;
    public static ArrayList<Item> purchasableItems;
    public static void initGameMode(Map<String, Object> landData) {
        if(playable) {
            if(!(Boolean) landData.get("multiplayer")) {
                initSinglePlayer(landData);
            }
        } else {
            loaded = false;
        }
    }
    private static void initSinglePlayer(Map<String, Object> landData){
        seed = new Random();
        int ai = seed.nextInt(3);
        Character player = loadNewClass(selectedType);
        String email = FirebaseManager.mAuth.getCurrentUser().getEmail();
        player.setName(email);
        player.setTeam(Character.Team.team_0);
        ArrayList<Location> locations = parseLocations(landData);
        ArrayList<Location> startLocations = parseStartLocations((Map<String, Object>) landData.get("start_locations"), locations);
        game = new SoloGame(player, locations, new ArrayList<Mob>(), email, startLocations);
        addPlayersToStartLocation(player, startLocations);
        Character bot = loadNewClass(Character.CharacterClass.values()[ai]);
        addPlayersToStartLocation(bot, startLocations);
        game.addAI(bot, Character.Team.team_1);
        loaded = true;
        game.getShop().addAll(purchasableItems);
    }
    public static void loadDefaultConfiguration(){
        loadAllCharacterTypes();
        loadAllPurchasableItems();
    }
    private static void loadAllCharacterTypes() {
        if(FirebaseManager.dbInitialized) {
            allCharacterTypes = new HashMap<String, Character>();
            FirebaseManager.db.collection("characters")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Character newChar = new Character(
                                            (int)Double.parseDouble(doc.get("base_health").toString()),
                                            (int)Double.parseDouble(doc.get("base_attack").toString()),
                                            0,
                                            (int)Double.parseDouble(doc.get("base_energy").toString()),
                                            (int)Double.parseDouble(doc.get("health_per_level").toString()),
                                            (int)Double.parseDouble(doc.get("attack_per_level").toString()),
                                            0,
                                            (int)Double.parseDouble(doc.get("energy_per_level").toString()),
                                            0,
                                            Character.CharacterClass.valueOf(doc.getId()),
                                            null,
                                            null);
                                    allCharacterTypes.put(doc.getId(), newChar);
                                }
                                playable = true;
                            } else {
                                System.out.println(task.getException());
                                playable = false;
                            }
                        }
                    });
        } else {
            playable = false;
        }
    }
    public static Character loadNewClass(Character.CharacterClass type) {
        if(playable) {
            return allCharacterTypes.get(type.toString()).cloneSelf();
        }
        else return null;
    }
    private static ArrayList<Location> parseLocations(Map<String, Object> landData) {
        ArrayList<Location> locations = new ArrayList<>();
        Map<String, Object> landLocations = (Map<String, Object>) landData.get("locations");
        Set<String> locKeys = landLocations.keySet();
        for(String key: locKeys) {
            Map<String, Object> locData = (Map<String, Object>) landLocations.get(key);
            String team = (String)locData.get("team");
            if(team.length() == 0){
                team = "na";
            }
            Location newLoc = new Location(key, new ArrayList<Location>(), new ArrayList<Character>(),
                    (Boolean)(locData.get("defense")), Character.Team.valueOf(team));
            locations.add(newLoc);
        }
        for(Location loc: locations) {
            List<String> connList = (List<String>)((Map<String, Object>)landLocations.get(loc.getName())).get("connects");
            for(String connects: connList) {
                int index = findLoc(connects, locations);
                if(index >= 0) {
                    loc.addConnection(locations.get(index));
                }
            }
        }
        return locations;
    }
    private static ArrayList<Location> parseStartLocations(Map<String, Object> startLoc, ArrayList<Location> locations) {
        ArrayList<Location> startLocations = new ArrayList<>();
        int t1 = findLoc((String)startLoc.get("team_0"), locations), t2 = findLoc((String)startLoc.get("team_1"), locations);
        if(t1 >= 0) {
            startLocations.add(locations.get(t1));
        }
        if(t2 >= 0) {
            startLocations.add(locations.get(t2));
        }
        return startLocations;
    }
    private static int findLoc(String name, ArrayList<Location> locations) {
        for(int i = 0; i < locations.size(); i++) {
            if(locations.get(i).getName().equals(name)) {
                return i;
            }
        }
        System.out.println("findLoc() was not able to find the value for the specified name : " + name);
        return -1;
    }
    private static void addPlayersToStartLocation(Character player, ArrayList<Location> startLocations) {
        for(Location location: startLocations) {
            if(location.getTeam().equals(player.getTeam())) {
                location.addPlayer(player);
                break;
            }
        }
    }
    private static void loadAllPurchasableItems(){
        purchasableItems = new ArrayList<>();
        if(FirebaseManager.dbInitialized) {
            FirebaseManager.db.collection("items")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    if((Boolean)doc.get("purchasable")) {
                                        Item newItem = new Item(doc.getId(),
                                                Item.ItemBoostType.valueOf((String) doc.get("boostType")),
                                                (int) Double.parseDouble(doc.get("cost").toString()),
                                                (int) Double.parseDouble(doc.get("power").toString()),
                                                "",
                                                null, // needs to later be parsed in order for this to work
                                                (int) Double.parseDouble(doc.get("upgradeCost").toString()),
                                                (Boolean) doc.get("consumable"),
                                                true);
                                        purchasableItems.add(newItem);
                                    }
                                }
                            } else {
                                System.out.println(task.getException());
                            }
                        }
                    });
        }
    }
}
