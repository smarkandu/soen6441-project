package ca.concordia.soen6441.project.interfaces;

import ca.concordia.soen6441.project.context.PlayerManager;
import ca.concordia.soen6441.project.interfaces.context.HandOfCardsContext;
import ca.concordia.soen6441.project.interfaces.gameplay.behavior.PlayerBehavior;

import java.util.List;

/**
 * Represents a generic player in the game
 */
public interface Player {
    /**
     * Gets the list of country IDs owned by the player.
     *
     * @return A list of owned country IDs.
     */
    List<String> getOwnedCountries();

    /**
     * Gets the list of orders issued by the player.
     *
     * @return A list of issued orders.
     */
    List<Order> getOrders();

    /**
     * Issues a new order for the player.
     */
    void issue_order();

    /**
     * Retrieves and removes the next order from the player's order list.
     *
     * @return The next order, or null if no orders are available.
     */
    Order next_order();

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    String getName();

    /**
     * Gets the total number of reinforcements per turn for the player.
     *
     * @return The total number of reinforcements.
     */
    int getTotalNumberOfReinforcementsPerTurn();

    /**
     * Adds owned country to the player.
     *
     * @param p_country The country to be added
     */
    void addOwnedCountry(Country p_country);


    /**
     * Removes owner country to the player.
     *
     * @param p_country The country to be removed
     */
    void removeOwnedCountry(Country p_country);

    /**
     * Gets the current number of reinforcements available to the player.
     *
     * @return The number of reinforcements.
     */
    int getReinforcements();

    /**
     * Sets the number of reinforcements available to the player.
     *
     * @param p_reinforcements The number of reinforcements to be set.
     */
    void setReinforcements(int p_reinforcements);

    /**
     * Gets the total number of troops that have been ordered for deployment.
     *
     * @return The number of troops ordered to deploy.
     */
    int getNumberOfTroopsOrderedToDeploy();

    /**
     * Gets the total number of troops that have been ordered for advance.
     *
     * @return The number of troops ordered to advance.
     */
    int getNumberOfTroopsOrderedToAdvance(Country p_countryFrom);

    /**
     * Gets the object representing the cards the current player holds
     *
     * @return HandsOfCardsManager object for the current player
     */
    HandOfCardsContext getHandOfCardsManager();
    void addNegotiatedPlayer(Player p_player);
    boolean hasNegotiatedWith(Player p_player);
    void resetNegotiatedPlayers();
    List<Player> getNegotiatedPlayers();  // needed for reset check
    void removeNegotiatedPlayer(Player p_player);

    /**
     * Get the player's behavior object
     * @return PlayerBehavior object
     */
    PlayerBehavior getPlayerBehavior();

    /**
     * Gets the Player Manager
     * @return PlayerManager object
     */
    PlayerManager getPlayerManager();


    /**
     * Add new order to list of orders
     * @param p_newOrder new Order object to add
     */
    void addToOrders(Order p_newOrder);
}
