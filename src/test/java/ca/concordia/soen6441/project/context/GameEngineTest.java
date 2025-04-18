package ca.concordia.soen6441.project.context;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.log.LogEntryBuffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.*;

/**
 * Unit test for GameEngine class.
 * This test verifies the functionality of the showMap() and toMapString() method.
 */
public class GameEngineTest
{
    private Continent d_mockContinent;
    private Country d_mockCountry;
    private Country d_mockNeighbor;
    private LogEntryBuffer d_mockLogBuffer;
    private File d_tempSaveGameFile;

    /**
     * Stub Class for a NonSerializable GameEngine object
     */
    public class NonSerializableGameEngine extends GameEngine
    {
        private void writeObject(ObjectOutputStream p_objectOutputStream) throws IOException
        {
            throw new NotSerializableException("Forced failure for testing.");
        }
    }

    /**
     * Sets up mock data for GameEngine before each test.
     */
    @BeforeEach
    void setUp() throws IOException
    {
        // Real instance of GameEngine with mock components
        GameEngine l_gameEngine = new GameEngine(mock(ContinentManager.class), mock(CountryManager.class), mock(NeighborManager.class), mock(PlayerManager.class), mock(DeckOfCards.class));
        GameDriver.setGameEngine(l_gameEngine);

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
        GameDriver.getGameEngine().getContinentManager().getContinents().put("Asia", d_mockContinent);
        GameDriver.getGameEngine().getCountryManager().getCountries().put("India", d_mockCountry);
        GameDriver.getGameEngine().getCountryManager().getCountries().put("China", d_mockNeighbor); // Ensure neighbor exists


        // Mock continents map
        SortedMap<String, Continent> l_mockContinents = new TreeMap<>();
        l_mockContinents.put("Asia", d_mockContinent);
        GameDriver.getGameEngine().getContinentManager().getContinents().putAll(l_mockContinents);

        // Manually add the mock country
        GameDriver.getGameEngine().getCountryManager().getCountries().put("India", d_mockCountry);

        // Mock the logger
        d_mockLogBuffer = mock(LogEntryBuffer.class);
        LogEntryBuffer.setInstance(d_mockLogBuffer);

        d_tempSaveGameFile = File.createTempFile("test_game", ".ser");
    }

    @AfterEach
    void tearDown()
    {
        d_tempSaveGameFile.delete();
        LogEntryBuffer.setInstance(null);
    }

