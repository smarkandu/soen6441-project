package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Player;

import java.util.ArrayList;

public class OrderExecution extends MainPlay {
    private int d_currentPlayIndex;

    public OrderExecution(GameEngine p_gameEngine, int p_currentPlayIndex) {
        super(p_gameEngine);
        this.d_currentPlayIndex = p_currentPlayIndex;
    }

    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void deploy(String p_countryID, int p_toDeploy) {
        printInvalidCommandMessage();
    }

    @Override
    public void next()
    {
        if (d_currentPlayIndex == d_gameEngine.getPlayers().size() - 1)
        {
            d_gameEngine.setPhase(new AssignReinforcements(d_gameEngine));
        }
        else
        {
            d_gameEngine.setPhase(new OrderExecution(d_gameEngine, ++d_currentPlayIndex));
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
