package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.gameplay.behavior.PlayerBehavior;

public abstract class ComputerPlayerBehavior implements PlayerBehavior {
    @Override
    public void issue_order(Player p_player, Order p_order) {
        deployment();
        attackTransfer();
    }

    /**
     * Issue deployment orders
     */
    public abstract void deployment();

    /**
     * Issue attack / transfer orders
     */
    public abstract void attackTransfer();
}
