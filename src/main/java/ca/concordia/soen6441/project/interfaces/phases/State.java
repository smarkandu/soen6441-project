package ca.concordia.soen6441.project.interfaces.phases;

import ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType;

import java.util.List;

/**
 * Represents a state in the game, defining various actions that can be performed
 * depending on the current phase of gameplay. This interface follows the State Pattern
 * to manage phase-specific behaviors dynamically.
 */
public interface State {
    /**
     * Loads a map from a file.
     *
     * @param p_filename the name of the file containing the map data.
     */
    void loadMap(String p_filename);

    /**
     * Displays the current map.
     */
    void showMap();

    /**
     * Validates the currently loaded map to ensure it meets game requirements.
     */
    void validateMap();

    // edit map state behavior
    /**
     * Adds a new continent to the map.
     *
     * @param p_continentID   the identifier (name) of the continent.
     * @param p_continentValue the control value assigned to the continent.
     */
    void editContinentAdd(String p_continentID, int p_continentValue);

    /**
     * Removes an existing continent from the map.
     *
     * @param p_continentID the identifier of the continent (name) to be removed.
     */
    void editContinentRemove(String p_continentID);

    /**
     * Adds a new country to the map and associates it with a continent.
     *
     * @param p_countryID   the identifier (name) of the country.
     * @param p_continentID the identifier of the continent the country belongs to.
     */
    void editCountryAdd(String p_countryID, String p_continentID);

    /**
     * Removes a country from the map.
     *
     * @param p_countryID the identifier (name) of the country to be removed.
     */
    void editCountryRemove(String p_countryID);

    /**
     * Adds a neighboring connection between two countries.
     *
     * @param p_countryID         the identifier (name) of the country.
     * @param p_neighborCountryID the identifier (name) of the neighboring country.
     */
    void editNeighborAdd(String p_countryID, String p_neighborCountryID);

    /**
     * Removes a neighboring connection between two countries.
     *
     * @param p_countryID         the identifier (name) of the country.
     * @param p_neighborCountryID the identifier (name) of the neighboring country to be removed.
     */
    void editNeighborRemove(String p_countryID, String p_neighborCountryID);

    /**
     * Saves the current map configuration to a Domination file.
     *
     * @param p_filename the name of the file to save the map data.
     */
    void saveMap(String p_filename);

    /**
     * Adds a new player to the game.
     *
     * @param p_playerName The name of the player to add.
     * @param p_playerBehaviorType The player behavior type
     */
    void gamePlayerAdd(String p_playerName, PlayerBehaviorType p_playerBehaviorType);

    /**
     * Removes a player from the game.
     *
     * @param p_playerName the name of the player to be removed.
     */
    void gamePlayerRemove(String p_playerName);

    /**
     * Assigns countries to players at the start of the game.
     */
    void assignCountries();

    // Gameplay behaviour
    /**
     * Deploys armies to a specified country.
     *
     * @param p_countryID the identifier (name) of the country.
     * @param p_toDeploy  the number of armies to deploy.
     */
    void deploy(String p_countryID, int p_toDeploy);

    /**
     * Moves armies from one country to another.
     *
     * @param p_countryNameFrom the identifier (name) of the source country.
     * @param p_countryNameTo   the identifier (name) of the destination country.
     * @param p_toAdvance       the number of armies to move.
     */
    void advance(String p_countryNameFrom, String p_countryNameTo, int p_toAdvance);

    /**
     * The target territory’s army units count is tripled, and the territory becomes
     * neutral.
     * @param p_countryID String representing the country name to issue the blockade
     */
    void blockade(String p_countryID);
    
    /**
     * Ends the game.
     */
    void endGame();

    // Phase Related
    /**
     * Retrieves the name of the current game phase.
     *
     * @return the name of the current phase.
     */
    String getPhaseName();

    /**
     * Proceeds to the next phase in the game.
     */
    void next();

    /**
     * Prints a message indicating that the issued command is invalid
     * for the current game phase.
     */
    void printInvalidCommandMessage();

    /**
     * Bomb a the country of an enemy
     * @param p_countryNameToBomb string reprensent the name of the country to be bombed.
     */
    void bomb(String p_countryNameToBomb);
//    void bomb(String p_player, String p_countryNameToBomb);
    void airlift(String p_lSourceCountryID, String p_lTargetCountryID, int p_lNumArmies);

   /**
    * Issues a Diplomacy order to negotiate with another player.
    *
    * @param p_playerID ID of the player to negotiate with.
    */
    void negotiate(String p_playerID);

    /**
     * Allows the user to load his game
     * @param p_filename String denoting the filename
     */
    void loadGame(String p_filename);

    /**
     * Allows the user to save his game
     * @param p_filename String denoting the filename
     */
    void saveGame(String p_filename);

    /**
     * Allows the user to initiate a tournament
     * @param p_listOfMapFiles List of strings representing map filenames
     * @param p_listOfPlayerStrategies List of Strings representing the player strategies to be used
     * @param p_numberOfGames number of games to be played on each map
     * @param p_maxNumberOfTurns maximum number of turns for each game
     */
    void tournament(List<String> p_listOfMapFiles, List<String> p_listOfPlayerStrategies, int p_numberOfGames,
                    int p_maxNumberOfTurns);
}
