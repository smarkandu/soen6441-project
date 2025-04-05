package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class which implements the Strategy design pattern for the Aggressive player behavior
 */
public class AggressivePlayerBehavior extends ComputerPlayerBehavior {
    // Random instance for random decisions
    private Random d_random = new Random();
    //Deploys all available reinforcements to the strongest country.

    /**
     * {@inheritDoc}
     */
    @Override
    public void deployment(Player p_player) {
        // Get all owned country IDs and map them to Country objects
        List<String> l_ownedCountryIDs = p_player.getOwnedCountries();
        if (l_ownedCountryIDs.isEmpty()) {
            return;
        }

        // Retrieve the Country objects from the game engine's country manager
        List<Country> l_ownedCountries = new ArrayList<>();
        for (String l_id : l_ownedCountryIDs) {
            Country l_country = GameDriver.getGameEngine().getCountryManager().getCountries().get(l_id);
            if (l_country != null) {
                l_ownedCountries.add(l_country);
            }
        }

        // Find the strongest country -the one with the highest number of troops
        Country l_strongestCountry = null;
        int l_maxTroops = -1;
        for (Country l_country : l_ownedCountries) {
            if (l_country.getTroops() > l_maxTroops) {
                l_maxTroops = l_country.getTroops();
                l_strongestCountry = l_country;
            }
        }

        if (l_strongestCountry == null) {
            return;
        }

        // Determine the available reinforcements -total reinforcements minus already ordered deployment troops
        int l_remainingReinforcements = p_player.getReinforcements() - p_player.getNumberOfTroopsOrderedToDeploy();
        if (l_remainingReinforcements > 0) {
            // Deploy all remaining reinforcements to the strongest country
            GameDriver.getGameEngine().getPhase().deploy(l_strongestCountry.getID(), l_remainingReinforcements);
        }
    }

    //Issues attack orders from the strongest country and transfer orders from other owned countries
    //to centralize forces in the strongest country.

    /**
     * {@inheritDoc}
     */
    @Override
    public void attackTransfer(Player p_player) {
        //ATTACK PHASE**************

        // Get all owned country IDs and build list of Country objects.
        List<String> l_ownedCountryIDs = p_player.getOwnedCountries();
        if (l_ownedCountryIDs.isEmpty()) {
            return;
        }

        List<Country> l_ownedCountries = new ArrayList<>();
        for (String l_id : l_ownedCountryIDs) {
            Country l_country = GameDriver.getGameEngine().getCountryManager().getCountries().get(l_id);
            if (l_country != null) {
                l_ownedCountries.add(l_country);
            }
        }

        // Identify the strongest country among owned ones
        Country l_strongest = null;
        int l_maxTroops = -1;
        for (Country l_country : l_ownedCountries) {
            if (l_country.getTroops() > l_maxTroops) {
                l_maxTroops = l_country.getTroops();
                l_strongest = l_country;
            }
        }

        if (l_strongest == null || l_strongest.getTroops() <= 1) {
            // Not enough troops to attack
            return;
        }

        // Get neighbors of the strongest country
        List<String> l_neighborIDs = l_strongest.getNeighborIDs();
        List<String> l_enemyNeighborIDs = new ArrayList<>();

        // Consider a neighbor as enemy if it is not in the player's owned countries.
        for (String l_neighborID : l_neighborIDs) {
            if (!l_ownedCountryIDs.contains(l_neighborID)) {
                l_enemyNeighborIDs.add(l_neighborID);
            }
        }

        // If there are enemy neighbors, decide randomly to attack all or one
        if (!l_enemyNeighborIDs.isEmpty()) {
            // Compute available troops for attack from the strongest country
            int l_availableTroops = l_strongest.getTroops() - p_player.getNumberOfTroopsOrderedToAdvance(l_strongest);
            if (l_availableTroops > 0) {
                boolean l_attackAll = d_random.nextBoolean();
                if (l_attackAll) {
                    // Attack every enemy neighbor with a random number of available troops
                    for (String l_enemyID : l_enemyNeighborIDs) {
                        // Update available troops for each order (ensure we don't overspend)
                        l_availableTroops = l_strongest.getTroops() - p_player.getNumberOfTroopsOrderedToAdvance(l_strongest);
                        if (l_availableTroops <= 0) {
                            break;
                        }
                        int l_troopsToAttack = 1 + d_random.nextInt(l_availableTroops);
                        GameDriver.getGameEngine().getPhase().advance(l_strongest.getID(), l_enemyID, l_troopsToAttack);
                    }
                } else {
                    // Attack a single random enemy neighbor
                    String l_targetEnemy = l_enemyNeighborIDs.get(d_random.nextInt(l_enemyNeighborIDs.size()));
                    l_availableTroops = l_strongest.getTroops() - p_player.getNumberOfTroopsOrderedToAdvance(l_strongest);
                    if (l_availableTroops > 0) {
                        int l_troopsToAttack = 1 + d_random.nextInt(l_availableTroops);
                        GameDriver.getGameEngine().getPhase().advance(l_strongest.getID(), l_targetEnemy, l_troopsToAttack);
                    }
                }
            }
        }

        //TRANSFER PHASE (combine forces)***********

        // For every owned country (except the strongest), try to transfer available troops to the strongest country.
        for (String l_countryID : l_ownedCountryIDs) {
            // Skip the strongest country
            if (l_countryID.equals(l_strongest.getID())) {
                continue;
            }
            Country l_sourceCountry = GameDriver.getGameEngine().getCountryManager().getCountries().get(l_countryID);
            if (l_sourceCountry == null) {
                continue;
            }
            // Determine available troops (troops not yet committed in advance orders)
            int l_availableTroops = l_sourceCountry.getTroops() - p_player.getNumberOfTroopsOrderedToAdvance(l_sourceCountry);
            // Ensure at least one troop remains behind; transfer if more than one available
            if (l_availableTroops > 1) {
                // Check if the strongest country is a neighbor (transfer orders can only be issued between neighboring countries)
                List<String> l_sourceNeighbors = l_sourceCountry.getNeighborIDs();
                if (l_sourceNeighbors.contains(l_strongest.getID())) {
                    // Transfer all but one troop from source to the strongest country
                    int l_troopsToTransfer = l_availableTroops - 1;
                    GameDriver.getGameEngine().getPhase().advance(l_sourceCountry.getID(), l_strongest.getID(), l_troopsToTransfer);
                }
            }
        }

    }

    /**
     * String representing the object
     */
    @Override
    public String toString() {
        return "Aggressive";
    }
}
