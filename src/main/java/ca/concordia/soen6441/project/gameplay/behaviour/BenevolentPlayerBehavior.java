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
     * @param p_player The player performing the deploy action.
     */
    @Override
    public void deployment(Player p_player) {
        List<String> L_ownedCountries = p_player.getOwnedCountries();

        if (L_ownedCountries.isEmpty()) {
            return;
        }

        Country l_weakest = null;
        int l_minimumTroops = Integer.MAX_VALUE;

        for (String l_id : L_ownedCountries) {
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
     * Benevolent players do not attack; they focus on defense.
     *
     * @param p_player The player (not used in attack).
     */
    @Override
    public void attackTransfer(Player p_player) {
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
