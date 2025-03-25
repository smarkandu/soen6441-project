package ca.concordia.soen6441.project.interfaces;

import ca.concordia.soen6441.project.interfaces.context.HandOfCardsContext;

import java.util.List;
public interface Player {
    List<String> getOwnedCountries();
    List<Order> getOrders();
    void issue_order(Order p_order);
    Order next_order();
    String getName();
    int getTotalNumberOfReinforcementsPerTurn();
    void assignCountry(Country p_country);
    int getReinforcements();
    void setReinforcements(int p_reinforcements);
    int getNumberOfTroopsOrderedToDeploy();
    int getNumberOfTroopsOrderedToAdvance(Country p_countryFrom);
    HandOfCardsContext getHandOfCardsManager();
    void addNegotiatedPlayer(Player p_player);
    boolean hasNegotiatedWith(Player p_player);
    void resetNegotiatedPlayers();
    List<Player> getNegotiatedPlayers();  // needed for reset check
}
