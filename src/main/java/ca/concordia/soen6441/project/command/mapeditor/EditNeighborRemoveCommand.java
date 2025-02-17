package ca.concordia.soen6441.project.command.mapeditor;

import ca.concordia.soen6441.project.interfaces.Command;
import ca.concordia.soen6441.project.interfaces.GameContext;

public class EditNeighborRemoveCommand implements Command {
    private final String d_countryID;
    private final String d_neighborCountryID;

    public EditNeighborRemoveCommand(String p_countryID, String p_neighborCountryID) {
        this.d_countryID = p_countryID;
        this.d_neighborCountryID = p_neighborCountryID;
    }

    @Override
    public void interpret(GameContext p_context) {
        p_context.removeNeighbor(d_countryID, d_neighborCountryID);
    }
}
