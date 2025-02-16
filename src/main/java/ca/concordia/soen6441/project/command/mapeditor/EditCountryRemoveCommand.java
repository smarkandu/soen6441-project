package ca.concordia.soen6441.project.command.mapeditor;

import ca.concordia.soen6441.project.interfaces.Command;
import ca.concordia.soen6441.project.interfaces.GameContext;

public class EditCountryRemoveCommand implements Command {
    private final String d_countryID;

    public EditCountryRemoveCommand(String p_countryID) {
        this.d_countryID = p_countryID;
    }

    @Override
    public void interpret(GameContext p_context) {
        p_context.removeCountry(d_countryID);
    }
}
