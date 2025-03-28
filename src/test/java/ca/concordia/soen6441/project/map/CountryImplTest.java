package ca.concordia.soen6441.project.map;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.HandOfCardsContext;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class CountryImplTest {
    private CountryImpl d_country;
    private Continent d_mockContinent;
    private Player d_mockPlayer;

    @Before
    public void setUp() {
        d_mockContinent = new MockContinent("Continent1", 1);
        d_mockPlayer = new MockPlayer("Player1");
        d_country = new CountryImpl(1, "Country1", d_mockContinent, 10, 20, d_mockPlayer);
    }

    @Test
    public void testGetID() {
        assertEquals("Country1", d_country.getID());
    }

    @Test
    public void testGetNumericID() {
        assertEquals(1, d_country.getNumericID());
    }

    @Test
    public void testGetNeighborIDsInitiallyEmpty() {
        assertTrue(d_country.getNeighborIDs().isEmpty());
    }

    @Test
    public void testAddNeighbor() {
        Country l_neighbor = new CountryImpl(2, "Country2", d_mockContinent, 15, 25, d_mockPlayer);
        d_country.addNeighbor(l_neighbor);
        List<String> l_neighbors = d_country.getNeighborIDs();
        assertEquals(1, l_neighbors.size());
        assertEquals("Country2", l_neighbors.get(0));
    }

    @Test
    public void testRemoveNeighbor() {
        Country l_neighbor = new CountryImpl(2, "Country2", d_mockContinent, 15, 25, d_mockPlayer);
        d_country.addNeighbor(l_neighbor);
        d_country.removeNeighbor("Country2");
        assertTrue(d_country.getNeighborIDs().isEmpty());
    }

    @Test
    public void testGetTroops() {
        assertEquals(0, d_country.getTroops());
        d_country.setTroops(10);
        assertEquals(10, d_country.getTroops());
    }

    @Test
    public void testToStringFormat() {
        String expected = "Country1,10,20,Continent1";
        assertTrue(d_country.toString().startsWith(expected));
    }

    @Test
    public void testToMapStringFormat() {
        String expected = "1 Country1 1 10 20";
        assertEquals(expected, d_country.toMapString());
    }

    static class MockContinent implements Continent {
        private final String d_id;
        private final int d_numericId;

        public MockContinent(String p_id, int p_numericId) {
            this.d_id = p_id;
            this.d_numericId = p_numericId;
        }

        @Override
        public String getID() {
            return d_id;
        }

        @Override
        public int getValue() {
            return 0;
        }

        @Override
        public int getNumericID() {
            return d_numericId;
        }

        @Override
        public String toMapString() {
            return "";
        }
    }

    static class MockPlayer implements Player {
        private final String d_name;

        public MockPlayer(String p_name) {
            this.d_name = p_name;
        }

        @Override
        public List<String> getOwnedCountries() {
            return List.of();
        }

        @Override
        public List<Order> getOrders() {
            return List.of();
        }

        @Override
        public void issue_order(Order p_order) {

        }

        @Override
        public Order next_order() {
            return null;
        }

        @Override
        public String getName() {
            return d_name;
        }

        @Override
        public int getTotalNumberOfReinforcementsPerTurn() {
            return 0;
        }

        @Override
        public void addOwnedCountry(Country p_country) {

        }

        @Override
        public void removeOwnedCountry(Country p_country) {

        }

        @Override
        public int getReinforcements() {
            return 0;
        }

        @Override
        public void setReinforcements(int p_reinforcements) {

        }

        @Override
        public int getNumberOfTroopsOrderedToDeploy() {
            return 0;
        }

        @Override
        public int getNumberOfTroopsOrderedToAdvance(Country p_countryFrom) {
            return 0;
        }

        @Override
        public HandOfCardsContext getHandOfCardsManager() {
            return null;
        }

        @Override
        public void addNegotiatedPlayer(Player p_player) {

        }

        @Override
        public boolean hasNegotiatedWith(Player p_player) {
            return false;
        }

        @Override
        public void resetNegotiatedPlayers() {

        }

        @Override
        public List<Player> getNegotiatedPlayers() {
            return List.of();
        }

        @Override
        public void removeNegotiatedPlayer(Player p_player) {

        }
    }
}
