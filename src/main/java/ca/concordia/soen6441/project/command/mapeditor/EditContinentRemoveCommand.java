package ca.concordia.soen6441.project.command.mapeditor;

import ca.concordia.soen6441.project.interfaces.Command;
import ca.concordia.soen6441.project.interfaces.GameContext;

public class EditContinentRemoveCommand implements Command {
    private final String d_continentID;

    public EditContinentRemoveCommand(String p_continentID) {
        this.d_continentID = p_continentID;
    }

    @Override
    public void interpret(GameContext context) {
        context.removeContinent(d_continentID);
    }
}
