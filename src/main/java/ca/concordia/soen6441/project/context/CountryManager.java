package ca.concordia.soen6441.project.context;

import ca.concordia.soen6441.project.OverallFactory;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.context.CountryContext;
import ca.concordia.soen6441.project.interfaces.context.GameContext;

import java.util.*;

public class CountryManager implements CountryContext {
    private SortedMap<String, Country> d_Countries;
    private GameContext d_GameEngine;

    public CountryManager(GameContext p_GameEngine) {
        d_Countries = new TreeMap<String, Country>();
        d_GameEngine = p_GameEngine;
    }

    /**
     * Adds a country to the game map.
     *
     * @param p_numericID The numeric ID of the continent.
     * @param p_CountryID The unique identifier for the continent.
     */
    @Override
    public void addCountry(int p_numericID, String p_CountryID, String p_continentID, int p_xCoord, int p_yCoord) {
        Country l_country = OverallFactory.getInstance().CreateCountry(p_numericID, p_CountryID,
                d_GameEngine.getContinentManager().getContinents().get(p_continentID),
                p_xCoord, p_yCoord, d_GameEngine.getPlayerManager().getNeutralPlayer()); // neutral army by default
        l_country.setTroops(2); // Default value for neutral armies
        d_Countries.put(p_CountryID, l_country);
        System.out.println("Country added: " + d_Countries.get(l_country.getID()));
    }

    public void addCountry(String p_CountryID, String p_continentID) {
        Country l_country = OverallFactory.getInstance().CreateCountry(p_CountryID, d_GameEngine.getContinentManager().getContinents().get(p_continentID),
                d_GameEngine.getPlayerManager().getNeutralPlayer()); // neutral army by default
        l_country.setTroops(2); // Default value for neutral armies
        d_Countries.put(p_CountryID, l_country);
        System.out.println("Country added: " + d_Countries.get(l_country.getID()));
    }

    /**
     * Removes a country from the game map.
     *
     * @param p_countryID The unique identifier of the country to be removed.
     */
    @Override
    public void removeCountry(String p_countryID) {
        d_Countries.remove(p_countryID);
    }

    @Override
    public Country getCountryByNumericID(int p_numericIDOfCountry) {

        for (String l_key : d_Countries.keySet()) {
            if (d_Countries.get(l_key).getNumericID() == p_numericIDOfCountry) {
                return d_Countries.get(l_key); // found
            }
        }

        return null; // not found
    }

    /**
     * Retrieves all countries belonging to a specified continent.
     *
     * @param p_continentID The unique identifier of the continent.
     * @return A list of countries within the specified continent.
     */
    @Override
    public List<Country> getCountriesOfContinent(String p_continentID) {
        List<Country> l_countries = new ArrayList<>();
        for (Country l_country : d_Countries.values()) {
            if (l_country.getContinent().getID().equals(p_continentID)) {
                l_countries.add(l_country);
            }
        }
        return l_countries;
    }

    public SortedMap<String, Country> getCountries() {
        return d_Countries;
    }

    public void clear()
    {
        d_Countries.clear();
    }
}
