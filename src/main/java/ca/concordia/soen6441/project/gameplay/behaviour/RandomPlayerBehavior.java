package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.gameplay.orders.Advance;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;

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
        // Get the list of countries owned by the player
        List<String> l_ownedCountries = p_player.getOwnedCountries();

        // Shuffle to randomize order
        Collections.shuffle(l_ownedCountries);

        // Only proceed if there are owned countries
        if (!l_ownedCountries.isEmpty()) {
            // Get a random country ID from the list
            String l_randomCountryID = l_ownedCountries.get(0);

            // Get how many troops the player can still deploy
            int l_remainingTroops = p_player.getReinforcements() - p_player.getNumberOfTroopsOrderedToDeploy();

            // If there are troops left to deploy
            if (l_remainingTroops > 0 && l_randomCountryID != null) {
                // Choose a random number of troops to deploy (between 1 and remaining troops)
                int l_toDeploy = 1 + d_random.nextInt(l_remainingTroops);

                // Create a deploy order and add it to the player's orders
                p_player.getPlayerManager().getGameEngine().getPhase().deploy(l_randomCountryID, l_toDeploy);
            }
        }
        System.out.println(p_player.getPlayerManager().getGameEngine().getPhase().getPhaseName());
    }

    /**
     * {@inheritDoc}
     * Issues a single random attack or transfer order from one owned country to one of its neighbors.
     *
     * @param p_player The player issuing the order.
     */
    @Override
    public void attackTransfer(Player p_player) {
        // Get all owned countries
        List<String> l_ownedCountryIDs = p_player.getOwnedCountries();
        Collections.shuffle(l_ownedCountryIDs); // Shuffle for randomness

        for (String l_countryID : l_ownedCountryIDs) {
            // Get Country object
            Country l_source = p_player.getPlayerManager().getGameEngine().getCountryManager().getCountries().get(l_countryID);
            if (l_source == null || l_source.getTroops() <= 1) continue; // Skip if no troops to send

            // Shuffle neighbors
            List<String> l_neighbors = l_source.getNeighborIDs();
            Collections.shuffle(l_neighbors);

            for (String l_neighborID : l_neighbors) {
                Country l_target = p_player.getPlayerManager().getGameEngine().getCountryManager().getCountries().get(l_neighborID);
                if (l_target == null || l_source.equals(l_target)) continue;

                int l_availableTroops = l_source.getTroops() - 1 - p_player.getNumberOfTroopsOrderedToAdvance(l_source);

                if (l_availableTroops > 0) {
                    int l_toAdvance = 1 + d_random.nextInt(l_availableTroops);
                    p_player.addToOrders(new Advance(l_source, l_target, l_toAdvance, p_player, p_player.getPlayerManager().getGameEngine()));
                    System.out.println(p_player.getPlayerManager().getGameEngine().getPhase().getPhaseName());
                    return; // Only one advance per call
                }
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
