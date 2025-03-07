package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.ValidateMap;

import java.util.*;
public class ValidateMapImpl implements ValidateMap {

    private SortedMap<String, Continent> d_Continents;
    private SortedMap<String, Country> d_Countries;

    public ValidateMapImpl(
            SortedMap<String, Country> p_Countries,
            SortedMap<String, Continent> p_Continents) {
        this.d_Countries = p_Countries;
        this.d_Continents = p_Continents;
    }

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

    // NEW - Validate that every neighbor in a country's neighbor list exists in d_Countries.
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

    // NEW - Validate that neighbor relationships are bidirectional.
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

    // Helper method to check if the graph of countries is connected
    private boolean isGraphConnected() {
        if (d_Countries.isEmpty()) {
            System.out.println("Invalid map: No countries exists");
            return false;
        }

        Set<String> l_visited = new HashSet<>();
        String l_startCountry = d_Countries.firstKey();
        exploreCountries(l_startCountry, l_visited);

        // Ensure all countries are visited
        if(l_visited.size() != d_Countries.size()) {
            System.out.println("Not all countries are connected.");
            return false;
        }
        return true;
    }

    // DFS (depth first search) to explore all connected countries
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

    // Helper method to validate continent-country relationship
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
        if(!isCountryBelongsToOnlyOneContinent()) {
            System.out.println("There is one country belonging to more than one continent");
            return false;
        }

        return true;
    }

    private boolean isCountryBelongsToOnlyOneContinent() {
        int l_numberOfContinentsCountryBelongTo = 0;
        for(Country l_country: d_Countries.values()){
            l_numberOfContinentsCountryBelongTo = 0;
            for(Continent l_continent: d_Continents.values()){
                if(l_continent.getNumericID() == l_country.getContinent().getNumericID()){
                    l_numberOfContinentsCountryBelongTo++;
                    if(l_numberOfContinentsCountryBelongTo > 1) {
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