package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Player;

import java.util.ArrayList;

public class Startup extends Play {
    private CountryAssignment d_countryAssignment;

    public Startup(GameEngine p_gameEngine)
    {
        super(p_gameEngine);
        d_countryAssignment = new CountryAssignment(d_gameEngine);
    }

    public void loadMap(String p_filename)
    {
        printInvalidCommandMessage();
    }

    public void assignCountries()
    {
        d_countryAssignment.assignCountries();
        Reinforcement l_nextPhase = new Reinforcement(d_gameEngine);
        l_nextPhase.assignReinforcements();
        d_gameEngine.setNextPlayerIndex();
        l_nextPhase.assignReinforcements();
        d_gameEngine.setNextPlayerIndex();
        d_gameEngine.setPhase(new IssueOrder(d_gameEngine));
    }

    @Override
    public void deploy(Player p_player, String p_countryID, int p_toDeploy) {
        printInvalidCommandMessage();
    }

    public void gamePlayerAdd(String p_playerName)
    {
        d_gameEngine.addPlayer(p_playerName);

    }

    public void gamePlayerRemove(String p_playerName)
    {
        d_gameEngine.removePlayer(p_playerName);
    }

    public void next() {
        printInvalidCommandMessage();
    }
}
