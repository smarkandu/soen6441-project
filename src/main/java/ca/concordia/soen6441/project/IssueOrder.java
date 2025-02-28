package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;

import java.util.ArrayList;

public class IssueOrder extends MainPlay {
    public IssueOrder(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void deploy(Player p_player, String p_countryID, int p_toDeploy) {
        Country l_country = d_gameEngine.getCountries().get(p_countryID);
        if (p_player.equals(l_country.getOwner()))
        {
            int l_numberOfTroopsLeftToDeploy = p_player.getReinforcements() - p_player.getNumberOfTroopsOrderedToDeploy();
            if (l_numberOfTroopsLeftToDeploy >= p_toDeploy)
            {
                p_player.issue_order(new Deploy(p_player, l_country, p_toDeploy));
            }
            else
            {
                System.out.println("Only " + l_numberOfTroopsLeftToDeploy + " left to deploy!");
            }
        }
        else
        {
            System.out.println("Player " + p_player.getName() + " doesn't own this country!");
        }
    }

    @Override
    public void next() {
        d_gameEngine.setNextPlayerIndex();
        if (d_gameEngine.get_currentPlayerIndex() == 0)
        {
            d_gameEngine.setPhase(new OrderExecution(d_gameEngine));
        }
        else
        {
            d_gameEngine.setPhase(new IssueOrder(d_gameEngine));
        }
    }

    @Override
    public String getPhaseName()
    {
        Player l_currentPlayer = new ArrayList<Player>(d_gameEngine.getPlayers().values()).get(d_gameEngine.get_currentPlayerIndex());
        String l_currentOrders = l_currentPlayer.getOrders().toString();
        return l_currentOrders + "\n" + getClass().getSimpleName() + " ["
                + l_currentPlayer
                .getName() + "]";
    }
}