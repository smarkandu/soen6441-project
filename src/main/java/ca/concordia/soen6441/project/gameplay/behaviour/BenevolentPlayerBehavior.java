package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.util.*;

/**
 * Benevolent player behavior.
 * Focuses on defense by reinforcing weak countries and transferring troops
 * to safe or weak neighboring owned countries, avoiding attacks.
 */
public class BenevolentPlayerBehavior extends ComputerPlayerBehavior {

    @Override
    public void deployment(Player p_player) {
        List<String> l_ownedCountries = p_player.getOwnedCountries();
        if (l_ownedCountries.isEmpty()) return;

        Country l_weakest = null;
        int l_minTroops = Integer.MAX_VALUE;

        // Find the weakest owned country (least troops)
        for (String l_id : l_ownedCountries) {
            Country l_country = GameDriver.getGameEngine()
                    .getCountryManager().getCountries().get(l_id);
            if (l_country != null && l_country.getTroops() < l_minTroops) {
                l_weakest = l_country;
                l_minTroops = l_country.getTroops();
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

    @Override
    public void attackTransfer(Player p_player) {
        List<String> l_ownedCountries = p_player.getOwnedCountries();
        if (l_ownedCountries.isEmpty()) return;

        Map<String, Country> l_allCountries = GameDriver.getGameEngine()
                .getCountryManager().getCountries();

        //Copy of players map to prevent global mutation
        Map<String, Player> l_allPlayers = new HashMap<>(GameDriver.getGameEngine()
                .getPlayerManager().getPlayers());

        boolean l_transferred = false;

        for (String l_sourceId : l_ownedCountries) {
            Country l_source = l_allCountries.get(l_sourceId);
            if (l_source == null || l_source.getTroops() <= 1) continue;

            List<String> l_neighbors = l_source.getNeighborIDs();

            // Transfer to safe neutral neighbor (not adjacent to enemy)
            for (String l_neighborId : l_neighbors) {
                Country l_neighbor = l_allCountries.get(l_neighborId);
                if (l_neighbor == null || l_neighbor.getTroops() > 0) continue;

                boolean l_enemyNearby = false;
                for (Player l_enemy : l_allPlayers.values()) {
                    if (l_enemy.getName().equals(p_player.getName())) continue;

                    for (String l_enemyCountryId : l_enemy.getOwnedCountries()) {
                        if (l_neighbor.getNeighborIDs().contains(l_enemyCountryId)) {
                            l_enemyNearby = true;
                            break;
                        }
                    }
                    if (l_enemyNearby) break;
                }

                if (!l_enemyNearby) {
                    int l_availableTroops = l_source.getTroops()
                            - p_player.getNumberOfTroopsOrderedToAdvance(l_source);
                    if (l_availableTroops > 1) {
                        int l_toTransfer = l_availableTroops - 1;
                        GameDriver.getGameEngine().getPhase()
                                .advance(l_source.getID(), l_neighbor.getID(), l_toTransfer);
                        LogEntryBuffer.getInstance().appendToBuffer(
                                p_player.getName() + " advanced " + l_toTransfer +
                                        " troops from " + l_source.getID() +
                                        " to neutral neighbor " + l_neighbor.getID(), true);
                        l_transferred = true;
                    }
                }
            }

            // Fallback: transfer to weakest owned neighbor
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
                int l_availableTroops = l_source.getTroops()
                        - p_player.getNumberOfTroopsOrderedToAdvance(l_source);
                if (l_availableTroops > 1) {
                    int l_toTransfer = l_availableTroops - 1;
                    GameDriver.getGameEngine().getPhase()
                            .advance(l_source.getID(), l_weakestOwnedNeighbor.getID(), l_toTransfer);
                    LogEntryBuffer.getInstance().appendToBuffer(
                            p_player.getName() + " advanced " + l_toTransfer +
                                    " troops from " + l_source.getID() +
                                    " to owned neighbor " + l_weakestOwnedNeighbor.getID(), true);
                    l_transferred = true;
                }
            }
        }

        if (!l_transferred) {
            LogEntryBuffer.getInstance().appendToBuffer(
                    p_player.getName() + " could not find any valid reinforcement opportunities.", true);
        }
    }

    @Override
    public String toString() {
        return "Benevolent";
    }
}
