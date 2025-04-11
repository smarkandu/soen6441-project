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
        StringBuilder l_stringBuilder = new StringBuilder();

        // Add [Map] section
        l_stringBuilder.append("[Map]\n");
        l_stringBuilder.append("\n");


        // Add [Continents] section
        l_stringBuilder.append("[Continents]\n");
        List<Continent> l_continents = new ArrayList<>(
                GameDriver.getGameEngine()
                        .getContinentManager()
                        .getContinents()
                        .values()
        );
        l_continents.sort(Comparator.comparingInt(Continent::getNumericID));
        for (Continent l_cont : l_continents) {
            // ContinentImpl.toString() → "ID=Value"
            l_stringBuilder.append(l_cont.toString()).append("\n");
        }
        l_stringBuilder.append("\n");

        // Add [Territories] section
        l_stringBuilder.append("[Territories]\n");
        List<Country> l_countries = new ArrayList<>(
                GameDriver.getGameEngine()
                        .getCountryManager()
                        .getCountries()
                        .values()
        );
        l_countries.sort(Comparator.comparingInt(Country::getNumericID));
        for (Country c : l_countries) {
            // CountryImpl.toString() → "Name,x,y,ContinentID[,Neighbor1,Neighbor2...]"
            l_stringBuilder.append(c.toString()).append("\n");
        }

        return l_stringBuilder.toString();
    }
}
