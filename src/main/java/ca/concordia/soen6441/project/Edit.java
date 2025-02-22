package ca.concordia.soen6441.project;

public abstract class Edit extends Phase {
    public Edit(GameEngine l_gameEngine) {
        super(l_gameEngine);
    }

    public void showMap() {
        System.out.println("edited map is displayed");
    }
    public void setPlayers() {
        printInvalidCommandMessage();
    }
    public void assignCountries() {
        printInvalidCommandMessage();
    }

    //    public void reinforce() {
//        printInvalidCommandMessage();
//    }
//    public void attack() {
//        printInvalidCommandMessage();
//    }
//    public void fortify() {
//        printInvalidCommandMessage();
//    }

    public void endGame() {
        printInvalidCommandMessage();
    }
}