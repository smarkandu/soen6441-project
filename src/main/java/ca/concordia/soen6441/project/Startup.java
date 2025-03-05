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
        try
        {
            d_gameEngine.loadMap(p_filename);
        }
        catch(InvalidMapFileException e)
        {
            System.out.println("File not structured correctly." +
                    "\nPlease load another file.  Reverting previous load.");
            d_gameEngine.resetMap();
        }
    }

    public void assignCountries()
    {
        if (!d_gameEngine.isMapValid()) {
            System.out.println("A valid map must be loaded first");
        }
        else if (d_gameEngine.getPlayers().size() < 2) {
            System.out.println("You must have at least two players to proceed");
        }
        else
        {
            d_countryAssignment.assignCountries();

            // After assigning countries, go to the next phase for each player (Assign Reinforcements)
            AssignReinforcements l_assignReinforements = new AssignReinforcements(d_gameEngine);
            l_assignReinforements.execute();
        }
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

    public String getPhaseName()
    {
        System.out.println("\n*** Welcome to the Game Warzone! ***\nPlease load map using 'loadmap' and add players using 'gameplayer'");
        System.out.println("Once you have done the above, use the command 'assigncountries' to initiate action and start the game\n");
        return super.getPhaseName();
    }
}
