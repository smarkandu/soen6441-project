package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.context.hand.CardManager;
import ca.concordia.soen6441.project.gameplay.cards.AirliftCard;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.interfaces.context.HandOfCardsContext;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

/**
 * The Airlift order allows moving troops from one country to another,
 * even if they are not adjacent.
 */
public class Airlift implements Order {

    private Player d_player;
    private Country d_sourceCountry;
    private Country d_targetCountry;
    private int d_numArmies;
    private GameContext d_gameContext;

    /**
     * Constructor for Airlift order.
     *
     * @param p_sourceCountry the country from which to move troops
     * @param p_targetCountry the country to which to move troops
     * @param p_numArmies     the number of troops to move
     * @param p_player        the player issuing the order
     * @param p_gameContext   the current game context
     */
    public Airlift(Country p_sourceCountry, Country p_targetCountry, int p_numArmies,
                   Player p_player, GameContext p_gameContext) {
        this.d_sourceCountry = p_sourceCountry;
        this.d_targetCountry = p_targetCountry;
        this.d_numArmies = p_numArmies;
        this.d_player = p_player;
        this.d_gameContext = p_gameContext;
    }

    /**
     * Validates whether the airlift command is valid.
     *
     * @return error message if invalid, otherwise null
     */
    public String validate() {
        HandOfCardsContext l_cardManager = d_player.getHandOfCardsManager();
        CardManager<AirliftCard> l_airliftCardManager = l_cardManager.getAirLiftCardManager();

        if (!d_player.equals(d_sourceCountry.getOwner())) {
            return "ERROR: Player does not own the source country!";
        }

        if (!d_player.equals(d_targetCountry.getOwner())) {
            return "ERROR: Player does not own the target country!";
        }

        if (d_numArmies > d_sourceCountry.getTroops()) {
            return "ERROR: Not enough troops to airlift.";
        }

        if (l_airliftCardManager == null || !l_airliftCardManager.hasCard()) {
            return "ERROR: Player does not have an Airlift card!";
        }

        return null;
    }

    /**
     * Executes the Airlift order.
     */
    @Override
    public void execute() {
        String l_error = validate();
        if (l_error != null) {
            LogEntryBuffer.getInstance().appendToBuffer(l_error, true);
            return;
        }

        int l_troopsToMove = Math.min(d_numArmies, d_sourceCountry.getTroops());

        if (l_troopsToMove <= 0) {
            LogEntryBuffer.getInstance().appendToBuffer("No troops available to airlift.", true);
            return;
        }

        d_sourceCountry.setTroops(d_sourceCountry.getTroops() - l_troopsToMove);
        d_targetCountry.setTroops(d_targetCountry.getTroops() + l_troopsToMove);

        LogEntryBuffer.getInstance().appendToBuffer(d_player.getName()
                + " airlifted " + l_troopsToMove + " troops from "
                + d_sourceCountry.getID() + " to " + d_targetCountry.getID(), true);

        d_player.getHandOfCardsManager().getAirLiftCardManager().removeCard();
    }

    /**
     * Returns a string representation of the Airlift order.
     *
     * @return string describing the order
     */
    @Override
    public String toString() {
        return "{Airlift " + d_sourceCountry.getID() + " " + d_targetCountry.getID() + " " + d_numArmies + "}";
    }

}
