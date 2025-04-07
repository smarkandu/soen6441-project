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

        if(l_ownedCountries == null) {
            LogEntryBuffer.getInstance().appendToBuffer(
                    "[Cheater] can't deploy because his owned Countries list doesn't exist" +
                            GameDriver.getGameEngine().getPhase().getPhaseName(), true);
        }

        assert l_ownedCountries != null;
        if(l_ownedCountries.isEmpty()) {
            LogEntryBuffer.getInstance().appendToBuffer(
                    "[Cheater] can't deploy because his owned Countries list is empty" +
                            GameDriver.getGameEngine().getPhase().getPhaseName(), true);
        }

        // Get how many troops the player can still deploy
        int l_remainingTroops = p_player.getReinforcements() - p_player.getNumberOfTroopsOrderedToDeploy();

        Random l_random = new Random();
        GameDriver.getGameEngine().getPhase().deploy(l_ownedCountries.get(l_random.nextInt(l_ownedCountries.size())), l_remainingTroops);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attackTransfer(Player p_player) {
        final int l_ARMY_MULTIPLICATOR = 2;

        // Let's look if one of the adjacent countries is an enemy territory.
        // get all countries owned by cheater
        List<String> l_ownedCountries = p_player.getOwnedCountries();

        // get all neighbor countries
        Set<String> l_neighborCountries = new HashSet<>();
        for (String l_ownedCountry : l_ownedCountries) {
            Country l_adjacentCountry = GameDriver.getGameEngine().getCountryManager().getCountries().get(l_ownedCountry);
            l_neighborCountries.addAll(l_adjacentCountry.getNeighborIDs());
        }

        // remove countries own by the cheater and to neutral player
        Iterator<String> l_iterator = l_neighborCountries.iterator();
        while (l_iterator.hasNext()) {
            Country l_country = GameDriver.getGameEngine().getCountryManager().getCountries().get(l_iterator.next());
            String l_ownerOfCountry = l_country.getOwner().getName();
            if (l_ownerOfCountry.equals(p_player.getName()) || l_ownerOfCountry.equals("Neutral")) {
                l_iterator.remove();
            }
        }


        if (!l_neighborCountries.isEmpty()) {
            // conquer all neighbor countries and double army
            LogEntryBuffer.getInstance().appendToBuffer(
                    "[Cheater] is ready to conquer!" +
                            GameDriver.getGameEngine().getPhase().getPhaseName(), true);

            for (String l_neighborCountry : l_neighborCountries) {
                Country l_neighborTerritoryConquered = GameDriver.getGameEngine().getCountryManager().getCountries().get(l_neighborCountry);
                l_neighborTerritoryConquered.setOwner(p_player);
                l_neighborTerritoryConquered.setTroops(l_neighborTerritoryConquered.getTroops() * l_ARMY_MULTIPLICATOR);
                LogEntryBuffer.getInstance().appendToBuffer(
                        "[Cheater] has conquer " + l_neighborTerritoryConquered.getID() + ", and now there is " +
                                l_neighborTerritoryConquered.getTroops() + " " +
                                GameDriver.getGameEngine().getPhase().getPhaseName(), true);
            }
        } else {
            // if we don't have any neighbor enemy territory, let's advance.
            // get the country with the highest troop and advance to adjacent neutral territory

            Country l_countryWithHighestTroop = null;
            for (String l_ownedCountry : l_ownedCountries) {
                Country l_countryInExamination = GameDriver.getGameEngine().getCountryManager().getCountries().get(l_ownedCountry);
                if(l_countryWithHighestTroop == null) {
                    l_countryWithHighestTroop = l_countryInExamination;
                } else {
                    if(l_countryInExamination.getTroops() > l_countryWithHighestTroop.getTroops()) {
                        l_countryWithHighestTroop = l_countryInExamination;
                    }
                }
            }

            // find all neighbors not owned by cheater
            List<String> l_neighborCountry = l_countryWithHighestTroop.getNeighborIDs();


            // advance to one of them
            Collections.shuffle(l_neighborCountry);
            GameDriver.getGameEngine().getPhase().advance(l_countryWithHighestTroop.getID(),
                    l_neighborCountry.get(0), l_countryWithHighestTroop.getTroops());

            LogEntryBuffer.getInstance().appendToBuffer(
                    "[Cheater] has advanced to " + l_neighborCountry.get(0) + " " +
                            GameDriver.getGameEngine().getPhase().getPhaseName(), true);
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
