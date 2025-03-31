package ca.concordia.soen6441.project.interfaces.gameplay.behavior;

import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;

import java.util.List;

public interface PlayerBehavior {
    /**
     * Issues a new order for the player.
     * @param p_player The player to whom the order corresponds to
     * @param p_order The order to be issued.
     */
    void issue_order(Player p_player, Order p_order);
}
