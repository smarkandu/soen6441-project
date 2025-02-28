package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Player;

public class OrderExecution extends MainPlay {
    public OrderExecution(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void deploy(Player p_player, String p_countryID, int p_to_deploy) {
        printInvalidCommandMessage();
    }

    @Override
    public void next() {
        d_gameEngine.setNextPlayerIndex();
        if (d_gameEngine.get_currentPlayerIndex() == 0)
        {
            d_gameEngine.setPhase(new IssueOrder(d_gameEngine));
        }
    }

    @Override
    public String getPhaseName()
    {
        return getClass().getSimpleName() + " ["
                + d_gameEngine.getPlayers().values().toArray(new Player[0])[d_gameEngine.get_currentPlayerIndex()]
                .getName() + "]";
    }
}
