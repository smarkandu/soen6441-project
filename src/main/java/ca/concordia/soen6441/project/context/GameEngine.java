package ca.concordia.soen6441.project.context;

import ca.concordia.soen6441.project.OverallFactory;
import ca.concordia.soen6441.project.gameplay.PlayerImpl;
import ca.concordia.soen6441.project.interfaces.*;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.map.*;
import ca.concordia.soen6441.project.phases.Phase;
import ca.concordia.soen6441.project.phases.PreLoad;

import java.io.FileNotFoundException;
import java.util.stream.Collectors;


import java.util.*;

/**
 * GameEngine class represents the main controller of the game.
 * It manages the game state, executes commands, and interacts with other components.
 */
public class GameEngine implements GameContext, MapComponent {
    private Phase d_gamePhase;
    private SortedMap<String, Player> d_players;
    private ValidateMapImpl d_validateMapImpl;
    private ContinentManager d_ContinentManager;
    private CountryManager d_CountryManager;
    private NeighborManager d_NeighborManager;

    /**
     * Constructs a new GameEngine instance and initializes game data structures.
     */
    public GameEngine() {
        d_players = new TreeMap<String, Player>();
        d_ContinentManager = new ContinentManager();
        d_CountryManager = new CountryManager(this);
        d_NeighborManager = new NeighborManager(this);
        d_validateMapImpl = new ValidateMapImpl(d_CountryManager.getCountries(), d_ContinentManager.getContinents());
    }

    /**
     * Sets the current phase of the game.
     *
     * @param p_phase The game phase to set.
     */
    public void setPhase(Phase p_phase) {
        d_gamePhase = p_phase;
    }

