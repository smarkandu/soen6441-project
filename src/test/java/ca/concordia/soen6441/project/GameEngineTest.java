package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for GameEngine class.
 * This test verifies the functionality of the showMap() method.
 */
class GameEngineTest {
    private GameEngine d_gameEngine;
    private Continent d_mockContinent;
    private Country d_mockCountry;

    /**
     * Sets up the test environment before each test case.
     * Initializes the GameEngine and injects mock data.
     */
    @BeforeEach
    void setUp() {
        d_gameEngine = new GameEngine(); // Real instance

        // Mocking dependencies
        d_mockContinent = mock(Continent.class);
        d_mockCountry = mock(Country.class);

        when(d_mockContinent.getID()).thenReturn("Asia");
        when(d_mockContinent.getValue()).thenReturn(5);

        when(d_mockCountry.getID()).thenReturn("India");
        when(d_mockCountry.getTroops()).thenReturn(10);
        when(d_mockCountry.getNeighborIDs()).thenReturn(Collections.singletonList("China"));
        when(d_mockCountry.getContinent()).thenReturn(d_mockContinent);

        // Mock continents map
        SortedMap<String, Continent> l_mockContinents = new TreeMap<>();
        l_mockContinents.put("Asia", d_mockContinent);
        d_gameEngine.getContinents().putAll(l_mockContinents);

        // Manually add the mock country
        d_gameEngine.getCountries().put("India", d_mockCountry);
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
