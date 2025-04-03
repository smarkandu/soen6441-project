package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.gameplay.behavior.PlayerBehavior;
import ca.concordia.soen6441.project.ui.CommandLineInterface;

import java.io.Serializable;

/**
 * Class which implements the Strategy design pattern for the Human player behavior
 */
public class HumanPlayerBehavior implements PlayerBehavior, Serializable {
    /**
     * {@inheritDoc}
     */
    @Override
    public void issue_order(Player p_player) {
        CommandLineInterface l_commandLineInterface = new CommandLineInterface(p_player.getPlayerManager().getGameEngine());
        l_commandLineInterface.getInputFromUserAndProcess();
    }

    /**
     * String representing the object
     */
    @Override
    public String toString() {
        return "Human";
    }
}
