package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.gameplay.behavior.PlayerBehavior;

/**
 * Class which implements the Strategy design pattern for the Human player behavior
 */
public class HumanPlayerBehavior implements PlayerBehavior {
    /**
     * {@inheritDoc}
     */
    @Override
    public void issue_order(Player p_player, Order p_order) {
        p_player.getOrders().add(p_order);
    }

    /**
     * String representing the object
     */
    @Override
    public String toString() {
        return "Human";
    }
}
