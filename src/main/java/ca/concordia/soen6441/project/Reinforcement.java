package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.GameEngine;
import java.util.List;
import java.util.Map;

public class Reinforcement {

    private final GameEngine d_gameEngine;

    public Reinforcement(GameEngine p_gameEngine) {
        this.d_gameEngine = p_gameEngine;
    }


    public void assignReinforcements() {
        Map<String, Player> l_players = d_gameEngine.getPlayers();
        Map<String, Continent> l_continents = d_gameEngine.getContinents();

        for (Player l_player : l_players.values()) {
            int l_territoriesOwned = l_player.getOwnedCountries().size();
            int l_continentBonus = calculateContinentBonus(l_player, l_continents);

            int l_reinforcements = Math.max(3, (int) Math.floor(l_territoriesOwned / 3.0) + l_continentBonus);
            l_player.setReinforcements(l_reinforcements);

            System.out.println(l_player.getName() + " receives " + l_reinforcements + " reinforcements.");
        }
    }


    private int calculateContinentBonus(Player p_player, Map<String, Continent> p_continents) {
        int l_bonus = 0;

        for (Continent l_continent : p_continents.values()) {
            boolean l_ownsAll = true;


            for (Country l_country : d_gameEngine.getCountries().values()) {
                if (d_gameEngine.getContinentByNumericID(l_country.getNumericID()) == l_continent) {
                    if (!p_player.getOwnedCountries().contains(l_country.getID())) {
                        l_ownsAll = false;
                        break;
                    }
                }
            }

            if (l_ownsAll) {
                l_bonus += 5;
            }
        }
        return l_bonus;
    }
}
