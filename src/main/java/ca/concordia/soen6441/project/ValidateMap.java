package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;

import java.util.*;

public class ValidateMap {

    private SortedMap<String, Continent> d_Continents;
    private SortedMap<String, Country> d_Countries;
//    private SortedMap<String, Player> d_players;

    public ValidateMap(
            SortedMap<String, Country> p_Countries,
            SortedMap<String, Continent> d_Continents
    ) {
        this.d_Countries = p_Countries;
        this.d_Continents = d_Continents;
    }

    public boolean isMapValidate() {
        if (!isGraphConnected()) {
            //System.out.println("Invalid map: No countries exists or not all countries are connected.");
            return false;
        }

        if (!validateContinents()) {
//            System.out.println(
//                    "Invalid map: Continent or country association is incorrect." +
//                            "\nNote: Every continent must have at least one country." +
//                            "\n      Every country must belong to exactly one continent.");
            return false;
        }
        System.out.println("Map is valid.");

        return true;
    }

    // Helper method to check if the graph of countries is connected
    private boolean isGraphConnected() {
        if (d_Countries.isEmpty()) {
            return false; // No countries to connect
        }

        Set<String> d_visited = new HashSet<>();
        String l_startCountry = d_Countries.firstKey();
        exploreCountries(l_startCountry, d_visited);

        // Ensure all countries are visited
        return d_visited.size() == d_Countries.size();
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
                return false; // Country belongs to a non-existent continent
            }

            if (!d_continentToCountries.containsKey(l_continentID)) {
                d_continentToCountries.put(l_continentID, new HashSet<>());
            }
            d_continentToCountries.get(l_continentID).add(l_country.getID());

        }

        // Check every continent has at least one country
        for (Continent l_continent : d_Continents.values()) {
            if (!d_continentToCountries.containsKey(l_continent.getID()) || d_continentToCountries.get(l_continent.getID()).isEmpty()) {
                return false; // No countries associated with this continent
            }
        }

        // Ensure each country belongs to exactly one continent
        return true;
    }
}
