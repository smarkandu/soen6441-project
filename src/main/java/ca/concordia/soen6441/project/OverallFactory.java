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

    public Continent CreateContinent(String p_ID, int p_Value) {
        return new ContinentImpl(p_ID, p_Value);
    }

    public Country CreateCountry(String p_CountryID, String p_continentID, List<String> p_neighbors) {
    Country l_country = new CountryImpl(p_CountryID, p_continentID);
    return l_country;
}

}