/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator.objects;


import android.icu.util.ULocale;

import com.example.tb_moba_simulator.GameManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Character {
    public enum CharacterClass {
        archer, sword, shield
    }
    public enum Team {
        team_0, team_1, na
    }
    private int hpPT, atkPT, defPT, energyPT; // Short for attack per turn and defense per level
    private int baseHP, baseAtk, baseDef, baseEnergy;
    private int wealth;
    private int exp;
    private boolean hasMoved;
    private CharacterClass type;
    private List<Item> items;
    private int currHP, currEnergy;
    private Team team;
    private String name;
    private ArrayList<Spell> spells;
    private int kills, deaths, assist;

    public Character(int baseHp, int baseAtk, int baseDef, int baseEnergy, int hpPT,
                     int atkPT, int defPT, int energyPT, int exp, CharacterClass characterType,
                     String name, Team team) {
        this.baseHP = baseHp; this.baseAtk = baseAtk; this.baseDef = baseDef; this.baseEnergy = baseEnergy;
        this.hpPT = hpPT; this.atkPT = atkPT; this.defPT = defPT; this.energyPT = energyPT;
        this.wealth = 0;
        this.hasMoved = false;
        this.type = characterType;
        this.exp = exp;
        this.currHP = baseHp + (getLevel() * hpPT);
        this.currEnergy = baseEnergy + (getLevel() * energyPT);
        this.items = Collections.synchronizedList(new ArrayList<Item>());
        this.name = name;
        this.team = team;
        spells = new ArrayList<Spell>();
        kills = 0; deaths = 0; assist = 0;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
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

    public void setLevel(int level) {
        this.exp = 5 * level;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getCurrHP() {
        return currHP;
    }

    public void setCurrHP(int currHP) {
        if(currHP < 0) { this.currHP = 0; }
        else{ this.currHP = currHP;}
        if(this.currHP > this.getMaxHealth()) {
            this.currHP = this.getMaxHealth();
        }
    }

    public int getCurrEnergy() {
        return currEnergy;
    }

    public void setCurrEnergy(int currEnergy) {
        if(currEnergy < 0) { this.currEnergy = 0; }
        else{ this.currEnergy = currEnergy;}
        if(this.currEnergy > this.getMaxEnergy()) {
            this.currEnergy = this.getMaxEnergy();
        }
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

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setSpells(ArrayList<Spell> spells) {
        this.spells = spells;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }


    public void addSpell(Spell spell) {
        this.spells.add(spell);
    }

    public int getCurrAtk() {
        return baseAtk + atkPT * getLevel() + getBoost(Item.ItemBoostType.attack);
    }

    public int getBoost(Item.ItemBoostType boostType){
        int sum = 0;
        for(Item item: items) {
            if(item.getBoostType().equals(boostType) && !item.isConsumable()) {
                sum += item.getPower();
            }
        }
        return sum;
    }

    public int getMaxHealth(){
        return baseHP + hpPT * getLevel() + getBoost(Item.ItemBoostType.health);
    }

    public int getMaxEnergy(){
        return baseEnergy + energyPT * getLevel() + getBoost(Item.ItemBoostType.energy);
    }

    public int getLevel() {
        return (int)Math.floor(exp / 5.0) + 1;
    }

    public int getExp(){
        return this.exp;
    }

    public boolean purchaseItem(Item item){
        if(this.wealth >= item.getCost()) {
            items.add(item);
            this.wealth -= item.getCost();
            return true;
        }
        return false;
    }

    public boolean consumeItem(Item item) {
        int index = items.indexOf(item);
        if(index >= 0) {
            switch (item.getBoostType()) {
                case health:
                    this.currHP += item.getPower();
                    break;
                case energy:
                    this.currEnergy += item.getPower();
                    break;
                default:
                    this.baseAtk += item.getPower();
            }
            items.remove(index);
            return true;
        }
        return false;
    }
    public boolean sellItem(Item item) {
        int index = items.indexOf(item);
        if(index >= 0) {
            this.wealth += (int)(item.getCost() / 2);
            items.remove(index);
            return true;
        }
        return false;
    }

    public void rest(){
        this.currHP += (int)Math.floor(getMaxHealth() /2);
        this.currEnergy += (int)Math.floor(getMaxEnergy() /2);
        if(this.currHP > getMaxHealth()) {
            this.currHP = getMaxHealth();
        }
        if(this.currEnergy > getMaxEnergy()) {
            this.currEnergy = getMaxEnergy();
        }
    }

    public Spell getRandomUsableSpell() {
        ArrayList<Spell> usableSpells = new ArrayList<>();
        for(Spell spell: spells) {
            if(currEnergy >= spell.getCost() && spell.getLevel() <= this.getLevel()){
                usableSpells.add(spell);
            }
        }
        Random random = null;
        if(GameManager.loaded) {
            random = GameManager.seed;
        } else {
            random = new Random();
        }
        if(usableSpells.size() == 0) { return null; }
        return usableSpells.get(random.nextInt(usableSpells.size()));
    }

    public void attack(Character c) {
        c.setCurrHP(c.getCurrHP() - this.getCurrAtk());
    }
    public void attack(Character c, Spell spell) {
        c.setCurrHP(c.getCurrHP() - this.getSpellPower(spell));
    }
    public void attack(Mob mob) {
        mob.setHealth(mob.getHealth() - this.getCurrAtk());
    }
    public void attack(Mob mob, Spell spell) {
        mob.setHealth(mob.getHealth() - this.getSpellPower(spell));
    }
    public void restore(Spell spell) {
        switch(spell.getType()) {
            case energy: this.setCurrEnergy(this.getCurrEnergy() + this.getSpellPower(spell)); break;
            default: this.setCurrHP(this.getCurrHP() + this.getSpellPower(spell));
        }
    }
    public void restore(Character c, Spell spell) {
        switch(spell.getType()) {
            case energy: c.setCurrEnergy(c.getCurrEnergy() + this.getSpellPower(spell)); break;
            default: c.setCurrHP(c.getCurrHP() + this.getSpellPower(spell));
        }
    }
    public void restore(Mob mob, Spell spell) {
        switch(spell.getType()) {
            case health: mob.setHealth(mob.getHealth() + this.getSpellPower(spell)); break;
            default: ; // Do nothing
        }
    }
    public int getSpellPower(Spell spell) {
        return spell.getAtk_scale() * (this.getBoost(Item.ItemBoostType.attack) + this.getAtkPT() * getLevel())
                + spell.getHp_scale() * (this.getBoost(Item.ItemBoostType.health) + this.getHpPT() * getLevel()) +
                spell.getBase_atk() + spell.getAtkPL() * this.getLevel();
    }
    public Character cloneSelf(){
        Character newChar = new Character(this.baseHP,this.baseAtk, this.baseDef, this.baseEnergy, this.hpPT, this.atkPT, this.defPT, this.energyPT, this.exp,
                this.type, this.name, this.team);
        newChar.getSpells().addAll(this.spells);
        return newChar;
    }
}
