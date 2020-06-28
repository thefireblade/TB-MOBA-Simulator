package com.example.tb_moba_simulator.objects;

public class Defense {
    private int exp;
    private int health;
    private int reward;
    private String name; // Same as defense type
    private Character.Team team;

    public Defense(int exp, int health, int reward, String name, Character.Team team) {
        this.exp = exp;
        this.health = health;
        this.name = name;
        this.team = team;
    }
}
