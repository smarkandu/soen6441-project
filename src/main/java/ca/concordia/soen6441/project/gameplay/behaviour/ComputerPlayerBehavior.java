package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.gameplay.behavior.PlayerBehavior;

public abstract class ComputerPlayerBehavior implements PlayerBehavior {
    @Override
    public void issue_order(Player p_player, Order p_order) {
        deployment(p_player, p_order);
        attackTransfer(p_player, p_order);
    }

    /**
     * Issue deployment orders
     * @param p_player The player to whom the order corresponds to
     * @param p_order The order to be issued.
     */
    public abstract void deployment(Player p_player, Order p_order);

    /**
     * Issue attack / transfer orders
     * @param p_player The player to whom the order corresponds to
     * @param p_order The order to be issued.
     */
    public abstract void attackTransfer(Player p_player, Order p_order);
}
