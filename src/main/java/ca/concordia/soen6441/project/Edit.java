package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Player;

public abstract class Edit extends Phase {
    public Edit(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    public void showMap() {
        System.out.println(d_gameEngine);
    }
    public void setPlayers() {
        printInvalidCommandMessage();
    }
    public void assignCountries() {
        printInvalidCommandMessage();
    }
    public void deploy(Player p_player, String p_countryID, int p_to_deploy) {
        printInvalidCommandMessage();
    }
    public void endGame() {
        printInvalidCommandMessage();
    }
}