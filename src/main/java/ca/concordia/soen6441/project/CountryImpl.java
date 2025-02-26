package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CountryImpl implements Country, MapComponent {
    private final String d_ID;
    private final int d_xCoord;
    private final int d_yCoord;
    private final String d_ContinentID;
    private TreeMap<String, Country> d_Neighbors;
    private static int d_Counter = 0;
    private final int d_numericID;

    /**
     * Constructor (to be used when loading a .map file)
     * @param p_numericID
     * @param p_ID
     * @param p_ContinentID
     */
    public CountryImpl(int p_numericID, String p_ID, String p_ContinentID, int p_xCoord, int p_yCoord) {
        this.d_ID = p_ID;
        this.d_xCoord = p_xCoord;
        this.d_yCoord = p_yCoord;
        d_ContinentID = p_ContinentID;
        d_Neighbors = new TreeMap<String, Country>();
        this.d_numericID = p_numericID;
        if (p_numericID > d_Counter)
        {
            d_Counter = p_numericID;
        }
    }

    public CountryImpl(String p_ID, String p_ContinentID) {
        this.d_ID = p_ID;
        this.d_xCoord = 0; // Hardcoded
        this.d_yCoord = 0; // Hardcoded
        d_ContinentID = p_ContinentID;
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
                "," + d_ContinentID + (!l_NeighborIDsAsString.isEmpty()?
                "," + l_NeighborIDsAsString:"");
    }

    public static void resetCounter()
    {
        d_Counter = 0;
    }

    @Override
    public String toMapString() {   
    // Formats country details in the Domination format:
    // CountryNumericID x y ContinentNumericID Neighbor1NumericID Neighbor2NumericID ...
    // Get the numeric ID of the continent this country belongs to
       int l_continentNumericID = GameEngine.getInstance()
                                     .getContinentByNumericID(Integer.parseInt(d_ContinentID))
                                     .getNumericID();



        // Get the list of neighbors as numeric IDs
        String l_neighbors = d_Neighbors.values().stream()
                          .map(c -> String.valueOf(c.getNumericID()))
                          .collect(Collectors.joining(" "));

        // Format the country as: NumericID x y ContinentNumericID Neighbor1 Neighbor2 ...
        return d_numericID + " " + d_xCoord + " " + d_yCoord + " " + l_continentNumericID +
           (l_neighbors.isEmpty() ? "" : " " + l_neighbors);
    }
}
