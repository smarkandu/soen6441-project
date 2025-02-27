package ca.concordia.soen6441.project;

public abstract class Phase {
    GameEngine d_gameEngine;

    public Phase(GameEngine p_gameEngine) {
        this.d_gameEngine = p_gameEngine;
    }

    // general behavior
    abstract public void loadMap(String p_filename);
    abstract public void showMap();
    abstract public void validateMap();

    // edit map state behavior
    abstract public void editContinentAdd(String p_continentID, int p_continentValue);
    abstract public void editContinentRemove(String p_continentID);
    abstract public void editCountryAdd(String p_countryID, String p_continentID);
    abstract public void editCountryRemove(String p_countryID);
    abstract public void editNeighborAdd(String p_countryID, String p_neighborCountryID);
    abstract public void editNeighborRemove(String p_countryID, String p_neighborCountryID);
    abstract public void saveMap(String p_filename);

    // play state behavior
    // game setup state behavior

    // ToDO: (Marc) Delete setPlayers and uncomment the "new" methods
    // below (gamePlayerAdd, gamePlayerRemove).  You'll need to implement them in the child classes where setPlayer was
    // previously implemented
    abstract public void gamePlayerAdd(String p_playerName);
    abstract public void gamePlayerRemove(String p_playerName);

    abstract public void assignCountries();
    abstract public void endGame();

    // go to next phase
    abstract public void next();

    // methods common to all states
    public void printInvalidCommandMessage() {
        System.out.println("Invalid command in state "
                + this.getClass().getSimpleName() );
    }

    public String getPhaseName()
    {
        return getClass().getSimpleName();
    }

    public void assignReinforcements() {
    }
}