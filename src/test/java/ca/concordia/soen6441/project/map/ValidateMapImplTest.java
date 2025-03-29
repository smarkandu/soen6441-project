package ca.concordia.soen6441.project.map;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
    This class tests all the methods in the ValidateMapImpl class. This test class covers:
    Valid map scenario
    Missing neighbor existence
    Non-bidirectional neighbor relationships
    Disconnected graph
    Country assigned to a non-existent continent
    A continent with no associated countries
    A scenario simulating a country belonging to more than one continent (by having two continents with the same numeric ID)
 */
public class ValidateMapImplTest {

    private Continent d_continentAsia;
    private Country d_countryA;
    private Country d_countryB;
    private SortedMap<String, Country> d_countries;
    private SortedMap<String, Continent> d_continents;
    private ValidateMapImpl d_validateMap;
    private List<String> d_neighborsA;
    private List<String> d_neighborsB;


    /**
     * Sets up mock data for ValidateMapImpl before each test.
     */
    @BeforeEach
    void setUp() {
        //Create a continent mock.
        d_continentAsia = Mockito.mock(Continent.class);
        Mockito.when(d_continentAsia.getID()).thenReturn("Asia");
        Mockito.when(d_continentAsia.getNumericID()).thenReturn(1);

        //Create two country mocks - A and B.
        d_countryA = Mockito.mock(Country.class);
        Mockito.when(d_countryA.getID()).thenReturn("A");
        d_countryB = Mockito.mock(Country.class);
        Mockito.when(d_countryB.getID()).thenReturn("B");

        d_countries = new TreeMap<>();
        d_continents = new TreeMap<>();
        d_validateMap = new ValidateMapImpl(d_countries, d_continents);

        //Set up neighbor lists - A lists B but B does not list A.
        d_neighborsA = new ArrayList<>();
        d_neighborsB = new ArrayList<>();

    }

    /**
     * This test ensure the method isMapValid in ValidateMapImplTest
     * validate maps.
     */
    @Test
    void isMapValid() {
        Mockito.when(d_countryA.getContinent()).thenReturn(d_continentAsia);
        Mockito.when(d_countryB.getContinent()).thenReturn(d_continentAsia);

        //Set up bidirectional neighbor relationships.
        d_neighborsA.add("B");
        Mockito.when(d_countryA.getNeighborIDs()).thenReturn(d_neighborsA);

        d_neighborsB.add("A");
        Mockito.when(d_countryB.getNeighborIDs()).thenReturn(d_neighborsB);

        d_countries.put("A", d_countryA);
        d_countries.put("B", d_countryB);

        d_continents.put("Asia", d_continentAsia);

        assertTrue(d_validateMap.isMapValid(), "Expected the map to be valid.");
    }

    /**
     * This test ensures that every country's neighbor exits
     */
    @Test
    void testInvalidNeighborExistence() {
        Mockito.when(d_countryA.getContinent()).thenReturn(d_continentAsia);

        d_neighborsA.add("B"); // B is missing.
        Mockito.when(d_countryA.getNeighborIDs()).thenReturn(d_neighborsA);

        d_countries.put("A", d_countryA);

        d_continents.put("Asia", d_continentAsia);

        assertFalse(d_validateMap.isMapValid(), "Expected the map to be invalid due to missing neighbor existence.");
    }

    /**
     * This test ensures that every neighbor have a bidirectional connection
     */
    @Test
    void testInvalidBidirectionalNeighbors() {
        Mockito.when(d_countryA.getContinent()).thenReturn(d_continentAsia);
        Mockito.when(d_countryB.getContinent()).thenReturn(d_continentAsia);


        d_neighborsA.add("B");
        Mockito.when(d_countryA.getNeighborIDs()).thenReturn(d_neighborsA);

        Mockito.when(d_countryB.getNeighborIDs()).thenReturn(d_neighborsB);

        d_countries.put("A", d_countryA);
        d_countries.put("B", d_countryB);

        d_continents.put("Asia", d_continentAsia);

        assertFalse(d_validateMap.isMapValid(), "Expected the map to be invalid due to non-bidirectional neighbor relationship.");
    }

