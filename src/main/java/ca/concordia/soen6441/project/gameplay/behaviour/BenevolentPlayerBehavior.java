package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;

import java.util.List;

/**
 * Class which implements the Strategy design pattern for the Benevolent player behavior.
 * Focuses on defense by reinforcing the weakest owned country and avoids attacks.
 */
public class BenevolentPlayerBehavior extends ComputerPlayerBehavior {

    /**
     * Deploy reinforcements to the weakest (least troops) owned country.
     *
     * @param player The player performing the deploy action.
     */
    @Override
    public void deployment(Player player) {
        List<String> ownedCountries = player.getOwnedCountries();

        if (ownedCountries.isEmpty()) {
            return;
        }

        Country weakest = null;
        int minimumTroops = Integer.MAX_VALUE;

        for (String id : ownedCountries) {
            Country current = GameDriver.getGameEngine()
                    .getCountryManager()
                    .getCountries()
                    .get(id);

            if (current != null && current.getTroops() < minimumTroops) {
                weakest = current;
                minimumTroops = current.getTroops();
            }
        }

        if (weakest != null) {
            int availableTroops = player.getReinforcements()
                    - player.getNumberOfTroopsOrderedToDeploy();

            if (availableTroops > 0) {
                GameDriver.getGameEngine()
                        .getPhase()
                        .deploy(weakest.getID(), availableTroops);
            }
        }

        System.out.println("[Benevolent] deployment done for: " + player.getName());
    }

    /**
     * Benevolent players do not attack; they focus on defense.
     *
     * @param player The player (not used in attack).
     */
    @Override
    public void attackTransfer(Player player) {
        System.out.println("[Benevolent] No attack. Benevolent strategy is passive.");
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
