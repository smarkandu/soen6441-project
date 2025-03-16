package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.map.InvalidMapFileException;
import ca.concordia.soen6441.project.gameplay.CountryAssignment;

import java.io.FileNotFoundException;

/**
 * The Startup class represents the initial phase of the game.
 * Players can load maps, add players, and assign countries before the game begins.
 */
public class Startup extends Play {
    private CountryAssignment d_countryAssignment;

    /**
     * Constructs the Startup phase.
     *
     * @param p_gameEngine The game engine instance controlling the game state.
     */
    public Startup(GameEngine p_gameEngine) {
        super(p_gameEngine);
        d_countryAssignment = new CountryAssignment(d_gameEngine);
    }

    /**
     * Loads a map from a file. If the file is not found or invalid, an error is displayed.
     *
     * @param p_filename The filename of the map to load.
     */
    public void loadMap(String p_filename) {
        try {
            d_gameEngine.loadMap(p_filename);
        } catch (InvalidMapFileException e) {
            System.out.println("File not structured correctly.\n" +
                    "Please load another file.  Reverting previous load.");
            d_gameEngine.resetMap();
        } catch (FileNotFoundException e) {
            System.out.println("File '" + p_filename + "' not found.  Please try loading another map file instead");
        }
    }

    /**
     * Assigns countries to players if the map is valid and at least two players are present.
     */
    public void assignCountries() {
        if (!d_gameEngine.isMapValid()) {
            System.out.println("A valid map must be loaded first");
        } else if (d_gameEngine.getPlayers().size() < 2) {
            System.out.println("You must have at least two players to proceed");
        } else {
            d_countryAssignment.assignCountries();

            // After assigning countries, go to the next phase for each player (Assign Reinforcements)
            AssignReinforcements l_assignReinforcements = new AssignReinforcements(d_gameEngine);
            l_assignReinforcements.execute();
        }
    }

    @Override
    public void deploy(String p_countryID, int p_toDeploy) {
        printInvalidCommandMessage();
    }

    /**
     * Adds a new player to the game.
     *
     * @param p_playerName The name of the player to add.
     */
    public void gamePlayerAdd(String p_playerName) {
        d_gameEngine.addPlayer(p_playerName);
    }

    /**
     * Removes an existing player from the game.
     *
     * @param p_playerName The name of the player to remove.
     */
    public void gamePlayerRemove(String p_playerName) {
        d_gameEngine.removePlayer(p_playerName);
    }

    /**
     * Invalid command for this phase.
     */
    public void next() {
        printInvalidCommandMessage();
    }

    /**
     * Displays instructions for starting the game.
     *
     * @return The name of the phase.
     */
    public String getPhaseName() {
        System.out.println("\n*** Welcome to the Game Warzone! ***\nPlease load map using 'loadmap' and add players using 'gameplayer'");
        System.out.println("Once you have done the above, use the command 'assigncountries' to initiate action and start the game\n");
        return super.getPhaseName();
    }
}