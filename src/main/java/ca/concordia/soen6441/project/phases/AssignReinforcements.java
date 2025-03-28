// Removed import for IssueOrder
package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.gameplay.PlayerImpl;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.util.Map;

public class AssignReinforcements extends MainPlay {

    public AssignReinforcements(GameContext p_gameEngine) {
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
    public void advance(String p_countryNameFrom, String p_countryNameTo, int p_toAdvance) {
        printInvalidCommandMessage();
    }

    @Override
    public void bomb(String p_countryID) {
        printInvalidCommandMessage();
    }

    @Override
    public void blockade(String p_countryID) {
        printInvalidCommandMessage();
    }

    @Override
    public void airlift(String p_sourceCountryID, String p_targetCountryID, int p_numArmies) {
        printInvalidCommandMessage();
    }

    @Override
    public void negotiate(String p_playerID) {
        printInvalidCommandMessage();
    }

    @Override
    public void next() {
        printInvalidCommandMessage();
    }

    public void execute() {
        for (int l_i = 0; l_i < d_gameEngine.getPlayerManager().getPlayers().size(); l_i++) {
            Player l_player = d_gameEngine.getPlayerManager().getPlayer(l_i);

            if (!((PlayerImpl) l_player).getNegotiatedPlayers().isEmpty()) {
                ((PlayerImpl) l_player).resetNegotiatedPlayers();
                LogEntryBuffer.getInstance().appendToBuffer("Diplomacy list reset for player: " + l_player.getName(), true);
            }

            Map<String, Continent> l_continents = d_gameEngine.getContinentManager().getContinents();

            int l_territoriesOwned = l_player.getOwnedCountries().size();
            int l_continentBonus = calculateContinentBonus(l_player, l_continents);

            int l_reinforcements = Math.max(3, (int) Math.floor(l_territoriesOwned / 3.0)) + l_continentBonus;
            l_player.setReinforcements(l_reinforcements);

            System.out.println(l_player.getName() + " receives " + l_player.getReinforcements() + " reinforcements.");
        }

        // Removed circular dependency call to IssueOrder here.
    }

    private int calculateContinentBonus(Player p_player, Map<String, Continent> p_continents) {
        int l_bonus = 0;

        for (Continent l_continent : p_continents.values()) {
            boolean l_ownsAll = true;
            int l_countriesInContinent = 0;

            for (Country l_country : d_gameEngine.getCountryManager().getCountries().values()) {
                if (l_country.getContinent() == l_continent) {
                    l_countriesInContinent++;
                    if (!p_player.getOwnedCountries().contains(l_country.getID())) {
                        l_ownsAll = false;
                        break;
                    }
                }
            }

            if (l_countriesInContinent == 0) {
                l_ownsAll = false;
            }

            if (l_ownsAll) {
                l_bonus += l_continent.getValue();
            }
        }
        return l_bonus;
    }
}
