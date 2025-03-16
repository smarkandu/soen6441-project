package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.interfaces.Country;

import java.util.List;
import java.util.SortedMap;

public interface CountryContext {
    // Required
    void addCountry(int p_numericID, String p_CountryID, String p_continentID, int p_xCoord, int p_yCoord);
    void addCountry(String p_CountryID, String p_continentID);
    void removeCountry(String p_countryID);

    // Extra
    Country getCountryByNumericID(int p_numericIDOfCountry);
    List<Country> getCountriesOfContinent(String p_continentID);
    SortedMap<String, Country> getCountries();
}
