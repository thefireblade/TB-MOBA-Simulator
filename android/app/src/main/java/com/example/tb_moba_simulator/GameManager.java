package com.example.tb_moba_simulator;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.tb_moba_simulator.objects.Character;
import com.example.tb_moba_simulator.objects.Defense;
import com.example.tb_moba_simulator.objects.Game;
import com.example.tb_moba_simulator.objects.Item;
import com.example.tb_moba_simulator.objects.Location;
import com.example.tb_moba_simulator.objects.Mob;
import com.example.tb_moba_simulator.objects.SaveObject;
import com.example.tb_moba_simulator.objects.SoloGame;
import com.example.tb_moba_simulator.objects.Spell;
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
    public static ArrayList<Item> allItems;
    public static ArrayList<Map<String, Object>> lands;
    public static ArrayList<Mob> mobs;
    public static ArrayList<Defense> defenses;
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
        game = new SoloGame(player, locations, mobs, email, startLocations, defenses);
        addPlayersToStartLocation(player, startLocations);
        Character bot = loadNewClass(Character.CharacterClass.values()[ai]);
        game.addAI(bot, Character.Team.team_1);
        addPlayersToStartLocation(bot, startLocations);
        loaded = true;
        game.getShop().addAll(purchasableItems);
        game.loadMap();
    }
    public static void loadDefaultConfiguration(){
        loadAllItems();
        loadAllGameLands();
        loadAllDefenses();
        loadAllCharacterTypes();
    }
    private static void loadAllMobs(){
        mobs = new ArrayList<Mob>();
        if(FirebaseManager.dbInitialized) {
            FirebaseManager.db.collection("mobs")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Mob mob = new Mob(
                                            (int)Double.parseDouble(doc.get("health").toString()),
                                            (int)Double.parseDouble(doc.get("attack").toString()),
                                            getLootList((ArrayList<String>)doc.get("loot")),
                                            Double.parseDouble(doc.get("lootRate").toString()),
                                            (int)Double.parseDouble(doc.get("reward").toString()),
                                            doc.getId(),
                                            (int)Double.parseDouble(doc.get("exp").toString()),
                                            Character.Team.team_0
                                    );
                                    mobs.add(mob);
                                }
                            } else {
                                System.out.println(task.getException());
                            }
                        }
                    });
        }
    }
    private static void loadAllDefenses(){
        defenses = new ArrayList<Defense>();
        if(FirebaseManager.dbInitialized) {
            FirebaseManager.db.collection("defenses")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Defense defense = new Defense(
                                            (int)Double.parseDouble(doc.get("health").toString()),
                                            (int)Double.parseDouble(doc.get("attack").toString()),
                                            (int)Double.parseDouble(doc.get("reward").toString()),
                                            doc.getId(),
                                            (int)Double.parseDouble(doc.get("exp").toString()),
                                            Character.Team.team_0);
                                    defenses.add(defense);
                                }
                            } else {
                                System.out.println(task.getException());
                            }
                        }
                    });
        }
    }
    private static void loadAllGameLands(){
        lands = new ArrayList<Map<String, Object>>();
        if(FirebaseManager.dbInitialized) {
            FirebaseManager.db.collection("lands")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> data = document.getData();
                                    data.put("name", document.getId());
                                    lands.add(data);
                                }
                            } else {
                                System.out.println(task.getException());
                            }
                        }
                    });
        }
    }
    private static void loadAllCharacterTypes(){
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
                                    for(Map<String, Object> spell: (ArrayList<Map<String, Object>>)doc.get("spells")) {
                                        newChar.addSpell(parseSpell(spell));
                                    }
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
    private static Spell parseSpell(Map<String, Object> spellMap) {
        return new Spell(
                (String)spellMap.get("name"),
                (String)spellMap.get("Description"),
                (int)Double.parseDouble(spellMap.get("attack_per_level").toString()),
                (int)Double.parseDouble(spellMap.get("base_attack").toString()),
                (int)Double.parseDouble(spellMap.get("cost").toString()),
                (int)Double.parseDouble(spellMap.get("level_required").toString()),
                Spell.TargetGroup.valueOf((String)spellMap.get("target")),
                (int)Double.parseDouble(spellMap.get("priority").toString()),
                (int)Double.parseDouble(spellMap.get("scale_attack").toString()),
                (int)Double.parseDouble(spellMap.get("scale_health").toString()),
                Item.ItemBoostType.valueOf((String)spellMap.get("type")));
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
    private static void loadAllItems(){
        allItems = new ArrayList<>();
        purchasableItems = new ArrayList<>();
        if(FirebaseManager.dbInitialized) {
            FirebaseManager.db.collection("items")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Item newItem = new Item(doc.getId(),
                                            Item.ItemBoostType.valueOf((String) doc.get("boostType")),
                                            (int) Double.parseDouble(doc.get("cost").toString()),
                                            (int) Double.parseDouble(doc.get("power").toString()),
                                            "",
                                            null, // needs to later be parsed in order for this to work
                                            (int) Double.parseDouble(doc.get("upgradeCost").toString()),
                                            (Boolean) doc.get("consumable"),
                                            true);
                                    if((Boolean)doc.get("purchasable")) {
                                        purchasableItems.add(newItem);
                                    }
                                    allItems.add(newItem);
                                }
                                loadAllMobs();
                            } else {
                                System.out.println(task.getException());
                            }
                        }
                    });
        }
    }
    private static ArrayList<Item> getLootList(ArrayList<String> loot){
        ArrayList<Item> lootList = new ArrayList<>();
        for(String s: loot) {
            for(Item i: allItems) {
                if(i.getName().equals(s)) {
                    lootList.add(i);
                    break;
                }
            }
        }
        return lootList;
    }
    public static void loadSave(SaveObject save, Map<String, Object> landData) {
        if(playable) {
            Map<String, Object> playerInfo = save.getPlayerInfo();
            ArrayList<Location> locations = parseLocations(landData);
            Character player = parsePlayerInfo(playerInfo, locations);
            player.setTeam(Character.Team.team_0);
            Map<String, Object> ai = save.getTeam_1().get(0);
            Character bot = parsePlayerInfo(ai, locations);
            bot.setTeam(Character.Team.team_1);
            ArrayList<Location> startLocations = parseStartLocations((Map<String, Object>) landData.get("start_locations"), locations);
            game = new SoloGame(player, locations, mobs, FirebaseManager.mAuth.getCurrentUser().getEmail(), startLocations, defenses,
                    save.getLog(), save.getName());
        } else {
            loadDefaultConfiguration();
        }
    }
    private static Character parsePlayerInfo(Map<String, Object> playerInfo, ArrayList<Location> locations) {
        Character.CharacterClass type = Character.CharacterClass.valueOf((String)playerInfo.get("character"));
        Character player = loadNewClass(type);
        player.setWealth((int) Double.parseDouble(playerInfo.get("wealth").toString()));
        player.setCurrEnergy((int) Double.parseDouble(playerInfo.get("energy").toString()));
        player.setExp((int) Double.parseDouble(playerInfo.get("exp").toString()));
        player.setCurrHP((int) Double.parseDouble(playerInfo.get("health").toString()));
        player.setKills((int) Double.parseDouble(playerInfo.get("kills").toString()));
        player.setDeaths((int) Double.parseDouble(playerInfo.get("deaths").toString()));
        ArrayList<Item> playerItems = new ArrayList<>();
        for(String itemName: (ArrayList<String>)playerInfo.get("items")) {
            for(Item item: allItems) {
                if(item.getName().equals(itemName)) {
                    playerItems.add(item);
                }
            }
        }
        for(Location loc: locations) {
            if(loc.getName().equals((String)playerInfo.get("location"))) {
                loc.getPlayers().add(player);
            }
        }
        return player;
    }
}
