package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.context.ContinentManager;
import ca.concordia.soen6441.project.context.CountryManager;
import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.context.PlayerManager;
import ca.concordia.soen6441.project.gameplay.PlayerImpl;
import ca.concordia.soen6441.project.gameplay.behaviour.HumanPlayerBehavior;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link AssignReinforcements} class.
 * This test class validates the reinforcement allocation logic
 * for players based on their owned countries and continent control.
 */
public class AssignReinforcementsTest {

    /** Instance of the phase under test. */
    private AssignReinforcements d_assignReinforcements;

    /** Mocked continents used for testing. */
    private Continent d_mockAsia, d_mockEurope;

    /** Mocked countries owned by the player. */
    private SortedMap<String, Country> d_mockCountries;

    /** A real player object used in tests. */
    private PlayerImpl d_realPlayer;

    /**
     * Mock PlayerManager
     */
    private PlayerManager d_playerManager;

    /**
     * Sets up the mocked game environment before each test.
     * Initializes the game context, mock continents, and player/continent managers.
     */
    @BeforeEach
    void setUp() {
        d_mockCountries = new TreeMap<>();
        GameEngine l_mockGameEngine = mock(GameEngine.class);
        GameDriver.setGameEngine(l_mockGameEngine);
        CountryManager l_mockCountryManager = mock(CountryManager.class);
        when(GameDriver.getGameEngine().getCountryManager()).thenReturn(l_mockCountryManager);
        PlayerManager l_mockPlayerManager = mock(PlayerManager.class);
        when(GameDriver.getGameEngine().getPlayerManager()).thenReturn(l_mockPlayerManager);

        d_assignReinforcements = new AssignReinforcements();

        SortedMap<String, Continent> l_mockContinents = new TreeMap<>();

        Continent l_realAsia = mock(Continent.class);
        when(l_realAsia.getID()).thenReturn("Asia");
        when(l_realAsia.getValue()).thenReturn(7);

        Continent l_realEurope = mock(Continent.class);
        when(l_realEurope.getID()).thenReturn("Europe");
        when(l_realEurope.getValue()).thenReturn(5);

        l_mockContinents.put("Asia", l_realAsia);
        l_mockContinents.put("Europe", l_realEurope);

        d_mockAsia = l_mockContinents.get("Asia");
        d_mockEurope = l_mockContinents.get("Europe");
        d_playerManager = mock(PlayerManager.class);

        ContinentManager l_mockContinentManager = mock(ContinentManager.class);
        when(GameDriver.getGameEngine().getContinentManager()).thenReturn(l_mockContinentManager);
        when(GameDriver.getGameEngine().getContinentManager().getContinents()).thenReturn(l_mockContinents);
    }

    /**
     * Tests reinforcement calculation when a player controls an entire continent.
     * In this scenario, the player owns all countries in Asia (bonus = 7)
     * and all countries in Europe (bonus = 5), in addition to the base reinforcement.
     * Expected reinforcement = base + Asia bonus + Europe bonus = 3 + 7 + 5 = 15.
     */
    @Test
    void testReinforcementArmyCalculation() {
        List<String> l_ownedCountries = Arrays.asList("A1", "A2", "A3", "A4", "A5", "E1", "E2", "E3", "E4");
        d_realPlayer = new PlayerImpl("Tharun", new ArrayList<>(l_ownedCountries), new ArrayList<>(),
                new HumanPlayerBehavior());
        Map<String, Player> l_mockPlayers = new HashMap<>();
        l_mockPlayers.put(d_realPlayer.getName(), d_realPlayer);
        when(GameDriver.getGameEngine().getPlayerManager().getPlayers()).thenReturn(l_mockPlayers);
        when(GameDriver.getGameEngine().getPlayerManager().getPlayer(anyInt())).thenReturn(d_realPlayer);

        for (String l_countryId : l_ownedCountries) {
            Country l_country = mock(Country.class);
            when(l_country.getID()).thenReturn(l_countryId);
            if (l_ownedCountries.indexOf(l_countryId) < 5) {
                when(l_country.getContinent()).thenReturn(d_mockAsia);
            } else {
                when(l_country.getContinent()).thenReturn(d_mockEurope);
            }
            d_mockCountries.put(l_countryId, l_country);
        }

        when(GameDriver.getGameEngine().getCountryManager().getCountries()).thenReturn(d_mockCountries);

        d_assignReinforcements.execute();

        assertEquals(15, d_realPlayer.getReinforcements());
    }

