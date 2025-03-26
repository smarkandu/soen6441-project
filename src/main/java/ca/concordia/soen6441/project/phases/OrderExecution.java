package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.gameplay.orders.Advance;
import ca.concordia.soen6441.project.interfaces.Card;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.util.ArrayList;

/**
 * The OrderExecution class represents the phase where issued orders are executed.
 * It processes each player's orders in a round-robin fashion until all orders are executed.
 */
public class OrderExecution extends MainPlay {

    /**
     * Constructs an OrderExecution phase.
     *
     * @param p_gameEngine The game engine instance controlling the game state.
     */
    public OrderExecution(GameContext p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Invalid command for this phase.
     */
    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    /**
     * Invalid command for this phase.
     */
    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    /**
     * Invalid command for this phase.
     *
     * @param p_countryID The ID of the country where troops would be deployed.
     * @param p_toDeploy  The number of troops to deploy.
     */
    @Override
    public void deploy(String p_countryID, int p_toDeploy) {
        printInvalidCommandMessage();
    }

    @Override
    public void advance(String p_countryNameFrom, String p_countryNameTo, int p_toAdvance) {
        printInvalidCommandMessage();
    }

    @Override
    public void bomb(String p_countryID) {
        printInvalidCommandMessage();
    }

    @Override
    public void blockade(String p_countryID) {
        printInvalidCommandMessage();
    }

    @Override
    public void airlift(String p_sourceCountryID, String p_targetCountryID, int p_numArmies) {
        printInvalidCommandMessage();
    }

    @Override
    public void negotiate(String p_playerID) {
        printInvalidCommandMessage();
    }

    /**
     * Executes all player orders in a round-robin fashion until all orders are completed.
     */
    public void execute() {
        int l_currentPlayerIndex = 0;
        int[] l_playerWins = new int[d_gameEngine.getPlayerManager().getPlayers().size()];
        while (!allPlayersFinishedExecutingOrders()) {
            Player l_player = d_gameEngine.getPlayerManager().getPlayer(l_currentPlayerIndex);
            if (!l_player.getOrders().isEmpty()) {
                Order l_order = l_player.next_order();
                l_order.execute();

                if (l_order instanceof Advance)
                {
                    Advance l_advance = (Advance) l_order;
                    if (l_advance.conquersTerritory())
                    {
                        l_playerWins[l_currentPlayerIndex]++;
                    }
                }
            }

            l_currentPlayerIndex = (l_currentPlayerIndex + 1) % d_gameEngine.getPlayerManager().getPlayers().size();
        } // end while

        // Add cards to Players that conquered a country
        for (int l_i = 0; l_i < l_playerWins.length; l_i++)
        {
            if (l_playerWins[l_i] > 0)
            {

                Card l_cardDrawn = d_gameEngine.getDeckOfCards().getCardFromDeck();
                if (l_cardDrawn != null)
                {
                    d_gameEngine.getPlayerManager().getPlayer(l_i).getHandOfCardsManager().addCard(l_cardDrawn);
                    String l_message = d_gameEngine.getPlayerManager().getPlayer(l_i).getName() + " conquered " + l_playerWins[l_i]
                            + " countries this round! Awarded a " + l_cardDrawn + " card!";
                    LogEntryBuffer.getInstance().appendToBuffer(l_message, true);
                }
            }
        }

        validatePlayers();
        String l_playerWhoWon = null;
        if ((l_playerWhoWon = gameWonBy()) != null)
        {
            System.out.println("Game won by " + l_playerWhoWon + ": congratulations!");
            End l_end = new End(d_gameEngine);
            l_end.endGame();
        }
        else
        {
            AssignReinforcements l_assignReinforcements = new AssignReinforcements(d_gameEngine);
            l_assignReinforcements.execute();
        }
    }

    /**
     * Checks if all players have finished executing their orders.
     *
     * @return true if all players have no remaining orders, false otherwise.
     */
    public boolean allPlayersFinishedExecutingOrders() {
        int l_numberOfPlayersFinished = 0;

        for (Player l_player : d_gameEngine.getPlayerManager().getPlayers().values()) {
            if (l_player.getOrders().size() == 0) {
                l_numberOfPlayersFinished++;
            }
        }

        return l_numberOfPlayersFinished == d_gameEngine.getPlayerManager().getPlayers().size();
    }

    /**
     * Invalid command for this phase.
     */
    @Override
    public void next() {
        printInvalidCommandMessage();
    }

    public String gameWonBy() {
        ArrayList<Country> l_listOfCountries = new ArrayList<>(d_gameEngine.getCountryManager().getCountries().values());
        Player l_player = l_listOfCountries.get(0).getOwner();

        for (Country l_country : l_listOfCountries) {
            if (l_country.getOwner() == null || l_country.getOwner() != l_player) {
                return null;
            }
        }

        return l_player.getName();
    }

    public void validatePlayers()
    {
        ArrayList<Player> l_players = new ArrayList<>(d_gameEngine.getPlayerManager().getPlayers().values());
        for (int l_i = 0; l_i < l_players.size() ; l_i++)
        {
            if (l_players.get(l_i) == null || l_players.get(l_i).getOwnedCountries().isEmpty())
            {
                LogEntryBuffer.getInstance().appendToBuffer("Player " + l_players.get(l_i).getName() +
                        " no longer owns any countries and is no longer part of the game!", true);
                d_gameEngine.getPlayerManager().removePlayer(l_players.get(l_i).getName());
            }
        }
    }
}