package ca.concordia.soen6441.project.gameplay;

import ca.concordia.soen6441.project.context.hand.HandOfCardsManager;
import ca.concordia.soen6441.project.gameplay.orders.Advance;
import ca.concordia.soen6441.project.gameplay.orders.Deploy;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.HandOfCardsContext;
import ca.concordia.soen6441.project.interfaces.gameplay.behavior.PlayerBehavior;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * The PlayerImpl class represents a player in the game, implementing the Player interface.
 * It manages player properties, owned countries, orders, and reinforcements.
 */
public class PlayerImpl implements Player, Serializable {
    private String d_name;
    private ArrayList<String> d_ownedCountries;
    private ArrayList<Order> d_Orders;
    private HandOfCardsContext d_HandsOfCardsManager;
    private int d_Reinforcements;
    private List<Player> d_negotiatedPlayers = new ArrayList<>();
    private PlayerBehavior d_playerBehavior;

    /**
     * Constructs a PlayerImpl instance.
     *
     * @param p_name           The name of the player.
     * @param p_ownedCountries The list of country IDs owned by the player.
     * @param p_Orders         The list of orders issued by the player.
     */
    public PlayerImpl(String p_name, ArrayList<String> p_ownedCountries, ArrayList<Order> p_Orders,
                      PlayerBehavior p_playerBehavior) {
        this.d_name = p_name;
        this.d_ownedCountries = p_ownedCountries;
        this.d_Orders = p_Orders;
        this.d_Reinforcements = 0;
        this.d_HandsOfCardsManager = new HandOfCardsManager(this);
        this.d_playerBehavior = p_playerBehavior;
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    @Override
    public String getName() {
        return d_name;
    }

    /**
     * Gets the list of country IDs owned by the player.
     *
     * @return A list of owned country IDs.
     */
    @Override
    public List<String> getOwnedCountries() {
        return d_ownedCountries;
    }

    /**
     * Gets the list of orders issued by the player.
     *
     * @return A list of issued orders.
     */
    @Override
    public List<Order> getOrders() {
        return d_Orders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void issue_order() {
        d_playerBehavior.issue_order(this);
    }

    /**
     * Retrieves and removes the next order from the player's order list.
     *
     * @return The next order, or null if no orders are available.
     */
    @Override
    public Order next_order() {
        if (!d_Orders.isEmpty()) {
            return this.d_Orders.remove(0);
        } else {
            return null;
        }
    }

    /**
     * Gets the total number of reinforcements per turn for the player.
     *
     * @return The total number of reinforcements.
     */
    @Override
    public int getTotalNumberOfReinforcementsPerTurn() {
        int l_returnValue = 5;
        return l_returnValue;
    }

    /**
     * Adds owned country to the player.
     *
     * @param p_country The country to be added
     */
    @Override
    public void addOwnedCountry(Country p_country) {
        d_ownedCountries.add(p_country.getID());
    }

    /**
     * Removes owner country to the player.
     *
     * @param p_country The country to be removed
     */
    @Override
    public void removeOwnedCountry(Country p_country) {
        d_ownedCountries.remove(p_country.getID());
    }

    /**
     * Gets the current number of reinforcements available to the player.
     *
     * @return The number of reinforcements.
     */
    @Override
    public int getReinforcements() {
        return d_Reinforcements;
    }

    /**
     * Sets the number of reinforcements available to the player.
     *
     * @param p_Reinforcements The number of reinforcements to be set.
     */
    @Override
    public void setReinforcements(int p_Reinforcements) {
        d_Reinforcements = p_Reinforcements;
    }

    /**
     * Gets the total number of troops that have been ordered for deployment.
     *
     * @return The number of troops ordered to deploy.
     */
    @Override
    public int getNumberOfTroopsOrderedToDeploy() {
        int l_returnValue = 0;
        for (int l_i = 0; l_i < d_Orders.size(); l_i++) {
            if (d_Orders.get(l_i).getClass().getSimpleName().equals("Deploy")) {
                Deploy l_deployOrder = (Deploy) d_Orders.get(l_i);
                l_returnValue += l_deployOrder.get_to_deploy();
            }
        }
        return l_returnValue;
    }

    public int getNumberOfTroopsOrderedToAdvance(Country p_countryFrom) {
        int l_returnValue = 0;
        for (int l_i = 0; l_i < d_Orders.size(); l_i++) {
            if (d_Orders.get(l_i).getClass().getSimpleName().equals("Advance")) {
                Advance l_advanceOrder = (Advance) d_Orders.get(l_i);
                if (Objects.equals(l_advanceOrder.getSourceTerritory().getID(), p_countryFrom.getID())) {
                    l_returnValue += l_advanceOrder.getToAdvance();
                }
            }
        }
        return l_returnValue;
    }

    @Override
    public HandOfCardsContext getHandOfCardsManager() {
        return d_HandsOfCardsManager;
    }

    /**
     * Adds a player to the list of players this player has negotiated with.
     *
     * @param p_player The player to add to the negotiated list.
     */
    @Override
    public void addNegotiatedPlayer(Player p_player) {
        if (!d_negotiatedPlayers.contains(p_player)) {
            d_negotiatedPlayers.add(p_player);
        }
    }

    /**
     * Checks if this player has a diplomacy pact with the given player.
     *
     * @param p_player The player to check against.
     * @return true if a diplomacy pact exists, false otherwise.
     */
    @Override
    public boolean hasNegotiatedWith(Player p_player) {
        return d_negotiatedPlayers.contains(p_player);
    }

    /**
     * Clears all existing diplomacy pacts for this player.
     * Typically called at the beginning of a new round.
     */
    @Override
    public void resetNegotiatedPlayers() {
        d_negotiatedPlayers.clear();
    }

    /**
     * Returns the current list of negotiated players.
     *
     * @return A list of players this player cannot attack this turn.
     */
    @Override
    public List<Player> getNegotiatedPlayers() {
        return d_negotiatedPlayers;
    }

    /**
     * Removes a specific player from the list of negotiated players.
     *
     * @param p_player The player to remove from the diplomacy pact.
     */
    @Override
    public void removeNegotiatedPlayer(Player p_player) {
        d_negotiatedPlayers.remove(p_player);
    }

    /**
     * {@inheritDoc}
     */
    public PlayerBehavior getPlayerBehavior() {
        return d_playerBehavior;
    }

    /**
     * {@inheritDoc}
     */
    public void addToOrders(Order p_newOrder)
    {
        d_Orders.add(p_newOrder);
    }
}



