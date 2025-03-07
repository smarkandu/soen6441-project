package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for savemap in GameEngineTest class.
 */
class GameEngineTest {
    private GameEngine d_gameEngine;
    private Continent d_mockContinent;
    private Country d_mockCountry;
    private Country d_mockNeighbor;

/**
 * Sets up mock data for GameEngine before each test.
*/
    @BeforeEach
    void setUp() {
        d_gameEngine = new GameEngine(); // Real instance

        // Mock dependencies
        d_mockContinent = mock(Continent.class);
        d_mockCountry = mock(Country.class);
        d_mockNeighbor = mock(Country.class);

        when(d_mockContinent.getID()).thenReturn("Asia");
        when(d_mockContinent.getValue()).thenReturn(5);

        when(d_mockCountry.getID()).thenReturn("India");
        when(d_mockCountry.getNumericID()).thenReturn(1);
        when(d_mockCountry.getContinent()).thenReturn(d_mockContinent);
        when(d_mockCountry.getNeighborIDs()).thenReturn(List.of("China"));

        when(d_mockNeighbor.getID()).thenReturn("China");
        when(d_mockNeighbor.getNumericID()).thenReturn(2);
        when(d_mockNeighbor.getContinent()).thenReturn(l_mockContinent);
        when(d_mockNeighbor.getNeighborIDs()).thenReturn(List.of("India"));

        // Inject mock objects
        d_gameEngine.getContinents().put("Asia", d_mockContinent);
        d_gameEngine.getCountries().put("India", d_mockCountry);
        d_gameEngine.getCountries().put("China", d_mockNeighbor); // Ensure neighbor exists
    }
/**
* Tests the toMapString() method of GameEngine.
* Ensures that the generated map string contains expected data.
*/
    @Test
    void testToMapString() {
    d_gameEngine = new GameEngine(); // Real instance of GameEngine
    
    // Mock dependencies
    Continent d_mockContinent = mock(Continent.class);
    when(d_mockContinent.getID()).thenReturn("Asia");
    when(d_mockContinent.getValue()).thenReturn(5);
    when(d_mockContinent.toMapString()).thenReturn("Asia 5");

    Country d_mockCountry = mock(Country.class);
    when(d_mockCountry.getID()).thenReturn("India");
    when(d_mockCountry.getNumericID()).thenReturn(1);
    when(d_mockCountry.toMapString()).thenReturn("1 India Asia");

    // Add to game engine
    d_gameEngine.getContinents().put("Asia", d_mockContinent);
    d_gameEngine.getCountries().put("India", d_mockCountry);

    // Capture output
    String d_actualOutput = d_gameEngine.toMapString().trim();
    System.out.println("Captured toMapString Output:\n" + d_actualOutput);

    // Assertions
    assertFalse(d_actualOutput.isEmpty(), "toMapString() output is empty.");
    assertTrue(d_actualOutput.contains("Asia"), "Expected continent 'Asia' in output.");
    assertTrue(d_actualOutput.contains("1 India Asia"), "Expected country 'India' in output.");
}
}
