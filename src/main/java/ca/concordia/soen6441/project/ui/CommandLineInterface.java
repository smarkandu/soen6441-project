package ca.concordia.soen6441.project.ui;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType;
import ca.concordia.soen6441.project.phases.IssueOrder;
import ca.concordia.soen6441.project.phases.PreLoad;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType.HUMAN;

/**
 * This class provided the user interface in order to interact with our application
 */
public class CommandLineInterface {
  //  private PlayerBehaviorFactory d_playerBehaviorFactory = null;
    private Scanner d_scanner = null;

    /**
     * Constructor
     */
    public CommandLineInterface() {
//        this.d_playerBehaviorFactory = new PlayerBehaviorFactory();
        this.d_scanner = new Scanner(System.in);
    }

    /**
     * Starts the game execution loop, handling player commands.
     */
    public void start() {
        // Can change the state of the Context (GameEngine) object, e.g.
        GameDriver.getGameEngine().setPhase(new PreLoad());
        boolean l_continuePlaying = true;
        d_scanner = new Scanner(System.in); // we reset the scanner here on purpose

        while (l_continuePlaying) {
            try
            {
                if (GameDriver.getGameEngine().getPhase() instanceof IssueOrder)
                {
                    GameDriver.getGameEngine().getPlayerManager().getPlayer(GameDriver.getGameEngine().getPlayerManager().getCurrentPlayerIndex()).issue_order();
                }
                else
                {
                    l_continuePlaying = getInputFromUserAndProcess();
                }

            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean getInputFromUserAndProcess()
    {
        System.out.print(GameDriver.getGameEngine().getPhase().getPhaseName() + ">");
        String[] l_args = d_scanner.nextLine().split(" ");
        String l_action = l_args[0].toLowerCase();
        String l_operation = l_args.length > 1 ? l_args[1].toLowerCase() : null;

        boolean l_continuePlaying = true;

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
                GameDriver.getGameEngine().getPhase().showMap();
                break;
            case "savemap":
                GameDriver.getGameEngine().getPhase().saveMap(l_args[1]);
                break;
            case "assigncountries":
                GameDriver.getGameEngine().getPhase().assignCountries();
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
                GameDriver.getGameEngine().getPhase().bomb(l_countryNameToBomb);
                break;
            }
            case "blockade": {
                String l_countryNameToBlockade = l_args[1].replace("\"", "");
                GameDriver.getGameEngine().getPhase().blockade(l_countryNameToBlockade);
                break;
            }
            case "negotiate": {
                String l_targetPlayerID = l_args[1];
                GameDriver.getGameEngine().getPhase().negotiate(l_targetPlayerID);
                break;
            }
            case "gameplayer":
                processGamePlayer(l_args, l_operation);
                break;
            case "loadmap":
                GameDriver.getGameEngine().getPhase().loadMap(l_args[1]);
                break;
            case "validatemap":
                if (l_args.length == 1) {
                    GameDriver.getGameEngine().getPhase().validateMap();
                }
                break;
            case "next":
                GameDriver.getGameEngine().getPhase().next();
                break;
            case "exit":
                GameDriver.getGameEngine().getPhase().endGame();
                l_continuePlaying = false;
                break;
            case "loadgame": {
                String l_fileName = l_args[1];
                GameDriver.getGameEngine().getPhase().loadGame(l_fileName);
                }
                break;
            case "savegame":
                String l_fileName = l_args[1];
                GameDriver.getGameEngine().getPhase().saveGame(l_fileName);
                break;
            case "tournamentmode":
                processTournament(l_args);
                break;
            case "":
                // Do Nothing
                break;
            default:
                System.out.println("Command not recognized");
                break;
        }

        return l_continuePlaying;
    }

    /**
     * Processes EditContinent Command line string
     * @param p_args the string array of arguments passed
     * @param p_operation the string representing the operation
     */
    private void processEditContinent(String[] p_args, String p_operation)
    {
        if ("-add".equals(p_operation) && p_args.length == 4) {
            String l_continentID = p_args[2].replace("\"", "");
            int l_continentValue = Integer.parseInt(p_args[3]);
            GameDriver.getGameEngine().getPhase().editContinentAdd(l_continentID, l_continentValue);
        } else if ("-remove".equals(p_operation) && p_args.length == 3) {
            String l_continentID = p_args[2].replace("\"", "");
            GameDriver.getGameEngine().getPhase().editContinentRemove(l_continentID);
        } else {
            System.out.println("Operation not recognized");
        }
    }

    /**
     * Processes EditCountry Command line string
     * @param p_args the string array of arguments passed
     * @param p_operation the string representing the operation
     */
    private void processEditCountry(String[] p_args, String p_operation)
    {
        if ("-add".equals(p_operation) && p_args.length == 4) {
            String l_countryID = p_args[2].replace("\"", "");
            String l_continentID = p_args[3].replace("\"", "");
            GameDriver.getGameEngine().getPhase().editCountryAdd(l_countryID, l_continentID);
        } else if ("-remove".equals(p_operation) && p_args.length == 3) {
            String l_countryID = p_args[2].replace("\"", "");
            GameDriver.getGameEngine().getPhase().editCountryRemove(l_countryID);
        } else {
            System.out.println("Operation not recognized");
        }
    }

    /**
     * Processes EditNeighbor Command line string
     * @param p_args the string array of arguments passed
     * @param p_operation the string representing the operation
     */
    private void processEditNeighbor(String[] p_args, String p_operation)
    {
        if ("-add".equals(p_operation) && p_args.length == 4) {
            String l_countryID = p_args[2].replace("\"", "");
            String l_neighborCountryID = p_args[3].replace("\"", "");
            GameDriver.getGameEngine().getPhase().editNeighborAdd(l_countryID, l_neighborCountryID);
        } else if ("-remove".equals(p_operation) && p_args.length == 4) {
            String l_countryID = p_args[2].replace("\"", "");
            String l_neighborCountryID = p_args[3].replace("\"", "");
            GameDriver.getGameEngine().getPhase().editNeighborRemove(l_countryID, l_neighborCountryID);
        } else {
            System.out.println("Operation not recognized");
        }
    }

    /**
     * Processes Deploy Command line string
     * @param p_args the string array of arguments passed
     */
    private void processDeploy(String[] p_args)
    {
        String l_countryID = p_args[1].replace("\"", "");
        int l_toDeploy = Integer.parseInt(p_args[2]);
        GameDriver.getGameEngine().getPhase().deploy(l_countryID, l_toDeploy);
    }

    /**
     * Processes Advance Command line string
     * @param p_args the string array of arguments passed
     */
    private void processAdvance(String[] p_args)
    {
        String l_countryNameFrom = p_args[1].replace("\"", "");
        String l_countryNameTo = p_args[2].replace("\"", "");
        int l_toAdvance = Integer.parseInt(p_args[3]);
        GameDriver.getGameEngine().getPhase().advance(l_countryNameFrom, l_countryNameTo, l_toAdvance);
    }

    /**
     * Processes Airlift Command line string
     * @param p_args the string array of arguments passed
     */
    private void processAirlift(String[] p_args)
    {
        String l_sourceCountryID = p_args[1].replace("\"", "");
        String l_targetCountryID = p_args[2].replace("\"", "");
        int l_numArmies = Integer.parseInt(p_args[3]);
        GameDriver.getGameEngine().getPhase().airlift(l_sourceCountryID, l_targetCountryID, l_numArmies);
    }

    /**
     * Processes GamePlayer Command line string
     * @param p_args the string array of arguments passed
     * @param p_operation the string representing the operation
     */
    private void processGamePlayer(String[] p_args, String p_operation)
    {
        String l_playername = p_args[2];
        if ("-add".equals(p_operation)) {
            if (p_args.length == 3)
            {
                GameDriver.getGameEngine().getPhase().gamePlayerAdd(l_playername, HUMAN);
            }
            else if (p_args.length == 5 && p_args[3].equals("-behavior"))
            {
                try {
                    PlayerBehaviorType l_playerBehavior = PlayerBehaviorType.valueOf(p_args[4].toUpperCase());
                    GameDriver.getGameEngine().getPhase().gamePlayerAdd(l_playername, l_playerBehavior);
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
        if ("-remove".equals(p_operation) && p_args.length == 3) {
            GameDriver.getGameEngine().getPhase().gamePlayerRemove(l_playername);
        }
    }

    private void processTournament(String[] p_args)
    {
        List<String> l_listOfMapFiles = Arrays.stream(p_args[2].split(",")).toList();
        List<String> l_listOfPlayerStrategies = Arrays.stream(p_args[4].split(",")).toList();
        int l_numberOfGames = Integer.parseInt(p_args[6]);
        int l_maxNumberOfTurns = Integer.parseInt(p_args[8]);

        GameDriver.getGameEngine().getPhase().tournament(l_listOfMapFiles, l_listOfPlayerStrategies, l_numberOfGames,
                l_maxNumberOfTurns);
    }
}
