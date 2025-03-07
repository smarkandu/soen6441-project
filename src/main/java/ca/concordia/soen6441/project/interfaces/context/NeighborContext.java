package ca.concordia.soen6441.project.interfaces.context;

public interface NeighborContext {
    void addNeighbor(String p_CountryID, String p_neighborCountryID);
    void removeNeighbor(String p_neighborCountryID, String p_countryToAdd);
}
