package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.context.CountryManager;
import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.context.PlayerManager;
import ca.concordia.soen6441.project.gameplay.orders.Advance;
import ca.concordia.soen6441.project.gameplay.orders.Deploy;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link IssueOrder} class.
 * This class tests the behavior of the IssueOrder methods under different conditions
 * using mocked dependencies.
 */
public class IssueOrderTest {
    private IssueOrder d_issueOrder;
    private Player d_player;
    private Country d_country;
    private Country d_country2;

    private final ByteArrayOutputStream d_outContent = new ByteArrayOutputStream();

    @Mock
    private CountryManager d_countryManager;

    @Mock
    private Country d_countryFrom;

    @Mock
    private Country d_countryTo;

    @Mock
    private ca.concordia.soen6441.project.interfaces.context.HandOfCardsContext d_mockHandOfCardsManager;

    @Mock
    private ca.concordia.soen6441.project.context.hand.CardManager<ca.concordia.soen6441.project.gameplay.cards.AirliftCard> d_mockAirliftCardManager;

    /**
     * Sets up mock dependencies before each test case execution.
     */
    @BeforeEach
    void setUp() {
        // Mock dependencies required for "deploy" method
        GameEngine l_gameEngine = mock(GameEngine.class);
        MockitoAnnotations.openMocks(this);
        CountryManager l_mockCountryManager = mock(CountryManager.class);
        when(l_gameEngine.getCountryManager()).thenReturn(l_mockCountryManager);
        PlayerManager l_mockPlayerManager = mock(PlayerManager.class);
        when(l_gameEngine.getPlayerManager()).thenReturn(l_mockPlayerManager);

        d_player = mock(Player.class);
        d_country = mock(Country.class);
        d_country2 = mock(Country.class);

        // Setup mock AirliftCard manager for airlift tests
        when(d_player.getHandOfCardsManager()).thenReturn(d_mockHandOfCardsManager);
        when(d_mockHandOfCardsManager.getAirLiftCardManager()).thenReturn(d_mockAirliftCardManager);
        when(d_mockAirliftCardManager.hasCard()).thenReturn(true);

        // Mock country map in game engine
        TreeMap<String, Country> l_countries = new TreeMap<>();
        l_countries.put("Country1", d_country);
        l_countries.put("Country2", d_country2);
        when(d_country2.getID()).thenReturn("Country2");
        when(l_gameEngine.getCountryManager().getCountries()).thenReturn(l_countries);

        // Mock player behavior
        when(l_gameEngine.getPlayerManager().getPlayer(0)).thenReturn(d_player);

        // Initialize IssueOrder instance
        d_issueOrder = new IssueOrder(l_gameEngine, 0);

        System.setOut(new PrintStream(d_outContent));
    }

    @Test
    void testDeploy_SuccessfulDeployment() {
        when(d_country.getOwner()).thenReturn(d_player);
        when(d_player.getReinforcements()).thenReturn(10);
        when(d_player.getNumberOfTroopsOrderedToDeploy()).thenReturn(3);
        d_issueOrder.deploy("Country1", 5);
        verify(d_player, times(1)).addToOrders(any(Deploy.class));
    }

    @Test
    void testDeploy_NotEnoughTroopsAvailable() {
        when(d_country.getOwner()).thenReturn(d_player);
        when(d_player.getReinforcements()).thenReturn(5);
        when(d_player.getNumberOfTroopsOrderedToDeploy()).thenReturn(3);
        d_issueOrder.deploy("Country1", 4);
        verify(d_player, never()).issue_order(any(Deploy.class));
    }

    @Test
    void testDeploy_CountryNotOwned() {
        Player l_anotherPlayer = mock(Player.class);
        when(d_country.getOwner()).thenReturn(l_anotherPlayer);
        d_issueOrder.deploy("Country1", 5);
        verify(d_player, never()).issue_order(any(Deploy.class));
    }

    @Test
    void testAdvance_Error_SameSourceAndTarget() {
        d_issueOrder.advance("Country1", "Country1", 5);
        verify(d_player, never()).issue_order(any(Advance.class));
        assertTrue(d_outContent.toString().contains("ERROR: Source and target territories cannot be the same."));
    }

    @Test
    void testAdvance_Error_TroopsLeftToDeploy() {
        when(d_player.getReinforcements()).thenReturn(5);
        when(d_player.getNumberOfTroopsOrderedToDeploy()).thenReturn(0);
        when(d_player.getNumberOfTroopsOrderedToAdvance(d_country)).thenReturn(0);
        when(d_country.getTroops()).thenReturn(10);
        when(d_country.getOwner()).thenReturn(d_player);
        List<String> l_neighbors = new ArrayList<>();
        l_neighbors.add("Country2");
        when(d_country.getNeighborIDs()).thenReturn(l_neighbors);
        d_issueOrder.advance("Country1", "Country2", 5);
        verify(d_player, never()).issue_order(any(Advance.class));
        assertTrue(d_outContent.toString().contains("ERROR: You still have 5 left to deploy!"));
    }

