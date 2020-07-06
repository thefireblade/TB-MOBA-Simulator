/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Game interface that  will be the bread and butter of multiplayer and single player implementations
 */
public interface Game {
    /** All override methods descriptions will be in this class*/
    /**
     * Prepares the map in game.
     */
    public void loadMap();

    /**
     * Simulates running one turn of the game. This will process all movement commands.
     * @return the turn number
     */
    public int simulateTurn();

    /**
     * Saves the game and stores it in the cloud
     * @return true if success, false otherwise
     */
    public boolean saveGame();

    /**
     * Loads the game that was stored in cloud (Deprecated)
     * @param save The save object to be loaded
     * @return true if success, false otherwise
     */
    public boolean loadGame(SaveObject save);

    /**
     * Add a new ai of Character 'c' to the game. The character should already be designated to a location in game.
     * @param c The character of the ai to be added.
     * @param team The team that the ai should be added to
     */
    public void addAI(Character c, Character.Team team);

    /**
     * Add a new player to the game
     * @param team The team that the new player will belong to
     */
    public void addPlayer(Character.Team team);

    /**
     * Add a mob to the game. The mob should already be designated to a location in game.
     * @param mob The mob to be added.
     */
    public void addMob(Mob mob);

    /**
     * Add a collection of mobs to the game. The mobs should already be designated to a location in game.
     * @param mob the mobs to be added
     */
    public void addMobs(List<Mob> mob);

    /**
     * Gets the current focused player in the game (The user)
     * @return The character of the user
     */
    public Character getCurrentPlayer();

    /**
     * Get the current turn number
     * @return turn number
     */
    public int getTurnCount();

    /**
     * Checks if the user is currently at their Team starting location
     * @return true if the user is at their starting location, false otherwise
     */
    public boolean isPlayerAtBase();

    /**
     * Get all of the shop items in this particular game
     * @return The Items purchasable in the shop
     */
    public ArrayList<Item> getShop();

    /**
     * Get the name of the game (used for saving to the cloud)
     * @return the name of the game
     */
    public String getGameName();

    /**
     * Set the name of the game (used for saving to the cloud)
     * @param saveName Desired name of the game
     */
    public void setGameName(String saveName);

    /**
     * Gets the location object of the Character p
     * @param p the character we want the location to
     * @return The location that contains the character p
     */
    public Location locatePlayer(Character p);

    /**
     * Get the log of the game
     * @return the log String that contains all of the actions by AI and events
     */
    public String getLog();

    /**
     * Function that allows a Character 'c' to use a designated Spell 'spell'. The function manages the mechanisms of the spell. [Currently in progress of implementation]
     * @param spell Spell to be used
     * @param c The character using the spell (Energy will be deducted)
     * @param players The players that can be affected in the location
     * @param mobs The mobs that can be affected in the location
     * @param otherMob The singleton mob to be affected
     * @param otherC The singleton Character to be affected (Fields are null if none)
     */
    public void useSpell(Spell spell, Character c, List<Character> players, List<Mob> mobs, Mob otherMob, Character otherC);

    /**
     * Character performs a regular attack movement
     * @param c The character 'c' that performs the attack
     * @param mob The singleton mob to be attacked
     * @param player The singleton player to be attacked (Fields are null if none)
     */
    public void basicAttack(Character c, Mob mob, Character player);

    /**
     * Checks if the location has a character on a team other than Team 'team'
     * @param loc the location to be inspected
     * @param team the team to be checked
     * @return true if there is an enemy player
     */
    public boolean locationHasEnemyPlayer(Location loc, Character.Team team);

    /**
     * Checks if the location has an entity or character on a team other than Team 'team'
     * @param loc the location to be inspected
     * @param team the team to be checked
     * @return true if there is an enemy player or entity
     */
    public boolean locationHasEnemy(Location loc, Character.Team team);

    /**
     * Checks for the win conditions of the game and sees which team won.
     * @return the team that won
     */
    public Character.Team didWin();
}
