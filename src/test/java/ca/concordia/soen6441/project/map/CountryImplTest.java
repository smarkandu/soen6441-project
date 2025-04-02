package ca.concordia.soen6441.project.map;

import ca.concordia.soen6441.project.context.PlayerManager;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.HandOfCardsContext;
import ca.concordia.soen6441.project.interfaces.gameplay.behavior.PlayerBehavior;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testclass to test CountryImpl
 */
public class CountryImplTest {
    private CountryImpl d_country;
    private Continent d_mockContinent;
    private Player d_mockPlayer;

    /**
     * Setup before each test
     */
    @BeforeEach
    public void setUp() {
        d_mockContinent = new MockContinent("Continent1", 1);
        d_mockPlayer = new MockPlayer("Player1");
        d_country = new CountryImpl(1, "Country1", d_mockContinent, 10, 20, d_mockPlayer);
    }

    /**
     * Testcase for GetID
     */
    @Test
    public void testGetID() {
        assertEquals("Country1", d_country.getID());
    }

    /**
     * Testcase for GetNumericID
     */
    @Test
    public void testGetNumericID() {
        assertEquals(1, d_country.getNumericID());
    }

    /**
     * Testcase for adding a Neighbor
     */
    @Test
    public void testAddNeighbor() {
        Country l_neighbor = new CountryImpl(2, "Country2", d_mockContinent, 15, 25, d_mockPlayer);
        d_country.addNeighbor(l_neighbor);
        List<String> l_neighbors = d_country.getNeighborIDs();
        assertEquals(1, l_neighbors.size());
        assertEquals("Country2", l_neighbors.get(0));
    }

    /**
     * Testcase for removing a neighbor
     */
    @Test
    public void testRemoveNeighbor() {
        Country l_neighbor = new CountryImpl(2, "Country2", d_mockContinent, 15, 25, d_mockPlayer);
        d_country.addNeighbor(l_neighbor);
        d_country.removeNeighbor("Country2");
        assertTrue(d_country.getNeighborIDs().isEmpty());
    }

    /**
     * Testcase for GetTroops
     */
    @Test
    public void testGetTroops() {
        assertEquals(0, d_country.getTroops());
        d_country.setTroops(10);
        assertEquals(10, d_country.getTroops());
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

    /**
     * Mocked Class representing a Player
     */
    static class MockPlayer implements Player {
        private final String d_name;

        /**
         * Constructor
         * @param p_name Name of player
         */
        public MockPlayer(String p_name) {
            this.d_name = p_name;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<String> getOwnedCountries() {
            return List.of();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<Order> getOrders() {
            return List.of();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void issue_order() {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Order next_order() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getName() {
            return d_name;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getTotalNumberOfReinforcementsPerTurn() {
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void addOwnedCountry(Country p_country) {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void removeOwnedCountry(Country p_country) {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getReinforcements() {
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setReinforcements(int p_reinforcements) {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getNumberOfTroopsOrderedToDeploy() {
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getNumberOfTroopsOrderedToAdvance(Country p_countryFrom) {
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public HandOfCardsContext getHandOfCardsManager() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void addNegotiatedPlayer(Player p_player) {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNegotiatedWith(Player p_player) {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void resetNegotiatedPlayers() {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<Player> getNegotiatedPlayers() {
            return List.of();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void removeNegotiatedPlayer(Player p_player) {

        }

        @Override
        public PlayerBehavior getPlayerBehavior() {
            return null;
        }

        @Override
        public PlayerManager getPlayerManager() {
            return null;
        }

        @Override
        public void addToOrders(Order p_newOrder) {

        }
    }
}
