package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;

public class IssueOrder extends MainPlay {
    public IssueOrder(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void deploy(Player p_player, String p_countryID, int p_to_deploy) {
        Country l_country = d_gameEngine.getCountries().get(p_countryID);
        if (p_player.equals(l_country.getOwner()))
        {
            int numberOfTroopsLeftToDeploy = p_player.getReinforcements() - p_player.getNumberOfTroopsOrderedToDeploy();
            if (numberOfTroopsLeftToDeploy >= p_to_deploy)
            {
                p_player.issue_order(new Deploy(p_player, l_country, p_to_deploy));
            }
            else
            {
                System.out.println("Only " + numberOfTroopsLeftToDeploy + " left to deploy!");
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
        Player currentPlayer = d_gameEngine.getPlayers().values().toArray(new Player[0])[d_gameEngine.get_currentPlayerIndex()];
        String currentOrders = currentPlayer.getOrders().toString();
        return currentOrders + "\n" + getClass().getSimpleName() + " ["
                + currentPlayer
                .getName() + "]";
    }
}