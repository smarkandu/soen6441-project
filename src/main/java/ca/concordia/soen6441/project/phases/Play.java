package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameDriver;
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
        // Clear Queues
        GameDriver.getTournamentQueue().clear();
        GameDriver.getPriorTournaments().clear();

        GameContext l_currentGameContext = GameDriver.getGameEngine();
        StartupMethodImpl l_phaseMethodImpl = new StartupMethodImpl();
        int l_gameNumber = 1;
        for (int l_h = 0; l_h < p_listOfMapFiles.size(); l_h++)
        {
            for (int l_i = 0; l_i < p_numberOfGames; l_i++)
            {
                GameContext l_newGameContext = new GameEngine();
                GameDriver.setGameEngine(l_newGameContext); // set tournament match as current
                GameDriver.getGameEngine().setGameNumber(l_i); // set game number
                GameDriver.getGameEngine().setMaxNumberOfTurns(p_maxNumberOfTurns); // set max number of turns
                l_phaseMethodImpl.loadMap(p_listOfMapFiles.get(l_h)); // load map

                for (int l_j = 0; l_j < p_listOfPlayerStrategies.size(); l_j++)
                {
                    PlayerBehaviorType l_playerBehaviorType = PlayerBehaviorType.valueOf(p_listOfPlayerStrategies.get(l_j).toUpperCase());
                    l_phaseMethodImpl.gamePlayerAdd(p_listOfPlayerStrategies.get(l_j).toUpperCase() + l_j, l_playerBehaviorType);
                }
                l_phaseMethodImpl.assignCountries();
                GameDriver.getTournamentQueue().addLast(GameDriver.getGameEngine());
            }
        }

        // Add game that was interrupted for tournament at end of queue
        GameDriver.getTournamentQueue().addLast(l_currentGameContext);
        Startup l_startup = new Startup();
        l_startup.execute();
    }
}
