package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.MapComponent;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CountryImpl implements Country, MapComponent {
    private final String d_ID;
    private final int d_xCoord;
    private final int d_yCoord;
    private final Continent d_Continent;
    private TreeMap<String, Country> d_Neighbors;
    private static int d_Counter = 0;
    private final int d_numericID;
    private int d_troops;

    /**
     * Constructor (to be used when loading a .map file)
     * @param p_numericID
     * @param p_ID
     * @param p_Continent
     */
    public CountryImpl(int p_numericID, String p_ID, Continent p_Continent, int p_xCoord, int p_yCoord) {
        this.d_ID = p_ID;
        this.d_xCoord = p_xCoord;
        this.d_yCoord = p_yCoord;
        d_Continent = p_Continent;
        d_Neighbors = new TreeMap<String, Country>();
        this.d_numericID = p_numericID;
        if (p_numericID > d_Counter)
        {
            d_Counter = p_numericID;
        }
    }

    public CountryImpl(String p_ID, Continent p_Continent) {
        this.d_ID = p_ID;
        this.d_xCoord = 0; // Hardcoded
        this.d_yCoord = 0; // Hardcoded
        d_Continent = p_Continent;
        d_Neighbors = new TreeMap<String, Country>();
        this.d_numericID = ++d_Counter;
    }

    @Override
    public String getID() {
        return d_ID;
    }

    @Override
    public List<String> getNeighborIDs() {
        return d_Neighbors.values().stream().map(Country::getID)
                .collect(Collectors.toList());
    }

    @Override
    public void addNeighbor(Country p_Neighbor) {
        d_Neighbors.put(p_Neighbor.getID(), p_Neighbor);
    }

    @Override
    public void removeNeighbor(String p_NeighborID) {
        d_Neighbors.remove(p_NeighborID);
    }

    @Override
    public int getNumericID() {
        return d_numericID;
    }

    @Override
    public String toString() {
        String l_NeighborIDsAsString = d_Neighbors.values().stream().map(Country::getID)
                .collect(Collectors.joining(" "));

        return d_ID + ',' + d_xCoord +
                "," + d_yCoord +
                "," + d_Continent.getID() + (!l_NeighborIDsAsString.isEmpty()?
                "," + l_NeighborIDsAsString:"");
    }
    @Override
    public void setTroops(int p_troops) {
        this.d_troops = p_troops;  // ✅ Assign troops correctly
    }

    @Override
    public Continent getContinent() {
        return d_Continent;
    }

    public static void resetCounter()
    {
        d_Counter = 0;
    }

    @Override 
    public String toMapString() {   
    // Formats country details in the Domination format:
    return d_numericID + " " + d_ID + " " + d_Continent.getNumericID() + " " + d_xCoord + " " + d_yCoord;
    }
}