    /**
     * This test ensures that the map is connected
     */
    @Test
    void testDisconnectedGraph() {
        Country l_countryC = Mockito.mock(Country.class);
        Mockito.when(d_countryA.getID()).thenReturn("A");
        Mockito.when(d_countryB.getID()).thenReturn("B");
        Mockito.when(l_countryC.getID()).thenReturn("C");
        Mockito.when(d_countryA.getContinent()).thenReturn(d_continentAsia);
        Mockito.when(d_countryB.getContinent()).thenReturn(d_continentAsia);
        Mockito.when(l_countryC.getContinent()).thenReturn(d_continentAsia);

        //Connect A and B bidirectionally (A is neighbor of B and B is neighbour of A).
        d_neighborsA.add("B");
        Mockito.when(d_countryA.getNeighborIDs()).thenReturn(d_neighborsA);

        d_neighborsB.add("A");
        Mockito.when(d_countryB.getNeighborIDs()).thenReturn(d_neighborsB);

        //C is isolated.
        List<String> l_neighborsC = new ArrayList<>();
        Mockito.when(l_countryC.getNeighborIDs()).thenReturn(l_neighborsC);

        d_countries.put("A", d_countryA);
        d_countries.put("B", d_countryB);
        d_countries.put("C", l_countryC);

        d_continents.put("Asia", d_continentAsia);

        assertFalse(d_validateMap.isMapValid(), "Expected the map to be invalid due to disconnected graph.");
    }

    /**
     * This test ensures method verify that each country belongs to a existing continent
     */
    @Test
    void testInvalidContinentAssociation() {

        //Create a continent for the country (Europe) that is not added to the map.
        Continent l_continentEurope = Mockito.mock(Continent.class);
        Mockito.when(l_continentEurope.getID()).thenReturn("Europe");
        Mockito.when(l_continentEurope.getNumericID()).thenReturn(2);

        Mockito.when(d_countryA.getContinent()).thenReturn(l_continentEurope);
        //For simplicity, no neighbors.
        Mockito.when(d_countryA.getNeighborIDs()).thenReturn(new ArrayList<>());

        d_countries.put("A", d_countryA);

        //Only add Asia.
        d_continents.put("Asia", d_continentAsia);

        assertFalse(d_validateMap.isMapValid(), "Expected the map to be invalid because country A belongs to a non-existent continent.");
    }

    /**
     * This test ensuress that a continent's country list is not empty
     */
    @Test
    void testContinentWithNoCountries() {

        Continent l_continentEurope = Mockito.mock(Continent.class);
        Mockito.when(l_continentEurope.getID()).thenReturn("Europe");
        Mockito.when(l_continentEurope.getNumericID()).thenReturn(2);

        Mockito.when(d_countryA.getContinent()).thenReturn(d_continentAsia);

        //Use a self-neighbor for connectivity - a shortcut to simulate connectivity in a scenario where a country might otherwise appear isolated.
        d_neighborsA.add("A");
        Mockito.when(d_countryA.getNeighborIDs()).thenReturn(d_neighborsA);

        d_countries.put("A", d_countryA);

        d_continents.put("Asia", d_continentAsia);
        d_continents.put("Europe", l_continentEurope); //Europe has no country.

        assertFalse(d_validateMap.isMapValid(), "Expected the map to be invalid because continent Europe has no associated countries.");
    }

    /**
     * This test ensures that a country belong to only one continent
     */
    @Test
    void testCountryBelongsToMoreThanOneContinent() {
        //Create two continent mocks with the same numeric ID to simulate an error.
        Continent l_continent1 = Mockito.mock(Continent.class);
        Mockito.when(l_continent1.getID()).thenReturn("Cont1");
        Mockito.when(l_continent1.getNumericID()).thenReturn(1);

        Continent l_continent2 = Mockito.mock(Continent.class);
        Mockito.when(l_continent2.getID()).thenReturn("Cont2");
        //Simulate the error by setting the same numeric ID.
        Mockito.when(l_continent2.getNumericID()).thenReturn(1);

        Mockito.when(d_countryA.getContinent()).thenReturn(l_continent1);
        Mockito.when(d_countryA.getNeighborIDs()).thenReturn(new ArrayList<>());

        d_countries.put("A", d_countryA);

        d_continents.put("Cont1", l_continent1);
        d_continents.put("Cont2", l_continent2);

        //Expect false because the country appears to belong to two continents (both have the same numeric ID).
        assertFalse(d_validateMap.isMapValid(), "Expected the map to be invalid because a country is associated with more than one continent.");
    }
}

