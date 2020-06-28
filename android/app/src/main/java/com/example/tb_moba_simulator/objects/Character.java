package com.example.tb_moba_simulator.objects;


import android.icu.util.ULocale;

import java.util.ArrayList;

public class Character {
    public enum CharacterClass {
        archer, sword, shield
    }
    public enum Team {
        team_0, team_1
    }
    private int hpPT, atkPT, defPT, energyPT; // Short for attack per turn and defense per level
    private int baseHP, baseAtk, baseDef, baseEnergy;
    private int wealth;
    private int level;
    private boolean hasMoved;
    private CharacterClass type;
    private ArrayList<Item> items;
    private int currHP, currEnergy;
    private String currLocation;
    private Team team;
    private String name;

    public Character(int baseHp, int baseAtk, int baseDef, int baseEnergy, int hpPT,
                     int atkPT, int defPT, int energyPT, int level, CharacterClass characterType,
                     String name, Team team, String location) {
        this.baseHP = baseHp; this.baseAtk = baseAtk; this.baseDef = baseDef; this.baseEnergy = baseEnergy;
        this.hpPT = hpPT; this.atkPT = atkPT; this.defPT = defPT; this.energyPT = energyPT;
        this.wealth = 0;
        this.hasMoved = false;
        this.type = characterType;
        this.level = level;
        this.currHP = baseHp + (level * hpPT);
        this.currEnergy = baseEnergy + (level * energyPT);
        this.items = new ArrayList<Item>();
        this.name = name;
        this.team = team;
        this.currLocation = location;
    }

    public int getHpPT() {
        return hpPT;
    }

    public void setHpPT(int hpPT) {
        this.hpPT = hpPT;
    }

    public int getAtkPT() {
        return atkPT;
    }

    public void setAtkPT(int atkPT) {
        this.atkPT = atkPT;
    }

    public int getDefPT() {
        return defPT;
    }

    public void setDefPT(int defPT) {
        this.defPT = defPT;
    }

    public int getEnergyPT() {
        return energyPT;
    }

    public void setEnergyPT(int energyPT) {
        this.energyPT = energyPT;
    }

    public int getBaseHP() {
        return baseHP;
    }

    public void setBaseHP(int baseHP) {
        this.baseHP = baseHP;
    }

    public int getBaseAtk() {
        return baseAtk;
    }

    public void setBaseAtk(int baseAtk) {
        this.baseAtk = baseAtk;
    }

    public int getBaseDef() {
        return baseDef;
    }

    public void setBaseDef(int baseDef) {
        this.baseDef = baseDef;
    }

    public int getBaseEnergy() {
        return baseEnergy;
    }

    public void setBaseEnergy(int baseEnergy) {
        this.baseEnergy = baseEnergy;
    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public CharacterClass getType() {
        return type;
    }

    public void setType(CharacterClass type) {
        this.type = type;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getCurrHP() {
        return currHP;
    }

    public void setCurrHP(int currHP) {
        this.currHP = currHP;
    }

    public int getCurrEnergy() {
        return currEnergy;
    }

    public void setCurrEnergy(int currEnergy) {
        this.currEnergy = currEnergy;
    }

    public String getCurrLocation() {
        return currLocation;
    }

    public void setCurrLocation(String currLocation) {
        this.currLocation = currLocation;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