    @Test
    void testAdvance_Error_PlayerDoesNotOwnCountry() {
        when(d_countryFrom.getOwner()).thenReturn(mock(Player.class));
        d_issueOrder.advance("Country1", "Country2", 5);
        verify(d_player, never()).issue_order(any(Advance.class));
        assertTrue(d_outContent.toString().contains("ERROR: Player"));
    }

    @Test
    void testAdvance_Error_NotEnoughTroops() {
        when(d_player.getNumberOfTroopsOrderedToAdvance(d_country)).thenReturn(0);
        when(d_country.getTroops()).thenReturn(3);
        when(d_country.getOwner()).thenReturn(d_player);
        List<String> l_neighbors = new ArrayList<>();
        l_neighbors.add("Country2");
        when(d_country.getNeighborIDs()).thenReturn(l_neighbors);
        d_issueOrder.advance("Country1", "Country2", 5);
        verify(d_player, never()).issue_order(any(Advance.class));
        assertTrue(d_outContent.toString().contains("ERROR: Only 3 left to advance!"));
    }

    @Test
    void testAdvance_Error_NotANeighbor() {
        when(d_country.getOwner()).thenReturn(d_player);
        List<String> l_neighbors = new ArrayList<>();
        when(d_country.getNeighborIDs()).thenReturn(l_neighbors);
        when(d_player.getNumberOfTroopsOrderedToAdvance(d_country)).thenReturn(0);
        when(d_country.getTroops()).thenReturn(10);
        d_issueOrder.advance("Country1", "Country2", 5);
        verify(d_player, never()).issue_order(any(Advance.class));
        assertTrue(d_outContent.toString().contains("ERROR: Country2 is not a neighbor"));
    }

    @Test
    void testAirlift_SourceAndTargetSame_NoOrderIssued() {
        when(d_country.getOwner()).thenReturn(d_player);
        when(d_player.getReinforcements()).thenReturn(0);
        when(d_player.getNumberOfTroopsOrderedToDeploy()).thenReturn(0);

        TreeMap<String, Country> l_countries = new TreeMap<>();
        l_countries.put("Country1", d_country);

        GameEngine l_gameEngine = mock(GameEngine.class);
        CountryManager l_countryManager = mock(CountryManager.class);
        PlayerManager l_playerManager = mock(PlayerManager.class);

        when(l_gameEngine.getCountryManager()).thenReturn(l_countryManager);
        when(l_gameEngine.getPlayerManager()).thenReturn(l_playerManager);
        when(l_countryManager.getCountries()).thenReturn(l_countries);
        when(l_playerManager.getPlayer(0)).thenReturn(d_player);

        IssueOrder l_issueOrder = new IssueOrder(l_gameEngine, 0);
        l_issueOrder.airlift("Country1", "Country1", 5);
        verify(d_player, never()).issue_order(any());
    }

    /**
     * Tests that airlift fails when the source country is not owned by the player.
     */
    @Test
    void testAirlift_SourceNotOwnedByPlayer_NoOrderIssued() {
        Player l_otherPlayer = mock(Player.class);
        when(d_country.getOwner()).thenReturn(l_otherPlayer);
        d_issueOrder.airlift("Country1", "Country2", 5);
        verify(d_player, never()).issue_order(any());
    }

    /**
     * Tests that airlift fails when the target country is not owned by the player.
     */
    @Test
    void testAirlift_TargetNotOwnedByPlayer_NoOrderIssued() {
        when(d_country.getOwner()).thenReturn(d_player);
        when(d_country2.getOwner()).thenReturn(mock(Player.class));
        d_issueOrder.airlift("Country1", "Country2", 5);
        verify(d_player, never()).issue_order(any());
    }

    /**
     * Tests the golden case where the player owns both countries and airlift is valid.
     */
    @Test
    void testAirlift_ValidOrder_OrderIssued() {
        when(d_country.getOwner()).thenReturn(d_player);
        when(d_country2.getOwner()).thenReturn(d_player);
        when(d_player.getReinforcements()).thenReturn(0);
        when(d_player.getNumberOfTroopsOrderedToDeploy()).thenReturn(0);
        when(d_country.getTroops()).thenReturn(10);
        d_issueOrder.airlift("Country1", "Country2", 5);
        verify(d_player, times(1)).addToOrders(any());
    }
}
