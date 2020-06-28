package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;

public interface Game {
    public void loadMap();
    public int simulateTurn(); // Returns turn number
    public boolean hasEnded();
    public void spawnMobs();
    public boolean saveGame();
    public void addAI(Character.Team team);
    public void addPlayer(Character.Team team);

}
