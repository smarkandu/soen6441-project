package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Country;
import java.util.ArrayList;
import java.util.List;

public class CountryImpl implements Country {
    private final String d_ID;
    private final int d_xCoord;
    private final int d_yCoord;
    private final String d_ContinentID;
    private List<String> d_NeighborIDs;
    private int d_armies;

    public CountryImpl(String p_ID, String p_ContinentID) {
        this.d_ID = p_ID;
        this.d_xCoord = 0;
        this.d_yCoord = 0;
        this.d_ContinentID = p_ContinentID;  // Fixed `final` variable issue
        this.d_NeighborIDs = new ArrayList<>();
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
        if (!d_NeighborIDs.contains(p_NeighborID)) {  // Fixed undefined variable
            d_NeighborIDs.add(p_NeighborID);
        }
    }

    @Override
    public void removeNeighborID(String p_NeighborID) {
        d_NeighborIDs.remove(p_NeighborID);
    }

    @Override
    public void addArmies(int p_armies) {
        if (p_armies <= 0) {
            throw new IllegalArgumentException("Army count must be positive.");
        }
        this.d_armies += p_armies;
    }

    // This method is not in the interface, so no @Override needed
    public int getArmies() {
        return d_armies;
    }

    @Override
    public String getContinentID() {  
        return d_ContinentID;
    }

    @Override
    public String toString() {
        String l_NeighborIDsAsString = String.join(",", d_NeighborIDs);

        return d_ID + ',' + d_xCoord +
                "," + d_yCoord +
                "," + d_ContinentID + (!l_NeighborIDsAsString.isEmpty() ?
                "," + l_NeighborIDsAsString : "");
    }
}
