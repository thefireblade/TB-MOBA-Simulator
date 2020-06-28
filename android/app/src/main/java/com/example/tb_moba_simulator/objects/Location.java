package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;

public class Location {
    private String name;
    private ArrayList<Location> connects;
    public Location(String name, ArrayList<Location> connects) {
        this.name  = name;
        this.connects = connects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Location> getConnects() {
        return connects;
    }

    public void setConnects(ArrayList<Location> connects) {
        this.connects = connects;
    }
}
