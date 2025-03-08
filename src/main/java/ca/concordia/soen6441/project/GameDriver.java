package ca.concordia.soen6441.project;

/**
 * The GameDriver class serves as the entry point for the game.
 * It initializes and starts the game engine.
 */
public class GameDriver {

    /**
     * The main method initializes the game engine and starts the game.
     *
     * @param p_args Command-line arguments (not used).
     */
    public static void main(String[] p_args) {
        GameEngine l_gameEngine = new GameEngine();
        l_gameEngine.start();
    }
}
