package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

/**
 * This class represents bombing an adjacent enemy territory.
 */
public class Bomb implements Order {

    private final Country d_targetTerritory;
    private final Player d_initiator;

    public Bomb(Player p_initiator, Country p_targetTerritory) {
        this.d_targetTerritory = p_targetTerritory;
        this.d_initiator = p_initiator;
    }

    /**
     * Execute the order to bomb an enemy country
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
            int l_originalNumberOfTroops = d_targetTerritory.getTroops();
            d_targetTerritory.setTroops((int)(Math.floor(d_targetTerritory.getTroops()/2.0)));
            String l_resultMessage = d_initiator.getName() + " just dropped a bomb in " + d_targetTerritory.getID() + "! " +
                    d_targetTerritory.getOwner().getName() + "'s soldiers fell from " + l_originalNumberOfTroops + " to " +
                    d_targetTerritory.getTroops();

            LogEntryBuffer.getInstance().appendToBuffer( l_resultMessage, true);
        }
    }

    /**
     * Used to validate whether there are any issues prior to executing an Order
     * @return A string if an error occurs, null otherwise.
     */
    public String validate() {
        if (d_initiator.getOwnedCountries().contains(d_targetTerritory.getID())) {
            return "Error: Player cannot bomb his territory.";
        }
        if (!isTerritoryAdjacent(d_targetTerritory)) {
            return "Error: Player's territory is not adjacent to the target territory.";
        }
        if (d_targetTerritory.getTroops() == 0) {
            return "Error: Player cannot bomb a territory without troop.";
        }
        return null;
    }

    /**
     * Verify if one of the neighbors' countries belong to a particular player
     *
     * @param p_player string name of the player
     * 
     * @return boolean true if one of the neighbor's countries to be bombed belongs to the player
     */
    private boolean isTerritoryAdjacent(Country p_countryToBomb) {
        for(String l_neighbourCountry : p_countryToBomb.getNeighborIDs()) {
            if(d_initiator.getOwnedCountries().contains(l_neighbourCountry)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{Bomb " + d_targetTerritory.getID() + "}";
    }
}
