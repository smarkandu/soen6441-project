package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType;

import java.io.Serializable;

/**
 * The MainPlay class represents the abstract base class for main gameplay phases.
 * It defines common methods for loading maps, setting players, and assigning countries.
 */
public abstract class MainPlay extends Play implements Serializable
{

    /**
     * Constructs a MainPlay phase.
     */
    public MainPlay()
    {

    }

    /**
     * Invalid command for this phase.
     *
     * @param p_filename The filename of the map to load.
     */
    public void loadMap(String p_filename)
    {
        this.printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     */
    public void assignCountries()
    {
        this.printInvalidCommandMessage();
    }

    /**
     * Displays the current game map.
     */
    public void showMap()
    {
        GameDriver.getGameEngine().showMap(true);
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_playerName         The name of the player to add.
     * @param p_playerBehaviorType The player behavior type
     */
    @Override
    public void gamePlayerAdd(String p_playerName, PlayerBehaviorType p_playerBehaviorType)
    {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_playerName the name of the player to be removed.
     */
    @Override
    public void gamePlayerRemove(String p_playerName)
    {
        printInvalidCommandMessage();
    }

    /**
     * {@inheritDoc}
     */
    public void saveGame(String p_filename)
    {
        GameDriver.getGameEngine().saveGame(p_filename);
    }
}