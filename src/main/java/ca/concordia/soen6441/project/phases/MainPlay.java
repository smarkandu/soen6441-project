package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType;
import ca.concordia.soen6441.project.interfaces.context.GameContext;

/**
 * The MainPlay class represents the abstract base class for main gameplay phases.
 * It defines common methods for loading maps, setting players, and assigning countries.
 */
public abstract class MainPlay extends Play {

    /**
     * Constructs a MainPlay phase.
     *
     * @param p_gameEngine The game engine instance controlling the game state.
     */
    public MainPlay(GameContext p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_filename The filename of the map to load.
     */
    public void loadMap(String p_filename) {
        this.printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     */
    public void assignCountries() {
        this.printInvalidCommandMessage();
    }

    /**
     * Displays the current game map.
     */
    public void showMap() {
        d_gameEngine.showMap(true);
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_playerName The name of the player to add.
     * @param p_playerBehaviorType The player behavior type
     */
    @Override
    public void gamePlayerAdd(String p_playerName, PlayerBehaviorType p_playerBehaviorType) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     * @param p_playerName the name of the player to be removed.
     */
    @Override
    public void gamePlayerRemove(String p_playerName) {
        printInvalidCommandMessage();
    }
}