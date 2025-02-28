package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;

import java.util.ArrayList;
import java.util.Map;

public class AssignReinforcements extends MainPlay {

    public AssignReinforcements(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void gamePlayerAdd(String p_playerName) {
        printInvalidCommandMessage();
    }

    @Override
    public void gamePlayerRemove(String p_playerName) {
        printInvalidCommandMessage();
    }

    @Override
    public void deploy(String p_countryID, int p_toDeploy) {
        printInvalidCommandMessage();
    }

    @Override
    public void next() {
        printInvalidCommandMessage();
    }


    public void assignReinforcements() {
        for (int l_i = 0; l_i < d_gameEngine.getPlayers().size(); l_i++)
        {
            Player l_player = d_gameEngine.getPlayer(l_i);
            Map<String, Continent> l_continents = d_gameEngine.getContinents();

            int l_territoriesOwned = l_player.getOwnedCountries().size();
            int l_continentBonus = calculateContinentBonus(l_player, l_continents);

            int l_reinforcements = Math.max(3, (int) Math.floor(l_territoriesOwned / 3.0)) + l_continentBonus;
            l_player.setReinforcements(l_reinforcements);

            System.out.println(l_player.getName() + " receives " + l_player.getReinforcements() + " reinforcements.");
        }

        d_gameEngine.setPhase(new IssueOrder(d_gameEngine, 0));
    }


    private int calculateContinentBonus(Player p_player, Map<String, Continent> p_continents) {
        int l_bonus = 0;

        for (Continent l_continent : p_continents.values()) { // loop through all the continents
            boolean l_ownsAll = true;
            int l_countriesInContinent = 0;
            for (Country l_country : d_gameEngine.getCountries().values()) { // loop through all the countries

                if (l_country.getContinent() == l_continent) { // if country is part of continent
                    l_countriesInContinent++;
                    // if player doesn't own country in continent
                    if (!p_player.getOwnedCountries().contains(l_country.getID())) {
                        l_ownsAll = false;
                        break;
                    }
                }
            }
            if (l_countriesInContinent == 0)
            {
                l_ownsAll = false;
            }

            if (l_ownsAll) {
                l_bonus += l_continent.getValue();
            }
        }
        return l_bonus;
    }
}
