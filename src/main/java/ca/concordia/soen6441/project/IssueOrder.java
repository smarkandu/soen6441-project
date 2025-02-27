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
        p_player.issue_order(new Deploy(p_player, l_country, p_to_deploy));
    }

    public void next() {
        // TODO
    }
}