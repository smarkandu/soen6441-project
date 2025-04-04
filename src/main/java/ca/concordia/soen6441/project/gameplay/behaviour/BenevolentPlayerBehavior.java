package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;

import java.util.List;

/**
 * Class which implements the Strategy design pattern for the Benevolent player behavior.
 * Focuses on defense by reinforcing the weakest country.
 */
public class BenevolentPlayerBehavior extends ComputerPlayerBehavior {

    /**
     * Deploy reinforcements to the weakest (least troops) owned country.
     */
    @Override
    public void deployment(Player p_player) {
        List<String> ownedCountryIDs = p_player.getOwnedCountries();

        if (ownedCountryIDs.isEmpty()) return;

        Country weakestCountry = null;
        int minTroops = Integer.MAX_VALUE;

        for (String countryID : ownedCountryIDs) {
            Country country = GameDriver.getGameEngine().getCountryManager().getCountries().get(countryID);
            if (country != null && country.getTroops() < minTroops) {
                weakestCountry = country;
                minTroops = country.getTroops();
            }
        }

        if (weakestCountry != null) {
            int reinforcementsLeft = p_player.getReinforcements() - p_player.getNumberOfTroopsOrderedToDeploy();
            if (reinforcementsLeft > 0) {
                GameDriver.getGameEngine().getPhase().deploy(weakestCountry.getID(), reinforcementsLeft);
            }
        }
        System.out.println("[Benevolent] deployment done for: " + p_player.getName());
    }

    /**
     * Does not attack. Benevolent strategy avoids attacking.
     */
    @Override
    public void attackTransfer(Player p_player) {
        System.out.println("[Benevolent] No attack. Benevolent strategy is passive.");
    }

    @Override
    public String toString() {
        return "Benevolent";
    }
}
