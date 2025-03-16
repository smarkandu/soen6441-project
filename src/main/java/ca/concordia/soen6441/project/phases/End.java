package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.context.GameEngine;

/**
 * The End class represents the phase where the game ends.
 * Most game-related actions are invalid during this phase.
 */
public class End extends Phase {

    /**
     * Constructs the End phase.
     *
     * @param p_gameEngine The game engine instance controlling the game state.
     */
    public End(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_filename The filename of the map to load.
     */
    @Override
    public void loadMap(String p_filename) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     */
    @Override
    public void showMap() {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     */
    @Override
    public void validateMap() {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_continentID   The ID of the continent to add.
     * @param p_continentValue The value of the continent.
     */
    @Override
    public void editContinentAdd(String p_continentID, int p_continentValue) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_continentID The ID of the continent to remove.
     */
    @Override
    public void editContinentRemove(String p_continentID) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_countryID   The ID of the country to add.
     * @param p_continentID The ID of the continent where the country belongs.
     */
    @Override
    public void editCountryAdd(String p_countryID, String p_continentID) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_countryID The ID of the country to remove.
     */
    @Override
    public void editCountryRemove(String p_countryID) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_countryID        The ID of the country.
     * @param p_neighborCountryID The ID of the neighboring country to add.
     */
    @Override
    public void editNeighborAdd(String p_countryID, String p_neighborCountryID) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_countryID        The ID of the country.
     * @param p_neighborCountryID The ID of the neighboring country to remove.
     */
    @Override
    public void editNeighborRemove(String p_countryID, String p_neighborCountryID) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_filename The filename to save the map to.
     */
    @Override
    public void saveMap(String p_filename) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_playerName The name of the player to add.
     */
    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    /**
     * Invalid command for this phase.
     *
     * @param p_playerName The name of the player to remove.
     */
    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    /**
     * Invalid command for this phase.
     */
    @Override
    public void assignCountries() {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_countryID The ID of the country where troops would be deployed.
     * @param p_toDeploy  The number of troops to deploy.
     */
    @Override
    public void deploy(String p_countryID, int p_toDeploy) {
        printInvalidCommandMessage();
    }

    /**
     * Ends the game by exiting the application.
     */
    @Override
    public void endGame() {
        System.exit(1);
    }

    /**
     * Moves to the next phase.
     * This method is intentionally left blank as there is no next phase after the end.
     */
    @Override
    public void next() {
        // No action required for the end phase
    }
}
