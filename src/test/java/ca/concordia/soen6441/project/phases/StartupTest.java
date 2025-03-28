package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.gameplay.PlayerImpl;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link Startup#assignCountries()}.
 * Verifies that the game proceeds without warnings when map is valid and enough players exist.
 */
class StartupTest {

    /**
     * Tests that assignCountries proceeds to gameplay phase when setup is valid.
     */
    @Test
    void assignCountries() {
        GameEngine l_gameEngine = spy(new GameEngine());
        Startup l_startup = new Startup(l_gameEngine);

        // Add 2 players
        Player l_player1 = new PlayerImpl("Player1", new ArrayList<>(), new ArrayList<>());
        Player l_player2 = new PlayerImpl("Player2", new ArrayList<>(), new ArrayList<>());
        l_gameEngine.getPlayerManager().getPlayers().put(l_player1.getName(), l_player1);
        l_gameEngine.getPlayerManager().getPlayers().put(l_player2.getName(), l_player2);

        // Add 2 mock countries
        for (int l_i = 1; l_i <= 2; l_i++) {
            Country l_country = mock(Country.class);
            when(l_country.getID()).thenReturn("Country" + l_i);
            l_gameEngine.getCountryManager().getCountries().put("Country" + l_i, l_country);
        }

        // Simulate valid map
        doReturn(true).when(l_gameEngine).isMapValid();

        // Capture system output
        ByteArrayOutputStream l_outContent = new ByteArrayOutputStream();
        PrintStream l_originalOut = System.out;
        System.setOut(new PrintStream(l_outContent));

        // Act
        l_startup.assignCountries();

        // Restore system output
        System.setOut(l_originalOut);

        String l_output = l_outContent.toString().trim();
        System.out.println("Captured Output:\n" + l_output);

        // Assert: no errors or warnings printed
        assertFalse(l_output.contains("valid map must be loaded"), "Unexpected map error.");
        assertFalse(l_output.contains("at least two players"), "Unexpected player count error.");
        assertFalse(l_output.contains("Warning:"), "Unexpected warning message.");
    }
}

