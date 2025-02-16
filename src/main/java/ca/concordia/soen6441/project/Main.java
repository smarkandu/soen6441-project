package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Command;
import ca.concordia.soen6441.project.interfaces.GameContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean l_continue_playing = true;
        GameContext l_game_context = OverallFactory.getInstance().CreateGameContext();
        Scanner l_scanner = new Scanner(System.in);

        while (l_continue_playing)
        {
            System.out.print(">");
            String[] l_args = l_scanner.nextLine().split(" ");
            String l_action = l_args[0].toLowerCase();
            String l_operation = l_args.length > 1? l_args[1].toLowerCase(): null;
            Command commandToRun = null;

            switch (l_action)
            {
                case "editcontinent":
                    if ("-add".equals(l_operation) && l_args.length == 4)
                    {
                        String continentID = l_args[2].replace("\"", "");
                        int continentValue = Integer.parseInt(l_args[3]);
                        commandToRun = OverallFactory.getInstance().CreateEditContinentAddCommand(continentID, continentValue);
                    }
                    else if ("-remove".equals(l_operation) && l_args.length == 3)
                    {
                        String continentID = l_args[2].replace("\"", "");
                        commandToRun = OverallFactory.getInstance().CreateEditContinentRemoveCommand(continentID);
                    }
                    else
                    {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "editcountry":
                    if ("-add".equals(l_operation) && l_args.length == 4)
                    {
                        String countryID = l_args[2].replace("\"", "");
                        String continentID = l_args[3].replace("\"", "");
                        commandToRun = OverallFactory.getInstance().CreateEditCountryAddCommand(countryID, continentID);
                    }
                    else if ("-remove".equals(l_operation) && l_args.length == 3)
                    {
                        String countryID = l_args[2].replace("\"", "");
                        commandToRun = OverallFactory.getInstance().CreateEditCountryRemoveCommand(countryID);
                    }
                    else
                    {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "editneighbor":
                    if ("-add".equals(l_operation) && l_args.length == 4)
                    {
                        String countryID = l_args[2].replace("\"", "");
                        String neighborCountryID = l_args[3].replace("\"", "");
                        commandToRun = OverallFactory.getInstance().CreateEditNeighborAddCommand(countryID, neighborCountryID);
                    }
                    else if ("-remove".equals(l_operation) && l_args.length == 4)
                    {
                        String countryID = l_args[2].replace("\"", "");
                        String neighborCountryID = l_args[3].replace("\"", "");
                        commandToRun = OverallFactory.getInstance().CreateEditNeighborRemoveCommand(countryID, neighborCountryID);
                    }
                    else
                    {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "showmap":
                    commandToRun = OverallFactory.getInstance().CreateShowMapCommand();
                    break;
                case "savemap":
                    // commandToRun = OverallFactory.getInstance().CreateSaveMapCommand();
                    break;
                case "assigncountries":
                    // commandToRun = OverallFactory.getInstance().CreateAssignCountriesCommand();
                    break;
                case "deploy":
                    // commandToRun = OverallFactory.getInstance().CreateDeployCommand();
                    break;
                case "gameplayer":
                    // commandToRun = OverallFactory.CreateGamePlayerCommand();
                    break;
                case "loadmap":
                    // commandToRun = OverallFactory.CreateLoadMapCommand();
                    break;
                case "exit":
                    System.exit(1);
                    break;
                default:
                    System.out.println("Command not recognized");
                    break;
            }

            if (commandToRun != null)
            {
                commandToRun.interpret(l_game_context);
            }
        }

    }
}