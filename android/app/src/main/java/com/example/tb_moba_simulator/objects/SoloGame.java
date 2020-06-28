package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;

public class SoloGame implements Game {
    private int turnCount;
    private Character player;
    private ArrayList<Character> ai;
    private ArrayList<Location> locations;
    private String landType;
    private ArrayList<Mob> mobs;
    private String userEmail;
    public SoloGame(int turnCount, Character player,
                    ArrayList<Location> locations, ArrayList<Mob> mobs,
                    String landType, String userEmail) {
        this.turnCount = turnCount;
        this.player = player;
        this.ai = new ArrayList<Character>();
        this.locations = locations;
        this.mobs = mobs;
        this.landType = landType;
        this.userEmail = userEmail;
    }
    @Override
    public void loadMap() {

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
    public void addAI(Character.Team team) {

    }

    @Override
    public void addPlayer(Character.Team team) {
        // Doesn't do anything for the purposes of a solo game
    }
}
