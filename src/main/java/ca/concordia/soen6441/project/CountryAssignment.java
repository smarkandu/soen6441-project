package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.GameEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handles assigning one country per player at game start.
 */
public class CountryAssignment {

    private static GameEngine GameEngine = new GameEngine();


    public CountryAssignment(GameEngine gameEngine) {
        this.GameEngine = gameEngine;
    }


    public static void assignCountries() {
        List<Country> availableCountries = new ArrayList<>(GameEngine.getCountries().values());
        List<Player> players = new ArrayList<>(GameEngine.getPlayers().values());

        if (players.isEmpty() || availableCountries.isEmpty()) {
            System.out.println("Error: No players or no countries available for assignment.");
            return;
        }

        // Shuffle countries for randomness
        Collections.shuffle(availableCountries);

        // Assign one country per player
        for (int i = 0; i < players.size(); i++) {
            if (i >= availableCountries.size()) {
                System.out.println("Warning: Not enough countries to assign one per player.");
                break;
            }

            Country assignedCountry = availableCountries.get(i);
            players.get(i).assignCountry(assignedCountry);
            assignedCountry.setTroops(3); // Assign 3 troops per requirement

            System.out.println(players.get(i).getName() + " assigned to " + assignedCountry.getID() + " with 3 troops.");
        }
    }
}
