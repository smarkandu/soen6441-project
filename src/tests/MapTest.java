package tests;

import ca.concordia.soen6441.project.OverallFactory;
import ca.concordia.soen6441.project.ValidateMapImpl;
import ca.concordia.soen6441.project.interfaces.*;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class MapTest {
    private ValidateMapImpl d_validateMapImpl;
    private SortedMap<String, Continent> d_Continents;
    private SortedMap<String, Country> d_Countries;

    @BeforeEach
    protected void setUp() throws Exception {
        d_Continents = new TreeMap<>();
        d_Countries = new TreeMap<>();
    }

    @Test
    public void testCountryBelongsSeveralContinents() {
        Continent l_continentAfrica = OverallFactory.getInstance().CreateContinent(0, "Africa", 100, "brown");
        Continent l_continentAsia = OverallFactory.getInstance().CreateContinent(0, "Asia", 50, "blue");

        Country l_countryCongoAfrica = OverallFactory.getInstance().CreateCountry(0, "Congo", l_continentAfrica, 0, 0);
        Country l_countryCongoAsia = OverallFactory.getInstance().CreateCountry(1, "Congos", l_continentAsia, 0, 0);
        Country l_countryBenin = OverallFactory.getInstance().CreateCountry(2, "Benin", l_continentAfrica, 0, 0);

        l_countryCongoAfrica.addNeighbor(l_countryCongoAsia);
        l_countryCongoAfrica.addNeighbor(l_countryBenin);

        l_countryCongoAsia.addNeighbor(l_countryCongoAfrica);
        l_countryCongoAsia.addNeighbor(l_countryBenin);

        l_countryBenin.addNeighbor(l_countryCongoAsia);
        l_countryBenin.addNeighbor(l_countryCongoAfrica);


        d_Continents.put("Africa", l_continentAfrica);
        d_Continents.put("Asia", l_continentAsia);


        d_Countries.put("Congo", l_countryCongoAfrica);
        d_Countries.put("Congos", l_countryCongoAsia);
        d_Countries.put("Benin", l_countryBenin);
        

        d_validateMapImpl = new ValidateMapImpl(d_Countries, d_Continents);
        assertFalse(d_validateMapImpl.isMapValid());
    }

    @Test
    public void testCountryIsEmpty() {
        Continent l_continentAfrica = OverallFactory.getInstance().CreateContinent(0, "Africa", 100, "brown");

        d_Continents.put("Africa", l_continentAfrica);

        d_validateMapImpl = new ValidateMapImpl(d_Countries, d_Continents);
        assertFalse(d_validateMapImpl.isMapValid());

    }

}