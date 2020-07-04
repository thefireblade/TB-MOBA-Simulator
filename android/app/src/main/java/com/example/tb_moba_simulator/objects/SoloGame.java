package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;

public class SoloGame implements Game {
    private int turnCount;
    private Character player;
    private ArrayList<Character> ai;
    private ArrayList<Location> locations;
    private ArrayList<Mob> mobs;
    private String userEmail;
    public SoloGame(Character player,
                    ArrayList<Location> locations, ArrayList<Mob> mobs, String userEmail) {
        this.turnCount = 0;
        this.player = player;
        this.ai = new ArrayList<Character>();
        this.locations = locations;
        this.mobs = mobs;
        this.userEmail = userEmail;
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
}
