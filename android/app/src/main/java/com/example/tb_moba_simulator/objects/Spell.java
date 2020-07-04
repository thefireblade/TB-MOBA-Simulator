package com.example.tb_moba_simulator.objects;

public class Spell {
    public enum TargetGroup {
        self, group, other, allies
    }
    public enum SpellType {
        health, attack, energy
    }
    private String name;
    private String description;
    private int atkPL;
    private int base_atk;
    private int cost;
    private int level;
    private TargetGroup targets;
    private int priority;
    private int atk_scale;
    private int hp_scale;

    public Spell(String name, String description, int atkPL, int base_atk, int cost, int level, TargetGroup targets, int priority, int atk_scale, int hp_scale) {
        this.name = name;
        this.description = description;
        this.atkPL = atkPL;
        this.base_atk = base_atk;
        this.cost = cost;
        this.level = level;
        this.targets = targets;
        this.priority = priority;
        this.atk_scale = atk_scale;
        this.hp_scale = hp_scale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAtkPL() {
        return atkPL;
    }

    public void setAtkPL(int atkPL) {
        this.atkPL = atkPL;
    }

    public int getBase_atk() {
        return base_atk;
    }

    public void setBase_atk(int base_atk) {
        this.base_atk = base_atk;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public TargetGroup getTargets() {
        return targets;
    }

    public void setTargets(TargetGroup targets) {
        this.targets = targets;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getAtk_scale() {
        return atk_scale;
    }

    public void setAtk_scale(int atk_scale) {
        this.atk_scale = atk_scale;
    }

    public int getHp_scale() {
        return hp_scale;
    }

    public void setHp_scale(int hp_scale) {
        this.hp_scale = hp_scale;
    }
}
