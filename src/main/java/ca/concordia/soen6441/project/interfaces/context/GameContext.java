package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.context.DeckOfCards;
import ca.concordia.soen6441.project.interfaces.phases.State;
import ca.concordia.soen6441.project.map.InvalidMapFileException;
import ca.concordia.soen6441.project.phases.Phase;

import java.io.FileNotFoundException;

public interface GameContext {
    /**
     * To get the current phase of the game
     *
     * @return d_gamePhase return the game phase
     */
    State getPhase();

    /**
     * Sets the current phase of the game.
     *
     * @param p_phase The game phase to set.
     */
    void setPhase(Phase p_phase);

    /**
     * Prints a list of the continents (in alphabetical order) with their countries
     * underneath each one.
     *
     * @param p_isDetailed If true, prints country owners, army counts, and neighbors.
     *                     If false, prints only continent and country names.
     */
    void showMap(boolean p_isDetailed);

    /**
     * Checks the validity fo the map based on conditions like connectivity, accessibility, etc.
     *
     * @returs true if the map is valid, false otherwise.
     */
    boolean isMapValid();

    /**
     * Converts the game map into a formatted string representation.
     *
     * @return A string representation of the map in a structured format.
     */
    String toMapString();

    /**
     * Resets the game map by clearing all countries and continents.
     */
    void resetMap();

    /**
     * Loads a map from a file and validates its contents.
     *
     * @param p_filename The filename of the map to be loaded.
     * @throws InvalidMapFileException If the map file is invalid.
     * @throws FileNotFoundException   If the file is not found.
     */
    void loadMap(String p_filename) throws InvalidMapFileException, FileNotFoundException;

    /**
     * Returns continent manager object
     *
     * @return object of CountryManager
     */
    ContinentContext getContinentManager();

    /**
     * Returns country manager object
     *
     * @return object of CountryManager
     */
    CountryContext getCountryManager();

    /**
     * Returns neighbor manager object
     *
     * @return object of NeighborManager
     */
    NeighborContext getNeighborManager();

    /**
     * Returns player manager object
     *
     * @return object of PlayerManager
     */
    PlayerContext getPlayerManager();

    /**
     * Returns Deck of Cards object
     *
     * @return object of DeckOfCards
     */
    DeckOfCards getDeckOfCards();
}
