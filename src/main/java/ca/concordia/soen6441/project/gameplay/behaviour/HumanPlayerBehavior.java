package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.gameplay.behavior.PlayerBehavior;

import java.util.List;

public class HumanPlayerBehavior implements PlayerBehavior {

    /**
     * {@inheritDoc}
     */
    @Override
    public void issue_order(List<Order> p_Orders, Order p_order) {
        p_Orders.add(p_order);
    }
}
