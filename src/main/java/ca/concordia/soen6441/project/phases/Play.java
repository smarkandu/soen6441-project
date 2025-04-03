package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameDriver;

import java.io.Serializable;

// State of the State pattern
/**
 * The Play class represents an abstract phase in the game where the main gameplay occurs.
 * It extends the Phase class and defines behaviors specific to the play state.
 */
public abstract class Play extends Phase implements Serializable {

    /**
     * Constructs a Play phase.
     *
     * @param p_gameEngine The game engine instance controlling the game state.
     */
    public Play() {
        
    }

    /**
     * Displays the current game map.
     */
    public void showMap() {
        GameDriver.getGameEngine().showMap(false);
    }

    @Override
    public void editContinentAdd(String p_continentID, int p_continentValue) {
        printInvalidCommandMessage();
    }

    @Override
    public void editContinentRemove(String p_continentID) {
        printInvalidCommandMessage();
    }

    @Override
    public void editCountryAdd(String p_countryID, String p_continentID) {
        printInvalidCommandMessage();
    }

    @Override
    public void editCountryRemove(String p_countryID) {
        printInvalidCommandMessage();
    }

    @Override
    public void editNeighborAdd(String p_countryID, String p_neighborCountryID) {
        printInvalidCommandMessage();
    }

    @Override
    public void editNeighborRemove(String p_countryID, String p_neighborCountryID) {
        printInvalidCommandMessage();
    }

    public void validateMap() {
        printInvalidCommandMessage();
    }

    public void saveMap(String p_filename) {
        printInvalidCommandMessage();
    }

    /**
     * Ends the game by transitioning to the End phase.
     */
    public void endGame() {
        GameDriver.getGameEngine().setPhase(new End());
    }
}
