package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.GameEngine;
import java.util.List;
import java.util.Map;

public class Reinforcement {

    private final GameEngine gameEngine;

    public Reinforcement(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }


    public void assignReinforcements() {
        Map<String, Player> players = gameEngine.getPlayers();
        Map<String, Continent> continents = gameEngine.getContinents();

        for (Player player : players.values()) {
            int territoriesOwned = player.getOwnedCountries().size();
            int continentBonus = calculateContinentBonus(player, continents);

            int reinforcements = Math.max(3, (int) Math.floor(territoriesOwned / 3.0) + continentBonus);
            player.setReinforcements(reinforcements);

            System.out.println(player.getName() + " receives " + reinforcements + " reinforcements.");
        }
    }


    private int calculateContinentBonus(Player player, Map<String, Continent> continents) {
        int bonus = 0;

        for (Continent continent : continents.values()) {
            boolean ownsAll = true;


            for (Country country : gameEngine.getCountries().values()) {
                if (gameEngine.getContinentByNumericID(country.getNumericID()) == continent) {
                    if (!player.getOwnedCountries().contains(country.getID())) {
                        ownsAll = false;
                        break;
                    }
                }
            }

            if (ownsAll) {
                bonus += 5;
            }
        }
        return bonus;
    }
}
