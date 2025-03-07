package ca.concordia.soen6441.project.phases;
import ca.concordia.soen6441.project.ValidateMapImpl;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
/*
    NOTE: This class tests all the methods in the ValidateMapImpl class. This test class covers:
    Valid map scenario
    Missing neighbor existence
    Non-bidirectional neighbor relationships
    Disconnected graph
    Country assigned to a non-existent continent
    A continent with no associated countries
    A scenario simulating a country belonging to more than one continent (by having two continents with the same numeric ID)
 */
class ValidateMapTest {

    @Test
    void testValidMap() {
        //Create a continent mock.
        Continent l_continent = Mockito.mock(Continent.class);
        Mockito.when(l_continent.getID()).thenReturn("Asia");
        Mockito.when(l_continent.getNumericID()).thenReturn(1);

        //Create two country mocks - A and B.
        Country l_countryA = Mockito.mock(Country.class);
        Country l_countryB = Mockito.mock(Country.class);
        Mockito.when(l_countryA.getID()).thenReturn("A");
        Mockito.when(l_countryB.getID()).thenReturn("B");
        Mockito.when(l_countryA.getContinent()).thenReturn(l_continent);
        Mockito.when(l_countryB.getContinent()).thenReturn(l_continent);

        //Set up bidirectional neighbor relationships.
        List<String> l_neighborsA = new ArrayList<>();
        l_neighborsA.add("B");
        Mockito.when(l_countryA.getNeighborIDs()).thenReturn(l_neighborsA);

        List<String> l_neighborsB = new ArrayList<>();
        l_neighborsB.add("A");
        Mockito.when(l_countryB.getNeighborIDs()).thenReturn(l_neighborsB);

        SortedMap<String, Country> l_countries = new TreeMap<>();
        l_countries.put("A", l_countryA);
        l_countries.put("B", l_countryB);

        SortedMap<String, Continent> l_continents = new TreeMap<>();
        l_continents.put("Asia", l_continent);

        ValidateMapImpl l_validateMap = new ValidateMapImpl(l_countries, l_continents);
        assertTrue(l_validateMap.isMapValid(), "Expected the map to be valid.");
    }

    @Test
    void testInvalidNeighborExistence() {
        //Create a continent mock.
        Continent l_continent = Mockito.mock(Continent.class);
        Mockito.when(l_continent.getID()).thenReturn("Asia");
        Mockito.when(l_continent.getNumericID()).thenReturn(1);

        //Create one country mock: "A" with a neighbor "B" that doesn't exist.
        Country l_countryA = Mockito.mock(Country.class);
        Mockito.when(l_countryA.getID()).thenReturn("A");
        Mockito.when(l_countryA.getContinent()).thenReturn(l_continent);
        List<String> l_neighborsA = new ArrayList<>();
        l_neighborsA.add("B"); // B is missing.
        Mockito.when(l_countryA.getNeighborIDs()).thenReturn(l_neighborsA);

        SortedMap<String, Country> l_countries = new TreeMap<>();
        l_countries.put("A", l_countryA);

        SortedMap<String, Continent> l_continents = new TreeMap<>();
        l_continents.put("Asia", l_continent);

        ValidateMapImpl l_validateMap = new ValidateMapImpl(l_countries, l_continents);
        assertFalse(l_validateMap.isMapValid(), "Expected the map to be invalid due to missing neighbor existence.");
    }

    @Test
    void testInvalidBidirectionalNeighbors() {
        //Create a continent mock.
        Continent l_continent = Mockito.mock(Continent.class);
        Mockito.when(l_continent.getID()).thenReturn("Asia");
        Mockito.when(l_continent.getNumericID()).thenReturn(1);

        //Create two country mocks - A and B.
        Country l_countryA = Mockito.mock(Country.class);
        Country l_countryB = Mockito.mock(Country.class);
        Mockito.when(l_countryA.getID()).thenReturn("A");
        Mockito.when(l_countryB.getID()).thenReturn("B");
        Mockito.when(l_countryA.getContinent()).thenReturn(l_continent);
        Mockito.when(l_countryB.getContinent()).thenReturn(l_continent);

        //Set up neighbor lists - A lists B but B does not list A.
        List<String> l_neighborsA = new ArrayList<>();
        l_neighborsA.add("B");
        Mockito.when(l_countryA.getNeighborIDs()).thenReturn(l_neighborsA);

        List<String> l_neighborsB = new ArrayList<>(); // B's list is empty.
        Mockito.when(l_countryB.getNeighborIDs()).thenReturn(l_neighborsB);

        SortedMap<String, Country> l_countries = new TreeMap<>();
        l_countries.put("A", l_countryA);
        l_countries.put("B", l_countryB);

        SortedMap<String, Continent> l_continents = new TreeMap<>();
        l_continents.put("Asia", l_continent);

        ValidateMapImpl l_validateMap = new ValidateMapImpl(l_countries, l_continents);
        assertFalse(l_validateMap.isMapValid(), "Expected the map to be invalid due to non-bidirectional neighbor relationship.");
    }

