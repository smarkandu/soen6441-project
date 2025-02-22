package ca.concordia.soen6441.project;

// State of the State pattern
public abstract class Play extends Phase {
    public Play(GameEngine l_gameEngine) {
        super(l_gameEngine);
    }

    public void showMap() {
        System.out.println("map is being displayed");
    }

    public void editContinent() {
        printInvalidCommandMessage();
    }

    public void editCountry() {
        printInvalidCommandMessage();
    }

    public void editNeighbor() {
        printInvalidCommandMessage();
    }

    public void validateMap() {
        printInvalidCommandMessage();
    }

    public void saveMap() {
        printInvalidCommandMessage();
    }
    public void endGame() {
        d_gameEngine.setPhase(new End(d_gameEngine));
    }
}