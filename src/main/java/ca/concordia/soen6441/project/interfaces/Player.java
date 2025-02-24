package ca.concordia.soen6441.project.interfaces;

import java.util.List;

public interface Player {
    List<String> getOwnedCountries();
    List<Order> getOrders();
    void issue_order();
    Order next_order();
    void addCountry(String p_countryID);
    int getReinforcements();
    void setReinforcements(int p_reinforcements);
    void reduceReinforcements(int p_armies);
    String getName();
    void addReinforcements(int p_reinforcements);
}
