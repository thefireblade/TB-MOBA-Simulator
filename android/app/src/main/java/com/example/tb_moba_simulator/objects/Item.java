/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator.objects;

/**
 * Item class that will contain all the necessary information to construct an item object.
 */
public class Item {
    /**
     * The different types of boost of an item.
     */
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

    /**
     * Constructs an item of certain characteristics.
     * @param name Name of the item
     * @param type The boost type of the item
     * @param cost The amount of wealth used to buy the item
     * @param power The power of the item
     * @param imgSrc The image source that the item is linked to
     * @param upgrade The upgrade path of the item (The child of the item)
     * @param upgradeCost The cost to upgrade the item to the child
     * @param consumable Is the item consumable?
     * @param purchasable Is the item purchasable?
     */
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
    /** Getter and Setter Methods for Item fields */
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
