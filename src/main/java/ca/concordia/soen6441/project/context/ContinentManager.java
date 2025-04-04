package ca.concordia.soen6441.project.context;

import ca.concordia.soen6441.project.OverallFactory;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.context.ContinentContext;

import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Class for managing Continent operations
 */
public class ContinentManager implements ContinentContext, Serializable {
    private SortedMap<String, Continent> d_Continents;

    /**
     * Constructor
     */
    public ContinentManager() {
        this.d_Continents = new TreeMap<String, Continent>();
    }

    /**
     * Adds a continent to the game map.
     *
     * @param p_numericID      The numeric ID of the continent.
     * @param p_continentID    The unique identifier for the continent.
     * @param p_continentValue The control value of the continent.
     * @param p_colour         The color associated with the continent.
     */
    @Override
    public void addContinent(int p_numericID, String p_continentID, int p_continentValue, String p_colour) {
        Continent l_continent = OverallFactory.getInstance().CreateContinent(p_numericID, p_continentID, p_continentValue, p_colour);
        d_Continents.put(l_continent.getID(), l_continent);
        System.out.println("Continent added: " + d_Continents.get(l_continent.getID()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addContinent(String p_continentID, int p_continentValue) {
        Continent l_continent = OverallFactory.getInstance().CreateContinent(p_continentID, p_continentValue);
        d_Continents.put(l_continent.getID(), l_continent);
        System.out.println("Continent added: " + d_Continents.get(l_continent.getID()));
    }

    /**
     * Removes a continent from the game map.
     *
     * @param p_continentID The unique identifier of the continent to be removed.
     */
    public void removeContinent(String p_continentID) {
        d_Continents.remove(p_continentID);
    }

    /**
     * Retrieves a continent by its numeric ID.
     *
     * @param p_numericIDOfContinent The numeric ID of the continent.
     * @return The continent if found, otherwise null.
     */
    @Override
    public Continent getContinentByNumericID(int p_numericIDOfContinent) {

        for (String l_key : d_Continents.keySet()) {
            if (d_Continents.get(l_key).getNumericID() == p_numericIDOfContinent) {
                return d_Continents.get(l_key); // found
            }
        }

        return null; // not found
    }

    /**
     * Retrieves a continent by its unique identifier.
     *
     * @param p_continentID The unique identifier of the continent.
     * @return The continent if found, otherwise null.
     */
    @Override
    public Continent getContinent(String p_continentID) {
        return d_Continents.get(p_continentID); // Retrieve the continent from the map
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedMap<String, Continent> getContinents() {
        return d_Continents;
    }

    /**
     * Clears the Continents stored
     */
    public void clear()
    {
        d_Continents.clear();
    }
}
