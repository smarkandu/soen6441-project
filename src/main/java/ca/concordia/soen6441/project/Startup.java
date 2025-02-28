package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Player;

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

        // After assigning countries, go to the next phase for each player (Assign Reinforcements)
        AssignReinforcements l_assignReinforements = new AssignReinforcements(d_gameEngine);
        l_assignReinforements.execute();
    }

    @Override
    public void deploy(String p_countryID, int p_toDeploy) {
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
