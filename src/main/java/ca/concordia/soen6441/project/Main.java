package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Command;
import ca.concordia.soen6441.project.interfaces.GameContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean l_continuePlaying = true;
        GameContext l_gameContext = OverallFactory.getInstance().CreateGameContext();
        Scanner l_scanner = new Scanner(System.in);

        while (l_continuePlaying)
        {
            System.out.print(">");
            String[] l_args = l_scanner.nextLine().split(" ");
            String l_action = l_args[0].toLowerCase();
            String l_operation = l_args.length > 1? l_args[1].toLowerCase(): null;
            Command l_commandToRun = null;

            switch (l_action)
            {
                case "editcontinent":
                    if ("-add".equals(l_operation) && l_args.length == 4)
                    {
                        String l_continentID = l_args[2].replace("\"", "");
                        int l_continentValue = Integer.parseInt(l_args[3]);
                        l_commandToRun = OverallFactory.getInstance().CreateEditContinentAddCommand(l_continentID, l_continentValue);
                    }
                    else if ("-remove".equals(l_operation) && l_args.length == 3)
                    {
                        String l_continentID = l_args[2].replace("\"", "");
                        l_commandToRun = OverallFactory.getInstance().CreateEditContinentRemoveCommand(l_continentID);
                    }
                    else
                    {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "editcountry":
                    if ("-add".equals(l_operation) && l_args.length == 4)
                    {
                        String l_countryID = l_args[2].replace("\"", "");
                        String l_continentID = l_args[3].replace("\"", "");
                        l_commandToRun = OverallFactory.getInstance().CreateEditCountryAddCommand(l_countryID, l_continentID);
                    }
                    else if ("-remove".equals(l_operation) && l_args.length == 3)
                    {
                        String l_countryID = l_args[2].replace("\"", "");
                        l_commandToRun = OverallFactory.getInstance().CreateEditCountryRemoveCommand(l_countryID);
                    }
                    else
                    {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "editneighbor":
                    if ("-add".equals(l_operation) && l_args.length == 4)
                    {
                        String l_countryID = l_args[2].replace("\"", "");
                        String l_neighborCountryID = l_args[3].replace("\"", "");
                        l_commandToRun = OverallFactory.getInstance().CreateEditNeighborAddCommand(l_countryID, l_neighborCountryID);
                    }
                    else if ("-remove".equals(l_operation) && l_args.length == 4)
                    {
                        String l_countryID = l_args[2].replace("\"", "");
                        String l_neighborCountryID = l_args[3].replace("\"", "");
                        l_commandToRun = OverallFactory.getInstance().CreateEditNeighborRemoveCommand(l_countryID, l_neighborCountryID);
                    }
                    else
                    {
                        System.out.println("Operation not recognized");
                    }
                    break;
                case "showmap":
                    l_commandToRun = OverallFactory.getInstance().CreateShowMapCommand();
                    break;
                case "savemap":
                    // l_commandToRun = OverallFactory.getInstance().CreateSaveMapCommand();
                    break;
                case "assigncountries":
                    // l_commandToRun = OverallFactory.getInstance().CreateAssignCountriesCommand();
                    break;
                case "deploy":
                    // l_commandToRun = OverallFactory.getInstance().CreateDeployCommand();
                    break;
                case "gameplayer":
                    // l_commandToRun = OverallFactory.CreateGamePlayerCommand();
                    break;
                case "loadmap":
                    // l_commandToRun = OverallFactory.CreateLoadMapCommand();
                    break;
                case "exit":
                    l_continuePlaying = false;
                    break;
                default:
                    System.out.println("Command not recognized");
                    break;
            }

            if (l_commandToRun != null)
            {
                l_commandToRun.interpret(l_gameContext);
            }
        }

    }
}