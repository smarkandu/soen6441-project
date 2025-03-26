package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.log.LogEntryBuffer;
import ca.concordia.soen6441.project.gameplay.cards.AirliftCard;


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
     */
    public Airlift(Country p_sourceCountry, Country p_targetCountry, int p_numArmies, Player p_player) {
        super(p_sourceCountry, p_targetCountry, p_numArmies, p_player);
        this.d_player = p_player;
        this.d_sourceCountry = p_sourceCountry;
        this.d_targetCountry = p_targetCountry;
        this.d_numArmies = p_numArmies;
    }

    /**
     * Validates whether the airlift command can be executed.
     *
     * @return true if valid, false otherwise.
     */
    public boolean validate() {
        if (!d_player.equals(d_sourceCountry.getOwner())) {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Player does not own the source country!", true);
            return false;
        }

        if (d_numArmies > d_sourceCountry.getTroops()) {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Not enough troops to airlift.", true);
            return false;
        }

        if (!d_player.getHandOfCardsManager().hasAirliftCard()) {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Player does not have an Airlift card!", true);
            return false;
        }

        return true;
    }

    /**
     * Executes the Airlift order by moving troops from the source country to the target country.
     */
    @Override
    public void execute() {
        if (!validate()) {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Airlift execution failed due to invalid conditions.", true);
            return;
        }

        int l_actualTroopsAirlift = Math.min(d_numArmies, d_sourceCountry.getTroops());
        d_sourceCountry.setTroops(d_sourceCountry.getTroops() - l_actualTroopsAirlift);

        if (l_actualTroopsAirlift == 0) {
            LogEntryBuffer.getInstance().appendToBuffer("No troops exist in " + d_sourceCountry.getID() +
                    " for " + d_player.getName() + " to airlift. Command cancelled.", true);
            return;
        }

        LogEntryBuffer.getInstance().appendToBuffer(d_player.getName() + " airlifted " + l_actualTroopsAirlift +
                " troops from " + d_sourceCountry.getID() + " to " + d_targetCountry.getID(), true);

        // Move troops to the target country
        d_targetCountry.setTroops(d_targetCountry.getTroops() + l_actualTroopsAirlift);

        // Remove the Airlift card from the player's hand
        d_player.getHandOfCardsManager().removeCard(new AirliftCard());
    }

    /**
     * Gets the number of armies being airlifted.
     *
     * @return The number of armies.
     */
    public int getNumArmies() {
        return d_numArmies;
    }
}
