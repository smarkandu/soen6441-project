package ca.concordia.soen6441.project.interfaces;

import java.util.List;

public interface GameContext {
    void addContinent(int p_numericID, String p_continentID, int p_continentValue, String p_colour);
    void addContinent(String p_continentID, int p_continentValue);
    void addCountry(int p_numericID, String p_CountryID, String p_continentID, int p_xCoord, int p_yCoord);
    void addCountry(String p_CountryID, String p_continentID);
    void addNeighbor(String p_CountryID, String p_neighborCountryID);
    void removeContinent(String p_continentID);
    void removeCountry(String p_countryID);
    void removeNeighbor(String p_neighborCountryID, String p_countryToAdd);
    Continent getContinentByNumericID(int p_numericIDOfContinent);
    Country getCountryByNumericID(int p_numericIDOfCountry);
    void showMap();
    void validateMap();
}
