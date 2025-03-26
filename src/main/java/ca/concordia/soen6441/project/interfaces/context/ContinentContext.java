package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.interfaces.Continent;
import java.util.SortedMap;

public interface ContinentContext {
    /**
     * Adds a continent to the game map.
     *
     * @param p_numericID      The numeric ID of the continent.
     * @param p_continentID    The unique identifier for the continent.
     * @param p_continentValue The control value of the continent.
     * @param p_colour         The color associated with the continent.
     */
    void addContinent(int p_numericID, String p_continentID, int p_continentValue, String p_colour);

    /**
     * Add a new continent
     *
     * @param p_continentID      The unique identifier for the continent.
     * @param  p_continentValue  The control value of the continent.
     */
    void addContinent(String p_continentID, int p_continentValue);

    /**
     * Removes a continent from the game map.
     *
     * @param p_continentID The unique identifier of the continent to be removed.
     */
    void removeContinent(String p_continentID);

    // Extra

    /**
     * Returns a sorted map of continent names and their objects
     *
     * @return the sorted map containing continent name (key) and their object (value)
     */
    SortedMap<String, Continent> getContinents();

    /**
     * Provides the continent object based on numeric ID of the continent
     *
     * @param p_numericIDOfContinent Numeric ID of the continent
     * @return the continent object for the mentioned numeric ID
     */
    Continent getContinentByNumericID(int p_numericIDOfContinent);

    /**
     * Provides the continent object based on continent name
     *
     * @param p_continentID Name of the continent
     * @return the continent object for the mentioned continent name
     */
    Continent getContinent(String p_continentID);
}
