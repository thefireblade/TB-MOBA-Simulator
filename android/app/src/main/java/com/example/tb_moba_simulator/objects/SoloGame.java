package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;

public class SoloGame implements Game {
    private int turnCount;
    private Character player;
    private ArrayList<Character> ai;
    private ArrayList<Location> locations;
    private ArrayList<Location> startLocations;
    private ArrayList<Mob> mobs;
    private ArrayList<Item> shop;
    private String userEmail;
    public SoloGame(Character player,
                    ArrayList<Location> locations, ArrayList<Mob> mobs, String userEmail, ArrayList<Location> startLocations) {
        this.turnCount = 1;
        this.player = player;
        this.ai = new ArrayList<Character>();
        this.locations = locations;
        this.mobs = mobs;
        this.userEmail = userEmail;
        this.startLocations = startLocations;
        this.shop = new ArrayList<>();
    }
    @Override
    public void loadMap() {
        for(Location loc: locations){
            System.out.println(loc.getName());
        }
        System.out.println(userEmail);
    }

    @Override
    public int simulateTurn() {
        return 0;
    }

    @Override
    public boolean hasEnded() {
        return false;
    }

    @Override
    public void spawnMobs() {

    }

    @Override
    public boolean saveGame() {
        return false;
    }

    @Override
    public void addAI(Character c, Character.Team team) {
        int aiNum = ai.size() + 1;
        c.setName("AI " + aiNum);
        c.setTeam(team);
        ai.add(c);
    }

    @Override
    public void addPlayer(Character.Team team) {
        // Doesn't do anything for the purposes of a solo game
    }
    @Override
    public Character getCurrentPlayer(){
        return player;
    }

    @Override
    public int getTurnCount(){
        return turnCount;
    }
    @Override
    public boolean isPlayerAtBase(){
        for(Location location: startLocations) {
            if(location.getPlayers().indexOf(player) >= 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Item> getShop() {
        return shop;
    }
}