    /**
     * Tests reinforcement calculation without any continent bonus.
     * Player owns only partial countries in Asia and Europe, not the entire continent.
     * Expected reinforcement = floor(totalOwnedCountries / 3), with minimum 3.
     */
    @Test
    void testReinforcementWithoutContinentBonus() {
        d_mockCountries = new TreeMap<>();
        List<String> l_ownedCountries = Arrays.asList("A1", "A2", "A3", "E1", "E2", "E3");

        d_realPlayer = new PlayerImpl("Tharun", new ArrayList<>(l_ownedCountries), new ArrayList<>(),
                new HumanPlayerBehavior());
        Map<String, Player> l_mockPlayers = new HashMap<>();
        l_mockPlayers.put(d_realPlayer.getName(), d_realPlayer);
        when(GameDriver.getGameEngine().getPlayerManager().getPlayers()).thenReturn(l_mockPlayers);
        when(GameDriver.getGameEngine().getPlayerManager().getPlayer(anyInt())).thenReturn(d_realPlayer);

        for (int l_i = 0; l_i < l_ownedCountries.size(); l_i++) {
            Country l_country = mock(Country.class);
            when(l_country.getID()).thenReturn(l_ownedCountries.get(l_i));
            if (l_i < 3) {
                when(l_country.getContinent()).thenReturn(d_mockAsia);
            } else {
                when(l_country.getContinent()).thenReturn(d_mockEurope);
            }
            d_mockCountries.put(l_ownedCountries.get(l_i), l_country);
        }

        // Dummy unowned countries
        Country l_dummyAsia = mock(Country.class);
        when(l_dummyAsia.getID()).thenReturn("A_X");
        when(l_dummyAsia.getContinent()).thenReturn(d_mockAsia);
        d_mockCountries.put("A_X", l_dummyAsia);

        Country l_dummyEurope = mock(Country.class);
        when(l_dummyEurope.getID()).thenReturn("E_X");
        when(l_dummyEurope.getContinent()).thenReturn(d_mockEurope);
        d_mockCountries.put("E_X", l_dummyEurope);

        when(GameDriver.getGameEngine().getCountryManager().getCountries()).thenReturn(d_mockCountries);

        d_assignReinforcements.execute();

        assertEquals(3, d_realPlayer.getReinforcements());
    }

    /**
     * Tests the minimum reinforcement rule.
     * Even with fewer than 9 countries, a player should receive a minimum of 3 reinforcements.
     * This test ensures the lower bound logic is enforced correctly.
     */
    @Test
    void testMinimumReinforcementAllocation() {
        d_mockCountries = new TreeMap<>();
        List<String> l_ownedCountries = Arrays.asList("A1", "E1");

        d_realPlayer = new PlayerImpl("Tharun", new ArrayList<>(l_ownedCountries), new ArrayList<>(),
                new HumanPlayerBehavior());
        Map<String, Player> l_mockPlayers = new HashMap<>();
        l_mockPlayers.put(d_realPlayer.getName(), d_realPlayer);
        when(GameDriver.getGameEngine().getPlayerManager().getPlayers()).thenReturn(l_mockPlayers);
        when(GameDriver.getGameEngine().getPlayerManager().getPlayer(anyInt())).thenReturn(d_realPlayer);

        for (String l_countryId : l_ownedCountries) {
            Country l_country = mock(Country.class);
            when(l_country.getID()).thenReturn(l_countryId);
            when(l_country.getContinent()).thenReturn(l_countryId.startsWith("A") ? d_mockAsia : d_mockEurope);
            d_mockCountries.put(l_countryId, l_country);
        }

        // Dummy unowned countries
        Country l_dummyAsia = mock(Country.class);
        when(l_dummyAsia.getID()).thenReturn("A_X");
        when(l_dummyAsia.getContinent()).thenReturn(d_mockAsia);
        d_mockCountries.put("A_X", l_dummyAsia);

        Country l_dummyEurope = mock(Country.class);
        when(l_dummyEurope.getID()).thenReturn("E_X");
        when(l_dummyEurope.getContinent()).thenReturn(d_mockEurope);
        d_mockCountries.put("E_X", l_dummyEurope);

        when(GameDriver.getGameEngine().getCountryManager().getCountries()).thenReturn(d_mockCountries);

        d_assignReinforcements.execute();

        assertEquals(3, d_realPlayer.getReinforcements());
    }
}