package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Command;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;

import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class GameEngine {
    private Phase gamePhase;
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

    public void setPhase(Phase p_phase) {
        gamePhase = p_phase;
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
                        gamePhase.editContinentAdd(l_continentID, l_continentValue);
                    } else if ("-remove".equals(l_operation) && l_args.length == 3) {
                        String l_continentID = l_args[2].replace("\"", "");
                        gamePhase.editContinentRemove(l_continentID);
                    } else {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "editcountry":
                    if ("-add".equals(l_operation) && l_args.length == 4) {
                        String l_countryID = l_args[2].replace("\"", "");
                        String l_continentID = l_args[3].replace("\"", "");
                        gamePhase.editCountryAdd(l_countryID, l_continentID);
                    } else if ("-remove".equals(l_operation) && l_args.length == 3) {
                        String l_countryID = l_args[2].replace("\"", "");
                        gamePhase.editCountryRemove(l_countryID);
                    } else {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "editneighbor":
                    if ("-add".equals(l_operation) && l_args.length == 4) {
                        String l_countryID = l_args[2].replace("\"", "");
                        String l_neighborCountryID = l_args[3].replace("\"", "");
                        gamePhase.editNeighborAdd(l_countryID, l_neighborCountryID);
                    } else if ("-remove".equals(l_operation) && l_args.length == 4) {
                        String l_countryID = l_args[2].replace("\"", "");
                        String l_neighborCountryID = l_args[3].replace("\"", "");
                        gamePhase.editNeighborRemove(l_countryID, l_neighborCountryID);
                    } else {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "showmap":
                    gamePhase.showMap();
                    break;
                case "savemap":
                    gamePhase.saveMap();
                    break;
                case "assigncountries":
                    gamePhase.assignCountries();
                    break;
                case "deploy":
                    // TODO
                    break;
                case "gameplayer":
                    gamePhase.setPlayers();
                    break;
                case "loadmap":
                    gamePhase.loadMap();
                    break;
                case "exit":
                    gamePhase.endGame();
                    l_continuePlaying = false;
                    break;
                default:
                    System.out.println("Command not recognized");
                    break;
            }
        }
    }

    public void addContinent(String p_continentID, int p_continentValue) {
        Continent l_continent = OverallFactory.getInstance().CreateContinent(p_continentID, p_continentValue);
        d_Continents.put(l_continent.getID(), l_continent);
        System.out.println("Continent added: " + d_Continents.get(l_continent.getID()));
    }

    public void addCountry(String p_CountryID, String p_continentID) {
        Country l_country = OverallFactory.getInstance().CreateCountry(p_CountryID, p_continentID);
        d_Countries.put(p_CountryID, l_country);
        System.out.println("Country added: " + d_Countries.get(l_country.getID()));
    }

    public void addNeighbor(String p_CountryID, String p_neighborCountryID) {
        d_Countries.get(p_CountryID).addNeighborID(d_Countries.get(p_neighborCountryID).getID());
        d_Countries.get(p_neighborCountryID).addNeighborID(d_Countries.get(p_CountryID).getID());
        System.out.println("Neighbor added: " + d_Countries.get(p_CountryID));
        System.out.println("Neighbor added: " + d_Countries.get(p_neighborCountryID));
    }

    public void removeContinent(String p_continentID) {
        d_Continents.remove(p_continentID);
    }

    public void removeCountry(String p_countryID) {
        d_Countries.remove(p_countryID);
    }

    public void removeNeighbor(String p_CountryID, String p_neighborCountryID) {
        d_Countries.get(p_CountryID).removeNeighborID(p_neighborCountryID);
        d_Countries.get(p_neighborCountryID).removeNeighborID(p_CountryID);
    }

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
}