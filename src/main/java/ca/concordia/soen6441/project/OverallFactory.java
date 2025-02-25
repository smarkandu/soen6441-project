package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Command;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.GameContext;

import java.util.List;
import java.util.TreeMap;

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

    public Country CreateCountry(int p_numericID, String p_ID, String p_ContinentID) {
        return new CountryImpl(p_numericID, p_ID, p_ContinentID);
    }

    public Country CreateCountry(String p_ID, String p_ContinentID) {
        return new CountryImpl(p_ID, p_ContinentID);
    }
}