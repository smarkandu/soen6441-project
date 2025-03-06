package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameEngine;
import ca.concordia.soen6441.project.map.InvalidMapFileException;

import java.io.FileNotFoundException;

public class PreLoad extends Edit {
    public PreLoad(GameEngine p_gameEngine)
    {
        super(p_gameEngine);
    }

    public void loadMap(String p_filename) {
        try
        {
            d_gameEngine.loadMap(p_filename);
        }
        catch(InvalidMapFileException e)
        {
            // No need to write anything
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File '" + p_filename + "' not found.  Creating a new map to edit instead.");
            next();
        }
    }

    public void saveMap(String p_filename) {
        printInvalidCommandMessage();
    }

    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    public void next()
    {
        d_gameEngine.setPhase(new PostLoad(d_gameEngine));
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

    @Override
    public String getPhaseName()
    {
        System.out.println("\n*** Welcome to the Map Editor! ***\nTo preload an existing map, please use the command 'loadmap'");
        System.out.println("'next' will take you to the edit state of the MapEditor\n");
        return super.getPhaseName();
    }
}