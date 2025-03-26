package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;

/**
 * Implementation of the Order interface.
 * Represents a bomb card in the game with its properties and behaviors.
 */
public class Bomb implements Order {

    private final Country d_targetTerritory;

    public Bomb(Country p_targetTerritory) {
        this.d_targetTerritory = p_targetTerritory;
    }

    /**
     * Execute the order to bomb an enemy country
     */
    @Override
    public void execute() {
        // TODO #67
        // take half of the enemy army round down
        // does not change the ownership of the territory
        d_targetTerritory.setTroops((int)(Math.floor(d_targetTerritory.getTroops()/2.0)));
    }

    @Override
    public String toString() {
        return "{Bomb " + d_targetTerritory.getID() + "}";
    }
}
