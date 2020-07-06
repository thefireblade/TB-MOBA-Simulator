/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;

public class Defense extends Mob{
    public Defense(int hp, int atk, int reward, String name, int exp, Character.Team team) {
        super(hp, atk, new ArrayList<Item>(), 0.0, reward, name, exp, team);
    }
    @Override
    public Defense cloneSelf(){
        return new Defense(this.health, this.attack, this.reward, this.name, this.exp, this.team);
    }
}
