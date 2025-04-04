package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.util.*;

/**
 * Class which implements the Strategy design pattern for the Cheater player behavior
 */
public class CheaterPlayerBehavior extends ComputerPlayerBehavior {
    /**
     * {@inheritDoc}
     */
    @Override
    public void deployment(Player p_player) {
        List<String> l_ownedCountries = p_player.getOwnedCountries();
        Random l_random = new Random();
        GameDriver.getGameEngine().getPhase().deploy(l_ownedCountries.get(l_random.nextInt(l_ownedCountries.size())), p_player.getNumberOfTroopsOrderedToDeploy());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attackTransfer(Player p_player) {

        if(p_player.getNumberOfTroopsOrderedToDeploy() > 0)
        {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: You still have " + p_player.getNumberOfTroopsOrderedToDeploy() + " left to deploy!", true);
            return;
        }

        // get all countries owned by cheater
        List<String> l_ownedCountries = p_player.getOwnedCountries();

        // get all neighbor countries
        Set<String> l_neighborCountries = new HashSet<>();
        for (String l_ownedCountry : l_ownedCountries) {
            Country l_adjacentCountry = GameDriver.getGameEngine().getCountryManager().getCountries().get(l_ownedCountry);
            l_neighborCountries.addAll(l_adjacentCountry.getNeighborIDs());
        }

        // remove countries own by the cheater
        Iterator<String> l_iterator = l_neighborCountries.iterator();
        while (l_iterator.hasNext()) {
            Country l_country = GameDriver.getGameEngine().getCountryManager().getCountries().get(l_iterator.next());
            if (l_country.getOwner().getName().equals(p_player.getName())) {
                l_iterator.remove();
            }
        }

        if (!l_neighborCountries.isEmpty()) {
            // conquer all neighbor countries and double army
            for (String l_neighborCountry : l_neighborCountries) {
                Country l_neighborTerritoryConquered = GameDriver.getGameEngine().getCountryManager().getCountries().get(l_neighborCountry);
                l_neighborTerritoryConquered.setOwner(p_player);
                l_neighborTerritoryConquered.setTroops(l_neighborTerritoryConquered.getTroops() * 2);
            }
        } else {
            // TODO
        }
    }

    /**
     * String representing the object
     */
    @Override
    public String toString() {
        return "Cheater";
    }
}
