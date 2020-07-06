package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;

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

    public int getPowerLevel(){
        return this.health + this.attack * 2;
    }

    public void attack(Character c) {
        c.setCurrHP(c.getCurrHP() - this.getAttack());
    }
    public void attack(Mob mob) {
        mob.setHealth(mob.getHealth() - this.getAttack());
    }

    public Mob cloneSelf() {
        return new Mob(this.health, this.attack, this.loot, this.lootRate, this.reward, this.name, this.exp, this.team);
    }
}
