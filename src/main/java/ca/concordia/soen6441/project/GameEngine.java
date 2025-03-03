package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.*;

import java.util.stream.Collectors;


import java.util.*;

public class GameEngine implements GameContext, MapComponent {
    private Phase d_gamePhase;
    private SortedMap<String, Continent> d_Continents;
    private SortedMap<String, Country> d_Countries;
    private SortedMap<String, Player> d_players;

    public GameEngine() {
        d_Continents = new TreeMap<String, Continent>();
        d_Countries = new TreeMap<String, Country>();
        d_players = new TreeMap<String, Player>();
    }

    public void setPhase(Phase p_phase) {
        d_gamePhase = p_phase;
    }

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
                    String l_playername = l_args[2].toLowerCase();
                    if ("-add".equals(l_operation) && l_args.length == 3) {
                        d_gamePhase.gamePlayerAdd(l_playername);
                    }
                    if("-remove".equals(l_operation) && l_args.length == 3) {
                        d_gamePhase.gamePlayerRemove(l_playername);
                    }
                    break;
                case "loadmap":
                    d_gamePhase.loadMap(l_args[1]);
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
        Country l_country = OverallFactory.getInstance().CreateCountry(p_numericID, p_CountryID, d_Continents.get(p_continentID), p_xCoord, p_yCoord);
        d_Countries.put(p_CountryID, l_country);
        System.out.println("Country added: " + d_Countries.get(l_country.getID()));
    }

    public void addCountry(String p_CountryID, String p_continentID) {
        Country l_country = OverallFactory.getInstance().CreateCountry(p_CountryID, d_Continents.get(p_continentID));
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

    public void addPlayer(String p_playername) {
        d_players.put(p_playername, new PlayerImpl(p_playername, new ArrayList<>(), new ArrayList<>()));
        System.out.println("Player added: " + d_players.get(p_playername).getName());
    }

    public void removePlayer(String p_player) {
        d_players.remove(p_player);
    }

    @Override
public Continent getContinent(String p_continentID) {
    return d_Continents.get(p_continentID); // Retrieve the continent from the map
}

    @Override
public List<Country> getCountriesOfContinent(String p_continentID) {
    List<Country> l_countries = new ArrayList<>();
    for (Country l_country : d_Countries.values()) {
        if (l_country.getContinent().getID().equals(p_continentID)) {
            l_countries.add(l_country);
        }
    }
    return l_countries;
}

  /**
 * Prints a list of the continents (in alphabetical order) with their countries
 * underneath each one.
 *
 * @param p_isDetailed If true, prints country owners, army counts, and neighbors.
 *                   If false, prints only continent and country names.
 */
public void showMap(boolean p_isDetailed) {

    // Step 1: Retrieve all continents and sort them alphabetically
    List<Continent> l_sortedContinents = new ArrayList<>(d_Continents.values()); // Convert to list
    l_sortedContinents.sort(Comparator.comparing(Continent::getID)); // Sort by continent ID

    // Iterate through each sorted continent
    for (Continent l_continent : l_sortedContinents) {
        // Print the continent's name and its bonus value
        System.out.println(l_continent.getID() + " (Bonus: " + l_continent.getValue() + ")");

        // Step 2: Retrieve the list of countries belonging to this continent
        List<Country> l_countries = getCountriesOfContinent(l_continent.getID());

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

    public Map<String, Country> getCountries() {
        return d_Countries;
    }

    public Map<String, Player> getPlayers() {
        return d_players;
    }

    public Map<String, Continent> getContinents() {
        return d_Continents;
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

    @Override
    public String toMapString() {
    // Builds the map file format string

    // Create sections
    StringBuilder l_mapBuilder = new StringBuilder();
    
    // Add [continents] section
    l_mapBuilder.append("[continents]\n");
    d_Continents.values().stream()
    .sorted(Comparator.comparingInt(Continent::getNumericID)) // Sort by numeric ID
    .forEach(l_continent -> l_mapBuilder.append(l_continent.toMapString()).append("\n"));

    // Add [countries] section
    l_mapBuilder.append("\n[countries]\n");
   d_Countries.values().stream()
                .sorted(Comparator.comparingInt(Country::getNumericID))
                .forEach(l_country -> l_mapBuilder.append(l_country.toMapString()).append("\n"));

    // Add [borders] section
   l_mapBuilder.append("\n[borders]\n");
    d_Countries.values().stream()
            .sorted(Comparator.comparingInt(Country::getNumericID))
            .forEach(l_country -> {
                String l_borders = d_Countries.get(l_country.getID()).getNeighborIDs().stream()
                        .map(neighborID -> String.valueOf(d_Countries.get(neighborID).getNumericID())) // Convert to numeric ID
                        .collect(Collectors.joining(" "));
                l_mapBuilder.append(l_country.getNumericID())
                        .append(l_borders.isEmpty() ? "" : " " + l_borders)
                        .append("\n");
            });
    return l_mapBuilder.toString();
     }

     public Player getPlayer(int p_index)
     {
         return new ArrayList<Player>(d_players.values()).get(p_index);
     }

     public void loadMap(String p_filename) throws InvalidMapFileException {
         // Empty out contents of map in GameEngine
         resetMap();

         // Read Map into Game Engine
         MapFileReader l_mapFileReader = new MapFileReader();
         l_mapFileReader.readMapFile(p_filename, this);

         // Validate Map
         if (isMapValid())
         {
             System.out.println("Map " + p_filename + " loaded");
         }
         else
         {
             throw new InvalidMapFileException();
         }
     }

     public void resetMap()
     {
         d_Countries.clear();
         d_Continents.clear();
         CountryImpl.resetCounter();
         ContinentImpl.resetCounter();
     }

     public boolean isMapValid()
     {
         // TODO #5
         // remove hardcoded "true" value and check the conditions for validity
         // For any issues, use a print to specify what you found wrong
         return true;
     }

     public boolean isMapEmpty()
     {
         return d_Continents.isEmpty() && d_Countries.isEmpty();
     }
}