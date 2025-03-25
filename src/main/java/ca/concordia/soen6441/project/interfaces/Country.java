package ca.concordia.soen6441.project.interfaces;

import java.util.List;

/**
 * Represents a country in the game play
 */
public interface Country {
    /**
     * Gets the country ID.
     *
     * @return Country ID as a string.
     */
    String getID();

    /**
     * Retrieves the list of neighbor country IDs.
     *
     * @return List of neighboring country IDs.
     */
    List<String> getNeighborIDs();

    /**
     * Adds a neighboring country.
     *
     * @param p_Neighbor The country to be added as a neighbor.
     */
    void addNeighbor(Country p_Neighbor);

    /**
     * Removes a neighbor by its ID.
     *
     * @param p_NeighborID The ID of the neighbor to be removed.
     */
    void removeNeighbor(String p_NeighborID);

    /**
     * Gets the numeric ID of the country.
     *
     * @return Numeric ID of the country.
     */
    int getNumericID();

    /**
     * Formats country details in the Domination format.
     *
     * @return Formatted string representing the country.
     */
    String toMapString();

    /**
     * Gets the number of troops in the country.
     *
     * @return Number of troops.
     */
    int getTroops();

    /**
     * Sets the number of troops in the country.
     *
     * @param p_troops Number of troops to be set.
     */
    void setTroops(int p_troops);

    /**
     * Gets the owner of the country.
     *
     * @return The player who owns the country.
     */
    Player getOwner();

    /**
     * Sets the owner of the country.
     *
     * @param p_owner The player to be set as the owner.
     */
    void setOwner(Player p_owner);

    /**
     * Gets the continent of the country.
     *
     * @return Continent to which the country belongs.
     */
    Continent getContinent();
}
