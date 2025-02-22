package ca.concordia.soen6441.project;

public class PreLoad extends Edit {
    public PreLoad(GameEngine l_gameEngine) {
        super(l_gameEngine);
    }

    public void loadMap() {
        System.out.println("map has been loaded");
        d_gameEngine.setPhase(new PostLoad(d_gameEngine));
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

    public void saveMap() {
        printInvalidCommandMessage();
    }
    public void next() {
        System.out.println("must load map");
    }

    public void validateMap()
    {
        // TODO
    }
}