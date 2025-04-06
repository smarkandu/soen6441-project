package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.context.CountryManager;
import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.context.PlayerManager;
import ca.concordia.soen6441.project.gameplay.PlayerImpl;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.phases.Phase;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.log.LogEntryBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link RandomPlayerBehavior}.
 * <p>
 * Verifies that deployment and attackTransfer issue valid orders and log expected actions.
 */
public class RandomPlayerBehaviorTest {

    private GameEngine d_mockGameEngine;
    private PlayerManager d_mockPlayerManager;
    private CountryManager d_mockCountryManager;
    private Phase d_mockPhase;
    private RandomPlayerBehavior d_behavior;

    private PlayerImpl d_player;
    private Country d_country1;
    private Country d_country2;

    /**
     * Sets up mocks and test objects before each test, replicating structure of CountryAssignmentTest.
     */
    @BeforeEach
    void setUp() {
        // Mocks
        d_mockGameEngine = mock(GameEngine.class);
        d_mockPlayerManager = mock(PlayerManager.class);
        d_mockCountryManager = mock(CountryManager.class);
        d_mockPhase = mock(Phase.class);

        GameDriver.setGameEngine(d_mockGameEngine);
        when(d_mockGameEngine.getPlayerManager()).thenReturn(d_mockPlayerManager);
        when(d_mockGameEngine.getCountryManager()).thenReturn(d_mockCountryManager);
        when(d_mockGameEngine.getPhase()).thenReturn(d_mockPhase);
        when(d_mockPhase.getPhaseName()).thenReturn("Issue Orders");

        // Player with Random behavior
        d_behavior = new RandomPlayerBehavior();
        d_player = new PlayerImpl("RandomPlayer", new ArrayList<>(), new ArrayList<>(), d_behavior);

        // Mock Countries
        d_country1 = mock(Country.class);
        d_country2 = mock(Country.class);

        when(d_country1.getID()).thenReturn("C1");
        when(d_country1.getTroops()).thenReturn(5);
        when(d_country1.getNeighborIDs()).thenReturn(Collections.singletonList("C2"));

        when(d_country2.getID()).thenReturn("C2");
        when(d_country2.getTroops()).thenReturn(3);
        when(d_country2.getNeighborIDs()).thenReturn(Collections.singletonList("C1"));

        // Set up map
        SortedMap<String, Country> l_countries = new TreeMap<>();
        l_countries.put("C1", d_country1);
        l_countries.put("C2", d_country2);
        when(d_mockCountryManager.getCountries()).thenReturn(l_countries);

        // Set up player manager
        SortedMap<String, Player> l_players = new TreeMap<>();
        l_players.put(d_player.getName(), d_player);
        when(d_mockPlayerManager.getPlayers()).thenReturn(l_players);

        // Add owned countries
        d_player.getOwnedCountries().add("C1");

        // Reinforcements
        d_player.setReinforcements(5);

        // Clear logs
        LogEntryBuffer.getInstance().getLogInfo().setLength(0);
    }

    /**
     * Tests that deployment issues a valid deploy order and logs the action.
     */
    @Test
    void testDeploymentIssuesOrderAndLogs() {
        // Act
        d_behavior.deployment(d_player);

        // Assert
        verify(d_mockPhase, atLeastOnce()).deploy(eq("C1"), anyInt());

        String l_logs = LogEntryBuffer.getInstance().getLogInfo().toString();
        assertTrue(l_logs.contains("deployment() executed in phase: Issue Orders"),
                "Expected deployment log message not found.");
    }

    /**
     * Tests that attackTransfer issues a valid advance order to a neighbor and logs the action.
     */
    @Test
    void testAttackTransferIssuesOrderAndLogs() {

        // Act
        d_behavior.attackTransfer(d_player);

        // Assert
        verify(d_mockPhase, atLeastOnce()).advance(eq("C1"), eq("C2"), anyInt());

        String l_logs = LogEntryBuffer.getInstance().getLogInfo().toString();
        assertTrue(l_logs.contains("attackTransfer() executed in phase: Issue Orders"),
                "Expected attackTransfer log message not found.");
    }
}
