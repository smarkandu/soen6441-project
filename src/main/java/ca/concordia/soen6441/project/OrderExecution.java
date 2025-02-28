package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;

import java.util.ArrayList;
import java.util.Map;

public class OrderExecution extends MainPlay {
    public OrderExecution(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void deploy(String p_countryID, int p_toDeploy) {
        printInvalidCommandMessage();
    }

    public void execute()
    {
        int l_current_player_index = 0;
        while (!allPlayersFinishedExecutingOrders())
        {
            Player l_player = d_gameEngine.getPlayer(l_current_player_index);
            if (!l_player.getOrders().isEmpty())
            {
                Order order = l_player.next_order();
                order.execute();
            }

            l_current_player_index = (l_current_player_index + 1) % d_gameEngine.getPlayers().size();
        }

        AssignReinforcements l_assignReinforements = new AssignReinforcements(d_gameEngine);
        l_assignReinforements.execute();
    }

    public boolean allPlayersFinishedExecutingOrders()
    {
        int numberOfPlayersFinished = 0;

        for (Player player: d_gameEngine.getPlayers().values())
        {
               if (player.getOrders().size() == 0)
               {
                   numberOfPlayersFinished++;
               }
        }

        return numberOfPlayersFinished == d_gameEngine.getPlayers().size();
    }

    @Override
    public void next()
    {
        printInvalidCommandMessage();
    }
}
