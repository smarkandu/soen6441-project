package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.interfaces.Continent;
import java.util.SortedMap;

public interface ContinentContext {
    void addContinent(int p_numericID, String p_continentID, int p_continentValue, String p_colour);
    void addContinent(String p_continentID, int p_continentValue);
    void removeContinent(String p_continentID);

    // Extra
    SortedMap<String, Continent> getContinents();
    Continent getContinentByNumericID(int p_numericIDOfContinent);
    Continent getContinent(String p_continentID);
}
