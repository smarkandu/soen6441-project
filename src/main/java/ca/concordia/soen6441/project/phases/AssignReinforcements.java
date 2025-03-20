package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.context.GameContext;

import java.util.Map;

/**
 * The AssignReinforcements class handles the reinforcement phase of the game.
 * It calculates and assigns reinforcements to players based on the number of territories owned
 * and any continent control bonuses.
 */
public class AssignReinforcements extends MainPlay {

    /**
     * Constructs an AssignReinforcements phase.
     *
     * @param p_gameEngine The game engine instance controlling the game state.
     */
    public AssignReinforcements(GameContext p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_playerName The name of the player to add (not used in this phase).
     */
    @Override
    public void gamePlayerAdd(String p_playerName) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_playerName The name of the player to remove (not used in this phase).
     */
    @Override
    public void gamePlayerRemove(String p_playerName) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_countryID The country where troops would be deployed.
     * @param p_toDeploy  The number of troops to deploy.
     */
    @Override
    public void deploy(String p_countryID, int p_toDeploy) {
        printInvalidCommandMessage();
    }

    @Override
    public void advance(String p_countryNameFrom, String p_countryNameTo, int p_toAdvance) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     */
    @Override
    public void next() {
        printInvalidCommandMessage();
    }

    /**
     * Executes the reinforcement phase by assigning reinforcements to players
     * based on the number of territories they own and continent control bonuses.
     */
    public void execute() {
        for (int l_i = 0; l_i < d_gameEngine.getPlayerManager().getPlayers().size(); l_i++) {
            Player l_player = d_gameEngine.getPlayerManager().getPlayer(l_i);
            Map<String, Continent> l_continents = d_gameEngine.getContinentManager().getContinents();

            int l_territoriesOwned = l_player.getOwnedCountries().size();
            int l_continentBonus = calculateContinentBonus(l_player, l_continents);

            int l_reinforcements = Math.max(3, (int) Math.floor(l_territoriesOwned / 3.0)) + l_continentBonus;
            l_player.setReinforcements(l_reinforcements);

            System.out.println(l_player.getName() + " receives " + l_player.getReinforcements() + " reinforcements.");
        }

        d_gameEngine.setPhase(new IssueOrder(d_gameEngine, 0));
    }

    /**
     * Calculates the continent bonus for a player.
     * A player receives a bonus if they fully own a continent.
     *
     * @param p_player    The player whose bonus is being calculated.
     * @param p_continents The map of all continents in the game.
     * @return The continent bonus for the player.
     */
    private int calculateContinentBonus(Player p_player, Map<String, Continent> p_continents) {
        int l_bonus = 0;

        for (Continent l_continent : p_continents.values()) { // loop through all the continents
            boolean l_ownsAll = true;
            int l_countriesInContinent = 0;
            for (Country l_country : d_gameEngine.getCountryManager().getCountries().values()) { // loop through all the countries

                if (l_country.getContinent() == l_continent) { // if country is part of continent
                    l_countriesInContinent++;
                    // if player doesn't own country in continent
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
