package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;

public class Mob {
    private int health;
    private int attack;
    private ArrayList<Item> loot;
    private double lootRate;
    private int reward;
    private String name; // Same as mob type
    private int exp;
    public Mob(int hp, int atk, ArrayList<Item> loot, double lootRate, int reward, String name, int exp) {
        this.health = hp;
        this.attack = atk;
        this.loot = loot;
        this.lootRate = lootRate;
        this.reward = reward;
        this.name = name;
        this.exp = exp;
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
}
