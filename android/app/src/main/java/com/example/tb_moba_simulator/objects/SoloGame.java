/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator.objects;

import android.widget.TextView;

import com.example.tb_moba_simulator.FirebaseManager;
import com.example.tb_moba_simulator.GameManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SoloGame implements Game {
    private final int mobSpawnRate = 5;
    private int turnCount;
    private Character player;
    private ArrayList<Character> ai;
    private ArrayList<Mob> aiMobs;
    private ArrayList<Location> locations;
    private ArrayList<Location> startLocations;
    private ArrayList<Mob> mobs;
    private ArrayList<Defense> defenses;
    private ArrayList<Item> shop;
    private String userEmail;
    private String saveName;
    private String log;
    private Map<Character.Team, Integer> mobTiers;
    private ArrayList<Map<Character, Spell>> attackQueue;
    public SoloGame(Character player,
                    ArrayList<Location> locations, ArrayList<Mob> mobs, String userEmail, ArrayList<Location> startLocations, ArrayList<Defense> defenses) {
        this.turnCount = 1;
        this.player = player;
        this.ai = new ArrayList<Character>();
        this.locations = locations;
        this.mobs = mobs;
        this.userEmail = userEmail;
        this.startLocations = startLocations;
        this.shop = new ArrayList<>();
        this.saveName = "";
        this.log = "";
        this.defenses = defenses;
        this.aiMobs = new ArrayList<Mob>();
        initTiers();
    }
    public SoloGame(Character player,
                    ArrayList<Location> locations, ArrayList<Mob> mobs, String userEmail, ArrayList<Location> startLocations, ArrayList<Defense> defenses, String log, String saveName) {
        this.turnCount = 1;
        this.player = player;
        this.ai = new ArrayList<Character>();
        this.locations = locations;
        this.mobs = mobs;
        this.userEmail = userEmail;
        this.startLocations = startLocations;
        this.shop = new ArrayList<>();
        this.saveName = saveName;
        this.log = log;
        this.defenses = defenses;
        this.aiMobs = new ArrayList<Mob>();
        initTiers();
    }
    private void initTiers(){
        mobTiers = new HashMap<>();
        for(Character.Team team: Character.Team.values()) {
            mobTiers.put(team, 0);
        }
    }
    private void spawnDefenses(){
        Character.Team[] allTeams = Character.Team.values();
        for(Character.Team team: allTeams) {
            if(team == Character.Team.na) {
                continue;
            }
            Location start = getTeamStartLocation(team);
            if(start == null) {
                continue;
            }
            spawnDefenses(defenses.size() - 1, start, team);
        }
    }
    private void spawnDefenses(int tier, Location loc, Character.Team team) {
        if(loc.isDefenseLoc() && !loc.hasDefense() && tier >= 0) {
            Defense defense = (Defense) defenses.get(tier).cloneSelf();
            defense.setTeam(team);
            loc.getEntities().add(defense);
            loc.setHasDefense(true);
            for(Location connected: loc.getConnects()) {
                spawnDefenses(tier - 1, connected, team);
            }
        }
    }
    private void spawnMobs() {
        if(mobs.size() > 0) {
            Character.Team[] allTeams = Character.Team.values();
            for(Character.Team team: allTeams) {
                if(team == Character.Team.na) {
                    continue;
                }
                int tier = mobTiers.get(team);
                if(tier >= mobs.size()) {
                    tier = mobs.size() - 1;
                    mobTiers.put(team, tier);
                }
                Mob spawn = mobs.get(tier).cloneSelf();
                spawn.setTeam(team);
                addMobToStartLocation(spawn, team);
                aiMobs.add(spawn);
                newLogEntry(spawn.getName() + " has spawned at their base for " + spawn.getTeam().toString() + ".");
            }
        }
    }
    private void addMobToStartLocation(Mob mob, Character.Team team) {
        for(Location loc: startLocations) {
            if(loc.getTeam().equals(team)) {
                loc.getEntities().add(mob);
                break;
            }
        }
    }
    private Location getTeamStartLocation(Character.Team team ) {
        for(Location loc: startLocations) {
            if(loc.getTeam().equals(team)) {
                return loc;
            }
        }
        System.out.println("Was unable to find the start location for team : " + team.toString());
        return null;
    }

    private void sortMobsToTiers(){
        Collections.sort(mobs, new Comparator<Mob>() {
            @Override
            public int compare(Mob u1, Mob u2) {
                return ((Integer)u1.getPowerLevel()).compareTo((Integer)u2.getPowerLevel());
            }
        });
        Collections.sort(defenses, new Comparator<Mob>() {
            @Override
            public int compare(Mob u1, Mob u2) {
                return ((Integer)u1.getPowerLevel()).compareTo((Integer)u2.getPowerLevel());
            }
        });
    }
    @Override
    public String getGameName() {
        return saveName;
    }

    @Override
    public void setGameName(String saveName) {
        this.saveName = saveName;
    }

    @Override
    public Location locatePlayer(Character p) {
        for(Location loc: locations) {
            if(loc.getPlayers().indexOf(p)>= 0){
                return loc;
            }
        }
        System.out.println("Unable to locate player : " + p.getName());
        return null; // Default is last location
    }
    private Location locateMob(Mob p) {
        for(Location loc: locations) {
            if(loc.getEntities().indexOf(p)>= 0){
                return loc;
            }
        }
        System.out.println("Unable to locate Mob : " + p.getName());
        if (locations.size() == 0) {return null;}
        return locations.get(locations.size() - 1); // Default is last location
    }

    @Override
    public String getLog() {
        return this.log;
    }

    @Override
    public void loadMap() {
        sortMobsToTiers();
//        spawnMobs();
        spawnDefenses();
    }
    @Override
    public Character.Team didWin(){
        for(Character.Team team: Character.Team.values()) {
            if(team.equals(team.na)) {
                continue;
            }
            boolean teamLost = true;
            for(Location l: locations) {
                for(Mob mob: l.getEntities()) {
                    if(mob.getTeam().equals(team)) {
                        teamLost = false;
                    }
                }
                if(!teamLost) {
                    break;
                }
            }
            if(teamLost) {
                switch(team){
                    case team_0: return Character.Team.team_1;
                    default: return Character.Team.team_0;
                }
            }
        }
        return Character.Team.na;
    }
    @Override
    public int simulateTurn() {
        for(Character bot: ai) {
            if(bot.getCurrHP() <= 0) {
                newLogEntry(bot.getName() + " is dead and did nothing.");
                continue;
            }
            if(!decisionMaker(bot)){
                System.out.println("Failed to make a decision for " + bot.getName());
                newLogEntry(bot.getName() + " did nothing.");
            }
            bot.setExp(bot.getExp() + 1);
            bot.setWealth(bot.getWealth() + 3);
        }
//        for(Mob mob: aiMobs) {
//            if(!decisionMaker(mob)){
//                System.out.println("Failed to make a decision for " + mob.getName());
//                newLogEntry(mob.getName() + " did nothing.");
//            }
//        }
        turnCount++;
        if(turnCount % 5 == 0) {
            reviveAllDeadPlayers();
        }
        if(player.getCurrHP() <= 0) {
            simulateTurn();
        }
        player.setExp(player.getExp() + 1);
        player.setWealth(player.getWealth() + 2);
        return turnCount;
    }
    private void reviveAllDeadPlayers(){
        if(player.getCurrHP() <= 0) {
            player.setCurrHP(player.getMaxHealth());
            newLogEntry(player.getName() + " has revived " + " at base.");
        }
        for(Character bot: ai) {
            if(bot.getCurrHP() <= 0) {
                bot.setCurrHP(bot.getMaxHealth());
                newLogEntry(bot.getName() + " has revived " + " at base.");
            }
        }
    }
    private void newLogEntry(String message) {
        this.log += "\n[Turn #" + turnCount +"]" + message;
    }
    private boolean decisionMaker(Mob mob) {
        Character.Team mobTeam = mob.getTeam();
        Location startLocation = getTeamStartLocation(mobTeam);
        Location mobLocation = locateMob(mob);
        if(mobLocation == null) {
            System.out.println("Failed to make a decision because mob isn't on map");
            return false;
        }
        Mob enemyMob = getEnemyMob(mobLocation, mobTeam);
        if(enemyMob != null) {
            mobBasicAttack(mob, enemyMob, null);
            return true;
        }
        Character enemyPlayer = getEnemyPlayer(mobLocation, mobTeam);
        if(enemyPlayer != null) {
            mobBasicAttack(mob, null, enemyPlayer);
            return true;
        }
        return moveFromBase(mob, mobLocation, true);
    }
    private boolean decisionMaker(Character bot) {
        Character.Team botTeam = bot.getTeam();
        Location startLocation = getTeamStartLocation(botTeam);
        Location botLocation = locatePlayer(bot);
        if(botLocation == null) {
            System.out.println("Failed to make a decision because player isn't on map");
            return false;
        }
        if (startLocation.getPlayers().indexOf(bot) >= 0) {
            if(bot.getWealth() > 3) {
                Item.ItemBoostType bias = Item.ItemBoostType.health;
                if(bot.getType().equals(Character.CharacterClass.archer)) {
                    bias = Item.ItemBoostType.attack;
                }
                Item buy = getMostExpensiveBuyableItem(bot.getWealth(), bias);
                if(buy != null) {
                    bot.purchaseItem(buy);
                    newLogEntry(bot.getName() + " has spent " + buy.getCost() + " to buy " + buy.getName());
                }
            }
            if(bot.getCurrHP() < (int)Math.floor(bot.getMaxHealth() * .3)) {
                bot.rest();
                newLogEntry(bot.getName() + " has rested at base restoring" + (int)Math.floor(bot.getMaxHealth() / 2) + " energy and health.");
                return true;
            }
        }
        Spell spell = null; //bot.getRandomUsableSpell(); Spells need reworking because of thread issues
        Mob attackMob = getEnemyMob(botLocation, botTeam);
        if(attackMob != null) {
            if(spell != null) {
                useSpell(spell, bot, botLocation.getPlayers(), botLocation.getEntities(), attackMob, null);
                return true;
            }
            basicAttack(bot, attackMob, null);
            return true;
        }
        Character human = getEnemyPlayer(botLocation, botTeam);
        if(human != null) {
            if(spell != null) {
                useSpell(spell, bot, botLocation.getPlayers(), botLocation.getEntities(), null, human);
                return true;
            }
            basicAttack(bot, null, human);
            return true;
        }
        if(bot.getCurrHP() < (int)Math.floor(bot.getMaxHealth() * .3)) {
            return moveFromBase(bot, botLocation, false);
        }
        return moveFromBase(bot, botLocation, true);
    }
    @Override
    public void basicAttack(Character c, Mob mob, Character player) {
        if(mob != null) {
            c.attack(mob);
            newLogEntry(c.getName() + " attacked " + mob.getName() + " dealing " + c.getCurrAtk() + " damage.");
            if (mob.getHealth() <= 0) {
                locateMob(mob).getEntities().remove(mob);
                c.setWealth(c.getWealth() + mob.getReward());
                newLogEntry(mob.getName() + " has been destroyed by " + c.getName() + " and " + c.getName() + " has gained " + mob.getReward() + " wealth.");
                checkLoot(c, mob);
            }
        }
        if(player != null) {
            c.attack(player);
            newLogEntry(c.getName() + " attacked " + player.getName() + " dealing " + c.getCurrAtk() + " damage.");
            if(player.getCurrHP() <= 0) {
                locatePlayer(player).getPlayers().remove(player);
                c.setKills(c.getKills() + 1);
                player.setDeaths(player.getDeaths() + 1);
                getTeamStartLocation(player.getTeam()).getPlayers().add(player);
                newLogEntry(player.getName() + " has been slain by " + c.getName() + " for 5 exp and has been recalled to base.");
                c.setExp(c.getExp() + 5);
            }
        }
    }

    public void mobBasicAttack(Mob c, Mob mob, Character player) {
        if(mob != null) {
            c.attack(mob);
            newLogEntry(c.getName() + " attacked " + mob.getName() + " dealing " + c.getAttack() + " damage.");
            if (mob.getHealth() <= 0) {
                locateMob(mob).getEntities().remove(mob);
                newLogEntry(mob.getName() + " has been destroyed by " + c.getName() + ".");
            }
        }
        if(player != null) {
            c.attack(player);
            newLogEntry(c.getName() + " attacked " + player.getName() + " dealing " + c.getAttack() + " damage.");
            if(player.getCurrHP() <= 0) {
                locatePlayer(player).getPlayers().remove(player);
                player.setDeaths(player.getDeaths() + 1);
                getTeamStartLocation(player.getTeam()).getPlayers().add(player);
                newLogEntry(player.getName() + " has been slain by " + c.getName() + " and has been recalled to base.");
            }
        }
    }
    @Override
    public void useSpell(Spell spell, Character c, List<Character> players, List<Mob> mobs, Mob otherMob, Character otherC) {
        Spell.TargetGroup targets = spell.getTargets();
        Character.Team cTeam = c.getTeam();
        switch(targets) {
            case self: c.restore(spell); break;
            case group:
                for(Character player: players){
                    if(!player.getTeam().equals(cTeam)) {
                        c.attack(player, spell);
                        newLogEntry(c.getName() + " has used " + spell.getName() + " on " + player.getName() + " dealing " + c.getSpellPower(spell) + " damage.");
                        if(player.getCurrHP() <= 0) {
                            Lock lock = new ReentrantLock();
                            lock.lock();
                            locatePlayer(player).getPlayers().remove(player);
                            lock.unlock();
                            c.setKills(c.getKills() + 1);
                            player.setDeaths(player.getDeaths() + 1);
                            getTeamStartLocation(player.getTeam()).getPlayers().add(player);
                            newLogEntry(player.getName() + " has been slain by " + c.getName() + " and has been recalled to base.");
                        }
                    }
                }
                for(Mob mob : mobs) {
                    if(!mob.getTeam().equals(cTeam)) {
                        c.attack(mob, spell);
                        newLogEntry(c.getName() + " has used " + spell.getName() + " on " + mob.getName() + " dealing " + c.getSpellPower(spell) + " damage.");
                        if(mob.getHealth() <= 0) {
                            locateMob(mob).getEntities().remove(mob);
                            c.setWealth(c.getWealth() + mob.getReward());
                            newLogEntry(mob.getName() + " has been destroyed by " + c.getName() + " and " + c.getName() + " has gained " + mob.getReward() + " wealth.");
                            checkLoot(c, mob);
                        }
                    }
                }
                break;
            case allies:
                for(Character player: players){
                    if(player.getTeam().equals(cTeam)) {
                        c.restore(player, spell);
                        newLogEntry(c.getName() + " has used " + spell.getName() + " on " + player.getName() + " restoring " + c.getSpellPower(spell) + " health.");
                    }
                }
                for(Mob mob : mobs) {
                    if(mob.getTeam().equals(cTeam)) {
                        c.restore(mob, spell);
                        newLogEntry(c.getName() + " has used " + spell.getName() + " on " + mob.getName() + " restoring " + c.getSpellPower(spell) + " health.");
                    }
                }
                break;
            default:
                if(otherMob != null) {
                    c.attack(otherMob);
                    newLogEntry(c.getName() + " has used " + spell.getName() + " on " + otherMob.getName() + " dealing " + c.getSpellPower(spell) + " damage.");
                    if(otherMob.getHealth() <= 0) {
                        locateMob(otherMob).getEntities().remove(otherMob);
                        c.setWealth(c.getWealth() + otherMob.getReward());
                        newLogEntry(otherMob.getName() + " has been destroyed by " + c.getName() + " and " + c.getName() + " has gained " + otherMob.getReward() + " wealth.");
                        checkLoot(c, otherMob);
                    }
                }
                if(otherC != null) {
                    c.attack(otherC);
                    newLogEntry(c.getName() + " has used " + spell.getName() + " on " + otherC.getName() + " dealing " + c.getSpellPower(spell) + " damage.");
                    if(otherC.getCurrHP() <= 0) {
                        locatePlayer(otherC).getPlayers().remove(otherC);
                        c.setKills(c.getKills() + 1);
                        otherC.setDeaths(otherC.getDeaths() + 1);
                        getTeamStartLocation(otherC.getTeam()).getPlayers().add(otherC);
                        newLogEntry(otherC.getName() + " has been slain by " + c.getName() + " and has been recalled to base.");
                    }
                }
        }
        c.setCurrEnergy(c.getCurrEnergy() - spell.getCost());
    }
    private void checkLoot(Character c, Mob b) {
        int lootSize = b.getLoot().size();
        if(b.getLootRate() > 0 && lootSize > 0) {
            double chance = GameManager.seed.nextInt(1000);
            if(chance < b.lootRate * 1000) {
                int itemIndex = GameManager.seed.nextInt(lootSize);
                Item lootedItem = b.getLoot().get(itemIndex);
                c.getItems().add(lootedItem);
                newLogEntry(c.getName() + " has looted " + lootedItem.getName() + " from + " + b.getName() + "!");
            }
        }
    }
    private boolean moveFromBase(Mob c, Location loc, boolean farther) {
        Location base = getTeamStartLocation(c.getTeam());
        int currDist = getDistFromLoc(loc, base);
        for(Location connected: loc.getConnects()){
            int dist = getDistFromLoc(connected, base);
            if(farther) {
                if(dist > currDist) {
                    loc.getPlayers().remove(c);
                    connected.getEntities().add(c);
                    newLogEntry(c.getName() + " has moved from " + loc.getName() + " to " + connected.getName() + ".");
                    return true;
                }
            } else {
                if(dist < currDist) {
                    loc.getPlayers().remove(c);
                    connected.getEntities().add(c);
                    newLogEntry(c.getName() + " has moved from " + loc.getName() + " to " + connected.getName() + ".");
                    return true;
                }
            }
        }
        return false;
    }
    private boolean moveFromBase(Character c, Location loc, boolean farther) {
        Location base = getTeamStartLocation(c.getTeam());
        int currDist = getDistFromLoc(loc, base);
        for(Location connected: loc.getConnects()){
            int dist = getDistFromLoc(connected, base);
            if(farther) {
                if(dist > currDist) {
                    loc.getPlayers().remove(c);
                    connected.getPlayers().add(c);
                    newLogEntry(c.getName() + " has moved from " + loc.getName() + " to " + connected.getName() + ".");
                    return true;
                }
            } else {
                if(dist < currDist) {
                    loc.getPlayers().remove(c);
                    connected.getPlayers().add(c);
                    newLogEntry(c.getName() + " has moved from " + loc.getName() + " to " + connected.getName() + ".");
                    return true;
                }
            }
        }
        return false;
    }
    private int getDistFromLoc(Location loc, Location loc2) {
        return getDistFromLoc(loc, loc2, 0, 69420);
    }
    private int getDistFromLoc(Location loc, Location loc2, int dist, int minDist) {
        if(dist > minDist) {
            return minDist;
        }
        if(loc.equals(loc2)) {
            return dist;
        }
        if(loc.getConnects().indexOf(loc2) >= 0) {
            return 1 + dist;
        }
        int min = minDist;
        for(Location connected: loc.getConnects()) {
            int temp = getDistFromLoc(connected, loc2, dist + 1, min);
            if(temp < min) {
                min = temp;
            }
        }
        return min;
    }
    private Mob getEnemyMob(Location loc, Character.Team team) {
        for(Mob mob: loc.getEntities()) {
            if(!mob.getTeam().equals(team)) {
                return mob;
            }
        }
        return null;
    }
    private Character getEnemyPlayer(Location loc, Character.Team team) {
        for(Character player: loc.getPlayers()) {
            if(!player.getTeam().equals(team)) {
                return player;
            }
        }
        return null;
    }
    public Item getMostExpensiveBuyableItem(int threshold, Item.ItemBoostType bias) {
        Item best = null;
        if (shop.size() > 0) {
            for (Item item : shop) {
                if(!item.getBoostType().equals(bias)) {
                    continue;
                }
                if(item.getCost() <= threshold) {
                    if(best == null) {
                        best = item;
                    }
                    else if(best.getCost() < item.getCost()) {
                        best = item;
                    }
                }
            }
        }
        return best;
    }
    @Override
    public boolean saveGame() {
        Map<String, Object> data = new HashMap<>();
        Date today = new Date();
        data.put("date", today.toString());
        data.put("name", saveName);
        data.put("log", log);
        data.put("mobs", new ArrayList<>());
        data.put("playerInfo", parsePlayer(player));

        for(Character.Team team : Character.Team.values()) {
            if(team.equals(team.na)) {
                continue;
            }
            List<Map<String, Object>> teamComp = new ArrayList<>();
            for(Character c: ai) {
                if(c.getTeam().equals(team)) {
                    teamComp.add(parsePlayer(c));
                }
            }
            data.put(team.toString(), teamComp);
        }
        data.put("turn", turnCount);
        data.put("user", FirebaseManager.mAuth.getCurrentUser().getEmail());
        data.put("landtype", "Single Lane");
        ArrayList<Map<String, Object>> defenses = new ArrayList<>();
        for(Location location: locations) {
            Map<String, Object> defObj = parseDefenses(location);
            if(defObj.keySet().size() > 0) {
                defenses.add(defObj);
            }
        }
        data.put("defenses", defenses);
        FirebaseManager.db.collection("saves").add(data);
        return true;
    }

    /**
     * Needs top be updated**
     * @param l
     * @return
     */
    private Map<String, Object> parseDefenses(Location l){
        Map<String, Object> data = new HashMap<>();
        for(Mob mob: l.getEntities()) {
            if(Defense.class.isInstance(mob)) {
                data.put("health", mob.getHealth());
                data.put("location", l.getName());
                data.put("team", mob.getTeam().toString());
                data.put("name", mob.getName());
                break;
            }
        }
        return data;
    }
    private Map<String, Object> parsePlayer(Character c) {
        Map<String,Object> playerInfo = new HashMap<>();
        playerInfo.put("character", c.getType().toString());
        playerInfo.put("didMove", true);
        playerInfo.put("energy", c.getCurrEnergy());
        playerInfo.put("exp", c.getExp());
        playerInfo.put("health", c.getCurrHP());
        playerInfo.put("location", locatePlayer(c).getName());
        playerInfo.put("wealth", c.getWealth());
        playerInfo.put("deaths", c.getDeaths());
        playerInfo.put("kills", c.getKills());
        playerInfo.put("name", c.getName());
        List<String> items = new ArrayList<>();
        for(Item item : c.getItems()) {
            items.add(item.getName());
        }
        playerInfo.put("items", items);
        return playerInfo;
    }
    @Override
    public boolean loadGame(SaveObject save) {
        return true;
    }
    @Override
    public void addAI(Character c, Character.Team team) {
        int aiNum = ai.size() + 1;
        c.setName("AI " + aiNum);
        c.setTeam(team);
        ai.add(c);
    }

    @Override
    public void addPlayer(Character.Team team) {
        // Doesn't do anything for the purposes of a solo game
    }
    @Override
    public Character getCurrentPlayer(){
        return player;
    }

    @Override
    public int getTurnCount(){
        return turnCount;
    }
    @Override
    public boolean isPlayerAtBase(){
        for(Location location: startLocations) {
            if(location.getPlayers().indexOf(player) >= 0 && location.getTeam().equals(player.getTeam())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Item> getShop() {
        return shop;
    }

    @Override
    public boolean locationHasEnemy(Location loc, Character.Team team) {
        for(Character player: loc.getPlayers()) {
            if(!player.getTeam().equals(team)) {
                return true;
            }
        }
        for(Mob mob: loc.getEntities()) {
            if(!mob.getTeam().equals(team)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean locationHasEnemyPlayer(Location loc, Character.Team team) {
        for(Character player: loc.getPlayers()) {
            if(!player.getTeam().equals(team)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void addMob(Mob mob){
        mobs.add(mob);
    }
    @Override
    public void addMobs(List<Mob> mob){
        mobs.addAll(mob);
    }
}
