package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;

public class Location {
    private String name;
    private ArrayList<Location> connects;
    private ArrayList<Character> players;
    private ArrayList<Mob> entities;
    private boolean defenseLoc;
    private Character.Team team;
    public Location(String name, ArrayList<Location> connects, ArrayList<Character> players, boolean defenseLoc, Character.Team team) {
        this.name  = name;
        this.players =players;
        this.connects = connects;
        this.entities = new ArrayList<>();
        this.defenseLoc = defenseLoc;
        this.team = team;
    }

    public ArrayList<Character> getPlayers() {
        return players;
    }

    public boolean isDefenseLoc() {
        return defenseLoc;
    }

    public void setDefenseLoc(boolean defenseLoc) {
        this.defenseLoc = defenseLoc;
    }

    public Character.Team getTeam() {
        return team;
    }

    public void setTeam(Character.Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addConnection(Location location) {
        connects.add(location);
    }
    public void addPlayer(Character c) {
        players.add(c);
    }
    public void removePlayer(Character c) {
        players.remove(c);
    }
    public ArrayList<Location> getConnects() {
        return connects;
    }

    public void setConnects(ArrayList<Location> connects) {
        this.connects = connects;
    }
}
