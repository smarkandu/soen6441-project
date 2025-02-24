package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Command;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.OverallFactory;
import ca.concordia.soen6441.project.interfaces.GameContext;

import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.ArrayList;

public class GameEngine implements GameContext {
    private Phase d_gamePhase;
    private SortedMap<String, Continent> d_Continents;
    private SortedMap<String, Country> d_Countries;
    private final String d_author;
    private final String d_image;
    private final Boolean d_wrap;
    private final Boolean d_scroll;

    public GameEngine() {
        d_Continents = new TreeMap<String, Continent>();
        d_Countries = new TreeMap<String, Country>();
        d_author = "SOEN6441";
        d_image = "noimage.bmp";
        d_wrap = false;
        d_scroll = false;
    }

    @Override
    public List<String> getContinentIDs() {
        return new ArrayList<String>(d_Continents.keySet());
    }

    @Override
    public List<String> getCountryIDs() {
        return new ArrayList<String>(d_Countries.keySet());
    }

    public void setPhase(Phase p_phase) {
        d_gamePhase = p_phase;
    }

    public void start() {
        // Can change the state of the Context (GameEngine) object, e.g.
//        setPhase(new PreLoad(this));
          setPhase(new PostLoad(this));
//        setPhase(new Startup(this));
//        setPhase(new IssueOrder(this));
//        setPhase(new OrderExecution(this));

        boolean l_continuePlaying = true;
        Scanner l_scanner = new Scanner(System.in);

        while (l_continuePlaying) {
            System.out.print(">");
            String[] l_args = l_scanner.nextLine().split(" ");
            String l_action = l_args[0].toLowerCase();
            String l_operation = l_args.length > 1 ? l_args[1].toLowerCase() : null;
            Command l_commandToRun = null;

            switch (l_action) {
                case "editcontinent":
                    if ("-add".equals(l_operation) && l_args.length == 4) {
                        String l_continentID = l_args[2].replace("\"", "");
                        int l_continentValue = Integer.parseInt(l_args[3]);
                        d_gamePhase.editContinentAdd(l_continentID, l_continentValue);
                    } else if ("-remove".equals(l_operation) && l_args.length == 3) {
                        String l_continentID = l_args[2].replace("\"", "");
                        d_gamePhase.editContinentRemove(l_continentID);
                    } else {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "editcountry":
                    if ("-add".equals(l_operation) && l_args.length == 4) {
                        String l_countryID = l_args[2].replace("\"", "");
                        String l_continentID = l_args[3].replace("\"", "");
                        d_gamePhase.editCountryAdd(l_countryID, l_continentID);
                    } else if ("-remove".equals(l_operation) && l_args.length == 3) {
                        String l_countryID = l_args[2].replace("\"", "");
                        d_gamePhase.editCountryRemove(l_countryID);
                    } else {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "editneighbor":
                    if ("-add".equals(l_operation) && l_args.length == 4) {
                        String l_countryID = l_args[2].replace("\"", "");
                        String l_neighborCountryID = l_args[3].replace("\"", "");
                        d_gamePhase.editNeighborAdd(l_countryID, l_neighborCountryID);
                    } else if ("-remove".equals(l_operation) && l_args.length == 4) {
                        String l_countryID = l_args[2].replace("\"", "");
                        String l_neighborCountryID = l_args[3].replace("\"", "");
                        d_gamePhase.editNeighborRemove(l_countryID, l_neighborCountryID);
                    } else {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "showmap":
                    d_gamePhase.showMap();
                    break;
                case "savemap":
                    d_gamePhase.saveMap();
                    break;
                case "assigncountries":
                    d_gamePhase.assignCountries();
                    break;
                case "deploy":
                    // TODO
                    break;
                case "gameplayer":
                    d_gamePhase.setPlayers();
                    break;
                case "loadmap":
                    d_gamePhase.loadMap();
                    break;
                case "exit":
                    d_gamePhase.endGame();
                    l_continuePlaying = false;
                    break;
                default:
                    System.out.println("Command not recognized");
                    break;
            }
        }
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
        for (String l_neighborID : p_neighbors) {
        if (d_Countries.containsKey(l_neighborID)) {
            d_Countries.get(l_neighborID).addNeighborID(p_CountryID);
            d_Countries.get(p_CountryID).addNeighborID(l_neighborID);
        }
    }
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
        System.out.println("==== Countries & Neighbors ====");
    for (String l_countryID : d_Countries.keySet()) {
        System.out.println(l_countryID + " -> " + d_Countries.get(l_countryID).getNeighborIDs());
    }
    System.out.println("==============================");
    }

    
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
    public void setContinents(SortedMap<String, Continent> p_continents) {
    this.d_Continents = p_continents;
}

public void setCountries(SortedMap<String, Country> p_countries) {
    this.d_Countries = p_countries;
}


}