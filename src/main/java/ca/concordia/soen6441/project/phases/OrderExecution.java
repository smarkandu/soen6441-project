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
 * This class represents the "Order Execution" phase of the game.
 * It sequentially executes all player orders in a round-robin fashion,
 * handles territory conquests, card rewards, and determines game completion.
 */
public class OrderExecution extends MainPlay {

    /**
     * Constructor to initialize the phase with the current game context.
     *
     * @param p_gameEngine the current game context
     */
    public OrderExecution(GameContext p_gameEngine) {
        super(p_gameEngine);
    }

    /** {@inheritDoc} */
    @Override
    public void gamePlayerAdd(String p_playerName) {
        printInvalidCommandMessage();
    }

    /** {@inheritDoc} */
    @Override
    public void gamePlayerRemove(String p_playerName) {
        printInvalidCommandMessage();
    }

    /** {@inheritDoc} */
    @Override
    public void deploy(String p_countryID, int p_toDeploy) {
        printInvalidCommandMessage();
    }

    /** {@inheritDoc} */
    @Override
    public void advance(String p_countryNameFrom, String p_countryNameTo, int p_toAdvance) {
        printInvalidCommandMessage();
    }

    /** {@inheritDoc} */
    @Override
    public void bomb(String p_countryID) {
        printInvalidCommandMessage();
    }

    /** {@inheritDoc} */
    @Override
    public void blockade(String p_countryID) {
        printInvalidCommandMessage();
    }

    /** {@inheritDoc} */
    @Override
    public void airlift(String p_sourceCountryID, String p_targetCountryID, int p_numArmies) {
        printInvalidCommandMessage();
    }

    /** {@inheritDoc} */
    @Override
    public void negotiate(String p_playerID) {
        printInvalidCommandMessage();
    }

    /** {@inheritDoc} */
    @Override
    public void next() {
        printInvalidCommandMessage();
    }

    /**
     * Executes all orders from each player in round-robin fashion.
     * Awards cards for territory conquests, validates player status,
     * checks for game winner, and transitions to the next phase.
     */
    public void execute() {
        int l_currentPlayerIndex = 0;
        int[] l_playerWins = new int[d_gameEngine.getPlayerManager().getPlayers().size()];

        while (!allPlayersFinishedExecutingOrders()) {
            Player l_player = d_gameEngine.getPlayerManager().getPlayer(l_currentPlayerIndex);
            if (!l_player.getOrders().isEmpty()) {
                Order l_order = l_player.next_order();
                l_order.execute();

                if (l_order instanceof Advance) {
                    Advance l_advance = (Advance) l_order;
                    if (l_advance.conquersTerritory()) {
                        l_playerWins[l_currentPlayerIndex]++;
                    }
                }
            }
            l_currentPlayerIndex = (l_currentPlayerIndex + 1) % d_gameEngine.getPlayerManager().getPlayers().size();
        }

        // Award cards to players who conquered at least one territory
        for (int l_i = 0; l_i < l_playerWins.length; l_i++) {
            if (l_playerWins[l_i] > 0) {
                Card l_cardDrawn = d_gameEngine.getDeckOfCards().getCardFromDeck();
                if (l_cardDrawn != null) {
                    d_gameEngine.getPlayerManager().getPlayer(l_i).getHandOfCardsManager().addCard(l_cardDrawn);
                    String l_message = d_gameEngine.getPlayerManager().getPlayer(l_i).getName()
                            + " conquered " + l_playerWins[l_i]
                            + " countries this round! Awarded a " + l_cardDrawn + " card!";
                    LogEntryBuffer.getInstance().appendToBuffer(l_message, true);
                }
            }
        }

        validatePlayers();

        String l_playerWhoWon = gameWonBy();
        if (l_playerWhoWon != null) {
            System.out.println("Game won by " + l_playerWhoWon + ": congratulations!");
            End l_end = new End(d_gameEngine);
            l_end.endGame();
        } else {
            AssignReinforcements l_assignReinforcements = new AssignReinforcements(d_gameEngine);
            l_assignReinforcements.execute();

            // Transition to next phase
            d_gameEngine.setPhase(new IssueOrder(d_gameEngine, 0));
        }
    }

    /**
     * Checks if all players have completed executing their orders.
     *
     * @return true if all players are done, false otherwise
     */
    public boolean allPlayersFinishedExecutingOrders() {
        int l_finished = 0;
        for (Player l_player : d_gameEngine.getPlayerManager().getPlayers().values()) {
            if (l_player.getOrders().isEmpty()) {
                l_finished++;
            }
        }
        return l_finished == d_gameEngine.getPlayerManager().getPlayers().size();
    }

    /**
     * Checks if a player has won the game.
     * A player wins if they own all the countries on the map.
     *
     * @return the name of the winning player, or null if no winner
     */
    public String gameWonBy() {
        ArrayList<Country> l_listOfCountries = new ArrayList<>(d_gameEngine.getCountryManager().getCountries().values());
        if (l_listOfCountries.isEmpty()) return null;

        Player l_player = l_listOfCountries.get(0).getOwner();
        for (Country l_country : l_listOfCountries) {
            if (l_country.getOwner() == null || l_country.getOwner() != l_player) {
                return null;
            }
        }
        return l_player.getName();
    }

    /**
     * Validates the current players list by removing any player
     * who no longer owns any territories.
     */
    public void validatePlayers() {
        ArrayList<Player> l_players = new ArrayList<>(d_gameEngine.getPlayerManager().getPlayers().values());
        for (Player l_player : l_players) {
            if (l_player == null || l_player.getOwnedCountries().isEmpty()) {
                LogEntryBuffer.getInstance().appendToBuffer("Player " + l_player.getName()
                        + " no longer owns any countries and is no longer part of the game!", true);
                d_gameEngine.getPlayerManager().removePlayer(l_player.getName());
            }
        }
    }
}
