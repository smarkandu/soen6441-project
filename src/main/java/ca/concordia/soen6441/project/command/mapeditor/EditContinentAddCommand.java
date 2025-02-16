package ca.concordia.soen6441.project.command.mapeditor;

import ca.concordia.soen6441.project.interfaces.Command;
import ca.concordia.soen6441.project.interfaces.GameContext;

public class EditContinentAddCommand implements Command {
    private final String d_continentID;
    private final int d_continentValue;

    public EditContinentAddCommand(String p_continentID, int p_continentValue) {
        this.d_continentID = p_continentID;
        this.d_continentValue = p_continentValue;
    }

    @Override
    public void interpret(GameContext context) {
        context.addContinent(d_continentID, d_continentValue);
    }
}
