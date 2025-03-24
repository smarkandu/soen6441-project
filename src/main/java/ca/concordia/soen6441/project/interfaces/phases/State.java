package ca.concordia.soen6441.project.interfaces.phases;

public interface State {
    void loadMap(String p_filename);
    void showMap();
    void validateMap();

    // edit map state behavior
    void editContinentAdd(String p_continentID, int p_continentValue);
    void editContinentRemove(String p_continentID);
    void editCountryAdd(String p_countryID, String p_continentID);
    void editCountryRemove(String p_countryID);
    void editNeighborAdd(String p_countryID, String p_neighborCountryID);
    void editNeighborRemove(String p_countryID, String p_neighborCountryID);
    void saveMap(String p_filename);

    // play state behavior
    // game setup state behavior
    void gamePlayerAdd(String p_playerName);
    void gamePlayerRemove(String p_playerName);
    void assignCountries();

    // Gameplay behaviour
    void deploy(String p_countryID, int p_toDeploy);
    void advance(String p_countryNameFrom, String p_countryNameTo, int p_toAdvance);
    void endGame();

    // Phase Related
    String getPhaseName();
    void next();

    void printInvalidCommandMessage();
}
