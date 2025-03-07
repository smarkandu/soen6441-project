package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameEngine;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;

class AssignReinforcementsTest {

    private GameEngine gameEngine;
    private AssignReinforcements assignReinforcements;
    private Player mockPlayer;
    private Continent mockAsia, mockEurope;
    private Map<String, Country> mockCountries;

    @BeforeEach
    void setUp() {

        gameEngine = mock(GameEngine.class);
        assignReinforcements = new AssignReinforcements(gameEngine);

        mockPlayer = mock(Player.class);
        when(mockPlayer.getName()).thenReturn("Tharun");

        mockAsia = mock(Continent.class);
        when(mockAsia.getID()).thenReturn("Asia");
        when(mockAsia.getValue()).thenReturn(7);


        mockEurope = mock(Continent.class);
        when(mockEurope.getID()).thenReturn("Europe");
        when(mockEurope.getValue()).thenReturn(5); // Bonus for controlling Europe

        Map<String, Player> mockPlayers = new HashMap<>();
        mockPlayers.put(mockPlayer.getName(), mockPlayer);
        when(gameEngine.getPlayers()).thenReturn(mockPlayers);


        when(gameEngine.getPlayer(anyInt())).thenReturn(mockPlayer);

        Map<String, Continent> mockContinents = new HashMap<>();
        mockContinents.put("Asia", mockAsia);
        mockContinents.put("Europe", mockEurope);
        when(gameEngine.getContinents()).thenReturn(mockContinents);

        // ✅ Initialize country map
        mockCountries = new HashMap<>();
    }


    @Test
    void testReinforcementArmyCalculation() {
        List<String> ownedCountries = Arrays.asList("A1", "A2", "A3", "A4", "A5", "E1", "E2", "E3", "E4");
        when(mockPlayer.getOwnedCountries()).thenReturn(ownedCountries);

        for (String countryId : ownedCountries) {
            Country country = mock(Country.class);
            when(country.getID()).thenReturn(countryId);
            if (ownedCountries.indexOf(countryId) < 5) {
                when(country.getContinent()).thenReturn(mockAsia);
            } else {
                when(country.getContinent()).thenReturn(mockEurope);
            }
            mockCountries.put(countryId, country);
        }

        when(gameEngine.getCountries()).thenReturn(mockCountries);

        assignReinforcements.execute();
        System.out.println("Expected: 15 | Actual: " + mockPlayer.getReinforcements());

        verify(mockPlayer).setReinforcements(15);
    }

    @Test
    void testReinforcementWithoutContinentBonus() {
        List<String> ownedCountries = Arrays.asList("C1", "C2", "C3", "C4", "C5", "C6");
        when(mockPlayer.getOwnedCountries()).thenReturn(ownedCountries);


        for (int i = 0; i < ownedCountries.size(); i++) {
            Country country = mock(Country.class);
            when(country.getID()).thenReturn(ownedCountries.get(i));


            if (i < 3) {
                when(country.getContinent()).thenReturn(mockAsia);
            } else {
                when(country.getContinent()).thenReturn(mockEurope);
            }

            mockCountries.put(ownedCountries.get(i), country);
        }


        assignReinforcements.execute();
        System.out.println("Expected: 3 | Actual: " + mockPlayer.getReinforcements());


        verify(mockPlayer).setReinforcements(3);
    }

    @Test
    void testMinimumReinforcementAllocation() {
        List<String> ownedCountries = Arrays.asList("C1", "C2");
        when(mockPlayer.getOwnedCountries()).thenReturn(ownedCountries);


        for (String countryId : ownedCountries) {
            Country country = mock(Country.class);
            when(country.getID()).thenReturn(countryId);
            when(country.getContinent()).thenReturn(mockAsia);
            mockCountries.put(countryId, country);
        }


        assignReinforcements.execute();


        System.out.println("Expected: 3 | Actual: " + mockPlayer.getReinforcements());

        verify(mockPlayer).setReinforcements(3);
    }

}
