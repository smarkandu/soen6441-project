/**
 * Summary: Deploy Armies Constraint Test
 *
 * This test ensures that a player **cannot deploy more armies than available reinforcements**.
 * The game should **prevent invalid deployments at the input level**, but also enforce a **fail-safe**
 * mechanism during execution.
 *
 * **Why is this test important?**
 * - Ensures fair gameplay by preventing players from deploying more armies than they have.
 * - Verifies that the game **rejects invalid deployments before they happen**.
 * - Protects the game logic from unexpected errors if an invalid command sneaks through.
 *
 * **Concept of Prevention vs. Exception Handling**
 * 1. **Prevention (Input Validation)** → The game should **not allow** invalid deployment orders to be issued.
 *    - If a player types `deploy India 10` but only has 5 reinforcements, the game should **reject the command immediately**.
 *    - This prevents the invalid move from entering the system.
 *
 * 2️. **Fail-Safe Exception Handling** → If an invalid deployment somehow still makes it through (e.g., due to a bug),
 *    - The game should **catch the error at execution time** and prevent unintended consequences.
 *    - This is what our test case ensures.
 * 
 * Logic:
 *       Prevention at Input Level: The game does not allow issuing an invalid deployment order.
 *       Does the player own the country?
 *       Does the player have enough reinforcements?
 *       If both conditions are met → Deployment proceeds 
 *       If reinforcement count is insufficient → Game prevents it & throws an exception 
 */





package ca.concordia.soen6441.project.command.gameplay;  // Defines package, ensuring test class is in correct structure

// Import JUnit dependencies for testing
import org.junit.jupiter.api.BeforeEach;    
import org.junit.jupiter.api.Test;              
import static org.junit.jupiter.api.Assertions.*;  

//  Import game-related dependencies
import ca.concordia.soen6441.project.GameEngine;  
import ca.concordia.soen6441.project.interfaces.GameContext;   
import ca.concordia.soen6441.project.PlayerImpl;              
import ca.concordia.soen6441.project.interfaces.Player;        

import java.util.ArrayList;
import java.util.List; 

/**
 * Test class to verify that a player cannot deploy more armies than available in their reinforcement pool.
 * Ensures that the game correctly handles reinforcement constraints.
 */
class DeployArmiesConstraintTest {   
    private GameContext d_gameContext;  // Stores game state (countries, players, reinforcements, etc.)
    private Player d_player;            // Represents the player being tested

    /**
     * Sets up a fresh game context and player before each test.
     */
    @BeforeEach  
    void setup() {  
        d_gameContext = new GameEngine();  // Initializes an empty game state
        d_player = new PlayerImpl("Player1");   // Creates a new test player
    d_gameContext.addCountry("India", "Asia", new ArrayList<>());  // Adds the country to the map
        d_gameContext.assignCountryToPlayer(d_player, "India"); // Assigns it to Player1
    }

    /**
     * Tests that deploying within the available reinforcements does not throw an error.
     */
    @Test
    void testValidDeployment() {  
        d_gameContext.assignReinforcements(d_player, 5);  // Player gets 5 reinforcements

        // Should NOT throw an error because 3 ≤ 5 (Valid Deployment)
        assertDoesNotThrow(() -> d_gameContext.deployArmies(d_player, "India", 3),    
                          "Deployment within limits should not throw an error");   
    }

    /**
     * Tests that deploying more armies than available results in an error.
     */
    @Test
    void testDeployMoreThanAvailableReinforcements() {  
        d_gameContext.assignReinforcements(d_player, 5);  // Player gets 5 reinforcements

        // Should throw an error because 10 > 5 (Invalid Deployment)
        Exception l_exception = assertThrows(IllegalArgumentException.class, () -> {  
            d_gameContext.deployArmies(d_player, "India", 10);  
        });

        // Verify the error message
        assertEquals("Insufficient reinforcements!", l_exception.getMessage(),  
                     "Error message should indicate insufficient reinforcements");
    }
}