    /**
     * Tests the toMapString() method of GameEngine.
     * Ensures that the generated map string contains expected data.
     */
    @Test
    void testToMapString()
    {
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
        TreeMap<String, Continent> l_treeMapContinent = new TreeMap<>();
        l_treeMapContinent.put(l_mockContinent.getID(), l_mockContinent);
        TreeMap<String, Country> l_treeMapCountry = new TreeMap<>();
        l_treeMapCountry.put(l_mockCountry.getID(), l_mockCountry);
        when(GameDriver.getGameEngine().getContinentManager().getContinents()).thenReturn(l_treeMapContinent);
        when(GameDriver.getGameEngine().getCountryManager().getCountries()).thenReturn(l_treeMapCountry);

        // Capture output
        String l_actualOutput = GameDriver.getGameEngine().toMapString().trim();
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
    void testShowMap()
    {
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
        TreeMap<String, Continent> l_treeMapContinent = new TreeMap<>();
        l_treeMapContinent.put(l_mockContinent.getID(), l_mockContinent);
        TreeMap<String, Country> l_treeMapCountry = new TreeMap<>();
        l_treeMapCountry.put(l_mockCountry.getID(), l_mockCountry);
        when(GameDriver.getGameEngine().getContinentManager().getContinents()).thenReturn(l_treeMapContinent);
        when(GameDriver.getGameEngine().getCountryManager().getCountries()).thenReturn(l_treeMapCountry);

        // Capture console output
        ByteArrayOutputStream l_outContent = new ByteArrayOutputStream();
        PrintStream l_originalOut = System.out;
        System.setOut(new PrintStream(l_outContent));

        // Call the method being tested
        GameDriver.getGameEngine().showMap(false);

        // Restore original System.out
        System.setOut(l_originalOut);

        // Get the captured output
        String l_actualOutput = l_outContent.toString().trim();
        System.out.println("Captured showMap Output:\n" + l_actualOutput);

        // Assertions to check expected values
        assertFalse(l_actualOutput.isEmpty(), "showMap() output is empty.");
        assertTrue(l_actualOutput.contains("Asia"), "Expected 'Asia' in output.");
    }

    /**
     * Tests that a player is correctly recognized as the winner
     * when they own all countries on the map.
     */
    @Test
    void testGameWonByPlayer()
    {
        Player l_mockPlayer = mock(Player.class);
        when(l_mockPlayer.getName()).thenReturn("PlayerOne");

        Country l_country1 = mock(Country.class);
        when(l_country1.getOwner()).thenReturn(l_mockPlayer);

        Country l_country2 = mock(Country.class);
        when(l_country2.getOwner()).thenReturn(l_mockPlayer);

        SortedMap<String, Country> l_mockCountries = new TreeMap<>();
        l_mockCountries.put("C1", l_country1);
        l_mockCountries.put("C2", l_country2);

        when(GameDriver.getGameEngine().getCountryManager().getCountries()).thenAnswer(inv -> l_mockCountries);

        String l_winner = GameDriver.getGameEngine().gameWonBy();

        assertEquals("PlayerOne", l_winner);
    }

    /**
     * Tests that no player is considered the winner if countries
     * are owned by multiple players.
     */
    @Test
    void testGameNotWonByAnyPlayer()
    {
        CountryManager l_mockCountryManager = mock(CountryManager.class);
        Player l_mockPlayer1 = mock(Player.class);
        Player l_mockPlayer2 = mock(Player.class);

        Country l_country1 = mock(Country.class);
        when(l_country1.getOwner()).thenReturn(l_mockPlayer1);

        Country l_country2 = mock(Country.class);
        when(l_country2.getOwner()).thenReturn(l_mockPlayer2);

        SortedMap<String, Country> l_mockCountries = new TreeMap<>();
        l_mockCountries.put("C1", l_country1);
        l_mockCountries.put("C2", l_country2);

        when(GameDriver.getGameEngine().getCountryManager().getCountries()).thenAnswer(inv -> l_mockCountries);

        String l_winner = GameDriver.getGameEngine().gameWonBy();

        assertNull(l_winner);
    }

    /**
     * Testcase for a successful save game
     */
    @Test
    void testSaveGame_success()
    {
        GameDriver.getGameEngine().saveGame(d_tempSaveGameFile.getAbsolutePath());

        assertTrue(d_tempSaveGameFile.exists(), "File should be created");
        assertTrue(d_tempSaveGameFile.length() > 0, "File should contain serialized data");

        verify(d_mockLogBuffer).appendToBuffer("Game saved as: " + d_tempSaveGameFile.getAbsolutePath(), true);
    }

    /**
     * Testcase for when an object is non-serializable
     */
    @Test
    void testSaveGame_nonSerializableObject_logsError()
    {
        // Create an object that throws a "NotSerializableException" exception when you attempt to write it
        GameEngine l_nonSerializableEngine = new NonSerializableGameEngine();
        GameDriver.setGameEngine(l_nonSerializableEngine);
        GameDriver.getGameEngine().saveGame(d_tempSaveGameFile.getAbsolutePath());

        verify(d_mockLogBuffer).appendToBuffer(startsWith("Issue saving game: " + d_tempSaveGameFile.getAbsolutePath()), eq(true));
    }

    /**
     * Testcase for when the filepath given is wrong
     */
    @Test
    void testSaveGame_filePathInvalid_logsError()
    {
        String l_invalidFilename = "/invalid_path/test.dat";
        GameDriver.getGameEngine().saveGame(l_invalidFilename);
        verify(d_mockLogBuffer).appendToBuffer(startsWith("Issue saving game: " + l_invalidFilename), eq(true));
    }
}
