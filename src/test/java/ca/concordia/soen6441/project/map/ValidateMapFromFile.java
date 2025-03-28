package ca.concordia.soen6441.project.map;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidateMapFromFile {
    static class ContinentStub implements Continent {
        private String d_id;
        private int d_value;
        private int d_numericId;

        public ContinentStub(String p_id, int p_value, int p_numericId) {
            this.d_id = p_id;
            this.d_value = p_value;
            this.d_numericId = p_numericId;
        }

        @Override
        public String getID() {
            return d_id;
        }

        @Override
        public int getValue() {
            return d_value;
        }

        @Override
        public int getNumericID() {
            return d_numericId;
        }

        @Override
        public String toMapString() {
            return d_id + " " + d_value;
        }
    }
    static class CountryStub implements Country {
        private String d_id;
        private List<String> d_neighborIDs = new ArrayList<>();
        private int d_numericId;
        private int d_troops;
        private Player d_owner;
        private Continent d_continent;

        public CountryStub(String p_id, int p_numericId, Continent p_continent) {
            this.d_id = p_id;
            this.d_numericId = p_numericId;
            this.d_continent = p_continent;
        }

        @Override
        public String getID() {
            return d_id;
        }

        @Override
        public List<String> getNeighborIDs() {
            return d_neighborIDs;
        }

        @Override
        public void addNeighbor(Country p_Neighbor) {
            if (!d_neighborIDs.contains(p_Neighbor.getID())) {
                d_neighborIDs.add(p_Neighbor.getID());
            }
        }

        @Override
        public void removeNeighbor(String p_NeighborID) {
            d_neighborIDs.remove(p_NeighborID);
        }

        @Override
        public int getNumericID() {
            return d_numericId;
        }

        @Override
        public String toMapString() {
            return d_id;
        }

        @Override
        public int getTroops() {
            return d_troops;
        }

        @Override
        public void setTroops(int p_troops) {
            d_troops = p_troops;
        }

        @Override
        public Player getOwner() {
            return d_owner;
        }

        @Override
        public void setOwner(Player p_owner) {
            d_owner = p_owner;
        }

        @Override
        public Continent getContinent() {
            return d_continent;
        }
    }
    private Player d_mockPlayer;
    @BeforeEach
    void setUp() {
        // Create a mock Player using Mockito
        d_mockPlayer = mock(Player.class);
    }


    /**
     * Test for a valid map simulation based on the below valid .map file:
     *
     * [continents]
     * NorthAmerica 5 yellow
     * SouthAmerica 4 yellow
     *
     * [countries]
     * 1 Canada 1 0 0
     * 2 UnitedStates 1 0 0
     * 3 Mexico 2 0 0
     *
     * [borders]
     * 1 3 2
     * 2 1 3
     * 3 1 2
     */
    @Test
    public void testIsMapValid_validMap() {
        // Create Continents.
        SortedMap<String, Continent> l_continents = new TreeMap<>();
        Continent l_northAmerica = new ContinentStub("NorthAmerica", 5,1);
        Continent l_southAmerica = new ContinentStub("SouthAmerica", 4, 2);
        l_continents.put(l_northAmerica.getID(), l_northAmerica);
        l_continents.put(l_southAmerica.getID(), l_southAmerica);

        // Create Countries.
        SortedMap<String, Country> l_countries = new TreeMap<>();
        CountryStub l_canada = new CountryStub("Canada", 1, l_northAmerica);
        CountryStub l_unitedStates = new CountryStub("UnitedStates", 2, l_northAmerica);
        CountryStub l_mexico = new CountryStub("Mexico", 3, l_southAmerica);

        // Set up neighbor relationships based on [borders]:
        // Canada neighbors: 3 and 2
        l_canada.addNeighbor(l_unitedStates);
        l_canada.addNeighbor(l_mexico);

        // UnitedStates neighbors: 1 and 3
        l_unitedStates.addNeighbor(l_canada);
        l_unitedStates.addNeighbor(l_mexico);

        // Mexico neighbors: 1 and 2
        l_mexico.addNeighbor(l_canada);
        l_mexico.addNeighbor(l_unitedStates);

        // Since neighbor relationships are bidirectional, the reverse is also set up.
        l_countries.put(l_canada.getID(), l_canada);
        l_countries.put(l_unitedStates.getID(), l_unitedStates);
        l_countries.put(l_mexico.getID(), l_mexico);

        // Create an instance of ValidateMapImpl with the simulated valid map data.
        ValidateMapImpl l_validator = new ValidateMapImpl(l_countries, l_continents);

        // Assert that the map is valid.
        assertTrue(l_validator.isMapValid(), "The system failed to identify the validity of map");
    }

    /**
     * Test for an invalid map simulation based on below:
     * [continents]
     * Asia 7 yellow
     *
     * [countries]
     *
     * [borders]
     *
     * NOTE: This map is invalid because it contains no countries.
     */
    @Test
    public void testIsMapValid_invalidMap() {
        // Create Continents.
        SortedMap<String, Continent> l_continents = new TreeMap<>();
        Continent l_asia = new ContinentStub("Asia", 7, 1);
        l_continents.put(l_asia.getID(), l_asia);

        // Create an empty SortedMap for Countries.
        SortedMap<String, Country> l_countries = new TreeMap<>();

        // Create an instance of ValidateMapImpl with the simulated invalid map data.
        ValidateMapImpl l_validator = new ValidateMapImpl(l_countries, l_continents);

        // Assert that the map is invalid.
        assertFalse(l_validator.isMapValid(), "The system failed to identify the invalidity of map");
    }
}
