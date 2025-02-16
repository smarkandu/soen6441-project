package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.command.GameContextImpl;
import ca.concordia.soen6441.project.command.gameplay.*;
import ca.concordia.soen6441.project.command.mapeditor.*;
import ca.concordia.soen6441.project.command.mapeditor.ShowMapCommand;
import ca.concordia.soen6441.project.interfaces.Command;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.GameContext;

import java.util.List;
import java.util.TreeMap;

public class OverallFactory {
    private static OverallFactory instance = null;

    private OverallFactory()
    {
    }

    public static OverallFactory getInstance()
    {
        if (instance == null)
        {
            instance = new OverallFactory();
        }

        return instance;
    }

    public Command CreateEditContinentAddCommand(String p_continentID, int p_continentValue)
    {
        return new EditContinentAddCommand(p_continentID, p_continentValue);
    }

    public Command CreateEditContinentRemoveCommand(String p_continentID)
    {
        return new EditContinentRemoveCommand(p_continentID);
    }

    public Command CreateEditCountryAddCommand(String p_country, String p_continentID)
    {
        return new EditCountryAddCommand(p_country, p_continentID);
    }

    public Command CreateEditCountryRemoveCommand(String p_country)
    {
        return new EditCountryRemoveCommand(p_country);
    }

    public Command CreateEditNeighborAddCommand(String p_country, String p_neighborCountryID)
    {
        return new EditNeighborAddCommand(p_country, p_neighborCountryID);
    }

    public Command CreateEditNeighborRemoveCommand(String p_country, String p_neighborCountryID)
    {
        return new EditNeighborRemoveCommand(p_country, p_neighborCountryID);
    }

    public GameContext CreateGameContext()
    {
        return new GameContextImpl(new TreeMap<String, Continent>(), new TreeMap<String, Country>());
    }

    public Continent CreateContinent(String p_ID, int p_Value)
    {
        return new ContinentImpl(p_ID, p_Value);
    }

    public Country CreateCountry(String p_ID, String p_ContinentID, List<String> p_NeighborIDs)
    {
        return new CountryImpl(p_ID, p_ContinentID, p_NeighborIDs);
    }


    public Command CreateAssignCountriesCommand()
    {
        return new AssignCountriesCommand();
    }

    public Command CreateDeployCommand()
    {
        return new DeployCommand();
    }

    public Command CreateGamePlayerCommand()
    {
        return new GamePlayerCommand();
    }

    public Command CreateLoadMapCommand()
    {
        return new LoadMapCommand();
    }

    public Command CreateShowMapCommand() {
        return new ShowMapCommand();
    }
}