    /**
     * Starts the game execution loop, handling player commands.
     */
    public void start() {
        // Can change the state of the Context (GameEngine) object, e.g.
        setPhase(new PreLoad(this));
        boolean l_continuePlaying = true;
        Scanner l_scanner = new Scanner(System.in);

        while (l_continuePlaying) {
            System.out.print(d_gamePhase.getPhaseName() + ">");
            String[] l_args = l_scanner.nextLine().split(" ");
            String l_action = l_args[0].toLowerCase();
            String l_operation = l_args.length > 1 ? l_args[1].toLowerCase() : null;

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
                    String l_countryID = l_args[1].replace("\"", "");
                    int l_toDeploy = Integer.parseInt(l_args[2]);
                    d_gamePhase.deploy(l_countryID, l_toDeploy);
                    break;
                case "gameplayer":
                    // TODO (Marc) You'll need to look for the add/remove flag
                    // (similar to commands above)
                    // Also we'll need to change setPlayers to something else
                    // (See notes in "Phase")
                    String l_playername = l_args[2];
                    if ("-add".equals(l_operation) && l_args.length == 3) {
                        d_gamePhase.gamePlayerAdd(l_playername);
                    }
                    if ("-remove".equals(l_operation) && l_args.length == 3) {
                        d_gamePhase.gamePlayerRemove(l_playername);
                    }
                    break;
                case "loadmap":
                    d_gamePhase.loadMap(l_args[1]);
                    break;
                case "validatemap":
                    if (l_args.length == 1) {
                        d_gamePhase.validateMap();
                    }
                    break;
                case "next":
                    d_gamePhase.next();
                    break;
                case "exit":
                    d_gamePhase.endGame();
                    l_continuePlaying = false;
                    break;
                case "":
                    // Do Nothing
                    break;
                default:
                    System.out.println("Command not recognized");
                    break;
            }
        }
    }




    /**
     * Adds a new player to the game.
     *
     * @param p_playername The name of the player to be added.
     */
    public void addPlayer(String p_playername) {
        d_players.put(p_playername, new PlayerImpl(p_playername, new ArrayList<>(), new ArrayList<>()));
        System.out.println("Player added: " + d_players.get(p_playername).getName());
    }

    /**
     * Removes a player from the game.
     *
     * @param p_player The name of the player to be removed.
     */
    public void removePlayer(String p_player) {
        d_players.remove(p_player);
    }

    /**
     * Prints a list of the continents (in alphabetical order) with their countries
     * underneath each one.
     *
     * @param p_isDetailed If true, prints country owners, army counts, and neighbors.
     *                     If false, prints only continent and country names.
     */
    public void showMap(boolean p_isDetailed) {

        // Step 1: Retrieve all continents and sort them alphabetically
        List<Continent> l_sortedContinents = new ArrayList<>(d_ContinentManager.getContinents().values()); // Convert to list
        l_sortedContinents.sort(Comparator.comparing(Continent::getID)); // Sort by continent ID

        // Iterate through each sorted continent
        for (Continent l_continent : l_sortedContinents) {
            // Print the continent's name and its bonus value
            System.out.println(l_continent.getID() + " (Bonus: " + l_continent.getValue() + ")");

            // Step 2: Retrieve the list of countries belonging to this continent
            List<Country> l_countries = d_CountryManager.getCountriesOfContinent(l_continent.getID());

            // Sort countries alphabetically based on their ID
            l_countries.sort(Comparator.comparing(Country::getID));

            // Iterate through each country in the continent
            for (Country l_country : l_countries) {
                // Start constructing the country info string
                StringBuilder l_countryInfo = new StringBuilder("  - " + l_country.getID());

                // If detailed view is enabled, append owner, army count, and neighboring countries
                if (p_isDetailed) {
                    Player l_owner = l_country.getOwner(); // Get the country owner
                    int l_armyCount = l_country.getTroops(); // Get the number of troops in the country
                    List<String> l_neighbors = l_country.getNeighborIDs(); // Get list of neighboring country IDs

                    // Append detailed information about the country
                    l_countryInfo.append(" | Owner: ").append(l_owner != null ? l_owner.getName() : "Neutral") // Owner's name or "Neutral"
                            .append(" | Armies: ").append(l_armyCount) // Number of armies stationed
                            .append(" | Neighbors: ").append(String.join(", ", l_neighbors)); // List of neighboring countries
                }

                // Print the formatted country information
                System.out.println(l_countryInfo);
            }
        }
    }

    public Map<String, Player> getPlayers() {
        return d_players;
    }

    /**
     * Returns a string representation of the current game state.
     *
     * @return A formatted string displaying continents and countries.
     */
    @Override
    public String toString() {
        // Format continents to string
        StringBuilder l_sbContinents = new StringBuilder();
        for (Continent l_continent : d_ContinentManager.getContinents().values()) {
            l_sbContinents.append(l_continent).append("\n");
        }

        // Format countries to string
        StringBuilder l_sbCountries = new StringBuilder();
        for (Country l_country : d_CountryManager.getCountries().values()) {
            l_sbCountries.append(l_country).append("\n");
        }

        String l_continentsStr = "[Continents]\n" + l_sbContinents;
        String l_territoriesStr = "[Territories]\n" + l_sbCountries;

        return "\n\n" + l_continentsStr + "\n\n" + l_territoriesStr;
    }

    /**
     * Converts the game map into a formatted string representation.
     *
     * @return A string representation of the map in a structured format.
     */
    @Override
    public String toMapString() {
        // Builds the map file format string

        // Create sections
        StringBuilder l_mapBuilder = new StringBuilder();

        // Add [continents] section
        l_mapBuilder.append("[continents]\n");
        d_ContinentManager.getContinents().values().stream()
                .sorted(Comparator.comparingInt(Continent::getNumericID)) // Sort by numeric ID
                .forEach(l_continent -> l_mapBuilder.append(l_continent.toMapString()).append("\n"));

        // Add [countries] section
        l_mapBuilder.append("\n[countries]\n");
        d_CountryManager.getCountries().values().stream()
                .sorted(Comparator.comparingInt(Country::getNumericID))
                .forEach(l_country -> l_mapBuilder.append(l_country.toMapString()).append("\n"));

        // Add [borders] section
        l_mapBuilder.append("\n[borders]\n");
        d_CountryManager.getCountries().values().stream()
                .sorted(Comparator.comparingInt(Country::getNumericID))
                .forEach(l_country -> {
                    String l_borders = d_CountryManager.getCountries().get(l_country.getID()).getNeighborIDs().stream()
                            .map(neighborID -> String.valueOf(d_CountryManager.getCountries().get(neighborID).getNumericID())) // Convert to numeric ID
                            .collect(Collectors.joining(" "));
                    l_mapBuilder.append(l_country.getNumericID())
                            .append(l_borders.isEmpty() ? "" : " " + l_borders)
                            .append("\n");
                });
        return l_mapBuilder.toString();
    }

    /**
     * Retrieves a player based on their index in the player list.
     *
     * @param p_index The index of the player.
     * @return The player at the specified index.
     */
    public Player getPlayer(int p_index) {
        return new ArrayList<Player>(d_players.values()).get(p_index);
    }

    /**
     * Loads a map from a file and validates its contents.
     *
     * @param p_filename The filename of the map to be loaded.
     * @throws InvalidMapFileException If the map file is invalid.
     * @throws FileNotFoundException   If the file is not found.
     */
    public void loadMap(String p_filename) throws InvalidMapFileException, FileNotFoundException {
        // Empty out contents of map in GameEngine
        resetMap();

        // Read Map into Game Engine
        MapFileReader l_mapFileReader = new MapFileReader();
        l_mapFileReader.readMapFile(p_filename, this);

        // Validate Map
        if (isMapValid()) {
            System.out.println("Map " + p_filename + " loaded");
        } else {
            throw new InvalidMapFileException();
        }
    }

    /**
     * Resets the game map by clearing all countries and continents.
     */
    public void resetMap() {
        d_CountryManager.clear();
        d_ContinentManager.clear();
        CountryImpl.resetCounter();
        ContinentImpl.resetCounter();
    }

    public boolean isMapValid() {
        // TODO #5
        // remove hardcoded "true" value and check the conditions for validity
        // For any issues, use a print to specify what you found wrong
        return d_validateMapImpl.isMapValid();
    }

    public ContinentManager getContinentManager() {
        return d_ContinentManager;
    }

    public CountryManager getCountryManager() {
        return d_CountryManager;
    }

    public NeighborManager getNeighborManager() {
        return d_NeighborManager;
    }

    /**
     * Checks if the map is empty.
     *
     * @return true if there are no continents or countries in the map.
     */
    public boolean isMapEmpty() {
        return d_ContinentManager.getContinents().isEmpty() && d_CountryManager.getCountries().isEmpty();
    }
}