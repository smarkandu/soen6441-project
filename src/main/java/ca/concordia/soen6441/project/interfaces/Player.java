package ca.concordia.soen6441.project.interfaces;

import java.util.List;

public interface Player {
    List<String> getOwnedCountries();
    List<Order> getOrders();
    void issue_order();
    Order next_order();
    String getName();
    int getTotalNumberOfReinforcementsPerTurn();
    void assignCountry(Country p_country);;
}
