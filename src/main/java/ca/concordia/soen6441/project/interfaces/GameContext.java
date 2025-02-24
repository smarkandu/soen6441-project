package ca.concordia.soen6441.project.interfaces;

import java.util.List;

public interface GameContext {
    List<String> getContinentIDs();
    List<String> getCountryIDs();
    void addContinent(String p_continentID, int p_continentValue);
    void addCountry(String p_CountryID, String p_continentID, List<String> p_neighbors);
    void addNeighbor(String p_CountryID, String p_neighborCountryID);
    void removeContinent(String p_continentID);
    void removeCountry(String p_countryID);
    void removeNeighbor(String p_neighborCountryID, String p_countryToAdd);
    void showMap();
    void assignCountryToPlayer(Player p_player, String p_countryID);
    int calculateReinforcements(Player p_player);
    void deployArmies(Player p_player, String p_countryID, int p_armies);
    void assignReinforcements(Player p_player, int p_reinforcements);
}
