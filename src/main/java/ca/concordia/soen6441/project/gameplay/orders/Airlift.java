package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;

/**
 * The Airlift order allows moving troops from one country to another,
 * even if they are not adjacent. It extends Advance but removes neighbor restrictions.
 */
public class Airlift extends Advance {
    private final Player d_player;
    private final int d_numArmies; // Store numArmies locally

    /**
     * Constructor for Airlift order.
     * @param p_sourceCountry The source country for airlift.
     * @param p_targetCountry The target country for airlift.
     * @param p_numArmies The number of armies to move.
     * @param p_player The player issuing the airlift.
     */
    public Airlift(Country p_sourceCountry, Country p_targetCountry, int p_numArmies, Player p_player) {
        super(p_sourceCountry, p_targetCountry, p_numArmies, p_player);
        this.d_player = p_player;
        this.d_numArmies = p_numArmies; // Store the value
    }

    /**
     * Validates whether the airlift command can be executed.
     * @return true if valid, false otherwise.
     */
    public boolean validate() {
        if (!d_player.equals(super.sourceCountry.getOwner())) {
            System.out.println("ERROR: Player does not own the source country!");
            return false;
        }


        if (getNumArmies() > super.sourceCountry.getTroops()) { // Using local getter method
            System.out.println("ERROR: Not enough troops to airlift.");
            return false;
        }

        return true;
    }

    /**
     * Gets the number of armies being airlifted.
     * @return The number of armies.
     */
    public int getNumArmies() {
        return d_numArmies;
    }
}
