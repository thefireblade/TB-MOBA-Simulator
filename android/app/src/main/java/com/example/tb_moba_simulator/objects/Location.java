package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Location {
    private String name;
    private ArrayList<Location> connects;
    private ArrayList<Character> players;
    private ArrayList<Mob> entities;
    private boolean defenseLoc;
    private boolean hasDefense;
    private Character.Team team;
    public Location(String name, ArrayList<Location> connects, ArrayList<Character> players, boolean defenseLoc, Character.Team team) {
        this.name  = name;
        this.players = players;
        this.connects = connects;
        this.entities = new ArrayList<Mob>();
        this.defenseLoc = defenseLoc;
        this.hasDefense = false;
        this.team = team;
    }

    public boolean hasDefense() {
        return hasDefense;
    }

    public void setHasDefense(boolean hasDefense) {
        this.hasDefense = hasDefense;
    }

    public ArrayList<Mob> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Mob> entities) {
        this.entities = entities;
    }

    public void setPlayers(ArrayList<Character> players) {
        this.players = players;
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
    public boolean equals(Location loc) {
        return loc.getName().equals(this.getName());
    }
}
