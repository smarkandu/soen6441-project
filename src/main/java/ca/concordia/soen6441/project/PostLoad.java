package ca.concordia.soen6441.project;

public class PostLoad extends Edit {
    public PostLoad(GameEngine l_gameEngine) {
        super(l_gameEngine);
    }

    public void loadMap() {
        System.out.println("map has been loaded");
    }

    public void editContinent() {
        // TODO
        System.out.println("continent has been edited");
    }

    public void editCountry() {
        // TODO
        System.out.println("country has been edited");
    }

    public void editNeighbor() {
        // TODO
        System.out.println("neighbor has been edited");
    }

    public void saveMap() {
        System.out.println("map has been saved");
        d_gameEngine.setPhase(new PlaySetup(d_gameEngine));
    }

    public void next() {
        System.out.println("must save map");
    }

    public void validateMap()
    {
        // TODO
    }
}