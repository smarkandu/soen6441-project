package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Command;

import java.util.Scanner;

public class GameEngine {
    private Phase gamePhase;

    public void setPhase(Phase p_phase) {
        gamePhase = p_phase;
    }

    public void start() {
        // Can change the state of the Context (GameEngine) object, e.g.
        setPhase(new PreLoad(this));
//        setPhase(new PostLoad(this));
//        setPhase(new Startup(this));
//        setPhase(new IssueOrder(this));

        boolean l_continuePlaying = true;
        Scanner l_scanner = new Scanner(System.in);

        while (l_continuePlaying) {
            System.out.print(">");
            String[] l_args = l_scanner.nextLine().split(" ");
            String l_action = l_args[0].toLowerCase();
            String l_operation = l_args.length > 1 ? l_args[1].toLowerCase() : null;
            Command l_commandToRun = null;

            switch (l_action) {
                case "editcontinent":
                    if ("-add".equals(l_operation) && l_args.length == 4) {
                        String l_continentID = l_args[2].replace("\"", "");
                        int l_continentValue = Integer.parseInt(l_args[3]);

                        // Command that edits context
                        // l_commandToRun = OverallFactory.getInstance().CreateEditContinentAddCommand(l_continentID, l_continentValue);
                        gamePhase.editContinent();
                    } else if ("-remove".equals(l_operation) && l_args.length == 3) {
                        String l_continentID = l_args[2].replace("\"", "");
                        gamePhase.editContinent();
                    } else {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "editcountry":
                    if ("-add".equals(l_operation) && l_args.length == 4) {
                        String l_countryID = l_args[2].replace("\"", "");
                        String l_continentID = l_args[3].replace("\"", "");
                        gamePhase.editCountry();
                    } else if ("-remove".equals(l_operation) && l_args.length == 3) {
                        String l_countryID = l_args[2].replace("\"", "");
                        gamePhase.editCountry();
                    } else {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "editneighbor":
                    if ("-add".equals(l_operation) && l_args.length == 4) {
                        String l_countryID = l_args[2].replace("\"", "");
                        String l_neighborCountryID = l_args[3].replace("\"", "");
                        gamePhase.editNeighbor();
                    } else if ("-remove".equals(l_operation) && l_args.length == 4) {
                        String l_countryID = l_args[2].replace("\"", "");
                        String l_neighborCountryID = l_args[3].replace("\"", "");
                        gamePhase.editNeighbor();
                    } else {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "showmap":
                    l_commandToRun = OverallFactory.getInstance().CreateShowMapCommand();
                    gamePhase.showMap();
                    break;
                case "savemap":
                    // l_commandToRun = OverallFactory.getInstance().CreateSaveMapCommand();
                    gamePhase.saveMap();
                    break;
                case "assigncountries":
                    // l_commandToRun = OverallFactory.getInstance().CreateAssignCountriesCommand();
                    gamePhase.assignCountries();
                    break;
                case "deploy":
                    // l_commandToRun = OverallFactory.getInstance().CreateDeployCommand();
                    // TODO
                    break;
                case "gameplayer":
                    // l_commandToRun = OverallFactory.CreateGamePlayerCommand();
                    gamePhase.setPlayers();
                    break;
                case "loadmap":
                    // l_commandToRun = OverallFactory.CreateLoadMapCommand();
                    gamePhase.loadMap();
                    break;
                case "exit":
                    gamePhase.endGame();
                    l_continuePlaying = false;
                    break;
                default:
                    System.out.println("Command not recognized");
                    break;

                // Can trigger State-dependent behavior by using
                // the methods defined in the State (Phase) object, e.g.
                //        gamePhase.loadMap();
                //        gamePhase.next();
            }
        }
    }
}