package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;

import java.util.*;

public class ValidateMap {

    private SortedMap<String, Continent> d_Continents;
    private SortedMap<String, Country> d_Countries;

    public ValidateMap(
            SortedMap<String, Country> p_Countries,
            SortedMap<String, Continent> d_Continents) {
        this.d_Countries = p_Countries;
        this.d_Continents = d_Continents;
    }

    public boolean isMapValidate() {
        if (!isGraphConnected()) {
            return false;
        }

        if (!validateContinents()) {
            return false;
        }

        System.out.println("Map is valid.");
        return true;
    }

    // Helper method to check if the graph of countries is connected
    private boolean isGraphConnected() {
        if (d_Countries.isEmpty()) {
            System.out.println("Invalid map: No countries exists");
            return false;
        }

        Set<String> d_visited = new HashSet<>();
        String l_startCountry = d_Countries.firstKey();
        exploreCountries(l_startCountry, d_visited);

        // Ensure all countries are visited
        if(d_visited.size() != d_Countries.size()) {
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
        Map<String, Set<String>> d_continentToCountries = new HashMap<>();

        // Populate continent-country mapping
        for (Country l_country : d_Countries.values()) {
            String l_continentID = l_country.getContinent().getID();
            // Check if the country's continent actually exists
            if (!d_Continents.containsKey(l_continentID)) {
                System.out.println("Country belongs to a non-existent continent");
                return false;
            }

            if (!d_continentToCountries.containsKey(l_continentID)) {
                d_continentToCountries.put(l_continentID, new HashSet<>());
            }
            d_continentToCountries.get(l_continentID).add(l_country.getID());

        }

        // Check every continent has at least one country
        for (Continent l_continent : d_Continents.values()) {
            if (!d_continentToCountries.containsKey(l_continent.getID()) || d_continentToCountries.get(l_continent.getID()).isEmpty()) {
                System.out.println("No countries associated with this continent");
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
                        return false;
                    }
                }
            }

        }
        return true;
    }
}







