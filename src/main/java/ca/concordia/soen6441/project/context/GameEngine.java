package ca.concordia.soen6441.project.context;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.MapComponent;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.*;
import ca.concordia.soen6441.project.interfaces.phases.State;
import ca.concordia.soen6441.project.log.LogEntryBuffer;
import ca.concordia.soen6441.project.map.*;
import ca.concordia.soen6441.project.phases.Phase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GameEngine class represents the main controller of the game.
 * It manages the game state, executes commands, and interacts with other components.
 */
public class GameEngine implements GameContext, MapComponent, Serializable
{
    private Phase d_gamePhase;
    private ValidateMapImpl d_validateMapImpl;
    private ContinentManager d_ContinentManager;
    private CountryManager d_CountryManager;
    private NeighborManager d_NeighborManager;
    private PlayerManager d_PlayerManager;
    private DeckOfCards d_DeckOfCards;
    private int d_GameNumber;
    private int d_numberOfTurns;
    private int d_maxNumberOfTurns;
    private String d_outcomeOfGame;
    private String d_mapFileLoaded;

    /**
     * Constructs a new GameEngine instance and initializes game data structures.
     */
    public GameEngine()
    {
        d_ContinentManager = new ContinentManager();
        d_CountryManager = new CountryManager();
        d_NeighborManager = new NeighborManager();
        d_PlayerManager = new PlayerManager();
        d_validateMapImpl = new ValidateMapImpl(d_CountryManager.getCountries(), d_ContinentManager.getContinents());
        d_DeckOfCards = new DeckOfCards();
        d_GameNumber = 1;
        d_numberOfTurns = 0;
        d_maxNumberOfTurns = Integer.MAX_VALUE;
        d_mapFileLoaded = null;
    }

    /**
     * Constructs a new GameEngine instance and initializes game data structures.
     */
    public GameEngine(ContinentManager p_continentManager, CountryManager p_countryManager, NeighborManager p_neighborManager, PlayerManager p_playerManager, DeckOfCards p_deckOfCards)
    {
        d_ContinentManager = p_continentManager;
        d_CountryManager = p_countryManager;
        d_NeighborManager = p_neighborManager;
        d_PlayerManager = p_playerManager;
        d_DeckOfCards = p_deckOfCards;
        d_GameNumber = 1;
        d_numberOfTurns = 0;
        d_maxNumberOfTurns = Integer.MAX_VALUE;
        d_mapFileLoaded = null;
    }

    public State getPhase()
    {
        return d_gamePhase;
    }

    /**
     * Sets the current phase of the game.
     *
     * @param p_phase The game phase to set.
     */
    public void setPhase(Phase p_phase)
    {
        d_gamePhase = p_phase;
    }


    /**
     * Prints a list of the continents (in alphabetical order) with their countries
     * underneath each one.
     *
     * @param p_isDetailed If true, prints country owners, army counts, and neighbors.
     *                     If false, prints only continent and country names.
     */
    public void showMap(boolean p_isDetailed)
    {

        // Step 1: Retrieve all continents and sort them alphabetically
        List<Continent> l_sortedContinents = new ArrayList<>(d_ContinentManager.getContinents().values()); // Convert to list
        l_sortedContinents.sort(Comparator.comparing(Continent::getID)); // Sort by continent ID

        // Iterate through each sorted continent
        for (Continent l_continent : l_sortedContinents)
        {
            // Print the continent's name and its bonus value
            System.out.println(l_continent.getID() + " (Bonus: " + l_continent.getValue() + ")");

            // Step 2: Retrieve the list of countries belonging to this continent
            List<Country> l_countries = d_CountryManager.getCountriesOfContinent(l_continent.getID());

            // Sort countries alphabetically based on their ID
            l_countries.sort(Comparator.comparing(Country::getID));

            // Iterate through each country in the continent
            for (Country l_country : l_countries)
            {
                // Start constructing the country info string
                StringBuilder l_countryInfo = new StringBuilder("  - " + l_country.getID());
                List<String> l_neighbors = l_country.getNeighborIDs();

                // If detailed view is enabled, append owner, army count, and neighboring countries
                if (p_isDetailed)
                {
                    Player l_owner = l_country.getOwner(); // Get the country owner
                    int l_armyCount = l_country.getTroops(); // Get the number of troops in the country

                    // Append detailed information about the country
                    l_countryInfo.append(" | Owner: ").append(l_owner.getName()) // Owner's name
                            .append(" | Armies: ").append(l_armyCount); // Number of armies stationed

                }

                l_countryInfo.append(" | Neighbors: ").append(String.join(", ", l_neighbors));

                // Print the formatted country information
                System.out.println(l_countryInfo);
            }
        }
    }


