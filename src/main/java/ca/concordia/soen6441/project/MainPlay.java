package ca.concordia.soen6441.project;

public abstract class MainPlay extends Play {
    public MainPlay(GameEngine p_gameEngine) {
        super(p_gameEngine);
    }

    public void loadMap(String filename) {
        this.printInvalidCommandMessage();
    }
    public void setPlayers() {
        this.printInvalidCommandMessage();
    }
    public void assignCountries() {
        this.printInvalidCommandMessage();
    }
}
