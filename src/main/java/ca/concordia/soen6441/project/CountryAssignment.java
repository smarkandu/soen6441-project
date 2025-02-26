package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.GameEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CountryAssignment {

    private static GameEngine GameEngine = new GameEngine();


    public CountryAssignment(GameEngine p_gameEngine) {
        this.GameEngine = p_gameEngine;
    }


    public static void assignCountries() {
        List<Country> l_availableCountries = new ArrayList<>(GameEngine.getCountries().values());
        List<Player> l_players = new ArrayList<>(GameEngine.getPlayers().values());

        if (l_players.isEmpty() || l_availableCountries.isEmpty()) {
            System.out.println("Error: No players or no countries available for assignment.");
            return;
        }

        // Shuffle countries for randomness
        Collections.shuffle(l_availableCountries);

        // Assign one country per player
        for (int l_i = 0; l_i < l_players.size(); l_i++) {
            if (l_i >= l_availableCountries.size()) {
                System.out.println("Warning: Not enough countries to assign one per player.");
                break;
            }

            Country l_assignedCountry = l_availableCountries.get(l_i);
            l_players.get(l_i).assignCountry(l_assignedCountry);
            l_assignedCountry.setTroops(3); // Assign 3 troops per requirement

            System.out.println(l_players.get(l_i).getName() + " assigned to " + l_assignedCountry.getID() + " with 3 troops.");
        }
    }
}
