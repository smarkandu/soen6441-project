package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.phases.PreLoad;
import ca.concordia.soen6441.project.ui.CommandLineInterface;

import java.util.Scanner;


/**
 * The GameDriver class serves as the entry point for the game.
 * It initializes and starts the game engine.
 */
public class GameDriver {
    static GameContext d_gameEngine = new GameEngine();

    /**
     * The main method initializes the game engine and starts the game.
     *
     * @param p_args Command-line arguments (not used).
     */
    public static void main(String[] p_args) {
        CommandLineInterface commandLineInterface = new CommandLineInterface(d_gameEngine);
        commandLineInterface.start();
    }
}
