package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Player;

import java.util.ArrayList;

public class OrderExecution extends MainPlay {
    public OrderExecution(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void deploy(Player p_player, String p_countryID, int p_toDeploy) {
        printInvalidCommandMessage();
    }

    @Override
    public void next()
    {
        AssignReinforcements l_nextPhase = new AssignReinforcements(d_gameEngine);
        l_nextPhase.assignReinforcements();
        d_gameEngine.setNextPlayerIndex();
        l_nextPhase.assignReinforcements();
        d_gameEngine.setNextPlayerIndex();
        d_gameEngine.setPhase(new IssueOrder(d_gameEngine));
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
