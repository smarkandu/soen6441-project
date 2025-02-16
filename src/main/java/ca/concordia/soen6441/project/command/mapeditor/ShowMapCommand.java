package ca.concordia.soen6441.project.command.mapeditor;

import ca.concordia.soen6441.project.interfaces.Command;
import ca.concordia.soen6441.project.interfaces.GameContext;

public class ShowMapCommand implements Command{

    @Override
    public void interpret(GameContext context) {
        context.showMap();
    }
}