    /**
     * Returns a string representation of the current game state.
     *
     * @return A formatted string displaying continents and countries.
     */
    @Override
    public String toString()
    {
        // Format continents to string
        StringBuilder l_sbContinents = new StringBuilder();
        for (Continent l_continent : d_ContinentManager.getContinents().values())
        {
            l_sbContinents.append(l_continent).append("\n");
        }

        // Format countries to string
        StringBuilder l_sbCountries = new StringBuilder();
        for (Country l_country : d_CountryManager.getCountries().values())
        {
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
    public String toMapString()
    {
        // Builds the map file format string

        // Create sections
        StringBuilder l_mapBuilder = new StringBuilder();

        // Add [continents] section
        l_mapBuilder.append("[continents]\n");
        d_ContinentManager.getContinents().values().stream().sorted(Comparator.comparingInt(Continent::getNumericID)) // Sort by numeric ID
                .forEach(l_continent -> l_mapBuilder.append(l_continent.toMapString()).append("\n"));

        // Add [countries] section
        l_mapBuilder.append("\n[countries]\n");
        d_CountryManager.getCountries().values().stream().sorted(Comparator.comparingInt(Country::getNumericID)).forEach(l_country -> l_mapBuilder.append(l_country.toMapString()).append("\n"));

        // Add [borders] section
        l_mapBuilder.append("\n[borders]\n");
        d_CountryManager.getCountries().values().stream().sorted(Comparator.comparingInt(Country::getNumericID)).forEach(l_country ->
        {
            String l_borders = d_CountryManager.getCountries().get(l_country.getID()).getNeighborIDs().stream().map(neighborID -> String.valueOf(d_CountryManager.getCountries().get(neighborID).getNumericID())) // Convert to numeric ID
                    .collect(Collectors.joining(" "));
            l_mapBuilder.append(l_country.getNumericID()).append(l_borders.isEmpty() ? "" : " " + l_borders).append("\n");
        });
        return l_mapBuilder.toString();
    }

    /**
     * Loads a map from a file and validates its contents.
     *
     * @param p_filename The filename of the map to be loaded.
     * @throws InvalidMapFileException If the map file is invalid.
     * @throws FileNotFoundException   If the file is not found.
     */
    public void loadMap(String p_filename) throws InvalidMapFileException, FileNotFoundException
    {
        // Empty out contents of map in GameEngine
        resetMap();

        // Read Map into Game Engine
        MapFileReader l_mapFileReader = new MapFileReader();
        l_mapFileReader.readMapFile(p_filename);

        // Validate Map
        if (isMapValid())
        {
            File l_file = new File(p_filename);
            String l_fileName = l_file.getName();
            setMapFileLoaded(l_fileName);
            LogEntryBuffer.getInstance().appendToBuffer("Map " + l_fileName + " loaded", true);
        }
        else
        {
            setMapFileLoaded(null);
            throw new InvalidMapFileException();
        }
    }

    /**
     * Resets the game map by clearing all countries and continents.
     */
    public void resetMap()
    {
        d_CountryManager.clear();
        d_ContinentManager.clear();
        CountryImpl.resetCounter();
        ContinentImpl.resetCounter();
    }

    public boolean isMapValid()
    {
        return d_validateMapImpl.isMapValid();
    }

    @Override
    public ContinentContext getContinentManager()
    {
        return d_ContinentManager;
    }

    @Override
    public CountryContext getCountryManager()
    {
        return d_CountryManager;
    }

    @Override
    public NeighborContext getNeighborManager()
    {
        return d_NeighborManager;
    }

    @Override
    public PlayerContext getPlayerManager()
    {
        return d_PlayerManager;
    }

    /**
     * Checks if the map is empty.
     *
     * @return true if there are no continents or countries in the map.
     */
    public boolean isMapEmpty()
    {
        return d_ContinentManager.getContinents().isEmpty() && d_CountryManager.getCountries().isEmpty();
    }

    public DeckOfCards getDeckOfCards()
    {
        return d_DeckOfCards;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignCountryToPlayer(Country p_country, Player p_player)
    {
        if (p_country == null)
        {
            System.out.println("ERROR: null value set for assignCountryToPlayer for p_country.  Operation cancelled.");
            return;
        }
        else if (p_player == null)
        {
            System.out.println("ERROR: null value set for assignCountryToPlayer for p_player.  Operation cancelled.");
            return;
        }
        else
        {
            p_country.setOwner(p_player);
            p_player.addOwnedCountry(p_country);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unassignCountryFromPlayer(Country p_country)
    {
        if (p_country.getOwner() != null)
        {
            p_country.getOwner().removeOwnedCountry(p_country);
        }

        p_country.setOwner(d_PlayerManager.getNeutralPlayer()); // Becomes Neutral
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfTurns()
    {
        return d_numberOfTurns;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int incrementNumberOfTurns()
    {
        return ++d_numberOfTurns;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxNumberOfTurns()
    {
        return d_maxNumberOfTurns;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxNumberOfTurns(int p_maxNumberOfTurns)
    {
        d_maxNumberOfTurns = p_maxNumberOfTurns;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOutcomeOfGame()
    {
        return d_outcomeOfGame;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOutcomeOfGame(String p_outcomeOfGame)
    {
        d_outcomeOfGame = p_outcomeOfGame;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGameNumber()
    {
        return d_GameNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameNumber(int p_gameNumber)
    {
        d_GameNumber = p_gameNumber;
    }

    /**
     * {@inheritDoc}
     */
    public String gameWonBy()
    {
        ArrayList<Country> l_listOfCountries = new ArrayList<>(GameDriver.getGameEngine().getCountryManager().getCountries().values());
        if (l_listOfCountries.isEmpty())
        {
            return null;
        }

        Player l_player = l_listOfCountries.get(0).getOwner();
        for (Country l_country : l_listOfCountries)
        {
            if (l_country.getOwner() == null || l_country.getOwner() != l_player)
            {
                if (GameDriver.getGameEngine().getNumberOfTurns() == GameDriver.getGameEngine().getMaxNumberOfTurns())
                {
                    GameDriver.getGameEngine().setOutcomeOfGame("Draw");
                    return GameDriver.getGameEngine().getOutcomeOfGame();
                }
                else
                {
                    return GameDriver.getGameEngine().getOutcomeOfGame();
                }
            }
        }

        GameDriver.getGameEngine().setOutcomeOfGame(l_player.getName());
        return GameDriver.getGameEngine().getOutcomeOfGame();
    }

    /**
     * {@inheritDoc}
     */
    public String getMapFileLoaded()
    {
        return d_mapFileLoaded;
    }

    /**
     * {@inheritDoc}
     */
    public void setMapFileLoaded(String p_mapFileLoaded)
    {
        d_mapFileLoaded = p_mapFileLoaded;
    }
}