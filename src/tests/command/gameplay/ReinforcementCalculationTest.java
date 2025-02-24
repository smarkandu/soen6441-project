// Summary: Reinforcement Calculation Test  

// Purpose  
// This test ensures that the reinforcement calculation logic correctly assigns armies  
// based on the number of territories a player owns.  

// Steps Performed in the Test  

// 1️.Setup the Game State  
//    - Initializes a fresh `GameContext` using `@BeforeEach`.  
//    - Creates a `Player` (`PlayerImpl`) named **"Player1"** for testing.  

// 2. Add Countries to the Game  
//    - Three countries (`India`, `China`, `Japan`) are added to the game.  
//    - These countries belong to the **Asia** continent.  
//    - No neighbors are assigned (empty `List.of()` used).  

// 3️. Assign Countries to Player  
//    - `India`, `China`, and `Japan` are assigned to **Player1** using `assignCountryToPlayer()`.  

// 4️. Calculate Reinforcements  
//    - Reinforcements should be the **greater** of:  
//      - `3` (minimum reinforcements in the game).  
//      - `Total owned countries / 3` (rounded down).  
//    - Expected reinforcement count is calculated using `Math.max(3, d_player.getOwnedCountries().size() / 3)`.  
//    - The actual reinforcements are fetched from `d_gameContext.calculateReinforcements(d_player)`.  

// 5️. Assertions & Validation  
//    - **If reinforcements are correct**, the test **passes** (`assertEquals(expected, actual)`).  
//    - **If incorrect**, the test **fails**

// 6. Logic
//  The formula used is Math.max(3, (total owned countries) / 3)
//  Test checks:
//  Expected reinforcements (as per formula) vs Actual reinforcements assigned
//  If they match → Reinforcement logic is correct
//  If they don’t match → Something is wrong with the calculation 





package ca.concordia.soen6441.project.command.gameplay;                 //Since our test file is inside tests/command/gameplay/, the package should match its location.

                                                                         // JUnit is required to run tests-We need to import JUnit 5 annotations.
import org.junit.jupiter.api.BeforeEach;                                 // @BeforeEach ensures a fresh setup before every test runs.Every test should start with a clean, initialized state to avoid dependencies between test cases.
import org.junit.jupiter.api.Test;                                       // @Test tells JUnit which methods are test cases. Any method marked with @Test will automatically run when you execute your tests
import static org.junit.jupiter.api.Assertions.*;                        //Assertions are used to verify expected behavior in your test cases.


import ca.concordia.soen6441.project.command.GameContextImpl;            //Game Context Import :For Testing the Game Logic
import ca.concordia.soen6441.project.interfaces.GameContext;             //GameContext → Interface defining map operations. GameContext is the interface that GameContextImpl implements.
import ca.concordia.soen6441.project.PlayerImpl;
import ca.concordia.soen6441.project.interfaces.Player;                  //Needed to retrieve reinforcement armies per player(logic).

                                                                         //Java Utility Imports (For Data Structures Used in GameContextImpl). We import these to set up the game’s map structure properly in tests and ensure we are testing realistic data handling.
import java.util.List;                                                   //Needed to store and pass test data for countries & their neighbors when testing GameContextImpl.
import java.util.ArrayList;                                              //ArrayList is used because it's fast for access and simple for testing.
import java.util.SortedMap;                                              // GameContextImpl stores continents & countries using sorted maps
import java.util.TreeMap;                                                // Ensure map elements are stored and retrieved in sorted order.
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;


class ReinforcementCalculationTest {                                     //The test class groups related tests to keep them organized.
    private GameContext d_gameContext;                                   //GameContext d_gameContext; → Represents the game state (players, armies, countries).
    private Player d_player;                                             //Player d_player; → Stores player-specific data (owned countries, reinforcement count).

    @BeforeEach                                                          //@BeforeEach → Ensures a clean test environment for every test run.
    void setup() {
        d_gameContext = new GameContextImpl();                           //Initializes the game state (empty map, empty players).
        d_player = new PlayerImpl("Player1");                            //Creates a new player ("Player1") for testing. PlayerImpl is assumed to be the actual implementation of Player.
    }

        @Test                                                            //@Test → Marks this method as a test case.
    void testReinforcementCalculation() {                                //Descriptive method name that clearly states what is being tested.
                                                                        //A player's reinforcement count depends on how many countries they own. Let's add some countries to the player's control.
        d_gameContext.addCountry("India", "Asia", new ArrayList<>());   //Creates a new country (India) and adds it to the game.Belongs to continent "Asia". No neighbors (List.of() is empty).
        d_gameContext.addCountry("China", "Asia", new ArrayList<>());   //Repeats the same for "China" and "Japan", adding three countries to the game
        d_gameContext.addCountry("Japan", "Asia", new ArrayList<>());


        d_gameContext.assignCountryToPlayer(d_player, "India");         // Assigns "India" to the player using the new method.
        d_gameContext.assignCountryToPlayer(d_player, "China");         // Assigns "China" to the player.
        d_gameContext.assignCountryToPlayer(d_player, "Japan");         // Assigns "Japan" to the player.

        int l_expectedReinforcement = Math.max(3, d_player.getOwnedCountries().size() / 3);                 //Each player receives at least 3 armies, or total owned countries / 3 (rounded down), whichever is higher.
        int l_actualReinforcement = d_gameContext.calculateReinforcements(d_player);                       // The actual number of reinforcements the game assigns.
        assertEquals(l_expectedReinforcement, l_actualReinforcement, "Reinforcement calculation is incorrect");   //If l_actualReinforcement doesn't match l_expectedReinforcement, the test fails and prints "Reinforcement calculation is incorrect".
    }
}