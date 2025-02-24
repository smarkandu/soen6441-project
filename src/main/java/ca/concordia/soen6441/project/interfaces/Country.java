package ca.concordia.soen6441.project.interfaces;

import java.util.List;

public interface Country {
    String getID();
    List<String> getNeighborIDs();
    void addNeighborID(String p_NeighborID);
    void removeNeighborID(String p_NeighborID);
    
    String getContinentID();  // Retrieves the continent ID of the country
    void addArmies(int p_armies);
}

