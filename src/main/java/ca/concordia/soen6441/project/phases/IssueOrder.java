package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameEngine;
import ca.concordia.soen6441.project.gameplay.orders.Deploy;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;

public class IssueOrder extends MainPlay {
    private int d_currentPlayIndex;

    public IssueOrder(GameEngine p_gameEngine, int p_currentPlayIndex) {
        super(p_gameEngine);
        d_currentPlayIndex = p_currentPlayIndex;
    }

    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void deploy(String p_countryID, int p_toDeploy) {
        Country l_country = d_gameEngine.getCountries().get(p_countryID);
        Player l_player = d_gameEngine.getPlayer(d_currentPlayIndex);

        if (l_player.equals(l_country.getOwner()))
        {
            int l_numberOfTroopsLeftToDeploy = l_player.getReinforcements() - l_player.getNumberOfTroopsOrderedToDeploy();
            if (l_numberOfTroopsLeftToDeploy >= p_toDeploy)
            {
                l_player.issue_order(new Deploy(l_player, l_country, p_toDeploy));
            }
            else
            {
                System.out.println("Only " + l_numberOfTroopsLeftToDeploy + " left to deploy!");
            }
        }
        else
        {
            System.out.println("Player " + l_player.getName() + " doesn't own this country!");
        }
    }

    @Override
    public void next() {
        if (d_currentPlayIndex == d_gameEngine.getPlayers().size() - 1)
        {
            OrderExecution l_orderExecution = new OrderExecution(d_gameEngine);
            l_orderExecution.execute();
        }
        else
        {
            d_gameEngine.setPhase(new IssueOrder(d_gameEngine, ++d_currentPlayIndex));
        }
    }

    @Override
    public String getPhaseName()
    {
        Player l_currentPlayer = d_gameEngine.getPlayer(d_currentPlayIndex);
        String l_currentOrders = l_currentPlayer.getOrders().toString();
        return l_currentOrders + "\n" + getClass().getSimpleName() + " ["
                + l_currentPlayer
                .getName() + "]";
    }
}