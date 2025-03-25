/**
 * The {@code Diplomacy} class implements the diplomacy order in the Warzone-style game.
 * This order represents a non-aggression pact between two players for the current turn.
 * When the order is executed, both players are marked as having a diplomacy agreement,
 * and they will not be able to attack each other until the next turn.
 * <p>
 * The diplomacy card is consumed upon execution.
 * The validate method ensures that the diplomacy action is legal.
 */
package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.log.LogEntryBuffer;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.context.HandOfCardsManager;

public class Diplomacy implements Order {

    /**
     * The player who issued the diplomacy order.
     */
    private final Player d_initiator;
    /**
     * The player who is the target of the diplomacy agreement.
     */
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
     * <p>
     * Steps:
     *     <ul>
     *         <li>Validates the order using {@code validate()} method.</li>
     *         <li>If valid, adds each player to the other's list of negotiated players.</li>
     *         <li>Consumes the diplomacy card using {@code useDiplomacyCard()}.</li>
     *         <li>Logs the event to the log buffer.</li>
     *     </ul>
     * </p>
     */
    @Override
    public void execute() {
        String l_validationResult = validate();
        if (l_validationResult != null) {
            LogEntryBuffer.getInstance().appendToBuffer(l_validationResult, true);
            return;
        }

        d_initiator.addNegotiatedPlayer(d_target);
        d_target.addNegotiatedPlayer(d_initiator);

        ((HandOfCardsManager) d_initiator.getHandOfCardsManager()).useDiplomacyCard();

        LogEntryBuffer.getInstance().appendToBuffer(
                d_initiator.getName() + " has negotiated a non-aggression pact with " + d_target.getName() + ".",
                true
        );


    }

    /**
     * Validates the Diplomacy order before execution.
     *
     * @return Null if valid; otherwise, an error message string.
     */
    public String validate() {
        if (d_target == null) {
            return "Error: Target player does not exist.";
        }
        if (d_initiator.equals(d_target)) {
            return "Error: Cannot negotiate with yourself.";
        }
        if (!d_initiator.getHandOfCardsManager().hasDiplomacyCard()) {
            return "Error: " + d_initiator.getName() + " does not have a diplomacy card.";
        }
        return null;
    }

    /**
     * Returns a readable string representation of the Diplomacy order.
     *
     * @return A string describing the diplomacy action.
     */
    @Override
    public String toString() {
        return "{Diplomacy with " + d_target.getName() + "}";
    }

    /**
     * Gets the player who initiated the diplomacy.
     *
     * @return The initiating player.
     */
    public Player getInitiator() {
        return d_initiator;
    }

    /**
     * Gets the target player of the diplomacy.
     *
     * @return The target player.
     */
    public Player getTarget() {
        return d_target;
    }
}
