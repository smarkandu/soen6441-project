package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.context.CountryManager;
import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import org.junit.jupiter.api.Test;

import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link OrderExecution} class.
 * <p>
 * This test class verifies the correctness of game-winning logic based on
 * ownership of all countries by a single player.
 */
public class OrderExecutionTest {

    /**
     * Tests that a player is correctly recognized as the winner
     * when they own all countries on the map.
     */
    @Test
    void testGameWonByPlayer() {
        GameEngine l_mockGame = mock(GameEngine.class);
        CountryManager l_mockCountryManager = mock(CountryManager.class);
        Player l_mockPlayer = mock(Player.class);
        when(l_mockPlayer.getName()).thenReturn("PlayerOne");

        Country l_country1 = mock(Country.class);
        when(l_country1.getOwner()).thenReturn(l_mockPlayer);

        Country l_country2 = mock(Country.class);
        when(l_country2.getOwner()).thenReturn(l_mockPlayer);

        SortedMap<String, Country> l_mockCountries = new TreeMap<>();
        l_mockCountries.put("C1", l_country1);
        l_mockCountries.put("C2", l_country2);

        when(l_mockGame.getCountryManager()).thenReturn(l_mockCountryManager);
        when(l_mockCountryManager.getCountries()).thenAnswer(inv -> l_mockCountries);

        OrderExecution l_orderExecution = new OrderExecution();
        String l_winner = l_orderExecution.gameWonBy();

        assertEquals("PlayerOne", l_winner);
    }

    /**
     * Tests that no player is considered the winner if countries
     * are owned by multiple players.
     */
    @Test
    void testGameNotWonByAnyPlayer() {
        GameEngine l_mockGame = mock(GameEngine.class);
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

        when(l_mockGame.getCountryManager()).thenReturn(l_mockCountryManager);
        when(l_mockCountryManager.getCountries()).thenAnswer(inv -> l_mockCountries);

        OrderExecution l_orderExecution = new OrderExecution();
        String l_winner = l_orderExecution.gameWonBy();

        assertNull(l_winner);
    }
}
