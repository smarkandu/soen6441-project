package ca.concordia.soen6441.project.command.mapeditor;

import ca.concordia.soen6441.project.interfaces.Command;
import ca.concordia.soen6441.project.interfaces.GameContext;

import java.util.ArrayList;

public class EditCountryAddCommand implements Command {
    private final String d_countryID;
    private final String d_continentID;

    public EditCountryAddCommand(String p_countryID, String p_continentID) {
        this.d_countryID = p_countryID;
        this.d_continentID = p_continentID;
    }

    @Override
    public void interpret(GameContext p_context) {
        p_context.addCountry(d_countryID, d_continentID, new ArrayList<String>());
    }
}
