package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.OverallFactory;
import ca.concordia.soen6441.project.gameplay.CountryAssignment;
import ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType;
import ca.concordia.soen6441.project.log.LogEntryBuffer;
import ca.concordia.soen6441.project.map.InvalidMapFileException;

import java.io.FileNotFoundException;

/**
 * The Startup class represents the initial phase of the game.
 * Players can load maps, add players, and assign countries before the game begins.
 */
public class Startup extends Play
{
    private CountryAssignment d_countryAssignment;

    /**
     * Constructs the Startup phase.
     */
    public Startup()
    {
        d_countryAssignment = new CountryAssignment();
    }

    /**
     * Loads a map from a file. If the file is not found or invalid, an error is displayed.
     *
     * @param p_filename The filename of the map to load.
     */
    public void loadMap(String p_filename)
    {
        try
        {
            GameDriver.getGameEngine().loadMap(p_filename);
        } catch (InvalidMapFileException e)
        {
            System.out.println("File not structured correctly.\n" + "Please load another file.  Reverting previous load.");
            GameDriver.getGameEngine().resetMap();
        } catch (FileNotFoundException e)
        {
            System.out.println("File '" + p_filename + "' not found.  Please try loading another map file instead");
        }
    }

    /**
     * Assigns countries to players if the map is valid and at least two players are present.
     */
    public void assignCountries()
    {
        if (!GameDriver.getGameEngine().isMapValid())
        {
            System.out.println("A valid map must be loaded first");
        }
        else if (GameDriver.getGameEngine().getPlayerManager().getPlayers().size() < 2)
        {
            System.out.println("You must have at least two players to proceed");
        }
        else
        {
            LogEntryBuffer.getInstance().appendToBuffer("\n*** Map: " + GameDriver.getGameEngine().getMapFileLoaded() + "; Game #" + GameDriver.getGameEngine().getGameNumber() + " begins! ***", true);
            d_countryAssignment.assignCountries();

            // After assigning countries, go to the next phase for each player (Assign Reinforcements)
            AssignReinforcements l_assignReinforcements = new AssignReinforcements();
            l_assignReinforcements.execute();
        }
    }

    @Override
    public void deploy(String p_countryID, int p_toDeploy)
    {
        printInvalidCommandMessage();
    }

    @Override
    public void advance(String p_countryNameFrom, String p_countryNameTo, int p_toAdvance)
    {
        printInvalidCommandMessage();
    }

    @Override
    public void bomb(String p_countryID)
    {
        printInvalidCommandMessage();
    }

    @Override
    public void blockade(String p_countryID)
    {
        printInvalidCommandMessage();
    }

    @Override
    public void airlift(String p_sourceCountryID, String p_targetCountryID, int p_numArmies)
    {
        printInvalidCommandMessage();
    }

    @Override
    public void negotiate(String p_playerID)
    {
        printInvalidCommandMessage();
    }

    /**
     * {@inheritDoc}
     */
    public void gamePlayerAdd(String p_playerName, PlayerBehaviorType p_playerBehaviorType)
    {
        GameDriver.getGameEngine().getPlayerManager().addPlayer(p_playerName, p_playerBehaviorType);
    }

    /**
     * Removes an existing player from the game.
     *
     * @param p_playerName The name of the player to remove.
     */
    public void gamePlayerRemove(String p_playerName)
    {
        GameDriver.getGameEngine().getPlayerManager().removePlayer(p_playerName);
    }

    /**
     * Invalid command for this phase.
     */
    public void next()
    {
        printInvalidCommandMessage();
    }

    /**
     * Displays instructions for starting the game.
     *
     * @return The name of the phase.
     */
    @Override
    public String getPhaseName()
    {
        System.out.println("\n*** Welcome to the Game Warzone! ***\nPlease load map using 'loadmap' and add players using 'gameplayer'");
        System.out.println("Once you have done the above, use the command 'assigncountries' to initiate action and start the game\n");
        return super.getPhaseName();
    }

    /**
     * {@inheritDoc}
     */
    public void saveGame(String p_filename)
    {
        printInvalidCommandMessage();
    }

    public void execute()
    {
        if (GameDriver.getTournamentQueue().size() > 1)
        {
            GameDriver.getPriorTournaments().add(GameDriver.getGameEngine());
            GameDriver.setGameEngine(GameDriver.getTournamentQueue().removeFirst());
            GameDriver.getGameEngine().getPhase().assignCountries();
        }
        else if (GameDriver.getTournamentQueue().size() == 1)
        {
            LogEntryBuffer.getInstance().appendToBuffer("\nThe tournament is over!  The results are as follows:\n", true);
            GameDriver.getTournamentResults();
            LogEntryBuffer.getInstance().appendToBuffer("\nWe now return you back to your game!\n", true);
            GameDriver.setGameEngine(GameDriver.getTournamentQueue().removeFirst());
        }
        else if (GameDriver.getGameEngine().getOutcomeOfGame() != null)
        {
            // Create new game engine
            GameDriver.setGameEngine(OverallFactory.getInstance().CreateGameEngine());
            Startup l_startup = new Startup();
            GameDriver.getGameEngine().setPhase(l_startup);
        }
    }
}
