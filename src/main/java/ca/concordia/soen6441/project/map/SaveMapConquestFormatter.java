package ca.concordia.soen6441.project.map;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.MapComponent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SaveMapConquestFormatter
{
    public String conquestFormatter(){
        // Builds the map file format string
        // Create sections
        StringBuilder sb = new StringBuilder();

        // Add [Map] section
        sb.append("[Map]\n");
        sb.append("\n");


        // Add [Continents] section
        sb.append("[Continents]\n");
        List<Continent> l_continents = new ArrayList<>(
                GameDriver.getGameEngine()
                        .getContinentManager()
                        .getContinents()
                        .values()
        );
        l_continents.sort(Comparator.comparingInt(Continent::getNumericID));
        for (Continent l_cont : l_continents) {
            // ContinentImpl.toString() → "ID=Value"
            sb.append(l_cont.toString()).append("\n");
        }
        sb.append("\n");

        // Add [Territories] section
        sb.append("[Territories]\n");
        List<Country> countries = new ArrayList<>(
                GameDriver.getGameEngine()
                        .getCountryManager()
                        .getCountries()
                        .values()
        );
        countries.sort(Comparator.comparingInt(Country::getNumericID));
        for (Country c : countries) {
            // CountryImpl.toString() → "Name,x,y,ContinentID[,Neighbor1,Neighbor2...]"
            sb.append(c.toStringWithComma()).append("\n");
        }

        return sb.toString();
    }
}
