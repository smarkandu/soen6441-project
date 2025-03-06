package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameEngine;

public abstract class Edit extends Phase {
    public Edit(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    public void showMap() {
        d_gameEngine.showMap(false);
    }
    public void setPlayers() {
        printInvalidCommandMessage();
    }
    public void assignCountries() {
        printInvalidCommandMessage();
    }
    public void deploy(String p_countryID, int p_toDeploy) {
        printInvalidCommandMessage();
    }
    public void endGame() {
        printInvalidCommandMessage();
    }

    public void validateMap()
    {
        // TODO #5
        // Edit as needed
        if (d_gameEngine.isMapValid())
        {
            System.out.println("Map is valid");
        }
        else
        {
            System.out.println("Map is not valid");
        }
    }
}