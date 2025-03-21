package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.context.GameContext;

/**
 * The Edit class represents an abstract phase where the game can be modified.
 * This phase includes operations like showing the map and validating its structure.
 */
public abstract class Edit extends Phase {

    /**
     * Constructs an Edit phase.
     *
     * @param p_gameEngine The game engine instance controlling the game state.
     */
    public Edit(GameContext p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Displays the current game map.
     */
    public void showMap() {
        d_gameEngine.showMap(false);
    }

    /**
     * Invalid command for this phase.
     */
    public void setPlayers() {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     */
    public void assignCountries() {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_countryID The ID of the country where troops would be deployed.
     * @param p_toDeploy  The number of troops to deploy.
     */
    public void deploy(String p_countryID, int p_toDeploy) {
        printInvalidCommandMessage();
    }

    public void advance(String p_countryNameFrom, String p_countryNameTo, int p_toAdvance)
    {
        printInvalidCommandMessage();
    }

    @Override
    public void bomb(String p_countryID) {
        printInvalidCommandMessage();
    }

    @Override
    public void blockade(String p_countryID) {
        printInvalidCommandMessage();
    }

    @Override
    public void airlift(String p_sourceCountryID, String p_targetCountryID, int p_numArmies) {
        printInvalidCommandMessage();
    }

    @Override
    public void negotiate(String p_playerID) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     */
    public void endGame() {
        printInvalidCommandMessage();
    }

    /**
     * Validates the current game map.
     * Prints whether the map is valid or not.
     */
    public void validateMap() {
        if (d_gameEngine.isMapValid()) {
            System.out.println("Map is valid");
        } else {
            System.out.println("Map is not valid");
        }
    }
}