package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameEngine;

/**
 * The Phase class represents the abstract base class for different phases in the game.
 * Each phase defines specific behaviors for game actions.
 */
public abstract class Phase {
    protected GameEngine d_gameEngine;

    /**
     * Constructs a Phase instance.
     *
     * @param p_gameEngine The game engine instance controlling the game state.
     */
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
    // below (gamePlayerAdd, gamePlayerRemove).  You'll need to implement them in the child classes where setPlayers was
    // previously implemented
    abstract public void gamePlayerAdd(String p_playerName);
    abstract public void gamePlayerRemove(String p_playerName);
    abstract public void assignCountries();

    abstract public void deploy(String p_countryID, int p_toDeploy);
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
}
