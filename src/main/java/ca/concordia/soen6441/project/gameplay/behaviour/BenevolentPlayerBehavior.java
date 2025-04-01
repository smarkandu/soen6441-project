package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.interfaces.gameplay.behavior.PlayerBehavior;
import ca.concordia.soen6441.project.gameplay.orders.Deploy;

import java.util.Map;

/**
 * Implements Benevolent player strategy — reinforces weakest country.
 */
public class BenevolentPlayerBehavior implements PlayerBehavior {

    private final GameContext d_gameContext = null;

    public BenevolentPlayerBehavior() {
        
    }

    @Override
    public void issue_order(Player p_player, Order p_order) {
        int l_reinforcements = p_player.getReinforcements();

        if (l_reinforcements <= 0 || p_player.getOwnedCountries().isEmpty()) {
            return;
        }

        Country l_weakest = null;
        int l_minTroops = Integer.MAX_VALUE;

        Map<String, Country> allCountries = d_gameContext.getCountryManager().getCountries();

        for (String countryID : p_player.getOwnedCountries()) {
            Country country = allCountries.get(countryID);
            if (country != null && country.getTroops() < l_minTroops) {
                l_weakest = country;
                l_minTroops = country.getTroops();
            }
        }

        if (l_weakest != null) {
            p_player.issue_order(new Deploy(p_player, l_weakest, l_reinforcements));
        }
    }
}
