package ca.concordia.soen6441.project;

public class IssueOrder extends MainPlay {
    public IssueOrder(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    public void next() {
        // TODO
    }
}