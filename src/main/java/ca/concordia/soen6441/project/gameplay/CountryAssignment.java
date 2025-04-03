package ca.concordia.soen6441.project.gameplay;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handles the assignment of countries to players in the game.
 */
public class CountryAssignment {
    

    /**
     * Constructs a CountryAssignment instance with the specified game engine.
     *
     * @param p_gameEngine The game engine instance controlling the game state.
     */
    public CountryAssignment() {
    }

    /**
     * Assigns countries to players in the game.
     * Each player is assigned one country at random, and the country receives an initial troop allocation.
     * If there are fewer countries than players, a warning is displayed.
     */
    public void assignCountries() {
        List<Country> l_availableCountries = new ArrayList<>(GameDriver.getGameEngine().getCountryManager().getCountries().values());
        List<Player> l_players = new ArrayList<>(GameDriver.getGameEngine().getPlayerManager().getPlayers().values());

        if (l_players.isEmpty() || l_availableCountries.isEmpty()) {
            LogEntryBuffer.getInstance().appendToBuffer("Error: No players or no countries available for assignment.",true);
            return;
        }

        // Shuffle countries for randomness
        Collections.shuffle(l_availableCountries);

        // Assign one country per player
        for (int l_i = 0; l_i < l_players.size(); l_i++) {
            if (l_i >= l_availableCountries.size()) {
                LogEntryBuffer.getInstance().appendToBuffer("Warning: Not enough countries to assign one per player.",true);
                break;
            }

            Country l_assignedCountry = l_availableCountries.get(l_i);
            GameDriver.getGameEngine().assignCountryToPlayer(l_assignedCountry, l_players.get(l_i));
            l_assignedCountry.setTroops(3); // Assign 3 troops per requirement

         LogEntryBuffer.getInstance().appendToBuffer(l_players.get(l_i).getName() + " assigned to " + l_assignedCountry.getID() + " with 3 troops.",true);
        }
    }
}
