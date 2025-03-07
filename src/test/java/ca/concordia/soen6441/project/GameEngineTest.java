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

class GameEngineTest {
    private GameEngine d_gameEngine;
    private Continent d_mockContinent;
    private Country d_mockCountry;

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

    // ✅ **Instead of mocking `getCountriesOfContinent`, manually add the mock country**
    d_gameEngine.getCountries().put("India", d_mockCountry);
}

    @Test
    void testShowMap() {
        
        ByteArrayOutputStream l_outContent = new ByteArrayOutputStream();
        PrintStream l_originalOut = System.out;
        System.setOut(new PrintStream(l_outContent));

        d_gameEngine.showMap(false);

        System.setOut(l_originalOut);

        //System.setOut(System.out); // Restore original System.out
        String l_actualOutput = l_outContent.toString().trim();
        System.out.println("Captured showMap Output:\n" + l_actualOutput);

        assertFalse(l_actualOutput.isEmpty(), "showMap() output is empty.");
        assertTrue(l_actualOutput.contains("Asia"), "Expected 'Asia' in output.");
    }
}
