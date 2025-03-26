package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.HandOfCardsContext;
import ca.concordia.soen6441.project.log.LogEntryBuffer;
import ca.concordia.soen6441.project.gameplay.cards.AirliftCard;
import ca.concordia.soen6441.project.context.hand.CardManager;
import ca.concordia.soen6441.project.interfaces.context.GameContext;

/**
 * The Airlift order allows moving troops from one country to another,
 * even if they are not adjacent. It extends Advance but removes neighbor restrictions.
 */
public class Airlift extends Advance {
    private final Player d_player;
    private final Country d_sourceCountry;
    private final Country d_targetCountry;
    private int d_numArmies;

    /**
     * Constructor for Airlift order.
     *
     * @param p_sourceCountry The source country for airlift.
     * @param p_targetCountry The target country for airlift.
     * @param p_numArmies The number of armies to move.
     * @param p_player The player issuing the airlift.
     * @param p_gameContext The game context.
     */
    public Airlift(Country p_sourceCountry, Country p_targetCountry, int p_numArmies, Player p_player, GameContext p_gameContext) {
        super(p_sourceCountry, p_targetCountry, p_numArmies, p_player, p_gameContext);
        this.d_player = p_player;
        this.d_sourceCountry = p_sourceCountry;
        this.d_targetCountry = p_targetCountry;
        this.d_numArmies = p_numArmies;
    }

    /**
     * Validates whether the airlift command can be executed.
     *
     * @return Error message if invalid, or null if valid.
     */
    @Override
    public String validate() {
        HandOfCardsContext cardManager = d_player.getHandOfCardsManager();
        CardManager<AirliftCard> airliftCardManager = cardManager.getAirLiftCardManager();

        if (!d_player.equals(d_sourceCountry.getOwner())) {
            return "ERROR: Player does not own the source country!";
        }

        if (!d_player.equals(d_targetCountry.getOwner())) {
            return "ERROR: Player does not own the target country!";
        }

        if (d_numArmies > d_sourceCountry.getTroops()) {
            return "ERROR: Not enough troops to airlift.";
        }

        if (airliftCardManager == null || !airliftCardManager.hasCard()) {
            return "ERROR: Player does not have an Airlift card!";
        }

        return null; // No errors, validation successful
    }

    /**
     * Executes the Airlift order by moving troops from the source country to the target country.
     */
    @Override
    public void execute() {
        String validationError = validate();
        if (validationError != null) {
            LogEntryBuffer.getInstance().appendToBuffer(validationError, true);
            return;
        }

        int l_actualTroopsAirlift = Math.min(d_numArmies, d_sourceCountry.getTroops());
        if (l_actualTroopsAirlift == 0) {
            LogEntryBuffer.getInstance().appendToBuffer("No troops exist in " + d_sourceCountry.getID() +
                    " for " + d_player.getName() + " to airlift. Command cancelled.", true);
            return;
        }

        // Move troops from source to target
        d_sourceCountry.setTroops(d_sourceCountry.getTroops() - l_actualTroopsAirlift);
        d_targetCountry.setTroops(d_targetCountry.getTroops() + l_actualTroopsAirlift);

        LogEntryBuffer.getInstance().appendToBuffer(d_player.getName() + " airlifted " + l_actualTroopsAirlift +
                " troops from " + d_sourceCountry.getID() + " to " + d_targetCountry.getID(), true);

        // Remove the Airlift card from the player's hand
        d_player.getHandOfCardsManager().getAirLiftCardManager().removeCard();
    }
}
