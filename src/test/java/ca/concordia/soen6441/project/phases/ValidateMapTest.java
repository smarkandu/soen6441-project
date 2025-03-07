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
        Continent continent = Mockito.mock(Continent.class);
        Mockito.when(continent.getID()).thenReturn("Asia");
        Mockito.when(continent.getNumericID()).thenReturn(1);

        //Create two country mocks - A and B.
        Country countryA = Mockito.mock(Country.class);
        Country countryB = Mockito.mock(Country.class);
        Mockito.when(countryA.getID()).thenReturn("A");
        Mockito.when(countryB.getID()).thenReturn("B");
        Mockito.when(countryA.getContinent()).thenReturn(continent);
        Mockito.when(countryB.getContinent()).thenReturn(continent);

        //Set up bidirectional neighbor relationships.
        List<String> neighborsA = new ArrayList<>();
        neighborsA.add("B");
        Mockito.when(countryA.getNeighborIDs()).thenReturn(neighborsA);

        List<String> neighborsB = new ArrayList<>();
        neighborsB.add("A");
        Mockito.when(countryB.getNeighborIDs()).thenReturn(neighborsB);

        SortedMap<String, Country> countries = new TreeMap<>();
        countries.put("A", countryA);
        countries.put("B", countryB);

        SortedMap<String, Continent> continents = new TreeMap<>();
        continents.put("Asia", continent);

        ValidateMapImpl validateMap = new ValidateMapImpl(countries, continents);
        assertTrue(validateMap.isMapValid(), "Expected the map to be valid.");
    }

    @Test
    void testInvalidNeighborExistence() {
        //Create a continent mock.
        Continent continent = Mockito.mock(Continent.class);
        Mockito.when(continent.getID()).thenReturn("Asia");
        Mockito.when(continent.getNumericID()).thenReturn(1);

        //Create one country mock: "A" with a neighbor "B" that doesn't exist.
        Country countryA = Mockito.mock(Country.class);
        Mockito.when(countryA.getID()).thenReturn("A");
        Mockito.when(countryA.getContinent()).thenReturn(continent);
        List<String> neighborsA = new ArrayList<>();
        neighborsA.add("B"); // B is missing.
        Mockito.when(countryA.getNeighborIDs()).thenReturn(neighborsA);

        SortedMap<String, Country> countries = new TreeMap<>();
        countries.put("A", countryA);

        SortedMap<String, Continent> continents = new TreeMap<>();
        continents.put("Asia", continent);

        ValidateMapImpl validateMap = new ValidateMapImpl(countries, continents);
        assertFalse(validateMap.isMapValid(), "Expected the map to be invalid due to missing neighbor existence.");
    }

    @Test
    void testInvalidBidirectionalNeighbors() {
        //Create a continent mock.
        Continent continent = Mockito.mock(Continent.class);
        Mockito.when(continent.getID()).thenReturn("Asia");
        Mockito.when(continent.getNumericID()).thenReturn(1);

        //Create two country mocks - A and B.
        Country countryA = Mockito.mock(Country.class);
        Country countryB = Mockito.mock(Country.class);
        Mockito.when(countryA.getID()).thenReturn("A");
        Mockito.when(countryB.getID()).thenReturn("B");
        Mockito.when(countryA.getContinent()).thenReturn(continent);
        Mockito.when(countryB.getContinent()).thenReturn(continent);

        //Set up neighbor lists - A lists B but B does not list A.
        List<String> neighborsA = new ArrayList<>();
        neighborsA.add("B");
        Mockito.when(countryA.getNeighborIDs()).thenReturn(neighborsA);

        List<String> neighborsB = new ArrayList<>(); // B's list is empty.
        Mockito.when(countryB.getNeighborIDs()).thenReturn(neighborsB);

        SortedMap<String, Country> countries = new TreeMap<>();
        countries.put("A", countryA);
        countries.put("B", countryB);

        SortedMap<String, Continent> continents = new TreeMap<>();
        continents.put("Asia", continent);

        ValidateMapImpl validateMap = new ValidateMapImpl(countries, continents);
        assertFalse(validateMap.isMapValid(), "Expected the map to be invalid due to non-bidirectional neighbor relationship.");
    }

    @Test
    void testDisconnectedGraph() {
        //Create a continent mock.
        Continent continent = Mockito.mock(Continent.class);
        Mockito.when(continent.getID()).thenReturn("Asia");
        Mockito.when(continent.getNumericID()).thenReturn(1);

        //Create three country mocks: A, B (connected) and C (not connected).
        Country countryA = Mockito.mock(Country.class);
        Country countryB = Mockito.mock(Country.class);
        Country countryC = Mockito.mock(Country.class);
        Mockito.when(countryA.getID()).thenReturn("A");
        Mockito.when(countryB.getID()).thenReturn("B");
        Mockito.when(countryC.getID()).thenReturn("C");
        Mockito.when(countryA.getContinent()).thenReturn(continent);
        Mockito.when(countryB.getContinent()).thenReturn(continent);
        Mockito.when(countryC.getContinent()).thenReturn(continent);

        //Connect A and B bidirectionally (A is neighbor of B and B is neighbour of A).
        List<String> neighborsA = new ArrayList<>();
        neighborsA.add("B");
        Mockito.when(countryA.getNeighborIDs()).thenReturn(neighborsA);

        List<String> neighborsB = new ArrayList<>();
        neighborsB.add("A");
        Mockito.when(countryB.getNeighborIDs()).thenReturn(neighborsB);

        //C is isolated.
        List<String> neighborsC = new ArrayList<>();
        Mockito.when(countryC.getNeighborIDs()).thenReturn(neighborsC);

        SortedMap<String, Country> countries = new TreeMap<>();
        countries.put("A", countryA);
        countries.put("B", countryB);
        countries.put("C", countryC);

        SortedMap<String, Continent> continents = new TreeMap<>();
        continents.put("Asia", continent);

        ValidateMapImpl validateMap = new ValidateMapImpl(countries, continents);
        assertFalse(validateMap.isMapValid(), "Expected the map to be invalid due to disconnected graph.");
    }

    @Test
    void testInvalidContinentAssociation() {
        //Create a valid continent (Asia) for the map.
        Continent continentAsia = Mockito.mock(Continent.class);
        Mockito.when(continentAsia.getID()).thenReturn("Asia");
        Mockito.when(continentAsia.getNumericID()).thenReturn(1);

        //Create a continent for the country (Europe) that is not added to the map.
        Continent continentEurope = Mockito.mock(Continent.class);
        Mockito.when(continentEurope.getID()).thenReturn("Europe");
        Mockito.when(continentEurope.getNumericID()).thenReturn(2);

        //Create a country that belongs to Europe.
        Country countryA = Mockito.mock(Country.class);
        Mockito.when(countryA.getID()).thenReturn("A");
        Mockito.when(countryA.getContinent()).thenReturn(continentEurope);
        //For simplicity, no neighbors.
        Mockito.when(countryA.getNeighborIDs()).thenReturn(new ArrayList<>());

        SortedMap<String, Country> countries = new TreeMap<>();
        countries.put("A", countryA);

        SortedMap<String, Continent> continents = new TreeMap<>();
        //Only add Asia.
        continents.put("Asia", continentAsia);

        ValidateMapImpl validateMap = new ValidateMapImpl(countries, continents);
        assertFalse(validateMap.isMapValid(), "Expected the map to be invalid because country A belongs to a non-existent continent.");
    }

    @Test
    void testContinentWithNoCountries() {
        //Create two continent mocks: Asia and Europe.
        Continent continentAsia = Mockito.mock(Continent.class);
        Mockito.when(continentAsia.getID()).thenReturn("Asia");
        Mockito.when(continentAsia.getNumericID()).thenReturn(1);

        Continent continentEurope = Mockito.mock(Continent.class);
        Mockito.when(continentEurope.getID()).thenReturn("Europe");
        Mockito.when(continentEurope.getNumericID()).thenReturn(2);

        //Create one country that belongs to Asia.
        Country countryA = Mockito.mock(Country.class);
        Mockito.when(countryA.getID()).thenReturn("A");
        Mockito.when(countryA.getContinent()).thenReturn(continentAsia);
        //Use a self-neighbor for connectivity - a shortcut to simulate connectivity in a scenario where a country might otherwise appear isolated.
        //Addng A as its own neighbor
        List<String> neighborsA = new ArrayList<>();
        neighborsA.add("A");
        Mockito.when(countryA.getNeighborIDs()).thenReturn(neighborsA);

        SortedMap<String, Country> countries = new TreeMap<>();
        countries.put("A", countryA);

        SortedMap<String, Continent> continents = new TreeMap<>();
        continents.put("Asia", continentAsia);
        continents.put("Europe", continentEurope); //Europe has no country.

        ValidateMapImpl validateMap = new ValidateMapImpl(countries, continents);
        assertFalse(validateMap.isMapValid(), "Expected the map to be invalid because continent Europe has no associated countries.");
    }

    @Test
    void testCountryBelongsToMoreThanOneContinent() {
        //Create two continent mocks with the same numeric ID to simulate an error.
        Continent continent1 = Mockito.mock(Continent.class);
        Mockito.when(continent1.getID()).thenReturn("Cont1");
        Mockito.when(continent1.getNumericID()).thenReturn(1);

        Continent continent2 = Mockito.mock(Continent.class);
        Mockito.when(continent2.getID()).thenReturn("Cont2");
        //Simulate the error by setting the same numeric ID.
        Mockito.when(continent2.getNumericID()).thenReturn(1);

        //Create a country that belongs to continent1.
        Country countryA = Mockito.mock(Country.class);
        Mockito.when(countryA.getID()).thenReturn("A");
        Mockito.when(countryA.getContinent()).thenReturn(continent1);
        Mockito.when(countryA.getNeighborIDs()).thenReturn(new ArrayList<>());

        SortedMap<String, Country> countries = new TreeMap<>();
        countries.put("A", countryA);

        SortedMap<String, Continent> continents = new TreeMap<>();
        continents.put("Cont1", continent1);
        continents.put("Cont2", continent2);

        ValidateMapImpl validateMap = new ValidateMapImpl(countries, continents);
        //Expect false because the country appears to belong to two continents (both have the same numeric ID).
        assertFalse(validateMap.isMapValid(), "Expected the map to be invalid because a country is associated with more than one continent.");
    }
}

