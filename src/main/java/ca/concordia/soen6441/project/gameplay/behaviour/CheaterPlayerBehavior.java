package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.gameplay.behavior.PlayerBehavior;

/**
 * Class which implements the Strategy design pattern for the Cheater player behavior
 */
public class CheaterPlayerBehavior implements PlayerBehavior {
    /**
     * {@inheritDoc}
     */
    @Override
    public void issue_order(Player p_player, Order p_order) {

    }

    @Override
    public String toString() {
        return "Cheater";
    }
}
