package ca.concordia.soen6441.project;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class PostLoad extends Edit {
    public PostLoad(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    public void loadMap(String p_filename) {
        System.out.println("map has been loaded");
    }

    public void saveMap(String p_filename) {
    // Fetch the map data in Domination format
        String l_mapData = d_gameEngine.toMapString();

        // Write the data to the specified file
        try (PrintWriter l_writer = new PrintWriter(new File(p_filename))) {
            l_writer.write(l_mapData);
            System.out.println("Map successfully saved to: " + p_filename);
        } catch (IOException l_exception) {
            System.err.println("Error saving map: " + l_exception.getMessage());
        }
    }

    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    public void next() {
        d_gameEngine.setPhase(new Startup(d_gameEngine));
    }

    public void validateMap()
    {
        d_gameEngine.validateMap();
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