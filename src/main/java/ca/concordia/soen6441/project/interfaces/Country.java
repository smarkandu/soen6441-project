package ca.concordia.soen6441.project.interfaces;

import java.util.List;

public interface Country {
    String getID();
    List<String> getNeighborIDs();
    void addNeighbor(Country p_Neighbor);
    void removeNeighbor(String p_NeighborID);
    int getNumericID();
    void setTroops(int p_troops);
}
