package ca.concordia.soen6441.project;

// State of the State pattern
public abstract class Play extends Phase {
    public Play(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    public void showMap() {
        d_gameEngine.showMap(false);
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
    public void validateMap() {
        printInvalidCommandMessage();
    }

    public void saveMap(String p_filename) {
        printInvalidCommandMessage();
    }
    public void endGame() {
        d_gameEngine.setPhase(new End(d_gameEngine));
    }
}