    @Test
    void testDisconnectedGraph() {
        //Create a continent mock.
        Continent l_continent = Mockito.mock(Continent.class);
        Mockito.when(l_continent.getID()).thenReturn("Asia");
        Mockito.when(l_continent.getNumericID()).thenReturn(1);

        //Create three country mocks: A, B (connected) and C (not connected).
        Country l_countryA = Mockito.mock(Country.class);
        Country l_countryB = Mockito.mock(Country.class);
        Country l_countryC = Mockito.mock(Country.class);
        Mockito.when(l_countryA.getID()).thenReturn("A");
        Mockito.when(l_countryB.getID()).thenReturn("B");
        Mockito.when(l_countryC.getID()).thenReturn("C");
        Mockito.when(l_countryA.getContinent()).thenReturn(l_continent);
        Mockito.when(l_countryB.getContinent()).thenReturn(l_continent);
        Mockito.when(l_countryC.getContinent()).thenReturn(l_continent);

        //Connect A and B bidirectionally (A is neighbor of B and B is neighbour of A).
        List<String> l_neighborsA = new ArrayList<>();
        l_neighborsA.add("B");
        Mockito.when(l_countryA.getNeighborIDs()).thenReturn(l_neighborsA);

        List<String> l_neighborsB = new ArrayList<>();
        l_neighborsB.add("A");
        Mockito.when(l_countryB.getNeighborIDs()).thenReturn(l_neighborsB);

        //C is isolated.
        List<String> l_neighborsC = new ArrayList<>();
        Mockito.when(l_countryC.getNeighborIDs()).thenReturn(l_neighborsC);

        SortedMap<String, Country> l_countries = new TreeMap<>();
        l_countries.put("A", l_countryA);
        l_countries.put("B", l_countryB);
        l_countries.put("C", l_countryC);

        SortedMap<String, Continent> l_continents = new TreeMap<>();
        l_continents.put("Asia", l_continent);

        ValidateMapImpl l_validateMap = new ValidateMapImpl(l_countries, l_continents);
        assertFalse(l_validateMap.isMapValid(), "Expected the map to be invalid due to disconnected graph.");
    }

    @Test
    void testInvalidContinentAssociation() {
        //Create a valid continent (Asia) for the map.
        Continent l_continentAsia = Mockito.mock(Continent.class);
        Mockito.when(l_continentAsia.getID()).thenReturn("Asia");
        Mockito.when(l_continentAsia.getNumericID()).thenReturn(1);

        //Create a continent for the country (Europe) that is not added to the map.
        Continent l_continentEurope = Mockito.mock(Continent.class);
        Mockito.when(l_continentEurope.getID()).thenReturn("Europe");
        Mockito.when(l_continentEurope.getNumericID()).thenReturn(2);

        //Create a country that belongs to Europe.
        Country l_countryA = Mockito.mock(Country.class);
        Mockito.when(l_countryA.getID()).thenReturn("A");
        Mockito.when(l_countryA.getContinent()).thenReturn(l_continentEurope);
        //For simplicity, no neighbors.
        Mockito.when(l_countryA.getNeighborIDs()).thenReturn(new ArrayList<>());

        SortedMap<String, Country> l_countries = new TreeMap<>();
        l_countries.put("A", l_countryA);

        SortedMap<String, Continent> l_continents = new TreeMap<>();
        //Only add Asia.
        l_continents.put("Asia", l_continentAsia);

        ValidateMapImpl l_validateMap = new ValidateMapImpl(l_countries, l_continents);
        assertFalse(l_validateMap.isMapValid(), "Expected the map to be invalid because country A belongs to a non-existent continent.");
    }

    @Test
    void testContinentWithNoCountries() {
        //Create two continent mocks: Asia and Europe.
        Continent l_continentAsia = Mockito.mock(Continent.class);
        Mockito.when(l_continentAsia.getID()).thenReturn("Asia");
        Mockito.when(l_continentAsia.getNumericID()).thenReturn(1);

        Continent l_continentEurope = Mockito.mock(Continent.class);
        Mockito.when(l_continentEurope.getID()).thenReturn("Europe");
        Mockito.when(l_continentEurope.getNumericID()).thenReturn(2);

        //Create one country that belongs to Asia.
        Country l_countryA = Mockito.mock(Country.class);
        Mockito.when(l_countryA.getID()).thenReturn("A");
        Mockito.when(l_countryA.getContinent()).thenReturn(l_continentAsia);
        //Use a self-neighbor for connectivity - a shortcut to simulate connectivity in a scenario where a country might otherwise appear isolated.
        //Addng A as its own neighbor
        List<String> l_neighborsA = new ArrayList<>();
        l_neighborsA.add("A");
        Mockito.when(l_countryA.getNeighborIDs()).thenReturn(l_neighborsA);

        SortedMap<String, Country> l_countries = new TreeMap<>();
        l_countries.put("A", l_countryA);

        SortedMap<String, Continent> l_continents = new TreeMap<>();
        l_continents.put("Asia", l_continentAsia);
        l_continents.put("Europe", l_continentEurope); //Europe has no country.

        ValidateMapImpl l_validateMap = new ValidateMapImpl(l_countries, l_continents);
        assertFalse(l_validateMap.isMapValid(), "Expected the map to be invalid because continent Europe has no associated countries.");
    }

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

        //Create a country that belongs to continent1.
        Country l_countryA = Mockito.mock(Country.class);
        Mockito.when(l_countryA.getID()).thenReturn("A");
        Mockito.when(l_countryA.getContinent()).thenReturn(l_continent1);
        Mockito.when(l_countryA.getNeighborIDs()).thenReturn(new ArrayList<>());

        SortedMap<String, Country> l_countries = new TreeMap<>();
        l_countries.put("A", l_countryA);

        SortedMap<String, Continent> l_continents = new TreeMap<>();
        l_continents.put("Cont1", l_continent1);
        l_continents.put("Cont2", l_continent2);

        ValidateMapImpl l_validateMap = new ValidateMapImpl(l_countries, l_continents);
        //Expect false because the country appears to belong to two continents (both have the same numeric ID).
        assertFalse(l_validateMap.isMapValid(), "Expected the map to be invalid because a country is associated with more than one continent.");
    }
}

