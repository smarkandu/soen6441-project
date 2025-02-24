package ca.concordia.soen6441.project.command;

import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.OverallFactory;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.GameContext;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class GameContextImpl implements GameContext {
    private final String d_author;
    private final String d_image;
    private final Boolean d_wrap;
    private final Boolean d_scroll;
    private SortedMap<String, Continent> d_Continents;
    private SortedMap<String, Country> d_Countries;

    public GameContextImpl(SortedMap<String, Continent> p_Continents, SortedMap<String, Country> p_Countries) {
        this.d_Continents = p_Continents;
        this.d_Countries = p_Countries;
        d_author = "SOEN6441";
        d_image = "noimage.bmp";
        d_wrap = false;
        d_scroll = false;
    }

    public GameContextImpl() {                                                    //This ensures the test code does not break when calling new GameContextImpl(); It initializes empty data structures (TreeMap) for d_Continents and d_Countries, ensuring we don’t get null pointer exceptions.
    this.d_Continents = new TreeMap<>();
    this.d_Countries = new TreeMap<>();
     this.d_author = "SOEN6441";
     this.d_image = "noimage.bmp";
     this.d_wrap = false;
     this.d_scroll = false;
}


    @Override
    public List<String> getContinentIDs() {
        return new ArrayList<String>(d_Continents.keySet());
    }

    @Override
    public List<String> getCountryIDs() {
        return new ArrayList<String>(d_Countries.keySet());
    }

    @Override
    public void addContinent(String p_continentID, int p_continentValue) {
        Continent l_continent = OverallFactory.getInstance().CreateContinent(p_continentID, p_continentValue);
        d_Continents.put(l_continent.getID(), l_continent);
        System.out.println("Continent added: " + d_Continents.get(l_continent.getID()));
    }

    @Override
    public void addCountry(String p_CountryID, String p_continentID, List<String> p_neighbors) {
        Country l_country = OverallFactory.getInstance().CreateCountry(p_CountryID, p_continentID, p_neighbors);
        d_Countries.put(p_CountryID, l_country);
        System.out.println("Country added: " + d_Countries.get(l_country.getID()));
    }

    @Override
    public void addNeighbor(String p_CountryID, String p_neighborCountryID) {
        d_Countries.get(p_CountryID).addNeighborID(d_Countries.get(p_neighborCountryID).getID());
        d_Countries.get(p_neighborCountryID).addNeighborID(d_Countries.get(p_CountryID).getID());
        System.out.println("Neighbor added: " + d_Countries.get(p_CountryID));
        System.out.println("Neighbor added: " + d_Countries.get(p_neighborCountryID));
    }

    @Override
    public void removeContinent(String p_continentID) {
        d_Continents.remove(p_continentID);
    }

    @Override
    public void removeCountry(String p_countryID) {
        d_Countries.remove(p_countryID);
    }

    @Override
    public void removeNeighbor(String p_CountryID, String p_neighborCountryID) {
        if (d_Countries.containsKey(p_CountryID) && d_Countries.containsKey(p_neighborCountryID)) {
            d_Countries.get(p_CountryID).removeNeighborID(p_neighborCountryID);
            d_Countries.get(p_neighborCountryID).removeNeighborID(p_CountryID);
        }
    }

    @Override
    public void showMap() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        String l_mapStr = "[Map]\n"
                + "author=" + d_author + "\n"
                + "image=" + d_image + "\n"
                + "wrap=" + d_wrap + "\n"
                + "scroll=" + d_scroll;

        // Format continents to string
        StringBuilder l_sbContinents = new StringBuilder();
        for (Continent l_continent : d_Continents.values()) {
            l_sbContinents.append(l_continent).append("\n");
        }

        // Format countries to string
        StringBuilder l_sbCountries = new StringBuilder();
        for (Country l_country : d_Countries.values()) {
            l_sbCountries.append(l_country).append("\n");
        }

        String l_continentsStr = "[Continents]\n" + l_sbContinents;
        String l_territoriesStr = "[Territories]\n" + l_sbCountries;

        return l_mapStr + "\n\n" + l_continentsStr + "\n\n" + l_territoriesStr;
    }
      @Override
    public int calculateReinforcements(Player p_player) {
        int l_ownedCountries = p_player.getOwnedCountries().size();                     //Retrieves the total number of countries owned by the player. Calls p_player.getOwnedCountries().size() to count how many territories the player owns
        int l_reinforcements = Math.max(3, l_ownedCountries / 3);                       //Each player receives at least 3 armies, or (number of owned countries) / 3 (rounded down)
        return l_reinforcements;                                                        //Returns the calculated reinforcements to be assigned to the player at the start of their turn.
    }

    // @Override
    // public void assignReinforcements(Player p_player, int p_reinforcements) {
    //     p_player.setReinforcements(p_reinforcements);
    // }
     @Override
    public void assignReinforcements(Player p_player, int p_reinforcements) {
        p_player.addReinforcements(p_reinforcements);
    }
    /**
     * Deploys armies to a specified country.
     * **Prevention Mechanism:** Ensures that players **cannot issue an invalid order**.
     * **Fail-Safe Handling:** Throws an error if deployment is impossible.
     */
    @Override
    public void deployArmies(Player p_player, String p_countryID, int p_armies) {
        // **Check if player has enough reinforcements before allowing deployment**
        if (p_armies > p_player.getReinforcements()) {
            throw new IllegalArgumentException("Insufficient reinforcements!");
        }

        // **Prevention Mechanism**: Stop invalid deployments at input level
        if (!p_player.getOwnedCountries().contains(p_countryID)) {
            throw new IllegalArgumentException("Player does not own country: " + p_countryID);
        }

        // **Reduce reinforcement pool** (game state updates)
        p_player.reduceReinforcements(p_armies);

        // **Deploy the armies**
        if (d_Countries.containsKey(p_countryID)) {
            d_Countries.get(p_countryID).addArmies(p_armies);
        } else {
            throw new IllegalArgumentException("Country does not exist: " + p_countryID);
        }

        System.out.println(p_armies + " armies deployed to " + p_countryID + " by " + p_player.getName());
    }

         @Override
    public void assignCountryToPlayer(Player p_player, String p_countryID) {           //Assigns a country to a player. In the test case, countries are added to the game but not linked to any player.Players own countries only in PlayerImpl, but GameContextImpl does not track which player owns which country.The test should ideally use a method like assignCountryToPlayer() in GameContextImpl.
        if (d_Countries.containsKey(p_countryID)) {
            p_player.addCountry(p_countryID);
        } else {
            throw new IllegalArgumentException("Country ID does not exist: " + p_countryID);
        }
    }
   
    }

