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
    public void next()
    {
        Reinforcement nextPhase = new Reinforcement(d_gameEngine);
        nextPhase.assignReinforcements();
        d_gameEngine.setNextPlayerIndex();
        nextPhase.assignReinforcements();
        d_gameEngine.setNextPlayerIndex();
        d_gameEngine.setPhase(new IssueOrder(d_gameEngine));
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
