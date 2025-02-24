/**
 * Summary: Map Connectivity Validation Test
 *
 * This test case verifies that the game map is a connected graph, meaning all countries accross any continent
 * should be reachable from any starting country. It ensures that the game will not allow
 * a broken or disconnected map structure, which is crucial for fair gameplay.
 *
 * What This Test Does:
 * 1. Sets Up the Game Map
 *    - Initializes an empty `GameContext` before each test using `@BeforeEach`.
 *    - Creates a new map with continents (Asia, Europe) and countries (India, China, Russia).
 *    - Adds neighbor connections between countries to establish a graph structure.
 *
 * 2. Validates Map Connectivity
 *    - Uses Breadth-First Search (BFS) to check if all countries can be reached from a starting point.
 *    - Keeps track of visited countries using a list.
 *    - If any country is unreachable, the test fails.
 *
 * 3. Assertions & Error Handling
 *    - If the map is correctly connected, the test passes (`assertTrue(isConnected)`).
 *    - If there are no countries in the map, the test throws an exception (`IllegalStateException`).
 *    - Ensures the entire map is validated, not just individual countries.
 *
 * 4. Why This Test is Important?
 *     - Prevents broken maps where some territories are unreachable.
 *     - Ensures fair gameplay by confirming all players can move across the map.
 *     - Validates correct map-building logic before gameplay begins.
 * 5. Logic:
 *  We pick any country as the starting point.
 *  Breadth-First Search (BFS) is used to traverse all reachable countries.
 *  After BFS completes, we compare:
 *  Total visited countries vs Total countries in the map
 *  If they match → The map is fully connected 
 *  If they don’t match → Some territories are isolated → Invalid Map! 
 */






package ca.concordia.soen6441.project.command.mapeditor;                 //Since our test file is inside tests/command/mapeditor/, the package should match its location.

                                                                         // JUnit is required to run tests-We need to import JUnit 5 annotations.
import org.junit.jupiter.api.BeforeEach;                                 // @BeforeEach ensures a fresh setup before every test runs.Every test should start with a clean, initialized state to avoid dependencies between test cases.
import org.junit.jupiter.api.Test;                                       // @Test tells JUnit which methods are test cases. Any method marked with @Test will automatically run when you execute your tests
import static org.junit.jupiter.api.Assertions.*;                        //Assertions are used to verify expected behavior in your test cases.


import ca.concordia.soen6441.project.GameEngine;                        //Game Context Import :For Testing the Game Logic
import ca.concordia.soen6441.project.interfaces.GameContext;             //GameContext → Interface defining map operations. GameContext is the interface that GameEngine implements.
import ca.concordia.soen6441.project.interfaces.Continent;               // Continent & Country → interfaces Used to test country/continent structures. Imported because they are Core game elements that need to be tested for correctness.
import ca.concordia.soen6441.project.interfaces.Country;

                                                                         //Java Utility Imports (For Data Structures Used in GameEngine). We import these to set up the game’s map structure properly in tests and ensure we are testing realistic data handling.
import java.util.List;                                                   //Needed to store and pass test data for countries & their neighbors when testing GameEngine.
import java.util.ArrayList;                                              //ArrayList is used because it's fast for access and simple for testing.
import java.util.SortedMap;                                              // GameEngine stores continents & countries using sorted maps
import java.util.TreeMap;                                                // Ensure map elements are stored and retrieved in sorted order.
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

                                                                        //Now that we have the necessary imports, we need to set up our test class and initialize the test environment before writing individual test cases.
