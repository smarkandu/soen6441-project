package ca.concordia.soen6441.project.phases;
import ca.concordia.soen6441.project.context.GameEngine;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * The PostLoad class represents the phase after loading a map.
 * Players can edit the map and perform necessary validations before starting the game.
 */
public class PostLoad extends Edit {

    /**
     * Constructs the PostLoad phase.
     *
     * @param p_gameEngine The game engine instance controlling the game state.
     */
    public PostLoad(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Indicates that the map has been successfully loaded.
     *
     * @param p_filename The filename of the loaded map.
     */
    public void loadMap(String p_filename) {
        System.out.println("map has been loaded");
    }

    /**
     * Saves the current map to a file.
     *
     * @param p_filename The filename to save the map to.
     */
    public void saveMap(String p_filename) {
        // Fetch the map data in Domination format
        String l_mapData = d_gameEngine.toMapString();

        // Write the data to the specified file
        try (PrintWriter l_writer = new PrintWriter(p_filename)) {
            l_writer.write(l_mapData);
            System.out.println("Map successfully saved to: " + p_filename);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    /**
     * Moves to the next phase by resetting the map and transitioning to the Startup phase.
     */
    public void next() {
        // Reset the engine.  Map needs to be loaded at beginning of game.
        d_gameEngine.resetMap();

        // Go to the startup phase of game.
        d_gameEngine.setPhase(new Startup(d_gameEngine));
    }

    /**
     * Validates the current map.
     */
    public void validateMap() {
        d_gameEngine.isMapValid();
    }

    @Override
    public void editContinentAdd(String p_continentID, int p_continentValue) {
        d_gameEngine.addContinent(p_continentID, p_continentValue);
    }

    @Override
    public void editContinentRemove(String p_continentID) {
        d_gameEngine.removeContinent(p_continentID);
    }

    @Override
    public void editCountryAdd(String p_countryID, String p_continentID) {
        d_gameEngine.addCountry(p_countryID, p_continentID);
    }

    @Override
    public void editCountryRemove(String p_countryID) {
        d_gameEngine.removeCountry(p_countryID);
    }

    @Override
    public void editNeighborAdd(String p_countryID, String p_neighborCountryID) {
        d_gameEngine.addNeighbor(p_countryID, p_neighborCountryID);
    }

    @Override
    public void editNeighborRemove(String p_countryID, String p_neighborCountryID) {
        d_gameEngine.removeNeighbor(p_countryID, p_neighborCountryID);
    }
}
