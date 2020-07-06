/**
 * Jason Huang
 * 110779373
 */
package com.example.tb_moba_simulator.objects;

import java.util.ArrayList;

/**
 * Defense object to represent defensive structures on the map. It extends a Mob object.
 */
public class Defense extends Mob{
    /**
     * Basic constructor for the defense object
     * @param hp The health of the defense
     * @param atk The attack of the defense
     * @param reward The amount of wealth gained after destroying the defense
     * @param name The unique name of the defense
     * @param exp The experience gained after destroying the defense
     * @param team The team that the defense belongs to (Team enum)
     */
    public Defense(int hp, int atk, int reward, String name, int exp, Character.Team team) {
        super(hp, atk, new ArrayList<Item>(), 0.0, reward, name, exp, team);
    }

    /**
     * Clones the defense object
     * @return the cloned defense object
     */
    @Override
    public Defense cloneSelf(){
        return new Defense(this.health, this.attack, this.reward, this.name, this.exp, this.team);
    }
}
