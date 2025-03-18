package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.gameplay.orders.Deploy;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;

/**
 * The IssueOrder class represents the phase where players issue their orders.
 * This includes deploying troops and progressing to the next phase.
 */
public class IssueOrder extends MainPlay {
    private int d_currentPlayIndex;

    /**
     * Constructs an IssueOrder phase.
     *
     * @param p_gameEngine      The game engine instance controlling the game state.
     * @param p_currentPlayIndex The index of the current player issuing orders.
     */
    public IssueOrder(GameContext p_gameEngine, int p_currentPlayIndex) {
        super(p_gameEngine);
        d_currentPlayIndex = p_currentPlayIndex;
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
     * Allows the current player to deploy troops to a country they own.
     *
     * @param p_countryID The ID of the country to deploy troops to.
     * @param p_toDeploy  The number of troops to deploy.
     */
    @Override
    public void deploy(String p_countryID, int p_toDeploy) {
        Country l_country = d_gameEngine.getCountryManager().getCountries().get(p_countryID);
        Player l_player = d_gameEngine.getPlayerManager().getPlayer(d_currentPlayIndex);

        if (l_player.equals(l_country.getOwner())) {
            int l_numberOfTroopsLeftToDeploy = l_player.getReinforcements() - l_player.getNumberOfTroopsOrderedToDeploy();
            if (l_numberOfTroopsLeftToDeploy >= p_toDeploy) {
                l_player.issue_order(new Deploy(l_player, l_country, p_toDeploy));
            } else {
                System.out.println("Only " + l_numberOfTroopsLeftToDeploy + " left to deploy!");
            }
        } else {
            System.out.println("Player " + l_player.getName() + " doesn't own this country!");
        }
    }

    /**
     * Moves to the next phase in the game.
     * If all players have issued orders, proceeds to order execution.
     */
    @Override
    public void next() {
        Player l_player = d_gameEngine.getPlayerManager().getPlayer(d_currentPlayIndex);
        int l_numberOfTroopsLeftToDeploy = l_player.getReinforcements() - l_player.getNumberOfTroopsOrderedToDeploy();

        if (l_player.getReinforcements() - l_player.getNumberOfTroopsOrderedToDeploy() > 0)
        {
            System.out.println("You still have " + l_numberOfTroopsLeftToDeploy + " left to deploy!");
        }
        else if (d_currentPlayIndex == d_gameEngine.getPlayerManager().getPlayers().size() - 1) {
            OrderExecution l_orderExecution = new OrderExecution(d_gameEngine);
            l_orderExecution.execute();
        } else {
            d_gameEngine.setPhase(new IssueOrder(d_gameEngine, ++d_currentPlayIndex));
        }
    }

    /**
     * Returns the name of the current phase along with the current player's orders.
     *
     * @return A string representing the current phase and the player's orders.
     */
    @Override
    public String getPhaseName() {
        Player l_currentPlayer = d_gameEngine.getPlayerManager().getPlayer(d_currentPlayIndex);
        String l_currentOrders = l_currentPlayer.getOrders().toString();
        return l_currentOrders + "\n" + getClass().getSimpleName() + " ["
                + l_currentPlayer.getName() + "]";
    }
}