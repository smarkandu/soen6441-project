package ca.concordia.soen6441.project;

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