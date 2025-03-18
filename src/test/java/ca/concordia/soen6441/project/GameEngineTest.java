package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Unit test for GameEngine class.
 * This test verifies the functionality of the showMap() and toMapString() method.
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
        when(d_mockNeighbor.getContinent()).thenReturn(d_mockContinent);
        when(d_mockNeighbor.getNeighborIDs()).thenReturn(List.of("India"));

        // Inject mock objects
        d_gameEngine.getContinentManager().getContinents().put("Asia", d_mockContinent);
        d_gameEngine.getCountryManager().getCountries().put("India", d_mockCountry);
        d_gameEngine.getCountryManager().getCountries().put("China", d_mockNeighbor); // Ensure neighbor exists
    

    // Mock continents map
        SortedMap<String, Continent> l_mockContinents = new TreeMap<>();
        l_mockContinents.put("Asia", d_mockContinent);
        d_gameEngine.getContinentManager().getContinents().putAll(l_mockContinents);

        // Manually add the mock country
        d_gameEngine.getCountryManager().getCountries().put("India", d_mockCountry);
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
        d_gameEngine.getContinentManager().getContinents().put("Asia", l_mockContinent);
        d_gameEngine.getCountryManager().getCountries().put("India", l_mockCountry);

        // Capture output
        String l_actualOutput = d_gameEngine.toMapString().trim();
        System.out.println("Captured toMapString Output:\n" + l_actualOutput);

        // Assertions
        assertFalse(l_actualOutput.isEmpty(), "toMapString() output is empty.");
        assertTrue(l_actualOutput.contains("Asia"), "Expected continent 'Asia' in output.");
        assertTrue(l_actualOutput.contains("1 India Asia"), "Expected country 'India' in output.");
    }
    /**
     * Tests the showMap() method of GameEngine.
     * Ensures that the map output contains expected continent and country data.
     */
    @Test
    void testShowMap() {
        // Capture console output
        ByteArrayOutputStream l_outContent = new ByteArrayOutputStream();
        PrintStream l_originalOut = System.out;
        System.setOut(new PrintStream(l_outContent));

        // Call the method being tested
        d_gameEngine.showMap(false);

        // Restore original System.out
        System.setOut(l_originalOut);

        // Get the captured output
        String l_actualOutput = l_outContent.toString().trim();
        System.out.println("Captured showMap Output:\n" + l_actualOutput);

        // Assertions to check expected values
        assertFalse(l_actualOutput.isEmpty(), "showMap() output is empty.");
        assertTrue(l_actualOutput.contains("Asia"), "Expected 'Asia' in output.");
    }
}
