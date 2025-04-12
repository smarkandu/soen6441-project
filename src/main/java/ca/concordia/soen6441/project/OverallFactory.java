package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.map.ContinentImpl;
import ca.concordia.soen6441.project.map.CountryImpl;
import ca.concordia.soen6441.project.phases.Startup;

/**
 * Factory class for creating Continent and Country objects.
 */
public class OverallFactory
{
    private static OverallFactory instance = null;

    /**
     * Private constructor to prevent direct instantiation.
     */
    private OverallFactory()
    {
    }

    /**
     * Returns the singleton instance of OverallFactory.
     *
     * @return The singleton instance of OverallFactory.
     */
    public static OverallFactory getInstance()
    {
        if (instance == null)
        {
            instance = new OverallFactory();
        }

        return instance;
    }

    /**
     * To set the instance (usually for mocking)
     *
     * @param p_instance an OverallFactory object, or null (to reset)
     */
    public static void setInstance(OverallFactory p_instance)
    {
        instance = p_instance;
    }

    /**
     * Creates a Continent instance with numeric ID, name, value, and color.
     *
     * @param p_numericID Numeric ID of the continent.
     * @param p_ID        String ID of the continent.
     * @param p_Value     Control value of the continent.
     * @param p_colour    Color representation of the continent.
     * @return A new Continent instance.
     */
    public Continent CreateContinent(int p_numericID, String p_ID, int p_Value, String p_colour)
    {
        return new ContinentImpl(p_numericID, p_ID, p_Value, p_colour);
    }

    /**
     * Creates a Continent instance with name and control value.
     *
     * @param p_ID    String ID of the continent.
     * @param p_Value Control value of the continent.
     * @return A new Continent instance.
     */
    public Continent CreateContinent(String p_ID, int p_Value)
    {
        return new ContinentImpl(p_ID, p_Value);
    }

    /**
     * Creates a Country instance with numeric ID, name, associated continent, and coordinates.
     *
     * @param p_numericID Numeric ID of the country.
     * @param p_ID        String ID of the country.
     * @param p_Continent Continent to which the country belongs.
     * @param p_xCoord    X coordinate of the country.
     * @param p_yCoord    Y coordinate of the country.
     * @param p_troops    Number of troops
     * @return A new Country instance.
     */
    public Country CreateCountry(int p_numericID, String p_ID, Continent p_Continent, int p_xCoord, int p_yCoord, Player p_owner, int p_troops)
    {
        return new CountryImpl(p_numericID, p_ID, p_Continent, p_xCoord, p_yCoord, p_owner, p_troops);
    }

    /**
     * Creates a Country instance with name and associated continent.
     *
     * @param p_ID        String ID of the country.
     * @param p_Continent Continent to which the country belongs.
     * @param p_troops    Number of troops
     * @return A new Country instance.
     */
    public Country CreateCountry(String p_ID, Continent p_Continent, Player p_owner, int p_troops)
    {
        return new CountryImpl(p_ID, p_Continent, p_owner, p_troops);
    }

    /**
     * Create a new Startup Phase object
     *
     * @return Startup object
     */
    public Startup CreateStartup()
    {
        return new Startup();
    }

    /**
     * Create a new GameEngine object
     *
     * @return GameEngine object
     */
    public GameEngine CreateGameEngine()
    {
        return new GameEngine();
    }
}