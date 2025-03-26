package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.interfaces.Country;

import java.util.List;
import java.util.SortedMap;

public interface CountryContext {
    // Required
    /**
     * Add country to the existing continent
     *
     * @param p_numericID    (int) The numeric ID of the continent.
     * @param p_CountryID    (String) The unique identifier (name) of the country.
     * @param p_continentID  (String) The unique identifier (name) of the continent.
     * @param p_xCoord       The x coordinate of the circle to mark the country
     * @param p_yCoord       The y coordinate of the circle to mark the country
     */
    void addCountry(int p_numericID, String p_CountryID, String p_continentID, int p_xCoord, int p_yCoord);

    /**
     * Add country to the existing continent
     *
     * @param p_CountryID (String) The unique identifier (name) of the country.
     * @param p_continentID (string) The unique identifier (name) of the continent.
     */
    void addCountry(String p_CountryID, String p_continentID);

    /**
     * Removes a country from the game map.
     *
     * @param p_countryID The unique identifier of the country to be removed.
     */
    void removeCountry(String p_countryID);

    // Extra

    /**
     * Provides country object for the mentioned numeric ID of the country
     *
     * @param p_numericIDOfCountry Numeric ID of the country
     * @return Country object for the mentioned numeric ID
     */
    Country getCountryByNumericID(int p_numericIDOfCountry);

    /**
     * Provides a list of all Country objects in a continent
     *
     * @param p_continentID the name of the continent
     * @return
     */
    List<Country> getCountriesOfContinent(String p_continentID);

    /**
     *Provides a sorted map of all the countries (name and country objects).
     *
     * @return a sorted map holding country name and its object
     */
    SortedMap<String, Country> getCountries();
}
