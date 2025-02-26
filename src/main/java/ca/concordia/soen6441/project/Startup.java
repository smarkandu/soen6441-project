package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Player;

import java.util.ArrayList;

public class Startup extends Play {
    public Startup(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    public void loadMap()
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
        d_gameEngine.addPlayer(new PlayerImpl(p_playerName, new ArrayList<>(), new ArrayList<>()));

    }

    public void gamePlayerRemove(String p_playerName)
    {
        // TODO: (Marc) Add implementation
        d_gameEngine.removePlayer(new PlayerImpl(p_playerName, new ArrayList<>(), null));
    }



    public void next() {
        // TODO
    }
}
