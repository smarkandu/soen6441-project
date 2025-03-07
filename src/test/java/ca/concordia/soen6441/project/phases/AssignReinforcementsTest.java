package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameEngine;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link AssignReinforcements} class.
 * <p>
 * This test suite ensures that reinforcement allocation is correctly computed
 * based on the number of owned territories and continent bonuses.
 * </p>
 */
class AssignReinforcementsTest {

    private GameEngine d_gameEngine;
    private AssignReinforcements d_assignReinforcements;
    private Player d_mockPlayer;
    private Continent d_mockAsia, d_mockEurope;
    private Map<String, Country> d_mockCountries;

    /**
     * Sets up the test environment before each test case.
     * <p>
     * Mocks the {@link GameEngine}, {@link Player}, {@link Continent}, and {@link Country}
     * objects to simulate the behavior of the game state.
     * </p>
     */
    @BeforeEach
    void setUp() {

        d_gameEngine = mock(GameEngine.class);
        d_assignReinforcements = new AssignReinforcements(d_gameEngine);

        d_mockPlayer = mock(Player.class);
        when(d_mockPlayer.getName()).thenReturn("Tharun");

        d_mockAsia = mock(Continent.class);
        when(d_mockAsia.getID()).thenReturn("Asia");
        when(d_mockAsia.getValue()).thenReturn(7);

        d_mockEurope = mock(Continent.class);
        when(d_mockEurope.getID()).thenReturn("Europe");
        when(d_mockEurope.getValue()).thenReturn(5); // Bonus for controlling Europe

        Map<String, Player> l_mockPlayers = new HashMap<>();
        l_mockPlayers.put(d_mockPlayer.getName(), d_mockPlayer);
        when(d_gameEngine.getPlayers()).thenReturn(l_mockPlayers);

        when(d_gameEngine.getPlayer(anyInt())).thenReturn(d_mockPlayer);

        Map<String, Continent> l_mockContinents = new HashMap<>();
        l_mockContinents.put("Asia", d_mockAsia);
        l_mockContinents.put("Europe", d_mockEurope);
        when(d_gameEngine.getContinents()).thenReturn(l_mockContinents);

        d_mockCountries = new HashMap<>();
    }

    /**
     * Tests reinforcement allocation when a player owns multiple territories and entire continents.
     * <p>
     * The player owns 9 territories, including all of Asia (bonus: 7) and all of Europe (bonus: 5).
     * Expected reinforcements: (9/3) + 7 + 5 = 15.
     * </p>
     */
    @Test
    void testReinforcementArmyCalculation() {
        List<String> l_ownedCountries = Arrays.asList("A1", "A2", "A3", "A4", "A5", "E1", "E2", "E3", "E4");
        when(d_mockPlayer.getOwnedCountries()).thenReturn(l_ownedCountries);

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

        when(d_gameEngine.getCountries()).thenReturn(d_mockCountries);

        d_assignReinforcements.execute();
        System.out.println("Expected: 15 | Actual: " + d_mockPlayer.getReinforcements());

        verify(d_mockPlayer).setReinforcements(15);
    }

    /**
     * Tests reinforcement allocation when a player owns 6 territories distributed across two continents.
     * <p>
     * Since the player does not own all territories in either continent, no continent bonus is applied.
     * Expected reinforcements: max(3, (6/3)) = 3.
     * </p>
     */
    @Test
    void testReinforcementWithoutContinentBonus() {
        List<String> l_ownedCountries = Arrays.asList("C1", "C2", "C3", "C4", "C5", "C6");
        when(d_mockPlayer.getOwnedCountries()).thenReturn(l_ownedCountries);

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

        d_assignReinforcements.execute();
        System.out.println("Expected: 3 | Actual: " + d_mockPlayer.getReinforcements());

        verify(d_mockPlayer).setReinforcements(3);
    }

    /**
     * Tests reinforcement allocation when a player owns fewer than 3 territories.
     * <p>
     * The reinforcement allocation rule specifies that a player should receive at least 3 reinforcements per turn.
     * Expected reinforcements: 3 (minimum allocation).
     * </p>
     */
    @Test
    void testMinimumReinforcementAllocation() {
        List<String> l_ownedCountries = Arrays.asList("C1", "C2");
        when(d_mockPlayer.getOwnedCountries()).thenReturn(l_ownedCountries);

        for (String l_countryId : l_ownedCountries) {
            Country l_country = mock(Country.class);
            when(l_country.getID()).thenReturn(l_countryId);
            when(l_country.getContinent()).thenReturn(d_mockAsia);
            d_mockCountries.put(l_countryId, l_country);
        }

        d_assignReinforcements.execute();
        System.out.println("Expected: 3 | Actual: " + d_mockPlayer.getReinforcements());

        verify(d_mockPlayer).setReinforcements(3);
    }
}