class ValidateMapCommandTest {                                          //The test class.
    private GameContext d_gameContext;                                  // Declares a GameContext variable, which represents the game's map structure.This will be initialized before each test to simulate a real game environment.
    private SortedMap<String, Continent> d_continents;                  // Declares a sorted map to store continents in a structured order.
    private SortedMap<String, Country> d_countries;                     // Similar to d_continents, but stores countries instead.


@BeforeEach                                                             // Prevents test contamination (i.e., one test modifying data and affecting others).Helps us avoid false positives/negatives in tests caused by leftover data from previous tests.
void setup() {                                                          // Defines the method that will prepare our test environment.
    d_continents = new TreeMap<>();                                     // Creates a fresh, empty TreeMap for storing continents.This ensures that each test starts with a clean map.
    d_countries = new TreeMap<>();                                      // Same as above, but for countries.
    d_gameContext = new GameEngine();     // Initializes the GameEngine object, which is what we will be testing.Passes our TreeMaps to simulate the actual way the game stores map data.
     ((GameEngine) d_gameContext).setContinents(d_continents);
    ((GameEngine) d_gameContext).setCountries(d_countries);
}


@Test                                                                   //Marks this method as a test case. JUnit automatically runs it when we execute tests.
void testMapIsConnected() {                                             //Defines our method for validating the map's connectivity.

                                                                        // Step 1: Create continents and add them to the game
    d_gameContext.addContinent("Asia", 5);                              //Adds a continent named "Asia" with a value of 5 reinforcement armies.
    d_gameContext.addContinent("Europe", 3);                            //Adds another continent "Europe" with a different reinforcement value. This ensures our test handles multiple continents.

                                                                            // Step 2: Create countries and add them to the game
    d_gameContext.addCountry("India", "Asia", List.of("China"));            //Creates a country "India", which belongs to the Asia continent. Specifies "China" as its neighbor, forming a connection.
    d_gameContext.addCountry("China", "Asia", List.of("India", "Russia"));  //Adds "China" and connects it to India & Russia. This establishes cross-continent connectivity.
    d_gameContext.addCountry("Russia", "Europe", List.of("China"));         //Adds "Russia" to Europe but connects it to China, linking the continents

    // DEBUG: Print countries and neighbors
    System.out.println("DEBUG: Checking Country Connections");
    d_gameContext.showMap();
                                                                               // Step 3: Check if the game context validates the map correctly
    boolean l_isConnected = validateMapConnectivity();                         //Calls the validation method. Checks if all countries are reachable from any starting point.

                                                                               // Step 4: Assert that the map is fully connected
    assertTrue(l_isConnected, "The map should be a connected graph");          //Asserts that the map must be fully connected. If it fails, JUnit will show an error saying: "The map should be a connected graph".
}


private boolean validateMapConnectivity() {                                     // Defines a helper method to check if all countries are connected. This simulates how the game verifies a valid map.
            if (d_countries.isEmpty()) {
    throw new IllegalStateException("Map validation failed: A valid map must contain at least one country.");
}                                                                               //If there are no countries, handle the exception.

                                                                                // Step 1: Pick a random country as the starting point.
    String l_startCountry = d_countries.keySet().iterator().next();             //Picks any country as the starting point for traversal.This ensures we check connectivity from any country

                                                                                // Step 2: Use BFS to check if all countries are reachable
    Set<String> l_visited = new HashSet<>();                                    //Stores the list of visited countries to track traversal.
    Queue<String> l_queue = new LinkedList<>();                                 //Creates a queue for Breadth-First Search (BFS) traversal. BFS ensures we visit every country exactly once.
    l_queue.add(l_startCountry);                                                //Adds the first country to start checking its connections.
    l_visited.add(l_startCountry);                                              // Marks the starting country as visited.

    while (!l_queue.isEmpty()) {                                                //Iterates through all reachable countries.
        String l_current = l_queue.poll();                                      //Removes the first country from the queue.
                                                                                // Validate the country exists in d_countries (safety check)
        if (!d_countries.containsKey(l_current)) {
            throw new IllegalStateException("Unexpected error: Country " + l_current + " does not exist in the map.");
        }
        for (String l_neighbor : d_countries.get(l_current).getNeighborIDs()) { // Loops through all connected neighbors of the current country.
            if (!l_visited.contains(l_neighbor)) {                              // If the neighbor hasn't been visited yet, mark it as visited immediately and add it to the queue.
                l_visited.add(l_neighbor);                                      //Adds the neighbor to the visited list.
                l_queue.add(l_neighbor);                                        //Pushes the neighbor into the queue to check its connections later.
            }
        }
    }

                                                                            // Step 3: The map is connected if all countries have been visited
    return l_visited.size() == d_countries.size();                            // If all countries were visited, the map is fully connected.
}
}