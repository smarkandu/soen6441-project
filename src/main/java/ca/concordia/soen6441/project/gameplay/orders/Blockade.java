package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

/**
 * Represents a Blockade order in the game.
 * When executed, the territory's troops are tripled and the ownership is transferred to the neutral player.
 */
public class Blockade implements Order {

    /** The country/territory where the blockade is applied. */
    private Country d_territory;

    /** The player who issues the blockade order. */
    private Player d_initiator;

    /** Reference to the game engine context. */
    private GameContext d_gameEngine;

    /**
     * Constructs a new Blockade order.
     *
     * @param p_territory The country where the blockade will be applied.
     * @param p_initiator The player issuing the order.
     * @param p_gameEngine The game context used to update the game state.
     */
    public Blockade(Country p_territory, Player p_initiator, GameContext p_gameEngine) {
        this.d_territory = p_territory;
        this.d_initiator = p_initiator;
        this.d_gameEngine = p_gameEngine;
    }

    /**
     * Executes the Blockade order. If validation fails, logs an error message.
     * Otherwise, the territory becomes neutral and its troop count is tripled.
     */
    @Override
    public void execute() {
        String l_errorMsg = null;
        if ((l_errorMsg = validate()) != null)
        {
            // Error found, write to screen and add to log
            LogEntryBuffer.getInstance().appendToBuffer(l_errorMsg, true);
        }
        else {
            // Assign Country as Neutral
            d_gameEngine.assignCountryToPlayer(d_territory, d_gameEngine.getPlayerManager().getNeutralPlayer());

            // Change number of troops to new amount, per specs
            int l_oldTroopNumber = d_territory.getTroops();
            d_territory.setTroops(l_oldTroopNumber * 3);

            LogEntryBuffer.getInstance().appendToBuffer("Blockade created in " + d_territory.getID() + " by " + d_initiator.getName()
                    + ".  Country becomes neutral and troops have increased from " + l_oldTroopNumber + " to " + d_territory.getTroops(), true);
        }
    }

    /**
     * Validates the Blockade order before execution.
     *
     * @return Null if the order is valid; otherwise, an error message.
     */
    public String validate() {
        if (!d_territory.getOwner().getName().equals(d_initiator.getName())) {
            return "Error: Player does not own the territory to create blockade on.";
        }
        return null;
    }

    /**
     * Returns a string representation of the Blockade order.
     *
     * @return A string identifying the order and the target territory.
     */
    @Override
    public String toString() {
        return "{Blockade " + d_territory.getID() + "}";
    }
}
