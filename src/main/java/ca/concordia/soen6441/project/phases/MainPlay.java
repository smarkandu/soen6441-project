package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.io.*;
import java.util.List;

/**
 * The MainPlay class represents the abstract base class for main gameplay phases.
 * It defines common methods for loading maps, setting players, and assigning countries.
 */
public abstract class MainPlay extends Play implements Serializable {

    /**
     * Constructs a MainPlay phase.
     */
    public MainPlay() {
        
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
        GameDriver.getGameEngine().showMap(true);
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

    /**
     * {@inheritDoc}
     */
    public void loadGame(String p_filename) {
        // TODO
        try (ObjectInputStream l_objectInputStream = new ObjectInputStream(new FileInputStream(p_filename))) {
            GameEngine l_gameEngine = (GameEngine) l_objectInputStream.readObject(); // Deserialize the object
            GameDriver.setGameEngine(l_gameEngine);
            LogEntryBuffer.getInstance().appendToBuffer("Game loaded from: " + p_filename, true);
        } catch (IOException | ClassNotFoundException e) {
            LogEntryBuffer.getInstance().appendToBuffer("Issue loading game: " + p_filename + ":\n"
                    + e.getMessage(), true);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void saveGame(String p_filename) {
        try (ObjectOutputStream l_objectOutputStream = new ObjectOutputStream(new FileOutputStream(p_filename))) {
            l_objectOutputStream.writeObject(p_filename);
            LogEntryBuffer.getInstance().appendToBuffer("Game saved as: " + p_filename, true);
        } catch (IOException e) {
            LogEntryBuffer.getInstance().appendToBuffer("Issue saving game: " + p_filename + ":\n"
                    + e.getMessage(), true);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void tournament(List<String> p_listOfMapFiles, List<String> p_listOfPlayerStrategies, int p_numberOfGames,
                           int p_maxNumberOfTurns)
    {
        //TODO
    }

}