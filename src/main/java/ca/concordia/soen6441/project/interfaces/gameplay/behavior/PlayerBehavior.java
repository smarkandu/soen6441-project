package ca.concordia.soen6441.project.interfaces.gameplay.behavior;

import ca.concordia.soen6441.project.interfaces.Player;

public interface PlayerBehavior {
    /**
     * Issues a new order for the player.
     * @param p_player The player to whom the order corresponds to
     */
    void issue_order(Player p_player);
}
