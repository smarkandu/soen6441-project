package ca.concordia.soen6441.project.ui;

import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.phases.PreLoad;

import java.util.Scanner;

/**
 * This class provided the user interface in order to interact with our application
 */
public class CommandLineInterface {
    private GameContext d_gameEngine = null;

    /**
     * Constructor
     * @param p_gameEngine GameContext object
     */
    public CommandLineInterface(GameContext p_gameEngine) {
        this.d_gameEngine = p_gameEngine;
    }

    /**
     * Starts the game execution loop, handling player commands.
     */
    public void start() {
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
                        processEditContinent(l_args, l_operation);
                        break;
                    case "editcountry":
                        processEditCountry(l_args, l_operation);
                        break;
                    case "editneighbor":
                        processEditNeighbor(l_args, l_operation);
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
                        processDeploy(l_args);
                        break;
                    }
                    case "advance": {
                        processAdvance(l_args);
                        break;
                    }
                    case "airlift": {
                        processAirlift(l_args);
                        break;
                    }
                    case "bomb": {
                        String l_countryNameToBomb = l_args[1].replace("\"", "");
                        d_gameEngine.getPhase().bomb(l_countryNameToBomb);
                        break;
                    }
                    case "blockade": {
                        String l_countryNameToBlockade = l_args[1].replace("\"", "");
                        d_gameEngine.getPhase().blockade(l_countryNameToBlockade);
                        break;
                    }
                    case "negotiate": {
                        String l_targetPlayerID = l_args[1];
                        d_gameEngine.getPhase().negotiate(l_targetPlayerID);
                        break;
                    }
                    case "gameplayer":
                        processGamePlayer(l_args, l_operation);
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

    private void processEditContinent(String[] p_args, String p_operation)
    {
        if ("-add".equals(p_operation) && p_args.length == 4) {
            String l_continentID = p_args[2].replace("\"", "");
            int l_continentValue = Integer.parseInt(p_args[3]);
            d_gameEngine.getPhase().editContinentAdd(l_continentID, l_continentValue);
        } else if ("-remove".equals(p_operation) && p_args.length == 3) {
            String l_continentID = p_args[2].replace("\"", "");
            d_gameEngine.getPhase().editContinentRemove(l_continentID);
        } else {
            System.out.println("Operation not recognized");
        }
    }

    private void processEditCountry(String[] p_args, String p_operation)
    {
        if ("-add".equals(p_operation) && p_args.length == 4) {
            String l_countryID = p_args[2].replace("\"", "");
            String l_continentID = p_args[3].replace("\"", "");
            d_gameEngine.getPhase().editCountryAdd(l_countryID, l_continentID);
        } else if ("-remove".equals(p_operation) && p_args.length == 3) {
            String l_countryID = p_args[2].replace("\"", "");
            d_gameEngine.getPhase().editCountryRemove(l_countryID);
        } else {
            System.out.println("Operation not recognized");
        }
    }

    private void processEditNeighbor(String[] p_args, String p_operation)
    {
        if ("-add".equals(p_operation) && p_args.length == 4) {
            String l_countryID = p_args[2].replace("\"", "");
            String l_neighborCountryID = p_args[3].replace("\"", "");
            d_gameEngine.getPhase().editNeighborAdd(l_countryID, l_neighborCountryID);
        } else if ("-remove".equals(p_operation) && p_args.length == 4) {
            String l_countryID = p_args[2].replace("\"", "");
            String l_neighborCountryID = p_args[3].replace("\"", "");
            d_gameEngine.getPhase().editNeighborRemove(l_countryID, l_neighborCountryID);
        } else {
            System.out.println("Operation not recognized");
        }
    }

    private void processDeploy(String[] p_args)
    {
        String l_countryID = p_args[1].replace("\"", "");
        int l_toDeploy = Integer.parseInt(p_args[2]);
        d_gameEngine.getPhase().deploy(l_countryID, l_toDeploy);
    }

    private void processAdvance(String[] p_args)
    {
        String l_countryNameFrom = p_args[1].replace("\"", "");
        String l_countryNameTo = p_args[2].replace("\"", "");
        int l_toAdvance = Integer.parseInt(p_args[3]);
        d_gameEngine.getPhase().advance(l_countryNameFrom, l_countryNameTo, l_toAdvance);
    }

    private void processAirlift(String[] p_args)
    {
        String l_sourceCountryID = p_args[1].replace("\"", "");
        String l_targetCountryID = p_args[2].replace("\"", "");
        int l_numArmies = Integer.parseInt(p_args[3]);
        d_gameEngine.getPhase().airlift(l_sourceCountryID, l_targetCountryID, l_numArmies);
    }

    private void processGamePlayer(String[] p_args, String p_operation)
    {
        String l_playername = p_args[2];
        if ("-add".equals(p_operation) && p_args.length == 3) {
            d_gameEngine.getPhase().gamePlayerAdd(l_playername);
        }
        if ("-remove".equals(p_operation) && p_args.length == 3) {
            d_gameEngine.getPhase().gamePlayerRemove(l_playername);
        }
    }
}
