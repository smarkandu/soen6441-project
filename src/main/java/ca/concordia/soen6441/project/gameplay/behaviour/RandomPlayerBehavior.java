package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Class which implements the Strategy design pattern for the Random player behavior.
 * This behavior issues deploy and attack/transfer orders in a random fashion.
 */
public class RandomPlayerBehavior extends ComputerPlayerBehavior {

    /**
     * Random object for generating random numbers.
     */
    private Random d_random = new Random();

    /**
     * {@inheritDoc}
     * Issues a deploy order to a randomly chosen owned country with a random number of available reinforcements.
     *
     * @param p_player The player issuing the order.
     */

    @Override
    public void deployment(Player p_player) {
        List<String> l_ownedCountries = p_player.getOwnedCountries();
        Collections.shuffle(l_ownedCountries);

        if (!l_ownedCountries.isEmpty()) {
            while (p_player.getNumberOfTroopsOrderedToDeploy() < p_player.getReinforcements()) {
                String l_randomCountryID = l_ownedCountries.get(0);
                int l_remainingTroops = p_player.getReinforcements() - p_player.getNumberOfTroopsOrderedToDeploy();

                // FIX: prevent Random.nextInt(0) error
                if (l_remainingTroops <= 0) {
                    break;
                }

                int l_toDeploy = 1 + d_random.nextInt(l_remainingTroops);

                GameDriver.getGameEngine().getPhase().deploy(l_randomCountryID, l_toDeploy);
            }
        }

        LogEntryBuffer.getInstance().appendToBuffer(
                "[RandomPlayer] deployment() executed in phase: " +
                        GameDriver.getGameEngine().getPhase().getPhaseName(), true);
    }

    /**
     * {@inheritDoc}
     * Issues a single random attack or transfer order from one owned country to one of its neighbors.
     *
     * @param p_player The player issuing the order.
     */
    @Override
    public void attackTransfer(Player p_player) {
        LogEntryBuffer.getInstance().appendToBuffer(
                "[RandomPlayer] attackTransfer() executed in phase: " +
                        GameDriver.getGameEngine().getPhase().getPhaseName(), true);

        List<String> l_ownedCountryIDs = p_player.getOwnedCountries();
        Collections.shuffle(l_ownedCountryIDs);

        for (String l_countryID : l_ownedCountryIDs) {
            Country l_source = GameDriver.getGameEngine().getCountryManager().getCountries().get(l_countryID);
            if (l_source == null || l_source.getTroops() <= 1) continue;

            List<String> l_neighbors = l_source.getNeighborIDs();
            Collections.shuffle(l_neighbors);

            for (String l_neighborID : l_neighbors) {
                Country l_target = GameDriver.getGameEngine().getCountryManager().getCountries().get(l_neighborID);
                if (l_target == null) continue;

                int l_availableTroops = l_source.getTroops() - p_player.getNumberOfTroopsOrderedToAdvance(l_source);

                // Fix: skip if no troops
                if (l_availableTroops <= 0) continue;

                // Fix: prevent crash
                int l_toAdvance = 1 + d_random.nextInt(l_availableTroops);

                // check if owned or enemy
                if (p_player.getOwnedCountries().contains(l_neighborID)) {
                    // Transfer to owned neighbor
                    GameDriver.getGameEngine().getPhase().advance(l_countryID, l_neighborID, l_toAdvance);
                } else {
                    // Attack enemy neighbor
                    GameDriver.getGameEngine().getPhase().advance(l_countryID, l_neighborID, l_toAdvance);
                }

                // Prevent infinite loop: move to next source country
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     * Returns the name of the behavior strategy.
     *
     * @return String "Random".
     */
    @Override
    public String toString() {
        return "Random";
    }
}
