package com.example.tb_moba_simulator.objects;

public class Item {
    public enum ItemBoostType {
        health, attack, energy, exp, wealth
    }
    private ItemBoostType boostType;
    private int cost;
    private int power;
    private String imgSrc; //Needs to be error handled
    private Item upgrade;
    private int upgradeCost;
    private boolean consumable;
    private boolean purchasable;
    private String name;

    public Item(String name, ItemBoostType type, int cost, int power, String imgSrc, Item upgrade, int upgradeCost,
                boolean consumable, boolean purchasable) {
        this.name = name;
        this.boostType = type;
        this.cost = cost;
        this.power = power;
        this.imgSrc = imgSrc;
        this.upgrade = upgrade;
        this.upgradeCost = upgradeCost;
        this.consumable = consumable;
        this.purchasable = purchasable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemBoostType getBoostType() {
        return boostType;
    }

    public void setBoostType(ItemBoostType boostType) {
        this.boostType = boostType;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Item getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(Item upgrade) {
        this.upgrade = upgrade;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    public boolean isConsumable() {
        return consumable;
    }

    public void setConsumable(boolean consumable) {
        this.consumable = consumable;
    }

    public boolean isPurchasable() {
        return purchasable;
    }

    public void setPurchasable(boolean purchasable) {
        this.purchasable = purchasable;
    }
}
