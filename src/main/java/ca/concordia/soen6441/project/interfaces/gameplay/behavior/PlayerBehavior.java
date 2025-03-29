package ca.concordia.soen6441.project.interfaces.gameplay.behavior;

import ca.concordia.soen6441.project.interfaces.Order;

import java.util.List;

public interface PlayerBehavior {
    /**
     * Issues a new order for the player.
     * @param p_Orders The list of orders to add to
     * @param p_order The order to be issued.
     */
    void issue_order(List<Order> p_Orders, Order p_order);
}
