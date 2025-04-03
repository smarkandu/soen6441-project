package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.interfaces.phases.State;

import java.io.Serializable;
import java.util.List;

/**
 * The Phase class represents the abstract base class for different phases in the game.
 * Each phase defines specific behaviors for game actions.
 */
public abstract class Phase implements State, Serializable {
    protected GameContext d_gameEngine;

    /**
     * Constructs a Phase instance.
     *
     * @param p_gameEngine The game engine instance controlling the game state.
     */
    public Phase(GameContext p_gameEngine) {
        this.d_gameEngine = p_gameEngine;
    }

    // general behavior
    abstract public void loadMap(String p_filename);
    abstract public void showMap();
    abstract public void validateMap();

    // edit map state behavior
    abstract public void editContinentAdd(String p_continentID, int p_continentValue);
    abstract public void editContinentRemove(String p_continentID);
    abstract public void editCountryAdd(String p_countryID, String p_continentID);
    abstract public void editCountryRemove(String p_countryID);
    abstract public void editNeighborAdd(String p_countryID, String p_neighborCountryID);
    abstract public void editNeighborRemove(String p_countryID, String p_neighborCountryID);
    abstract public void saveMap(String p_filename);

    // play state behavior
    // game setup state behavior
    /**
     * {@inheritDoc}
     */
    abstract public void gamePlayerAdd(String p_playerName, PlayerBehaviorType p_playerBehaviorType);
    abstract public void gamePlayerRemove(String p_playerName);
    abstract public void assignCountries();

    // Gameplay behaviour
    abstract public void deploy(String p_countryID, int p_toDeploy);
    abstract public void advance(String p_countryNameFrom, String p_countryNameTo, int p_toAdvance);
    abstract public void bomb(String p_countryID);
    abstract public void blockade(String p_countryID);
    abstract public void airlift(String p_sourceCountryID, String p_targetCountryID, int p_numArmies);
    abstract public void negotiate(String p_playerID);

    abstract public void endGame();

    // go to next phase
    abstract public void next();

    /**
     * {@inheritDoc}
     */
    abstract public void loadGame(String p_filename);

    /**
     * {@inheritDoc}
     */
    abstract public void saveGame(String p_filename);

    /**
     * {@inheritDoc}
     */
    abstract public void tournament(List<String> p_listOfMapFiles, List<String> p_listOfPlayerStrategies, int p_numberOfGames,
                    int p_maxNumberOfTurns);



    // methods common to all states
    public void printInvalidCommandMessage() {
        System.out.println("Invalid command in state "
                + this.getClass().getSimpleName() );
    }

    public String getPhaseName()
    {
        return getClass().getSimpleName();
    }
}
