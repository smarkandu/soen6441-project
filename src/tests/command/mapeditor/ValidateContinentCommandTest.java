/**
 * Summary: Continent Connectivity Validation Test
 *
 *This test ensures that each continent in the game is a connected subgraph, meaning that all countries within a continent should be reachable
 *from any starting country inside the same continent.
 *If there are disconnected regions within a continent, the game should flag the map as invalid.
 *
 * What This Test Does
 *  1. Sets Up the Game Map**
 *      - Initializes a fresh `GameContext` before each test using `@BeforeEach`.
 *      - Creates a new continent ("Asia") and countries ("India", "China", "Japan").
 *      - Establishes neighbor connections **only within the same continent**.
 *
 *  2. Validates Continent Connectivity**
 *      - Uses **Breadth-First Search (BFS)** to check whether all countries within the specified continent are connected.
 *      - If a country in the continent cannot be reached, the test **fails**.
 *
 *  3. Assertions & Error Handling**
 *      - If the continent is correctly connected, the test **passes** (`assertTrue(isConnected)`).
 *      - If there are no countries within the continent, the test **throws an exception** (`IllegalStateException`).
 *      - Ensures **only intra-continent connectivity is validated**, meaning cross-continent connections do not affect the test.
 *
 *  4.  Why This Test is Important?**
 *    - **Ensures fair gameplay**: Prevents broken maps where some territories inside a continent are isolated.
 *    - **Validates correct continent-building logic** before gameplay begins.
 *    - **Prevents invalid continent structures** from being used in the game.
 * 
 * 5. Logic:
 *       Similar to Map Validation, but with one extra condition.
 *       Instead of checking all countries, BFS is restricted to one continent.
 *       Only intra-continent connections matter.
 *       After BFS completes, we compare:
 *       Total visited countries in the continent vs Total expected countries in that continent
 *       If they match → The continent is valid 
 *       If they don’t match → Some countries inside the continent are isolated → Invalid Continent! 
 */





package ca.concordia.soen6441.project.command.mapeditor;                        // Since our test file is inside tests/command/mapeditor/, the package should match its location.

import org.junit.jupiter.api.BeforeEach;                                        // @BeforeEach ensures a fresh setup before every test runs.
import org.junit.jupiter.api.Test;                                              // @Test tells JUnit which methods are test cases.
import static org.junit.jupiter.api.Assertions.*;                               // Assertions are used to verify expected behavior in tests.

import ca.concordia.soen6441.project.command.GameContextImpl;                   // Game Context Import: For Testing the Game Logic.
import ca.concordia.soen6441.project.interfaces.GameContext;                    // GameContext interface defines the map structure.
import ca.concordia.soen6441.project.interfaces.Continent;                      // Continent & Country are imported to test the continent structure.
import ca.concordia.soen6441.project.interfaces.Country;

import java.util.List;                                                          // Used to store test data for countries and neighbors.
import java.util.ArrayList;                                                     // ArrayList is used because it's fast for access and simple for testing.
import java.util.SortedMap;                                                     // GameContextImpl stores continents & countries using sorted maps.
import java.util.TreeMap;                                                       // TreeMap ensures map elements are stored in sorted order.
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

class ValidateContinentCommandTest {                                            // The test class name follows the format: Validate[Feature]CommandTest
    private GameContext d_gameContext;                                          // Represents the game's map structure.
    private SortedMap<String, Continent> d_continents;                          // Stores continents in sorted order.
    private SortedMap<String, Country> d_countries;                             // Stores countries in sorted order.

    @BeforeEach                                                                 // Runs before every test to set up a fresh environment.
    void setup() {
        d_continents = new TreeMap<>();                                         // Creates a fresh TreeMap for storing continents.
        d_countries = new TreeMap<>();                                          // Creates a fresh TreeMap for storing countries.
        d_gameContext = new GameContextImpl(d_continents, d_countries);         // Initializes the game context.
    }

    @Test                                                                       // Marks this method as a JUnit test case.
    void testContinentIsConnected() {
                                                                                // Step 1: Create a single continent (Asia) and add it to the game.
        d_gameContext.addContinent("Asia", 5);                                  // Adds a continent named "Asia" with reinforcement value 5.

                                                                                // Step 2: Create countries belonging to Asia and establish intra-continent connections.
        d_gameContext.addCountry("India", "Asia", List.of("China"));            // India neighbors China.
        d_gameContext.addCountry("China", "Asia", List.of("India", "Japan"));   // China neighbors India & Japan.
        d_gameContext.addCountry("Japan", "Asia", List.of("China"));            // Japan neighbors China, completing the loop.

                                                                                // Step 3: Validate if all countries inside Asia are connected.
        boolean l_isConnected = validateContinentConnectivity("Asia");

                                                                                // Step 4: Assert that the continent is fully connected.
        assertTrue(l_isConnected, "The continent should be a connected subgraph");
    }

    private boolean validateContinentConnectivity(String p_continentID) {
                                                                                // Step 1: Extract all countries that belong to the specified continent.
        List<String> l_countriesInContinent = new ArrayList<>();
        for (Country l_country : d_countries.values()) {                        // Iterate over all countries.
            if (l_country.getContinentID().equals(p_continentID)) {             // Check if the country belongs to the continent.
                l_countriesInContinent.add(l_country.getID());                  // Add it to the list.
            }
        }

        if (l_countriesInContinent.isEmpty()) {                                                           // If no countries belong to the continent, the test should fail.
            throw new IllegalStateException("Continent validation failed: No countries belong to " + p_continentID);
        }

                                                                                                          // Step 2: Use BFS to check if all countries in the continent are connected.
        Set<String> l_visited = new HashSet<>();                                                          // Keeps track of visited countries.
        Queue<String> l_queue = new LinkedList<>();                                                       // BFS queue for traversal.

        l_queue.add(l_countriesInContinent.get(0));                                                         // Start BFS from any country in the continent.
        l_visited.add(l_countriesInContinent.get(0));                                                       // Mark it as visited.

        while (!l_queue.isEmpty()) {
            String l_current = l_queue.poll();                                                              // Retrieve the first country from the queue.
            if (l_current == null) break;                                                                   // Null check for safety.

            for (String l_neighbor : d_countries.get(l_current).getNeighborIDs()) {                         // Iterate over neighbors.
                if (l_countriesInContinent.contains(l_neighbor) && !l_visited.contains(l_neighbor)) {       // Only consider neighbors that belong to the same continent.
                    l_visited.add(l_neighbor);                                                              // Mark the neighbor as visited.
                    l_queue.add(l_neighbor);                                                                // Push the neighbor into the queue for traversal.
                }
            }
        }

                                                                                                            // Step 3: The continent is connected if all countries within it have been visited.
        return l_visited.size() == l_countriesInContinent.size();
    }
}