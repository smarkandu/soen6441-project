package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;

import java.util.List;
import java.util.Map;

/**
 * Class which implements the Strategy design pattern for the Benevolent player behavior.
 * Focuses on defense by reinforcing the weakest owned country and avoids attacks.
 */
public class BenevolentPlayerBehavior extends ComputerPlayerBehavior {

    /**
     * Deploy reinforcements to the weakest (least troops) owned country.
     *
     * @param p_player The player performing the deploy action.
     */
    @Override
    public void deployment(Player p_player) {
        List<String> l_ownedCountries = p_player.getOwnedCountries();

        if (l_ownedCountries.isEmpty()) {
            return;
        }

        Country l_weakest = null;
        int l_minimumTroops = Integer.MAX_VALUE;

        for (String l_id : l_ownedCountries) {
            Country l_current = GameDriver.getGameEngine()
                    .getCountryManager()
                    .getCountries()
                    .get(l_id);

            if (l_current != null && l_current.getTroops() < l_minimumTroops) {
                l_weakest = l_current;
                l_minimumTroops = l_current.getTroops();
            }
        }

        if (l_weakest != null) {
            int l_availableTroops = p_player.getReinforcements()
                    - p_player.getNumberOfTroopsOrderedToDeploy();

            if (l_availableTroops > 0) {
                GameDriver.getGameEngine()
                        .getPhase()
                        .deploy(l_weakest.getID(), l_availableTroops);
            }
        }

        System.out.println("[Benevolent] deployment done for: " + p_player.getName());
    }

    /**
     * Transfers troops from the strongest to the weakest owned neighboring country.
     * Benevolent players do not attack but are allowed to transfer troops defensively.
     * This version also avoids transferring to a country adjacent to an enemy.
     *
     * @param p_player The player performing the transfer.
     */
    @Override
    public void attackTransfer(Player p_player) {
        List<String> l_ownedCountries = p_player.getOwnedCountries();

        if (l_ownedCountries.isEmpty()) {
            return;
        }

        Country l_strongest = null;
        int l_maxTroops = 0;

        for (String l_id : l_ownedCountries) {
            Country l_country = GameDriver.getGameEngine()
                    .getCountryManager()
                    .getCountries()
                    .get(l_id);

            if (l_country != null && l_country.getTroops() > l_maxTroops) {
                l_strongest = l_country;
                l_maxTroops = l_country.getTroops();
            }
        }

        if (l_strongest == null || l_strongest.getTroops() <= 1) {
            return;
        }

        List<String> l_neighbors = l_strongest.getNeighborIDs();
        Country l_weakestNeighbor = null;
        int l_minTroops = Integer.MAX_VALUE;

        for (String l_neighborId : l_neighbors) {
            Country l_neighbor = GameDriver.getGameEngine()
                    .getCountryManager()
                    .getCountries()
                    .get(l_neighborId);

            if (l_neighbor != null
                    && p_player.getOwnedCountries().contains(l_neighbor.getID())
                    && l_neighbor.getTroops() < l_minTroops) {
                l_weakestNeighbor = l_neighbor;
                l_minTroops = l_neighbor.getTroops();
            }
        }

        if (l_weakestNeighbor != null) {
            List<String> l_neighborOfNeighbor = l_weakestNeighbor.getNeighborIDs();
            Map<String, Player> l_allPlayers = GameDriver.getGameEngine()
                    .getPlayerManager()
                    .getPlayers();
            l_allPlayers.remove(p_player.getName());

            for (String l_otherPlayer : l_allPlayers.keySet()) {
                Player l_enemy = l_allPlayers.get(l_otherPlayer);
                for (String l_enemyCountryId : l_enemy.getOwnedCountries()) {
                    if (l_neighborOfNeighbor.contains(l_enemyCountryId)) {
                        System.out.println("[Benevolent] Skipped transfer due to enemy nearby: " + l_enemyCountryId);
                        return;
                    }
                }
            }

            int l_availableTroops = l_strongest.getTroops()
                    - p_player.getNumberOfTroopsOrderedToAdvance(l_strongest);

            if (l_availableTroops > 1) {
                int l_toTransfer = l_availableTroops - 1; // Leave at least 1 troop behind
                GameDriver.getGameEngine()
                        .getPhase()
                        .advance(l_strongest.getID(), l_weakestNeighbor.getID(), l_toTransfer);

                System.out.println("[Benevolent] Transferred " + l_toTransfer
                        + " troops from " + l_strongest.getID()
                        + " to " + l_weakestNeighbor.getID());
            }
        }
    }

    /**
     * Returns the string identifier for the strategy.
     *
     * @return "Benevolent"
     */
    @Override
    public String toString() {
        return "Benevolent";
    }
}
