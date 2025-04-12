package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.OverallFactory;
import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;

// State of the State pattern

/**
 * The Play class represents an abstract phase in the game where the main gameplay occurs.
 * It extends the Phase class and defines behaviors specific to the play state.
 */
public abstract class Play extends Phase implements Serializable
{

    /**
     * Constructs a Play phase.
     */
    public Play()
    {

    }

    /**
     * Displays the current game map.
     */
    @Override
    public void showMap()
    {
        GameDriver.getGameEngine().showMap(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editContinentAdd(String p_continentID, int p_continentValue)
    {
        printInvalidCommandMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editContinentRemove(String p_continentID)
    {
        printInvalidCommandMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editCountryAdd(String p_countryID, String p_continentID)
    {
        printInvalidCommandMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editCountryRemove(String p_countryID)
    {
        printInvalidCommandMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editNeighborAdd(String p_countryID, String p_neighborCountryID)
    {
        printInvalidCommandMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editNeighborRemove(String p_countryID, String p_neighborCountryID)
    {
        printInvalidCommandMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateMap()
    {
        printInvalidCommandMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveMap(String p_filename)
    {
        printInvalidCommandMessage();
    }

    /**
     * Ends the game by transitioning to the End phase.
     */
    @Override
    public void endGame()
    {
        GameDriver.getGameEngine().setPhase(new End());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadGame(String p_filename)
    {
        try (ObjectInputStream l_objectInputStream = new ObjectInputStream(new FileInputStream(p_filename)))
        {
            GameEngine l_gameEngine = (GameEngine) l_objectInputStream.readObject(); // Deserialize the object
            GameDriver.setGameEngine(l_gameEngine);
            LogEntryBuffer.getInstance().appendToBuffer("Game loaded from: " + p_filename, true);
        } catch (IOException | ClassNotFoundException e)
        {
            LogEntryBuffer.getInstance().appendToBuffer("Issue loading game: " + p_filename + ":\n" + e.getMessage(), true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tournament(List<String> p_listOfMapFiles, List<String> p_listOfPlayerStrategies, int p_numberOfGames, int p_maxNumberOfTurns)
    {
        // Validate # of turns
        if (p_listOfMapFiles == null || p_listOfMapFiles.size() < 2) {
            throw new IllegalArgumentException("Tournament must have at least two map files.");
        }

        // Validate # of player strategies
        if (p_listOfPlayerStrategies == null || p_listOfPlayerStrategies.size() < 2 || p_listOfPlayerStrategies.size() > 4) {
            throw new IllegalArgumentException("Tournament must have between 2 and 4 player strategies.");
        }

        // Validate # of games
        if (p_numberOfGames < 1 || p_numberOfGames > 5) {
            throw new IllegalArgumentException("Tournament must have between 1 and 5 games per map.");
        }

        // Validate # of turns
        if (p_maxNumberOfTurns < 10 || p_maxNumberOfTurns > 50) {
            throw new IllegalArgumentException("Tournament must have between 10 and 50 turns per game.");
        }

        // Clear Queues
        GameDriver.getTournamentQueue().clear();
        GameDriver.getPriorTournaments().clear();

        GameContext l_currentGameContext = GameDriver.getGameEngine();
        int l_gameNumber = 1;
        for (int l_h = 0; l_h < p_listOfMapFiles.size(); l_h++)
        {
            for (int l_i = 0; l_i < p_numberOfGames; l_i++)
            {
                GameContext l_newGameContext = OverallFactory.getInstance().CreateGameEngine();
                GameDriver.setGameEngine(l_newGameContext); // set tournament match as current
                GameDriver.getGameEngine().setGameNumber(l_i + 1); // set game number (starts at 1)
                GameDriver.getGameEngine().setMaxNumberOfTurns(p_maxNumberOfTurns); // set max number of turns
                Startup l_startup = new Startup();
                GameDriver.getGameEngine().setPhase(l_startup);
                GameDriver.getGameEngine().getPhase().loadMap(p_listOfMapFiles.get(l_h)); // load map

                for (int l_j = 0; l_j < p_listOfPlayerStrategies.size(); l_j++)
                {
                    PlayerBehaviorType l_playerBehaviorType = PlayerBehaviorType.valueOf(p_listOfPlayerStrategies.get(l_j).toUpperCase());
                    GameDriver.getGameEngine().getPhase().gamePlayerAdd(p_listOfPlayerStrategies.get(l_j).toUpperCase() + l_j, l_playerBehaviorType);
                }
                GameDriver.getTournamentQueue().addLast(GameDriver.getGameEngine());
            }
        }

        // Add game that was interrupted for tournament at end of queue
        GameDriver.getTournamentQueue().addLast(l_currentGameContext);
        Startup l_startup = OverallFactory.getInstance().CreateStartup();
        GameDriver.getGameEngine().setPhase(l_startup);
        LogEntryBuffer.getInstance().appendToBuffer("\nThe tournament is about to begin!\n", true);
        l_startup.execute();
    }
}
