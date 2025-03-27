package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.util.Random;

public class Blockade implements Order {
    private Country d_territory;
    private Player d_initiator;
    private GameContext d_gameEngine;

    public Blockade(Country p_territory, Player p_initiator, GameContext p_gameEngine) {
        this.d_territory = p_territory;
        this.d_initiator = p_initiator;
        this.d_gameEngine = p_gameEngine;
    }

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
     * Validates the Diplomacy order before execution.
     *
     * @return Null if valid; otherwise, an error message string.
     */
    public String validate() {
        if (!d_territory.getOwner().getName().equals(d_initiator.getName())) {
            return "Error: Player does not own the territory to create blockade on.";
        }

        return null;
    }

    /**
     * Get a String Representation representing this object
     * @return A String value
     */
    @Override
    public String toString() {
        return "{Blockade " + d_territory.getID() + "}";
    }
}
