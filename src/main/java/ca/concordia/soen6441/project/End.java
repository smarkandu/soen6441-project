package ca.concordia.soen6441.project;

public class End extends Phase {
    public End(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    @Override
    public void loadMap(String filename) {
        printInvalidCommandMessage();
    }

    @Override
    public void showMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void validateMap() {
        printInvalidCommandMessage();
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
    public void saveMap(String filename) {
        printInvalidCommandMessage();
    }

    @Override
    public void setPlayers() {
        printInvalidCommandMessage();
    }

    @Override
    public void assignCountries() {
        printInvalidCommandMessage();
    }

    @Override
    public void endGame() {
        System.exit(1);
    }

    @Override
    public void next() {

    }
}
