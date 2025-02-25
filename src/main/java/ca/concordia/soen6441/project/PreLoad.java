package ca.concordia.soen6441.project;

public class PreLoad extends Edit {
    public PreLoad(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    public void loadMap(String p_filename) {
        System.out.println("map has been loaded");

        // TODO: Shubo Uncomment below (feature #3)
//        MapFileReader mapFileReader = new MapFileReader();
//        mapFileReader.readMapFile(p_filename, d_gameEngine);
//        d_gameEngine.setPhase(new PostLoad(d_gameEngine));
    }

    public void saveMap(String p_filename) {
        printInvalidCommandMessage();
    }
    public void next() {
        System.out.println("must load map");
    }

    public void validateMap()
    {
        // TODO
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
}