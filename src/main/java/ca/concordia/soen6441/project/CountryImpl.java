package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Country;

import java.util.List;

public class CountryImpl implements Country {
    private final String d_ID;
    private final int d_xCoord;
    private final int d_yCoord;
    private final String d_ContinentID;
    private List<String> d_NeighborIDs;

    public CountryImpl(String p_ID, String p_ContinentID, List<String> p_NeighborIDs) {
        this.d_ID = p_ID;
        this.d_xCoord = 0;
        this.d_yCoord = 0;
        d_ContinentID = p_ContinentID;
        this.d_NeighborIDs = p_NeighborIDs;
    }

    @Override
    public String getID() {
        return d_ID;
    }

    @Override
    public List<String> getNeighborIDs() {
        return d_NeighborIDs;
    }

    @Override
    public void addNeighborID(String p_NeighborID) {
        d_NeighborIDs.add(p_NeighborID);
    }

    @Override
    public void removeNeighborID(String p_NeighborID) {
        d_NeighborIDs.remove(p_NeighborID);
    }

    @Override
    public String toString() {
        String l_NeighborIDsAsString = String.join(",", d_NeighborIDs);

        return d_ID + ',' + d_xCoord +
                "," + d_yCoord +
                "," + d_ContinentID + (!l_NeighborIDsAsString.isEmpty()?
                "," + l_NeighborIDsAsString:"");
    }
}
