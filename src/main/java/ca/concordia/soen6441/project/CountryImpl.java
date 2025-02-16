package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Country;

import java.util.List;

public class CountryImpl implements Country {
    private final String d_ID;
    private final int x_coord;
    private final int y_coord;
    private final String d_ContinentID;
    private List<String> d_NeighborIDs;

    public CountryImpl(String p_ID, String p_ContinentID, List<String> p_NeighborIDs) {
        this.d_ID = p_ID;
        this.x_coord = 0;
        this.y_coord = 0;
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
        String d_NeighborIDsAsString = String.join(",", d_NeighborIDs);

        return d_ID + ',' + x_coord +
                "," + y_coord +
                "," + d_ContinentID + (!d_NeighborIDsAsString.isEmpty()?
                "," + d_NeighborIDsAsString:"");
    }
}
