package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.context.DeckOfCards;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.phases.State;
import ca.concordia.soen6441.project.map.InvalidMapFileException;
import ca.concordia.soen6441.project.phases.Phase;

import java.io.FileNotFoundException;

public interface GameContext
{
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
     * @return true if the map is valid, false otherwise.
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


    /**
     * Sets metadata for ownership for both player (if not null) and country
     *
     * @param p_country the Country object to be owned
     * @param p_player  the Player object to own the country.  Null if unowned
     */
    void assignCountryToPlayer(Country p_country, Player p_player);

    /**
     * Removes ownership metadata from both player and country
     *
     * @param p_country the Country object to be set to unowned
     */
    void unassignCountryFromPlayer(Country p_country);

    /**
     * Get number of turns
     *
     * @return turn value (integer)
     */
    int getNumberOfTurns();

    /**
     * Increments number of turns by 1 and returns updated value
     *
     * @return updated turn value (integer)
     */
    int incrementNumberOfTurns();

    /**
     * Get the maximum number of turns allowable for the game
     *
     * @return integer value
     */
    int getMaxNumberOfTurns();

    /**
     * Set the maximum number of turns allowable for the game
     *
     * @param p_maxNumberOfTurns integer value
     */
    void setMaxNumberOfTurns(int p_maxNumberOfTurns);

    /**
     * Get the outcome of the game (i.e. the winner or "draw")
     *
     * @return String value
     */
    public String getOutcomeOfGame();

    /**
     * Set the outcome of the game (i.e. the winner or "draw")
     *
     * @param p_outcomeOfGame String value to set outcome
     */
    public void setOutcomeOfGame(String p_outcomeOfGame);

    /**
     * Get Game Number
     *
     * @return integer value representing the game number
     */
    public int getGameNumber();

    /**
     * Set Game Number
     *
     * @param p_gameNumber integer value to set the game number to
     */
    public void setGameNumber(int p_gameNumber);

    /**
     * Checks if a player has won the game.
     * A player wins if they own all the countries on the map.
     *
     * @return the name of the winning player, or null if no winner
     */
    String gameWonBy();

    /**
     * Gets the filename of the map currently loaded
     *
     * @return String value representing the map currently loaded
     */
    public String getMapFileLoaded();

    /**
     * Sets the filename of the map currently loaded
     *
     * @param p_mapFileLoaded String value representing the map to set the value to
     */
    public void setMapFileLoaded(String p_mapFileLoaded);
}
