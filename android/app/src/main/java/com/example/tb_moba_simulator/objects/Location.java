/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Location class that will make up the map. Currently implements an adjacency list 'connects' and implements breadth first search.
 */
public class Location {
    private String name;
    private ArrayList<Location> connects;
    private ArrayList<Character> players;
    private ArrayList<Mob> entities;
    private boolean defenseLoc;
    private boolean hasDefense;
    private Character.Team team;

    /**
     * The constructer for a location object
     * @param name The name of the location
     * @param connects The locations that the location connects to
     * @param players The players in the location
     * @param defenseLoc Can the location have a defensive structure?
     * @param team The team that the location belongs to
     */
    public Location(String name, ArrayList<Location> connects, ArrayList<Character> players, boolean defenseLoc, Character.Team team) {
        this.name  = name;
        this.players = players;
        this.connects = connects;
        this.entities = new ArrayList<Mob>();
        this.defenseLoc = defenseLoc;
        this.hasDefense = false;
        this.team = team;
    }
    /** Getter and Setter Methods for fields of Location */
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
