package ca.concordia.soen6441.project.interfaces.context;

public interface NeighborContext {
    /**
     * Adds a neighbor relationship between two countries.
     *
     * @param p_CountryID         The ID of the country.
     * @param p_neighborCountryID The ID of the neighboring country.
     */
    void addNeighbor(String p_CountryID, String p_neighborCountryID);

    /**
     * Removes a neighbor relationship between two countries.
     *
     * @param p_neighborCountryID The ID of the country.
     * @param p_countryToAdd      The ID of the neighboring country.
     */
    void removeNeighbor(String p_neighborCountryID, String p_countryToAdd);
}
