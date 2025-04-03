package ca.concordia.soen6441.project.context;

import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.interfaces.context.NeighborContext;

import java.io.Serializable;

/**
 * Manages all Neighbor operations
 */
public class NeighborManager implements NeighborContext, Serializable {
    private GameContext d_GameEngine;

    /**
     * Constructor
     * @param p_GameEngine GameContext object representing the GameEngine
     */
    public NeighborManager(GameContext p_GameEngine) {
        d_GameEngine = p_GameEngine;
    }

    /**
     * Adds a neighbor relationship between two countries.
     *
     * @param p_CountryID         The ID of the country.
     * @param p_neighborCountryID The ID of the neighboring country.
     */
    public void addNeighbor(String p_CountryID, String p_neighborCountryID) {
        d_GameEngine.getCountryManager().getCountries().get(p_CountryID).addNeighbor(d_GameEngine.getCountryManager().getCountries().get(p_neighborCountryID));
        d_GameEngine.getCountryManager().getCountries().get(p_neighborCountryID).addNeighbor(d_GameEngine.getCountryManager().getCountries().get(p_CountryID));
        System.out.println("Neighbor added: " + d_GameEngine.getCountryManager().getCountries().get(p_CountryID));
        System.out.println("Neighbor added: " + d_GameEngine.getCountryManager().getCountries().get(p_neighborCountryID));
    }

    /**
     * Removes a neighbor relationship between two countries.
     *
     * @param p_CountryID         The ID of the country.
     * @param p_neighborCountryID The ID of the neighboring country.
     */
    public void removeNeighbor(String p_CountryID, String p_neighborCountryID) {
        d_GameEngine.getCountryManager().getCountries().get(p_CountryID).removeNeighbor(p_neighborCountryID);
        d_GameEngine.getCountryManager().getCountries().get(p_neighborCountryID).removeNeighbor(p_CountryID);
    }
}
