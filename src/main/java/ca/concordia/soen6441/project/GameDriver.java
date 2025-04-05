package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.ui.CommandLineInterface;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * The GameDriver class serves as the entry point for the game.
 * It initializes and starts the game engine.
 */
public class GameDriver
{
    public static void getTournamentResults()
    {
        TreeMap<String, List<String>> returnValue = new TreeMap<>();
        for (int l_i = 0; l_i < d_priorTournaments.size(); l_i++)
        {
            String l_filename = d_priorTournaments.get(l_i).getMapFileLoaded();
            String l_outcome = d_priorTournaments.get(l_i).getOutcomeOfGame();
            if (!returnValue.containsKey(l_filename))
            {
                List<String> l_tempList = new ArrayList<String>();
                l_tempList.add(l_outcome);
                returnValue.put(l_filename, l_tempList);
            }
            else
            {
                List<String> l_tempList = returnValue.get(l_filename);
                l_tempList.add(l_outcome);
                returnValue.put(l_filename, l_tempList);
            }
        }

        System.out.println("Map\t\t\t[Game_Outcomes]");
        for (String key : returnValue.keySet())
        {
            System.out.println(key + "\t\t\t" + returnValue.get(key));
        }
    }

    private static GameContext d_gameEngine = new GameEngine();
    private static ArrayDeque<GameContext> d_tournamentQueue = new ArrayDeque<>();
    private static List<GameContext> d_priorTournaments = new ArrayList<>();

    public static GameContext getGameEngine()
    {
        return d_gameEngine;
    }

    public static void setGameEngine(GameContext p_gameEngine)
    {
        d_gameEngine = p_gameEngine;
    }

    public static ArrayDeque<GameContext> getTournamentQueue()
    {
        return d_tournamentQueue;
    }

    public static List<GameContext> getPriorTournaments()
    {
        return d_priorTournaments;
    }

    /**
     * The main method initializes the game engine and starts the game.
     *
     * @param p_args Command-line arguments (not used).
     */
    public static void main(String[] p_args)
    {
        CommandLineInterface l_commandLineInterface = new CommandLineInterface();
        l_commandLineInterface.start();
    }
}

