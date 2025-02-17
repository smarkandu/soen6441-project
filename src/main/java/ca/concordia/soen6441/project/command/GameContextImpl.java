package ca.concordia.soen6441.project.command;

import ca.concordia.soen6441.project.OverallFactory;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.GameContext;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class GameContextImpl implements GameContext {
    private final String d_author;
    private final String d_image;
    private final Boolean d_wrap;
    private final Boolean d_scroll;
    private SortedMap<String, Continent> d_Continents;
    private SortedMap<String, Country> d_Countries;

    public GameContextImpl(SortedMap<String, Continent> p_Continents, SortedMap<String, Country> p_Countries) {
        this.d_Continents = p_Continents;
        this.d_Countries = p_Countries;
        d_author = "SOEN6441";
        d_image = "noimage.bmp";
        d_wrap = false;
        d_scroll = false;
    }

    @Override
    public List<String> getContinentIDs() {
        return new ArrayList<String>(d_Continents.keySet());
    }

    @Override
    public List<String> getCountryIDs() {
        return new ArrayList<String>(d_Countries.keySet());
    }

    @Override
    public void addContinent(String p_continentID, int p_continentValue) {
        Continent l_continent = OverallFactory.getInstance().CreateContinent(p_continentID, p_continentValue);
        d_Continents.put(l_continent.getID(), l_continent);
        System.out.println("Continent added: " + d_Continents.get(l_continent.getID()));
    }

    @Override
    public void addCountry(String p_CountryID, String p_continentID, List<String> p_neighbors) {
        Country l_country = OverallFactory.getInstance().CreateCountry(p_CountryID, p_continentID, p_neighbors);
        d_Countries.put(p_CountryID, l_country);
        System.out.println("Country added: " + d_Countries.get(l_country.getID()));
    }

    @Override
    public void addNeighbor(String p_CountryID, String p_neighborCountryID) {
        d_Countries.get(p_CountryID).addNeighborID(d_Countries.get(p_neighborCountryID).getID());
        d_Countries.get(p_neighborCountryID).addNeighborID(d_Countries.get(p_CountryID).getID());
        System.out.println("Neighbor added: " + d_Countries.get(p_CountryID));
        System.out.println("Neighbor added: " + d_Countries.get(p_neighborCountryID));
    }

    @Override
    public void removeContinent(String p_continentID) {
        d_Continents.remove(p_continentID);
    }

    @Override
    public void removeCountry(String p_countryID) {
        d_Countries.remove(p_countryID);
    }

    @Override
    public void removeNeighbor(String p_CountryID, String p_neighborCountryID) {
        d_Countries.get(p_CountryID).removeNeighborID(p_neighborCountryID);
        d_Countries.get(p_neighborCountryID).removeNeighborID(p_CountryID);
    }

    @Override
    public void showMap() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        String l_mapStr = "[Map]\n"
                + "author=" + d_author + "\n"
                + "image=" + d_image + "\n"
                + "wrap=" + d_wrap + "\n"
                + "scroll=" + d_scroll;

        // Format continents to string
        StringBuilder l_sbContinents = new StringBuilder();
        for (Continent l_continent : d_Continents.values()) {
            l_sbContinents.append(l_continent).append("\n");
        }

        // Format countries to string
        StringBuilder l_sbCountries = new StringBuilder();
        for (Country l_country : d_Countries.values()) {
            l_sbCountries.append(l_country).append("\n");
        }

        String l_continentsStr = "[Continents]\n" + l_sbContinents;
        String l_territoriesStr = "[Territories]\n" + l_sbCountries;

        return l_mapStr + "\n\n" + l_continentsStr + "\n\n" + l_territoriesStr;
    }
}
