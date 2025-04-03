package ca.concordia.soen6441.project.map;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.ValidateMap;

import java.io.Serializable;
import java.util.*;

/**
 * Implementation of the ValidateMap interface.
 * Provides methods to check the validity of the map,
 * ensuring that all countries are connected and properly assigned to continents.
 */
public class ValidateMapImpl implements ValidateMap, Serializable {

    private SortedMap<String, Continent> d_Continents;
    private SortedMap<String, Country> d_Countries;

    /**
     * Constructor to initialize the map validator.
     *
     * @param p_Countries  A sorted map of country objects.
     * @param p_Continents A sorted map of continent objects.
     */
    public ValidateMapImpl(
            SortedMap<String, Country> p_Countries,
            SortedMap<String, Continent> p_Continents) {
        this.d_Countries = p_Countries;
        this.d_Continents = p_Continents;
    }

    /**
     * Validates the map by checking if the graph of countries is connected
     * and if each country belongs to a valid continent.
     *
     * @return true if the map is valid, false otherwise.
     */
    public boolean isMapValid() {
        // NEW - Validate that every neighbor exists
        if (!validateNeighborExistence()) {
            return false;
        }
        // NEW - Validate that neighbor relationships are bidirectional
        // So, if Country A lists Country B as a neighbor, then Country B must also list Country A as a neighbor.
        if (!validateBidirectionalNeighbors()) {
            return false;
        }

        if (!isGraphConnected()) {
            return false;
        }

        if (!validateContinents()) {
            return false;
        }

        System.out.println("Map is valid.");
        return true;
    }

    /**
     * Validate that every neighbor in a country's neighbor list exists in d_Countries.
     *
     * @return true if every neighbor in a country's neighbor list exists in d_Countries, otherwise false
     */
    private boolean validateNeighborExistence() {
        for (Country l_country : d_Countries.values()) {
            for (String l_neighborID : l_country.getNeighborIDs()) {
                if (!d_Countries.containsKey(l_neighborID)) {
                    System.out.println("Invalid map: Neighbor " + l_neighborID
                            + " for the country " + l_country.getID() + " does not exist in d_Countries database.");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Validate that neighbor relationships are bidirectional.
     *
     * @return true if every neighbor relationships are bidirectional, otherwise false.
     */
    private boolean validateBidirectionalNeighbors() {
        for (Country l_country : d_Countries.values()) {
            for (String l_neighborID : l_country.getNeighborIDs()) {
                Country l_neighborCountry = d_Countries.get(l_neighborID);
                // Check if the neighbor's list includes the original country.
                if (!l_neighborCountry.getNeighborIDs().contains(l_country.getID())) {
                    System.out.println("Invalid map: Bidirectional neighbor missing. Country "
                            + l_country.getID() + " lists " + l_neighborID
                            + " as neighbor, but not vice versa.");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Helper method to check if the graph of countries is connected
     *
     * @return true if all countries are connected, false otherwise.
     */
    private boolean isGraphConnected() {
        if (d_Countries.isEmpty()) {
            System.out.println("Invalid map: No countries exists");
            return false;
        }

        Set<String> l_visited = new HashSet<>();
        String l_startCountry = d_Countries.firstKey();
        exploreCountries(l_startCountry, l_visited);

        // Ensure all countries are visited
        if (l_visited.size() != d_Countries.size()) {
            System.out.println("Not all countries are connected.");
            return false;
        }
        return true;
    }

    /**
     * DFS (depth first search) to explore all connected countries
     *
     * @param p_countryID The starting country ID for traversal.
     * @param p_visited   A set of visited country IDs to track traversal.
     */

    private void exploreCountries(String p_countryID, Set<String> p_visited) {
        if (p_visited.contains(p_countryID)) {
            return;
        }

        p_visited.add(p_countryID);
        Country l_current = d_Countries.get(p_countryID);

        for (String l_neighborID : l_current.getNeighborIDs()) {
            if (d_Countries.containsKey(l_neighborID)) {
                exploreCountries(l_neighborID, p_visited);
            }
        }
    }


    /**
     * Helper method to validate continent-country relationship
     *
     * @return true if every country is attached to a continent, otherwise false
     */
    private boolean validateContinents() {
        Map<String, Set<String>> l_continentToCountries = new HashMap<>();

        // Populate continent-country mapping
        for (Country l_country : d_Countries.values()) {
            String l_continentID = l_country.getContinent().getID();
            // Check if the country's continent actually exists
            if (!d_Continents.containsKey(l_continentID)) {
                System.out.println("Invalid map: Country " + l_country.getID()
                        + " belongs to a non-existent continent " + l_continentID + ".");
                return false;
            }

            if (!l_continentToCountries.containsKey(l_continentID)) {
                l_continentToCountries.put(l_continentID, new HashSet<>());
            }
            l_continentToCountries.get(l_continentID).add(l_country.getID());
        }

        // Check every continent has at least one country
        for (Continent l_continent : d_Continents.values()) {
            if (!l_continentToCountries.containsKey(l_continent.getID()) || l_continentToCountries.get(l_continent.getID()).isEmpty()) {
                System.out.println("Invalid map: No countries associated with continent "
                        + l_continent.getID() + ".");
                return false;
            }
        }

        // Ensure each country belongs to exactly one continent
        if (!isCountryBelongsToOnlyOneContinent()) {
            System.out.println("There is one country belonging to more than one continent");
            return false;
        }

        return true;
    }

    /**
     * Checks whether each country belongs to only one continent.
     *
     * @return true if each country belongs to exactly one continent, false otherwise.
     */
    private boolean isCountryBelongsToOnlyOneContinent() {
        int l_numberOfContinentsCountryBelongTo = 0;
        for (Country l_country : d_Countries.values()) {
            l_numberOfContinentsCountryBelongTo = 0;
            for (Continent l_continent : d_Continents.values()) {
                if (l_continent.getNumericID() == l_country.getContinent().getNumericID()) {
                    l_numberOfContinentsCountryBelongTo++;
                    if (l_numberOfContinentsCountryBelongTo > 1) {
                        System.out.println("Invalid map: Country " + l_country.getID()
                                + " belongs to more than one continent.");
                        return false;
                    }
                }
            }
        }
        return true;
    }
}