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
    private CountryImpl country;
    private Continent mockContinent;
    private Player mockPlayer;

    @Before
    public void setUp() {
        mockContinent = new MockContinent("Continent1", 1);
        mockPlayer = new MockPlayer("Player1");
        country = new CountryImpl(1, "Country1", mockContinent, 10, 20, mockPlayer);
    }

    @Test
    public void testGetID() {
        assertEquals("Country1", country.getID());
    }

    @Test
    public void testGetNumericID() {
        assertEquals(1, country.getNumericID());
    }

    @Test
    public void testGetNeighborIDsInitiallyEmpty() {
        assertTrue(country.getNeighborIDs().isEmpty());
    }

    @Test
    public void testAddNeighbor() {
        Country neighbor = new CountryImpl(2, "Country2", mockContinent, 15, 25, mockPlayer);
        country.addNeighbor(neighbor);
        List<String> neighbors = country.getNeighborIDs();
        assertEquals(1, neighbors.size());
        assertEquals("Country2", neighbors.get(0));
    }

    @Test
    public void testRemoveNeighbor() {
        Country neighbor = new CountryImpl(2, "Country2", mockContinent, 15, 25, mockPlayer);
        country.addNeighbor(neighbor);
        country.removeNeighbor("Country2");
        assertTrue(country.getNeighborIDs().isEmpty());
    }

    @Test
    public void testGetTroops() {
        assertEquals(0, country.getTroops());
        country.setTroops(10);
        assertEquals(10, country.getTroops());
    }

    @Test
    public void testToStringFormat() {
        String expected = "Country1,10,20,Continent1";
        assertTrue(country.toString().startsWith(expected));
    }

    @Test
    public void testToMapStringFormat() {
        String expected = "1 Country1 1 10 20";
        assertEquals(expected, country.toMapString());
    }

    static class MockContinent implements Continent {
        private final String id;
        private final int numericId;

        public MockContinent(String id, int numericId) {
            this.id = id;
            this.numericId = numericId;
        }

        @Override
        public String getID() {
            return id;
        }

        @Override
        public int getValue() {
            return 0;
        }

        @Override
        public int getNumericID() {
            return numericId;
        }

        @Override
        public String toMapString() {
            return "";
        }
    }

    static class MockPlayer implements Player {
        private final String name;

        public MockPlayer(String name) {
            this.name = name;
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
            return name;
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
    }
}
