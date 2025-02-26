package ca.concordia.soen6441.project;

public class PostLoad extends Edit {
    public PostLoad(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    public void loadMap() {
        System.out.println("map has been loaded");
    }

    public void saveMap() {
        System.out.println("map has been saved");
        d_gameEngine.setPhase(new Startup(d_gameEngine));
    }

    @Override
    public void gamePlayerAdd(String p_playerName) {

    }

    @Override
    public void gamePlayerRemove(String p_playerName) {

    }

    public void next() {
        System.out.println("must save map");
    }

    public void validateMap()
    {
        // TODO
    }

    @Override
    public void editContinentAdd(String p_continentID, int p_continentValue) {
        d_gameEngine.addContinent(p_continentID, p_continentValue);
    }

    @Override
    public void editContinentRemove(String p_continentID) {
        d_gameEngine.removeContinent(p_continentID);
    }

    @Override
    public void editCountryAdd(String p_countryID, String p_continentID) {
        d_gameEngine.addCountry(p_countryID, p_continentID);
    }

    @Override
    public void editCountryRemove(String p_countryID) {
        d_gameEngine.removeCountry(p_countryID);
    }

    @Override
    public void editNeighborAdd(String p_countryID, String p_neighborCountryID) {
        d_gameEngine.addNeighbor(p_countryID, p_neighborCountryID);
    }

    @Override
    public void editNeighborRemove(String p_countryID, String p_neighborCountryID) {
        d_gameEngine.removeNeighbor(p_countryID, p_neighborCountryID);
    }
}