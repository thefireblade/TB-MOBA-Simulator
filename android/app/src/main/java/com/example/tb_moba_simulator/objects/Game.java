package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;
import java.util.List;

public interface Game {
    public void loadMap();
    public int simulateTurn(); // Returns turn number
    public boolean saveGame();
    public boolean loadGame(SaveObject save);
    public void addAI(Character c, Character.Team team);
    public void addPlayer(Character.Team team);
    public void addMob(Mob mob);
    public void addMobs(List<Mob> mob);
    public Character getCurrentPlayer();
    public int getTurnCount();
    public boolean isPlayerAtBase();
    public ArrayList<Item> getShop();
    public String getGameName();
    public void setGameName(String saveName);
    public Location locatePlayer(Character p);
    public String getLog();
    public void useSpell(Spell spell, Character c, List<Character> players, List<Mob> mobs, Mob otherMob, Character otherC);
    public void basicAttack(Character c, Mob mob, Character player);
    public boolean locationHasEnemyPlayer(Location loc, Character.Team team);
    public boolean locationHasEnemy(Location loc, Character.Team team);
    public Character.Team didWin();
}
