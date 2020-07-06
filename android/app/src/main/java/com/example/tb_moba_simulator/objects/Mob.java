/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;

/**
 * The mob object to be used to store the information for a 'Mob' entity (Monster, unit, defense, etc)
 */
public class Mob {
    protected int health;
    protected int attack;
    protected ArrayList<Item> loot;
    protected double lootRate;
    protected int reward;
    protected String name; // Same as mob type
    protected int exp;
    protected int rank;
    protected Character.Team team;

    /**
     * Constructor method that creates a mob object
     * @param hp the health of the mob
     * @param atk the attack of the mob
     * @param loot the list of items a player has a chance to get from killing the mob
     * @param lootRate The chance to get an item from loot
     * @param reward the wealth amount earned after killing the mob
     * @param name the name of the mob
     * @param exp the amount of experience gained from killing a mob
     * @param team the team that the mob belongs to (Team enum)
     */
    public Mob(int hp, int atk, ArrayList<Item> loot, double lootRate, int reward, String name, int exp, Character.Team team) {
        this.health = hp;
        this.attack = atk;
        this.loot = loot;
        this.lootRate = lootRate;
        this.reward = reward;
        this.name = name;
        this.exp = exp;
        this.rank = 0;
        this.team = team;
    }
    /** Getter and Setter methods for the mob fields */
    public Character.Team getTeam() {
        return team;
    }

    public void setTeam(Character.Team team) {
        this.team = team;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public ArrayList<Item> getLoot() {
        return loot;
    }

    public void setLoot(ArrayList<Item> loot) {
        this.loot = loot;
    }

    public double getLootRate() {
        return lootRate;
    }

    public void setLootRate(double lootRate) {
        this.lootRate = lootRate;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the calculated power level of the mob
     * @return
     */
    public int getPowerLevel(){
        return this.health + this.attack * 2;
    }

    /**
     * Allows the mob to attack a character or mob
     * @param c character or mob to be attacked
     */
    public void attack(Character c) {
        c.setCurrHP(c.getCurrHP() - this.getAttack());
    }
    public void attack(Mob mob) {
        mob.setHealth(mob.getHealth() - this.getAttack());
    }

    /**
     * Clone a copy of the mob
     * @return copy of the mob
     */
    public Mob cloneSelf() {
        return new Mob(this.health, this.attack, this.loot, this.lootRate, this.reward, this.name, this.exp, this.team);
    }
}
