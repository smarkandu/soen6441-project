package ca.concordia.soen6441.project;

public abstract class Phase {
    GameEngine d_gameEngine;

    public Phase(GameEngine l_gameEngine) {
        this.d_gameEngine = l_gameEngine;
    }

    // general behavior
    abstract public void loadMap();
    abstract public void showMap();
    abstract public void validateMap();

    // edit map state behavior
    abstract public void editContinentAdd(String p_continentID, int p_continentValue);
    abstract public void editContinentRemove(String p_continentID);
    abstract public void editCountryAdd(String p_countryID, String p_continentID);
    abstract public void editCountryRemove(String p_countryID);
    abstract public void editNeighborAdd(String p_countryID, String p_neighborCountryID);
    abstract public void editNeighborRemove(String p_countryID, String p_neighborCountryID);
    abstract public void saveMap();

    // play state behavior
    // game setup state behavior
    abstract public void setPlayers();
    abstract public void assignCountries();
    abstract public void endGame();

    // go to next phase
    abstract public void next();

    // methods common to all states
    public void printInvalidCommandMessage() {
        System.out.println("Invalid command in state "
                + this.getClass().getSimpleName() );
    }
}