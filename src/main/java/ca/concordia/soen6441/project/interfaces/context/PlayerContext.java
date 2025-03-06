package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.interfaces.Player;

import java.util.Map;

public interface PlayerContext {
    // Required
    void addPlayer(String p_playername);
    void removePlayer(String p_player);

    // Extra
    Player getPlayer(int p_index);
    Map<String, Player> getPlayers();
}
