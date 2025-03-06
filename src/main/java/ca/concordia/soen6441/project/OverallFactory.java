package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.map.ContinentImpl;
import ca.concordia.soen6441.project.map.CountryImpl;

public class OverallFactory {
    private static OverallFactory instance = null;

    private OverallFactory() {
    }

    public static OverallFactory getInstance() {
        if (instance == null) {
            instance = new OverallFactory();
        }

        return instance;
    }

    public Continent CreateContinent(int p_numericID, String p_ID, int p_Value, String p_colour) {
        return new ContinentImpl(p_numericID, p_ID, p_Value, p_colour);
    }

    public Continent CreateContinent(String p_ID, int p_Value) {
        return new ContinentImpl(p_ID, p_Value);
    }

    public Country CreateCountry(int p_numericID, String p_ID, Continent p_Continent, int p_xCoord, int p_yCoord) {
        return new CountryImpl(p_numericID, p_ID, p_Continent, p_xCoord, p_yCoord);
    }

    public Country CreateCountry(String p_ID, Continent p_Continent) {
        return new CountryImpl(p_ID, p_Continent);
    }
}