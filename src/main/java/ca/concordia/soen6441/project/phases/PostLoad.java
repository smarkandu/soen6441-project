package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.map.SaveMapConquestAdapter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * The PostLoad class represents the phase after loading a map.
 * Players can edit the map and perform necessary validations before starting the game.
 */
public class PostLoad extends Edit {

    /**
     * Constructs the PostLoad phase.
     */
    public PostLoad() {
        
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
     * Saves the current map to a Domination file.
     *
     * @param p_filename The filename to save the map to.
     */
    public void saveMap(String p_filename) {
        // Fetch the map data in Domination format
        String l_mapData = GameDriver.getGameEngine().toMapString();

        // Write the data to the specified file
        try (PrintWriter l_writer = new PrintWriter(p_filename)) {
            l_writer.write(l_mapData);
            System.out.println("DOMINATION Map successfully saved to: " + p_filename);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Moves to the next phase by resetting the map and transitioning to the Startup phase.
     */
    public void next() {
        // Reset the engine.  Map needs to be loaded at beginning of game.
        GameDriver.getGameEngine().resetMap();

        // Go to the startup phase of game.
        GameDriver.getGameEngine().setPhase(new Startup());
    }

    /**
     * Validates the current map.
     */
    public void validateMap() {
        GameDriver.getGameEngine().isMapValid();
    }

    @Override
    public void editContinentAdd(String p_continentID, int p_continentValue) {
        GameDriver.getGameEngine().getContinentManager().addContinent(p_continentID, p_continentValue);
    }

    @Override
    public void editContinentRemove(String p_continentID) {
        GameDriver.getGameEngine().getContinentManager().removeContinent(p_continentID);
    }

    @Override
    public void editCountryAdd(String p_countryID, String p_continentID) {
        GameDriver.getGameEngine().getCountryManager().addCountry(p_countryID, p_continentID);
    }

    @Override
    public void editCountryRemove(String p_countryID) {
        GameDriver.getGameEngine().getCountryManager().removeCountry(p_countryID);
    }

    @Override
    public void editNeighborAdd(String p_countryID, String p_neighborCountryID) {
        GameDriver.getGameEngine().getNeighborManager().addNeighbor(p_countryID, p_neighborCountryID);
    }

    @Override
    public void editNeighborRemove(String p_countryID, String p_neighborCountryID) {
        GameDriver.getGameEngine().getNeighborManager().removeNeighbor(p_countryID, p_neighborCountryID);
    }
}
