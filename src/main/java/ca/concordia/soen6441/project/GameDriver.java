package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.phases.PreLoad;

import java.util.Scanner;


/**
 * The GameDriver class serves as the entry point for the game.
 * It initializes and starts the game engine.
 */
public class GameDriver {
    static GameContext d_gameEngine = new GameEngine();


    /**
     * Starts the game execution loop, handling player commands.
     */
    public static void start() {
        // Can change the state of the Context (GameEngine) object, e.g.
        d_gameEngine.setPhase(new PreLoad(d_gameEngine));
        boolean l_continuePlaying = true;
        Scanner l_scanner = new Scanner(System.in);

        while (l_continuePlaying) {
            try
            {
                System.out.print(d_gameEngine.getPhase().getPhaseName() + ">");
                String[] l_args = l_scanner.nextLine().split(" ");
                String l_action = l_args[0].toLowerCase();
                String l_operation = l_args.length > 1 ? l_args[1].toLowerCase() : null;

                switch (l_action) {
                    case "editcontinent":
                        if ("-add".equals(l_operation) && l_args.length == 4) {
                            String l_continentID = l_args[2].replace("\"", "");
                            int l_continentValue = Integer.parseInt(l_args[3]);
                            d_gameEngine.getPhase().editContinentAdd(l_continentID, l_continentValue);
                        } else if ("-remove".equals(l_operation) && l_args.length == 3) {
                            String l_continentID = l_args[2].replace("\"", "");
                            d_gameEngine.getPhase().editContinentRemove(l_continentID);
                        } else {
                            System.out.println("Operation not recognized");
                        }
                        break;
                    case "editcountry":
                        if ("-add".equals(l_operation) && l_args.length == 4) {
                            String l_countryID = l_args[2].replace("\"", "");
                            String l_continentID = l_args[3].replace("\"", "");
                            d_gameEngine.getPhase().editCountryAdd(l_countryID, l_continentID);
                        } else if ("-remove".equals(l_operation) && l_args.length == 3) {
                            String l_countryID = l_args[2].replace("\"", "");
                            d_gameEngine.getPhase().editCountryRemove(l_countryID);
                        } else {
                            System.out.println("Operation not recognized");
                        }
                        break;
                    case "editneighbor":
                        if ("-add".equals(l_operation) && l_args.length == 4) {
                            String l_countryID = l_args[2].replace("\"", "");
                            String l_neighborCountryID = l_args[3].replace("\"", "");
                            d_gameEngine.getPhase().editNeighborAdd(l_countryID, l_neighborCountryID);
                        } else if ("-remove".equals(l_operation) && l_args.length == 4) {
                            String l_countryID = l_args[2].replace("\"", "");
                            String l_neighborCountryID = l_args[3].replace("\"", "");
                            d_gameEngine.getPhase().editNeighborRemove(l_countryID, l_neighborCountryID);
                        } else {
                            System.out.println("Operation not recognized");
                        }
                        break;
                    case "showmap":
                        d_gameEngine.getPhase().showMap();
                        break;
                    case "savemap":
                        d_gameEngine.getPhase().saveMap(l_args[1]);
                        break;
                    case "assigncountries":
                        d_gameEngine.getPhase().assignCountries();
                        break;
                    case "deploy": {
                        String l_countryID = l_args[1].replace("\"", "");
                        int l_toDeploy = Integer.parseInt(l_args[2]);
                        d_gameEngine.getPhase().deploy(l_countryID, l_toDeploy);
                        break;
                    }
                    case "advance": {
                        String l_countryNameFrom = l_args[1].replace("\"", "");
                        String l_countryNameTo = l_args[2].replace("\"", "");
                        int l_toAdvance = Integer.parseInt(l_args[3]);
                        d_gameEngine.getPhase().advance(l_countryNameFrom, l_countryNameTo, l_toAdvance);
                        break;
                    }
                    case "bomb": {
                        String l_countryID = l_args[1].replace("\"", "");
                        System.out.println("Bomb " + l_countryID);
                        d_gameEngine.getPhase().bomb(l_countryID);
                        break;
                    }
                    case "gameplayer":
                        String l_playername = l_args[2];
                        if ("-add".equals(l_operation) && l_args.length == 3) {
                            d_gameEngine.getPhase().gamePlayerAdd(l_playername);
                        }
                        if ("-remove".equals(l_operation) && l_args.length == 3) {
                            d_gameEngine.getPhase().gamePlayerRemove(l_playername);
                        }
                        break;
                    case "loadmap":
                        d_gameEngine.getPhase().loadMap(l_args[1]);
                        break;
                    case "validatemap":
                        if (l_args.length == 1) {
                            d_gameEngine.getPhase().validateMap();
                        }
                        break;
                    case "next":
                        d_gameEngine.getPhase().next();
                        break;
                    case "exit":
                        d_gameEngine.getPhase().endGame();
                        l_continuePlaying = false;
                        break;
                    case "":
                        // Do Nothing
                        break;
                    default:
                        System.out.println("Command not recognized");
                        break;
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * The main method initializes the game engine and starts the game.
     *
     * @param p_args Command-line arguments (not used).
     */
    public static void main(String[] p_args) {
        start();
    }
}
