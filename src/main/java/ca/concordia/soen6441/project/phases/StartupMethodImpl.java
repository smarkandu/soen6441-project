package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.gameplay.CountryAssignment;
import ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType;
import ca.concordia.soen6441.project.map.InvalidMapFileException;

import java.io.FileNotFoundException;

public class StartupMethodImpl {
    /**
     * Loads a map from a file. If the file is not found or invalid, an error is displayed.
     *
     * @param p_filename The filename of the map to load.
     */
    public void loadMap(String p_filename) {
        try {
            GameDriver.getGameEngine().loadMap(p_filename);
        } catch (InvalidMapFileException e) {
            System.out.println("File not structured correctly.\n" +
                    "Please load another file.  Reverting previous load.");
            GameDriver.getGameEngine().resetMap();
        } catch (FileNotFoundException e) {
            System.out.println("File '" + p_filename + "' not found.  Please try loading another map file instead");
        }
    }

    /**
     * Assigns countries to players if the map is valid and at least two players are present.
     */
    public void assignCountries() {
        if (!GameDriver.getGameEngine().isMapValid()) {
            System.out.println("A valid map must be loaded first");
        } else if (GameDriver.getGameEngine().getPlayerManager().getPlayers().size() < 2) {
            System.out.println("You must have at least two players to proceed");
        } else {
            CountryAssignment l_countryAssignment = new CountryAssignment();
            l_countryAssignment.assignCountries();

            // After assigning countries, go to the next phase for each player (Assign Reinforcements)
            AssignReinforcements l_assignReinforcements = new AssignReinforcements();
            l_assignReinforcements.execute();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void gamePlayerAdd(String p_playerName, PlayerBehaviorType p_playerBehaviorType) {
        GameDriver.getGameEngine().getPlayerManager().addPlayer(p_playerName, p_playerBehaviorType);
    }

    /**
     * Removes an existing player from the game.
     *
     * @param p_playerName The name of the player to remove.
     */
    public void gamePlayerRemove(String p_playerName) {
        GameDriver.getGameEngine().getPlayerManager().removePlayer(p_playerName);
    }
}
