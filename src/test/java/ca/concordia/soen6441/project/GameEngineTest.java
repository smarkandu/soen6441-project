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
    private Continent l_mockContinent;
    private Country l_mockCountry;
    private Country l_mockNeighbor;

/**
 * Sets up mock data for GameEngine before each test.
*/
    @BeforeEach
    void setUp() {
        d_gameEngine = new GameEngine(); // Real instance

        // Mock dependencies
        l_mockContinent = mock(Continent.class);
        l_mockCountry = mock(Country.class);
        l_mockNeighbor = mock(Country.class);

        when(l_mockContinent.getID()).thenReturn("Asia");
        when(l_mockContinent.getValue()).thenReturn(5);

        when(l_mockCountry.getID()).thenReturn("India");
        when(l_mockCountry.getNumericID()).thenReturn(1);
        when(l_mockCountry.getContinent()).thenReturn(l_mockContinent);
        when(l_mockCountry.getNeighborIDs()).thenReturn(List.of("China"));

        when(l_mockNeighbor.getID()).thenReturn("China");
        when(l_mockNeighbor.getNumericID()).thenReturn(2);
        when(l_mockNeighbor.getContinent()).thenReturn(l_mockContinent);
        when(l_mockNeighbor.getNeighborIDs()).thenReturn(List.of("India"));

        // Inject mock objects
        d_gameEngine.getContinents().put("Asia", l_mockContinent);
        d_gameEngine.getCountries().put("India", l_mockCountry);
        d_gameEngine.getCountries().put("China", l_mockNeighbor); // Ensure neighbor exists
    }
/**
* Tests the toMapString() method of GameEngine.
* Ensures that the generated map string contains expected data.
*/
    @Test
    void testToMapString() {
    d_gameEngine = new GameEngine(); // Real instance of GameEngine
    
    // Mock dependencies
    Continent l_mockContinent = mock(Continent.class);
    when(l_mockContinent.getID()).thenReturn("Asia");
    when(l_mockContinent.getValue()).thenReturn(5);
    when(l_mockContinent.toMapString()).thenReturn("Asia 5");

    Country l_mockCountry = mock(Country.class);
    when(l_mockCountry.getID()).thenReturn("India");
    when(l_mockCountry.getNumericID()).thenReturn(1);
    when(l_mockCountry.toMapString()).thenReturn("1 India Asia");

    // Add to game engine
    d_gameEngine.getContinents().put("Asia", l_mockContinent);
    d_gameEngine.getCountries().put("India", l_mockCountry);

    // Capture output
    String l_actualOutput = d_gameEngine.toMapString().trim();
    System.out.println("Captured toMapString Output:\n" + l_actualOutput);

    // Assertions
    assertFalse(l_actualOutput.isEmpty(), "toMapString() output is empty.");
    assertTrue(l_actualOutput.contains("Asia"), "Expected continent 'Asia' in output.");
    assertTrue(l_actualOutput.contains("1 India Asia"), "Expected country 'India' in output.");
}
}
