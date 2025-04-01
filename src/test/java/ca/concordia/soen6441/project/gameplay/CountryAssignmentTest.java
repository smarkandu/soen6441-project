package ca.concordia.soen6441.project.gameplay;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.context.PlayerManager;
import ca.concordia.soen6441.project.gameplay.behaviour.HumanPlayerBehavior;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.log.LogEntryBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link CountryAssignment}.
 *
 * This test verifies that countries are correctly assigned to players during the setup phase.
 */
public class CountryAssignmentTest {

    private GameEngine d_gameEngine;
    private CountryAssignment d_countryAssignment;

    /**
     * Mock PlayerManager
     */
    private PlayerManager d_playerManager;


    /**
     * Sets up the game engine and test data before each test.
     */
    @BeforeEach
    void setUp() {
        d_gameEngine = new GameEngine();
        d_countryAssignment = new CountryAssignment(d_gameEngine);
        d_playerManager = mock(PlayerManager.class);

        // Add 3 players
        Player l_player1 = new PlayerImpl("Player1", new ArrayList<>(), new ArrayList<>(), new HumanPlayerBehavior(),
                d_playerManager);
        Player l_player2 = new PlayerImpl("Player2", new ArrayList<>(), new ArrayList<>(), new HumanPlayerBehavior(),
                d_playerManager);
        Player l_player3 = new PlayerImpl("Player3", new ArrayList<>(), new ArrayList<>(), new HumanPlayerBehavior(),
                d_playerManager);

        d_gameEngine.getPlayerManager().getPlayers().put(l_player1.getName(), l_player1);
        d_gameEngine.getPlayerManager().getPlayers().put(l_player2.getName(), l_player2);
        d_gameEngine.getPlayerManager().getPlayers().put(l_player3.getName(), l_player3);

        // Add 3 mock countries
        for (int l_i = 1; l_i <= 3; l_i++) {
            Country l_country = mock(Country.class);
            when(l_country.getID()).thenReturn("Country" + l_i);
            when(l_country.getTroops()).thenReturn(3);
            d_gameEngine.getCountryManager().getCountries().put("Country" + l_i, l_country);
        }
    }

    /**
     * Tests that a warning is printed when there are more players than countries.
     *
     * This confirms that the system correctly logs a message when not all players can be assigned a country.
     */
@Test
void testAssignCountriesWithInsufficientCountries() {
    // Clear existing players/countries
    d_gameEngine.getPlayerManager().getPlayers().clear();
    d_gameEngine.getCountryManager().getCountries().clear();

    // Add 3 players
    Player l_player1 = new PlayerImpl("Player1", new ArrayList<>(), new ArrayList<>(), new HumanPlayerBehavior(),
            d_playerManager);
    Player l_player2 = new PlayerImpl("Player2", new ArrayList<>(), new ArrayList<>(), new HumanPlayerBehavior(),
            d_playerManager);
    Player l_player3 = new PlayerImpl("Player3", new ArrayList<>(), new ArrayList<>(), new HumanPlayerBehavior(),
            d_playerManager);
    d_gameEngine.getPlayerManager().getPlayers().put(l_player1.getName(), l_player1);
    d_gameEngine.getPlayerManager().getPlayers().put(l_player2.getName(), l_player2);
    d_gameEngine.getPlayerManager().getPlayers().put(l_player3.getName(), l_player3);

    // Add only 1 country
    Country l_country = mock(Country.class);
    when(l_country.getID()).thenReturn("Country1");
    when(l_country.getTroops()).thenReturn(3);
    d_gameEngine.getCountryManager().getCountries().put("Country1", l_country);

    // Clear previous logs if needed
    LogEntryBuffer.getInstance().getLogInfo().setLength(0);

    // Run the actual code
    d_countryAssignment.assignCountries();

    // Fetch the log output
    String l_logs = LogEntryBuffer.getInstance().getLogInfo().toString();

    assertTrue(l_logs.contains("Warning: Not enough countries to assign one per player"),
            "Expected warning message not found in log.");

    }

}

