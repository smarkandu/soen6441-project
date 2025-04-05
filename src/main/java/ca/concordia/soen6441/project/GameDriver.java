package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.ui.CommandLineInterface;

import java.util.ArrayDeque;

/**
 * The GameDriver class serves as the entry point for the game.
 * It initializes and starts the game engine.
 */
public class GameDriver {
    private static GameContext d_gameEngine = new GameEngine();
    private static ArrayDeque<GameContext> d_arrayDeque = new ArrayDeque<>();

    public static GameContext getGameEngine() {
        return d_gameEngine;
    }

    public static void setGameEngine(GameContext p_gameEngine) {
        d_gameEngine = p_gameEngine;
    }

    public static ArrayDeque<GameContext> getGameEngineStack() {
        return d_arrayDeque;
    }

    /**
     * The main method initializes the game engine and starts the game.
     *
     * @param p_args Command-line arguments (not used).
     */
    public static void main(String[] p_args) {
        CommandLineInterface l_commandLineInterface = new CommandLineInterface();
        l_commandLineInterface.start();
    }
}
