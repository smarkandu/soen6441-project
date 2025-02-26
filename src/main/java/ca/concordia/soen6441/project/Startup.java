package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Player;

import java.util.ArrayList;

public class Startup extends Play {
    public Startup(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    public void loadMap(String p_filename)
    {
        // TODO
    }

    public void assignCountries()
    {
        // TODO
    }

    public void gamePlayerAdd(String p_playerName)
    {
        // TODO: (Marc) Add implementation
        d_gameEngine.addPlayer(p_playerName);

    }

    public void gamePlayerRemove(String p_playerName)
    {
        // TODO: (Marc) Add implementation
        d_gameEngine.removePlayer(p_playerName);
    }



    public void next() {
        // TODO
    }
}
