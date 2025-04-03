package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.interfaces.Player;

/**
 * Class which implements the Strategy design pattern for the Random player behavior
 */
public class RandomPlayerBehavior extends ComputerPlayerBehavior {
    /**
     * {@inheritDoc}
     */
    @Override
    public void deployment(Player p_player) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attackTransfer(Player p_player) {

    }

    /**
     * String representing the object
     */
    @Override
    public String toString() {
        return "Random";
    }
}
