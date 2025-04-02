package ca.concordia.soen6441.project.context;

import ca.concordia.soen6441.project.gameplay.PlayerImpl;
import ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorFactory;
import ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.interfaces.context.PlayerContext;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Class managing the player operations
 */
public class PlayerManager implements PlayerContext {
    private SortedMap<String, Player> d_players;
    private Player d_neutralPlayer;
    private PlayerBehaviorFactory d_playerBehaviorFactory;
    private GameContext d_GameEngine;

    /**
     * Constructor
     */
    public PlayerManager(GameContext p_GameEngine) {
        d_players = new TreeMap<String, Player>();
        d_playerBehaviorFactory = new PlayerBehaviorFactory();
        d_neutralPlayer = new PlayerImpl("Neutral", new ArrayList<>(), new ArrayList<>(),
                d_playerBehaviorFactory.createPlayerBehavior(PlayerBehaviorType.HUMAN), this); // Will always exist
        d_GameEngine = p_GameEngine;
    }

    /**
     * Adds a new player to the game.
     *
     * @param p_playername The name of the player to be added.
     */
    public void addPlayer(String p_playername, PlayerBehaviorType p_playerBehaviorType) {
        if (p_playername.equalsIgnoreCase("neutral"))
        {
            System.out.println("Unable to create player due to restricted name: " + p_playername);
        }
        else
        {
            PlayerImpl l_playerToAdd = new PlayerImpl(p_playername, new ArrayList<>(), new ArrayList<>()
                    , d_playerBehaviorFactory.createPlayerBehavior(p_playerBehaviorType), this);
            d_players.put(p_playername, l_playerToAdd);
            System.out.println("Player added: " + d_players.get(p_playername).getName() + " ["
                    + l_playerToAdd.getPlayerBehavior() + "]");
        }
    }

    /**
     * Removes a player from the game.
     *
     * @param p_player The name of the player to be removed.
     */
    public void removePlayer(String p_player) {
        d_players.remove(p_player);
    }

    /**
     * Retrieves a player based on their index in the player list.
     *
     * @param p_index The index of the player.
     * @return The player at the specified index.
     */
    public Player getPlayer(int p_index) {
        return new ArrayList<Player>(d_players.values()).get(p_index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Player> getPlayers() {
        return d_players;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getNeutralPlayer() {
        return d_neutralPlayer;
    }

    public GameContext getGameEngine() {
        return d_GameEngine;
    }
}
