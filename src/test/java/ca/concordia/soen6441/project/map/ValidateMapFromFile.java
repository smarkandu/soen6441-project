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
        private String id;
        private int value;
        private int numericId;

        public ContinentStub(String id, int value, int numericId) {
            this.id = id;
            this.value = value;
            this.numericId = numericId;
        }

        @Override
        public String getID() {
            return id;
        }

        @Override
        public int getValue() {
            return value;
        }

        @Override
        public int getNumericID() {
            return numericId;
        }

        @Override
        public String toMapString() {
            return id + " " + value;
        }
    }
    static class CountryStub implements Country {
        private String id;
        private List<String> neighborIDs = new ArrayList<>();
        private int numericId;
        private int troops;
        private Player owner;
        private Continent continent;

        public CountryStub(String id, int numericId, Continent continent) {
            this.id = id;
            this.numericId = numericId;
            this.continent = continent;
        }

        @Override
        public String getID() {
            return id;
        }

        @Override
        public List<String> getNeighborIDs() {
            return neighborIDs;
        }

        @Override
        public void addNeighbor(Country p_Neighbor) {
            if (!neighborIDs.contains(p_Neighbor.getID())) {
                neighborIDs.add(p_Neighbor.getID());
            }
        }

        @Override
        public void removeNeighbor(String p_NeighborID) {
            neighborIDs.remove(p_NeighborID);
        }

        @Override
        public int getNumericID() {
            return numericId;
        }

        @Override
        public String toMapString() {
            return id;
        }

        @Override
        public int getTroops() {
            return troops;
        }

        @Override
        public void setTroops(int p_troops) {
            troops = p_troops;
        }

        @Override
        public Player getOwner() {
            return owner;
        }

        @Override
        public void setOwner(Player p_owner) {
            owner = p_owner;
        }

        @Override
        public Continent getContinent() {
            return continent;
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
        SortedMap<String, Continent> d_continents = new TreeMap<>();
        Continent l_northAmerica = new ContinentStub("NorthAmerica", 5,1);
        Continent l_southAmerica = new ContinentStub("SouthAmerica", 4, 2);
        d_continents.put(l_northAmerica.getID(), l_northAmerica);
        d_continents.put(l_southAmerica.getID(), l_southAmerica);

        // Create Countries.
        SortedMap<String, Country> countries = new TreeMap<>();
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
        countries.put(l_canada.getID(), l_canada);
        countries.put(l_unitedStates.getID(), l_unitedStates);
        countries.put(l_mexico.getID(), l_mexico);

        // Create an instance of ValidateMapImpl with the simulated valid map data.
        ValidateMapImpl l_validator = new ValidateMapImpl(countries, d_continents);

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
