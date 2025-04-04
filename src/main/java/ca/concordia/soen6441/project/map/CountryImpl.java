package ca.concordia.soen6441.project.map;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.MapComponent;
import ca.concordia.soen6441.project.interfaces.Player;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Implementation of the Country interface.
 * Represents a country in the game with its properties and behaviors.
 */

public class CountryImpl implements Country, MapComponent, Serializable {
    private final String d_ID;
    private final int d_xCoord;
    private final int d_yCoord;
    private final Continent d_Continent;
    private TreeMap<String, Country> d_Neighbors;
    private static int d_Counter = 0;
    private final int d_numericID;
    private int d_troops;
    private Player d_owner;

    /**
     * Constructs a new Country object with the specified parameter
     *
     * @param p_numericID Numeric ID of the country
     * @param p_ID        String ID of the country.
     * @param p_Continent Continent to which the country belongs.
     * @param p_xCoord    X coordinate of the country.
     * @param p_yCoord    Y coordinate of the country.
     * @param p_owner Player object representing the owner of the country
     */
    public CountryImpl(int p_numericID, String p_ID, Continent p_Continent, int p_xCoord, int p_yCoord, Player p_owner) {
        this.d_ID = p_ID;
        this.d_xCoord = p_xCoord;
        this.d_yCoord = p_yCoord;
        d_Continent = p_Continent;
        d_Neighbors = new TreeMap<String, Country>();
        this.d_numericID = p_numericID;
        if (p_numericID > d_Counter) {
            d_Counter = p_numericID;
        }
        this.d_owner = p_owner;
    }

    /**
     * Constructor to initialize a country without coordinates.
     *
     * @param p_ID        String ID of the country.
     * @param p_Continent Continent to which the country belongs.
     * @param p_owner Player object representing the owner
     */
    public CountryImpl(String p_ID, Continent p_Continent, Player p_owner) {
        this.d_ID = p_ID;
        this.d_xCoord = 0; // Hardcoded
        this.d_yCoord = 0; // Hardcoded
        d_Continent = p_Continent;
        d_Neighbors = new TreeMap<String, Country>();
        this.d_numericID = ++d_Counter;
        d_owner = p_owner;
    }

    /**
     * Gets the country ID.
     *
     * @return Country ID as a string.
     */
    @Override
    public String getID() {
        return d_ID;
    }

    /**
     * Retrieves the list of neighbor country IDs.
     *
     * @return List of neighboring country IDs.
     */
    @Override
    public List<String> getNeighborIDs() {
        return d_Neighbors.values().stream().map(Country::getID)
                .collect(Collectors.toList());
    }

    /**
     * Adds a neighboring country.
     *
     * @param p_Neighbor The country to be added as a neighbor.
     */
    @Override
    public void addNeighbor(Country p_Neighbor) {
        d_Neighbors.put(p_Neighbor.getID(), p_Neighbor);
    }

    /**
     * Removes a neighbor by its ID.
     *
     * @param p_NeighborID The ID of the neighbor to be removed.
     */
    @Override
    public void removeNeighbor(String p_NeighborID) {
        d_Neighbors.remove(p_NeighborID);
    }

    /**
     * Gets the numeric ID of the country.
     *
     * @return Numeric ID of the country.
     */
    @Override
    public int getNumericID() {
        return d_numericID;
    }

    /**
     * Converts the country object to a string representation.
     *
     * @return String representation of the country.
     */
    @Override
    public String toString() {
        String l_NeighborIDsAsString = d_Neighbors.values().stream().map(Country::getID)
                .collect(Collectors.joining(" "));

        return d_ID + ',' + d_xCoord +
                "," + d_yCoord +
                "," + d_Continent.getID() + (!l_NeighborIDsAsString.isEmpty() ?
                "," + l_NeighborIDsAsString : "");
    }

    /**
     * Gets the number of troops in the country.
     *
     * @return Number of troops.
     */
    public int getTroops() {
        return d_troops;
    }

    /**
     * Sets the number of troops in the country.
     *
     * @param p_troops Number of troops to be set.
     */
    @Override
    public void setTroops(int p_troops) {
        this.d_troops = p_troops;  // ✅ Assign troops correctly
    }

    /**
     * Gets the owner of the country.
     *
     * @return The player who owns the country.
     */
    @Override
    public Player getOwner() {
        return d_owner;
    }

    /**
     * Sets the owner of the country.
     *
     * @param p_owner The player to be set as the owner.
     */
    @Override
    public void setOwner(Player p_owner) {
        if (d_owner != null)
        {
            d_owner.getOwnedCountries().remove(this.getID());
        }
        d_owner = p_owner;
    }

    /**
     * Resets the country ID counter.
     */
    public static void resetCounter() {
        d_Counter = 0;
    }

    /**
     * Formats country details in the Domination format.
     *
     * @return Formatted string representing the country.
     */
    @Override
    public String toMapString() {
        // Formats country details in the Domination format:
        return d_numericID + " " + d_ID + " " + d_Continent.getNumericID() + " " + d_xCoord + " " + d_yCoord;
    }

    /**
     * Gets the continent of the country.
     *
     * @return Continent to which the country belongs.
     */
    public Continent getContinent() {
        return d_Continent;
    }
}
