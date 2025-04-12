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
        StringBuilder l_sb = new StringBuilder();

        // Add [Map] section
        l_sb.append("[Map]\n");
        l_sb.append("\n");


        // Add [Continents] section
        l_sb.append("[Continents]\n");
        List<Continent> l_continents = new ArrayList<>(
                GameDriver.getGameEngine()
                        .getContinentManager()
                        .getContinents()
                        .values()
        );
        l_continents.sort(Comparator.comparingInt(Continent::getNumericID));
        for (Continent l_cont : l_continents) {
            // ContinentImpl.toString() → "ID=Value"
            l_sb.append(l_cont.toString()).append("\n");
        }
        l_sb.append("\n");

        // Add [Territories] section
        l_sb.append("[Territories]\n");
        List<Country> l_countries = new ArrayList<>(
                GameDriver.getGameEngine()
                        .getCountryManager()
                        .getCountries()
                        .values()
        );
        l_countries.sort(Comparator.comparingInt(Country::getNumericID));
        for (Country l_c : l_countries) {
            // CountryImpl.toString() → "Name,x,y,ContinentID[,Neighbor1,Neighbor2...]"
            l_sb.append(l_c.toStringWithComma()).append("\n");
        }

        return l_sb.toString();
    }
}
