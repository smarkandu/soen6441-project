package ca.concordia.soen6441.project;

public class PreLoad extends Edit {
    public PreLoad(GameEngine p_gameEngine)
    {
        super(p_gameEngine);
    }

    public void loadMap(String p_filename) {
        try
        {
            d_gameEngine.loadMap(p_filename);
        }
        catch(InvalidMapFileException e)
        {
            System.out.println("File not structured correctly." +
                    "\nPlease use editor to correct and save, or load another file.");
        }
    }

    public void saveMap(String p_filename) {
        printInvalidCommandMessage();
    }

    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    public void next()
    {
        if (!d_gameEngine.isMapEmpty())
        {
            d_gameEngine.setPhase(new PostLoad(d_gameEngine));
        }
        else
        {
            d_gameEngine.setPhase(new Startup(d_gameEngine));
        }
    }

    @Override
    public void editContinentAdd(String p_continentID, int p_continentValue) {
        printInvalidCommandMessage();
    }

    @Override
    public void editContinentRemove(String p_continentID) {
        printInvalidCommandMessage();
    }

    @Override
    public void editCountryAdd(String p_countryID, String p_continentID) {
        printInvalidCommandMessage();
    }

    @Override
    public void editCountryRemove(String p_countryID) {
        printInvalidCommandMessage();
    }

    @Override
    public void editNeighborAdd(String p_countryID, String p_neighborCountryID) {
        printInvalidCommandMessage();
    }

    @Override
    public void editNeighborRemove(String p_countryID, String p_neighborCountryID) {
        printInvalidCommandMessage();
    }

    @Override
    public String getPhaseName()
    {
        System.out.println("\n*** Welcome to the Map Editor! ***\nPlease load or create a new map using 'loadmap', or skip directly to the game using the command 'next'");
        System.out.println("If a file is already loaded (or a new empty map created), 'next' will take you to the edit state of the MapEditor instead\n");
        return super.getPhaseName();
    }
}