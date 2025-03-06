package ca.concordia.soen6441.project.interfaces.context;

public interface ContinentContext {
    void addContinent(int p_numericID, String p_continentID, int p_continentValue, String p_colour);
    void addContinent(String p_continentID, int p_continentValue);
    void removeContinent(String p_continentID);
}
