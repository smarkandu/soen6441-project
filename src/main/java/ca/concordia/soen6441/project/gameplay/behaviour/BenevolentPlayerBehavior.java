package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.util.List;
import java.util.Map;

/**
 * Strategy class for Benevolent behavior.
 * Focuses on defense by reinforcing weakest owned country and safely transferring troops.
 */
public class BenevolentPlayerBehavior extends ComputerPlayerBehavior {

    /**
     * Deploy all reinforcements to the weakest owned country.
     */
    @Override
    public void deployment(Player p_player) {
        List<String> l_ownedCountries = p_player.getOwnedCountries();
        if (l_ownedCountries.isEmpty()) return;

        Country l_weakest = null;
        int l_minTroops = Integer.MAX_VALUE;

        // Find weakest owned country
        for (String l_id : l_ownedCountries) {
            Country l_current = GameDriver.getGameEngine()
                    .getCountryManager().getCountries().get(l_id);
            if (l_current != null && l_current.getTroops() < l_minTroops) {
                l_weakest = l_current;
                l_minTroops = l_current.getTroops();
            }
        }

        if (l_weakest != null) {
            int l_availableTroops = p_player.getReinforcements()
                    - p_player.getNumberOfTroopsOrderedToDeploy();
            if (l_availableTroops > 0) {
                GameDriver.getGameEngine().getPhase()
                        .deploy(l_weakest.getID(), l_availableTroops);
                LogEntryBuffer.getInstance().appendToBuffer(
                        p_player.getName() + " deployed " + l_availableTroops +
                                " troops to " + l_weakest.getID(), true);
            }
        }
    }

    /**
     * Transfers troops from strongest country to:
     * 1. Safe zero-troop neighbors (owned or unowned)
     * 2. Fallback: weakest owned neighbor if no safe target found
     */
    @Override
    public void attackTransfer(Player p_player) {
        List<String> l_ownedCountries = p_player.getOwnedCountries();
        if (l_ownedCountries.isEmpty()) return;

        // Find strongest owned country
        Country l_strongest = null;
        int l_maxTroops = 0;
        for (String l_id : l_ownedCountries) {
            Country l_country = GameDriver.getGameEngine()
                    .getCountryManager().getCountries().get(l_id);
            if (l_country != null && l_country.getTroops() > l_maxTroops) {
                l_strongest = l_country;
                l_maxTroops = l_country.getTroops();
            }
        }

        if (l_strongest == null || l_strongest.getTroops() <= 1) return;

        List<String> l_neighbors = l_strongest.getNeighborIDs();
        Map<String, Country> l_allCountries = GameDriver.getGameEngine()
                .getCountryManager().getCountries();
        Map<String, Player> l_allPlayers = GameDriver.getGameEngine()
                .getPlayerManager().getPlayers();
        l_allPlayers.remove(p_player.getName()); // exclude self

        // ✅ Try transferring to any 0-troop neighbor (safe if no enemy is adjacent)
        for (String l_neighborId : l_neighbors) {
            Country l_neighbor = l_allCountries.get(l_neighborId);
            if (l_neighbor == null || l_neighbor.getTroops() > 0) continue;

            boolean l_enemyNearby = false;

            // Check if enemy is adjacent to this neighbor
            for (Player l_enemy : l_allPlayers.values()) {
                for (String l_enemyCountryId : l_enemy.getOwnedCountries()) {
                    if (l_neighbor.getNeighborIDs().contains(l_enemyCountryId)) {
                        l_enemyNearby = true;
                        break;
                    }
                }
                if (l_enemyNearby) break;
            }

            if (!l_enemyNearby) {
                int l_availableTroops = l_strongest.getTroops()
                        - p_player.getNumberOfTroopsOrderedToAdvance(l_strongest);
                if (l_availableTroops > 1) {
                    int l_toTransfer = l_availableTroops - 1;
                    GameDriver.getGameEngine().getPhase()
                            .advance(l_strongest.getID(), l_neighbor.getID(), l_toTransfer);
                    LogEntryBuffer.getInstance().appendToBuffer(
                            p_player.getName() + " transferred " + l_toTransfer +
                                    " troops from " + l_strongest.getID() +
                                    " to " + l_neighbor.getID(), true);
                }
                return; // successful transfer, stop here
            } else {
                LogEntryBuffer.getInstance().appendToBuffer(
                        p_player.getName() + " skipped transfer to " + l_neighbor.getID()
                                + " due to enemy nearby", true);
            }
        }

        // ❗ No safe 0-troop neighbor found: fallback to weakest owned neighbor
        Country l_weakestOwnedNeighbor = null;
        int l_minTroops = Integer.MAX_VALUE;
        for (String l_neighborId : l_neighbors) {
            Country l_neighbor = l_allCountries.get(l_neighborId);
            if (l_neighbor != null && p_player.getOwnedCountries().contains(l_neighbor.getID())
                    && l_neighbor.getTroops() < l_minTroops) {
                l_weakestOwnedNeighbor = l_neighbor;
                l_minTroops = l_neighbor.getTroops();
            }
        }

        if (l_weakestOwnedNeighbor != null) {
            int l_availableTroops = l_strongest.getTroops()
                    - p_player.getNumberOfTroopsOrderedToAdvance(l_strongest);
            if (l_availableTroops > 1) {
                int l_toTransfer = l_availableTroops - 1;
                GameDriver.getGameEngine().getPhase()
                        .advance(l_strongest.getID(), l_weakestOwnedNeighbor.getID(), l_toTransfer);
                LogEntryBuffer.getInstance().appendToBuffer(
                        p_player.getName() + " transferred " + l_toTransfer +
                                " troops from " + l_strongest.getID() +
                                " to fallback " + l_weakestOwnedNeighbor.getID(), true);
            }
        }
    }

    /**
     * String representation of the strategy.
     */
    @Override
    public String toString() {
        return "Benevolent";
    }
}
