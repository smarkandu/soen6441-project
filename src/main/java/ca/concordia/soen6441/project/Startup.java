package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;

public class Startup extends Play {
    public Startup(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    public void loadMap(String p_filename)
    {
        // TODO
    }

    public void assignCountries()
    {
        SortedMap<String, Country> l_countries = d_gameEngine.getCountries();
        SortedMap<String, Player> l_players = d_gameEngine.getPlayers();

        if(l_countries.size() < l_players.size()) {
            return;
        }

        List<Country> l_listOfCountries = new ArrayList<Country>();
        l_listOfCountries.addAll(l_countries.values());


        Random l_random = new Random();
        for(Player l_player: l_players.values()) {
            int l_numberOfCountries = l_listOfCountries.size()-1;
            int l_index = l_random.nextInt(l_numberOfCountries);
            l_players.get(l_player.getName()).assignCountry(l_listOfCountries.get(l_index).getID());
            l_listOfCountries.remove(l_index);
        }
    }

    public void gamePlayerAdd(String p_playerName)
    {
        // TODO: (Marc) Add implementation
        d_gameEngine.addPlayer(p_playerName);

    }

    public void gamePlayerRemove(String p_playerName)
    {
        // TODO: (Marc) Add implementation
        d_gameEngine.removePlayer(p_playerName);
    }



    public void next() {
        // TODO
    }
}
