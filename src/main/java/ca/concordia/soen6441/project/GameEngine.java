package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Command;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.GameContext;

import java.io.File;
import java.util.*;

public class GameEngine implements GameContext {
    private Phase d_gamePhase;
    private static SortedMap<String, Continent> d_Continents; //CHANGED TO MAIN STRUCTURE - added Static
    private static SortedMap<String, Country> d_Countries; //CHANGED TO MAIN STRUCTURE - added Static
    private static SortedMap<String, String> d_files;
    private static String d_mapFilePath, d_directory, d_fileName;

    static {
        d_Continents = new TreeMap<String, Continent>();
        d_Countries = new TreeMap<String, Country>();
        d_files = new TreeMap<String, String>();
        d_mapFilePath = "";
        d_directory = "";
        d_fileName = "";
    }

/*  //CHANGES TO MAIN STRUCTURE - removed this block
    public GameEngine() {
        d_Continents = new TreeMap<String, Continent>();
        d_Countries = new TreeMap<String, Country>();
    }
*/
    // Add a continent to the game for file load - Shubo
    public void addContinent(Continent p_continent) {
        if (p_continent == null) {
            throw new IllegalArgumentException("Continent cannot be null");
        }
        d_Continents.put(p_continent.getID(), p_continent);
    }
    // Add a country to the game and link it to its continent
    public void addCountry(CountryImpl p_country) {
        if (p_country == null) {
            throw new IllegalArgumentException("Country cannot be null");
        }
        d_Countries.put(p_country.getID(),p_country);
        Continent continent = d_Continents.get(p_country.getD_ContinentID());
        if (continent != null) {
            continent.addCountry(p_country);
        }
    }

    public void setPhase(Phase p_phase) {
        d_gamePhase = p_phase;
    }

    public void start() {
        // Can change the state of the Context (GameEngine) object, e.g.
        setPhase(new PreLoad(this));
//          setPhase(new PostLoad(this));
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
                    d_gamePhase.saveMap(l_args[1]);
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
                    d_gamePhase.loadMap(l_args[1]);
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
    public void addContinent(int p_numericID, String p_continentID, int p_continentValue, String p_colour) {
        Continent l_continent = OverallFactory.getInstance().CreateContinent(p_numericID, p_continentID, p_continentValue, p_colour);
        d_Continents.put(l_continent.getID(), l_continent);
        System.out.println("Continent added: " + d_Continents.get(l_continent.getID()));
    }

    public void addContinent(String p_continentID, int p_continentValue) {
        Continent l_continent = OverallFactory.getInstance().CreateContinent(p_continentID, p_continentValue);
        d_Continents.put(l_continent.getID(), l_continent);
        System.out.println("Continent added: " + d_Continents.get(l_continent.getID()));
    }

    @Override
    public void addCountry(int p_numericID, String p_CountryID, String p_continentID, int p_xCoord, int p_yCoord) {
        Country l_country = OverallFactory.getInstance().CreateCountry(p_numericID, p_CountryID, p_continentID, p_xCoord, p_yCoord);
        d_Countries.put(p_CountryID, l_country);
        System.out.println("Country added: " + d_Countries.get(l_country.getID()));
    }

    public void addCountry(String p_CountryID, String p_continentID) {
        Country l_country = OverallFactory.getInstance().CreateCountry(p_CountryID, p_continentID);
        d_Countries.put(p_CountryID, l_country);
        System.out.println("Country added: " + d_Countries.get(l_country.getID()));
    }

    public void addNeighbor(String p_CountryID, String p_neighborCountryID) {
        d_Countries.get(p_CountryID).addNeighbor(d_Countries.get(p_neighborCountryID));
        d_Countries.get(p_neighborCountryID).addNeighbor(d_Countries.get(p_CountryID));
        System.out.println("Neighbor added: " + d_Countries.get(p_CountryID));
        System.out.println("Neighbor added: " + d_Countries.get(p_neighborCountryID));
    }

    @Override
    public Continent getContinentByNumericID(int p_numericIDOfContinent) {

        for (String l_key: d_Continents.keySet())
        {
            if (d_Continents.get(l_key).getNumericID() == p_numericIDOfContinent)
            {
                return d_Continents.get(l_key); // found
            }
        }

        return null; // not found
    }

    @Override
    public Country getCountryByNumericID(int p_numericIDOfCountry) {

        for (String l_key: d_Countries.keySet())
        {
            if (d_Countries.get(l_key).getNumericID() == p_numericIDOfCountry)
            {
                return d_Countries.get(l_key); // found
            }
        }

        return null; // not found
    }

    public void removeContinent(String p_continentID) {
        d_Continents.remove(p_continentID);
    }

    public void removeCountry(String p_countryID) {
        d_Countries.remove(p_countryID);
    }

    public void removeNeighbor(String p_CountryID, String p_neighborCountryID) {
        d_Countries.get(p_CountryID).removeNeighbor(p_neighborCountryID);
        d_Countries.get(p_neighborCountryID).removeNeighbor(p_CountryID);
    }
    // Record file path, directory, and fileName of the .map file
    public void addMapFilePath(String mapFilePathx){
        d_mapFilePath = mapFilePathx;
        // Create a File object
        File file = new File(d_mapFilePath);
        // Get the parent directory and file name
        d_directory = file.getParent() + File.separator;
        d_fileName = file.getName();
    }
    // Add files as per the .map file
    public void addFile(String p_fileType, String p_fileName){
        if (p_fileType == null || p_fileName == null) {
            throw new IllegalArgumentException("File type or file name cannot be null");
        }
        d_files.put(p_fileType, p_fileName);
    }


    // Remove all data before reloading the map
    public void clearMapData(){
        d_Continents.clear();
        d_Countries.clear();
        d_files.clear();
        d_mapFilePath = "";
        d_directory = "";
        d_fileName = "";
    }

    public void showMap() {
        System.out.println(this);
    }

    @Override
    public String toString() {
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

        return "\n\n" + l_continentsStr + "\n\n" + l_territoriesStr;
    }
}