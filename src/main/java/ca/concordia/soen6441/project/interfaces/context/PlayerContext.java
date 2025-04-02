package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType;
import ca.concordia.soen6441.project.interfaces.Player;

import java.util.Map;

public interface PlayerContext {
    // Required

    /**
     * Adds a new player to the game.
     *
     * @param p_playername The name of the player to be added.
     * @param p_playerBehaviorType The player behavior type
     */
    void addPlayer(String p_playername, PlayerBehaviorType p_playerBehaviorType);

    /**
     * Removes a player from the game.
     *
     * @param p_player The name of the player to be removed.
     */
    void removePlayer(String p_player);

    // Extra
    /**
     * Retrieves a player based on their index in the player list.
     *
     * @param p_index The index of the player.
     * @return The player at the specified index.
     */
    Player getPlayer(int p_index);

    /**
     * Returns a list of players in the form of a Map (data structure).
     *
     * @return Player names and their objects.
     */
    Map<String, Player> getPlayers();

    /**
     * Returns the Neutral player
     */
    Player getNeutralPlayer();

    /**
     * Get Index of CurrentPlayer
     * @return integer value
     */
    int getCurrentPlayerIndex();

    /**
     * Set Index of CurrentPlayer
     * @param p_newPlayIndex integer value for new player index
     */
    void setCurrentPlayerIndex(int p_newPlayIndex);
}
