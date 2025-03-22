package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;

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
        while (!allPlayersFinishedExecutingOrders()) {
            Player l_player = d_gameEngine.getPlayerManager().getPlayer(l_currentPlayerIndex);
            if (!l_player.getOrders().isEmpty()) {
                Order l_order = l_player.next_order();
                l_order.execute();
            }

            l_currentPlayerIndex = (l_currentPlayerIndex + 1) % d_gameEngine.getPlayerManager().getPlayers().size();
            showMap();
        }

        AssignReinforcements l_assignReinforcements = new AssignReinforcements(d_gameEngine);
        l_assignReinforcements.execute();
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
}