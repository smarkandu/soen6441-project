package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;

/**
 * Class which implements the Strategy design pattern for the Cheater player behavior
 */
public class CheaterPlayerBehavior extends ComputerPlayerBehavior {
    /**
     * {@inheritDoc}
     */
    @Override
    public void deployment(Player p_player, Order p_order) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attackTransfer(Player p_player, Order p_order) {

    }

    /**
     * String representing the object
     */
    @Override
    public String toString() {
        return "Cheater";
    }
}
