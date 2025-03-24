package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

/**
 * The Diplomacy order class represents a command issued by a player
 * to negotiate a non-aggression pact with another player for one turn.
 * Actual negotiation logic is handled elsewhere; this class simply logs the action.
 */
public class Diplomacy implements Order {
    /**
     * Executes the Diplomacy order.
     * Currently logs that the order was executed.
     */
    @Override
    public void execute() {
        LogEntryBuffer.getInstance().appendToBuffer("Diplomacy order executed.", true);
    }
}
