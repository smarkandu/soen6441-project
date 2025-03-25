package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.log.LogEntryBuffer;
import ca.concordia.soen6441.project.interfaces.Player;

/**
 * The Diplomacy order class represents a command issued by a player
 * to negotiate a non-aggression pact with another player for one turn.
 * When executed, the players will not attack each other during this turn.
 */
public class Diplomacy implements Order {

    private final Player d_initiator;
    private final Player d_target;

    /**
     * Constructor for Diplomacy Order.
     *
     * @param p_initiator The player issuing the diplomacy command.
     * @param p_target    The target player with whom the negotiation is made.
     */
    public Diplomacy(Player p_initiator, Player p_target) {
        this.d_initiator = p_initiator;
        this.d_target = p_target;
    }

    /**
     * Executes the Diplomacy order.
     * Actual non-aggression logic is handled in Advance order.
     */
    @Override
    public void execute() {
        d_initiator.addNegotiatedPlayer(d_target);
        d_target.addNegotiatedPlayer(d_initiator);
        
        LogEntryBuffer.getInstance().appendToBuffer(
            d_initiator.getName() + " has negotiated a non-aggression pact with " + d_target.getName() + ".",
            true
        );
    }

    @Override
    public String toString() {
        return "{Diplomacy with " + d_target.getName() + "}";
    }

    public Player getInitiator() {
        return d_initiator;
    }

    public Player getTarget() {
        return d_target;
    }
}
