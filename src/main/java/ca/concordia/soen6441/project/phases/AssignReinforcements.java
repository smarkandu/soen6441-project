package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.gameplay.PlayerImpl;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.util.Map;

/**
 * This phase is responsible for assigning reinforcement armies to players
 * at the start of their turns. The number of reinforcements depends on:
 * - The number of territories a player owns (min of 3)
 * - Bonus reinforcements for owning all countries in a continent
 */
public class AssignReinforcements extends MainPlay {

    /**
     * Constructs the AssignReinforcements phase.
     *
     * @param p_gameEngine the game context to operate on
     */
    public AssignReinforcements(GameContext p_gameEngine) {
        super(p_gameEngine);
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_countryID country ID
     * @param p_toDeploy  number of armies to deploy
     */
    @Override
    public void deploy(String p_countryID, int p_toDeploy) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_countryNameFrom source country
     * @param p_countryNameTo   target country
     * @param p_toAdvance       armies to move
     */
    @Override
    public void advance(String p_countryNameFrom, String p_countryNameTo, int p_toAdvance) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_countryID country to bomb
     */
    @Override
    public void bomb(String p_countryID) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_countryID country to blockade
     */
    @Override
    public void blockade(String p_countryID) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_sourceCountryID source of airlift
     * @param p_targetCountryID destination of airlift
     * @param p_numArmies       armies to airlift
     */
    @Override
    public void airlift(String p_sourceCountryID, String p_targetCountryID, int p_numArmies) {
        printInvalidCommandMessage();
    }

    /**
     * Invalid command for this phase.
     *
     * @param p_playerID player to negotiate with
     */
    @Override
    public void negotiate(String p_playerID) {
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
     * Executes the reinforcement phase:
     * - Resets negotiated players list
     * - Calculates reinforcements from territory and continent control
     * - Assigns reinforcement count to each player
     */
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

        // Set Current State to IssueOrder
        d_gameEngine.setPhase(new IssueOrder(d_gameEngine, 0));
    }

    /**
     * Calculates the continent control bonus for a player.
     * If the player owns all countries in a continent, the bonus is awarded.
     *
     * @param p_player     the player
     * @param p_continents map of all continents
     * @return the total continent bonus value
     */
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
