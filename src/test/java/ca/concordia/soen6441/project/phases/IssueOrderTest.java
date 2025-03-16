package ca.concordia.soen6441.project.phases;

import static org.mockito.Mockito.*;

import ca.concordia.soen6441.project.context.CountryManager;
import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.context.PlayerManager;
import ca.concordia.soen6441.project.gameplay.orders.Deploy;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

/**
 * Unit tests for the {@link IssueOrder} class.
 * This class tests the behavior of the deploy method under different conditions
 * using mocked dependencies.
 */
class IssueOrderTest {
    private IssueOrder d_issueOrder;
    private Player d_player;
    private Country d_country;

    /**
     * Sets up mock dependencies before each test case execution.
     */
    @BeforeEach
    void setUp() {
        // Mock dependencies required for "deploy" method
        GameEngine l_gameEngine = mock(GameEngine.class);
        CountryManager l_mockCountryManager = mock(CountryManager.class);
        when(l_gameEngine.getCountryManager()).thenReturn(l_mockCountryManager);
        PlayerManager l_mockPlayerManager = mock(PlayerManager.class);
        when(l_gameEngine.getPlayerManager()).thenReturn(l_mockPlayerManager);

        d_player = mock(Player.class);
        d_country = mock(Country.class);

        // Mock country map in game engine
        TreeMap<String, Country> l_countries = new TreeMap<>();
        l_countries.put("Country1", d_country);
        when(l_gameEngine.getCountryManager().getCountries()).thenReturn(l_countries);

        // Mock player behavior
        when(l_gameEngine.getPlayerManager().getPlayer(0)).thenReturn(d_player);

        // Initialize IssueOrder instance
        d_issueOrder = new IssueOrder(l_gameEngine, 0);
    }

    /**
     * Tests the deploy method when the player owns the country and has enough reinforcements.
     * Verifies that an order is issued successfully.
     */
    @Test
    void testDeploy_SuccessfulDeployment() {
        // Player owns the country and has enough reinforcements
        when(d_country.getOwner()).thenReturn(d_player);

        // Mock scenario where # of troops available is sufficient for deployment requested (i.e. 10 - 3 > 5)
        when(d_player.getReinforcements()).thenReturn(10);
        when(d_player.getNumberOfTroopsOrderedToDeploy()).thenReturn(3);

        // Call deploy method with an amount that is supported by the # of troops available
        d_issueOrder.deploy("Country1", 5);

        // Verify that issue_order is called with the Deploy object
        verify(d_player, times(1)).issue_order(any(Deploy.class));
    }

    /**
     * Tests the deploy method when the player does not have enough reinforcements.
     * Ensures that an order is NOT issued.
     */
    @Test
    void testDeploy_NotEnoughTroopsAvailable() {
        // Player owns the country but doesn't have enough reinforcements
        when(d_country.getOwner()).thenReturn(d_player);

        // Mock scenario where # of troops available is not sufficient for deployment requested (i.e. 5 - 3 < 4)
        when(d_player.getReinforcements()).thenReturn(5);
        when(d_player.getNumberOfTroopsOrderedToDeploy()).thenReturn(3);

        // Call deploy method with more troops than available
        d_issueOrder.deploy("Country1", 4);

        // Ensure that issue_order is NOT called due to insufficient troops
        verify(d_player, never()).issue_order(any(Deploy.class));
    }

    /**
     * Tests the deploy method when the player does not own the target country.
     * Ensures that an order is NOT issued.
     */
    @Test
    void testDeploy_CountryNotOwned() {
        // Mock when a Player does not own the country
        Player l_anotherPlayer = mock(Player.class);
        when(d_country.getOwner()).thenReturn(l_anotherPlayer);

        // Call deploy method for a country not owned
        d_issueOrder.deploy("Country1", 5);

        // Ensure that issue_order is NOT called because the player doesn't own the country
        verify(d_player, never()).issue_order(any(Deploy.class));
    }
}