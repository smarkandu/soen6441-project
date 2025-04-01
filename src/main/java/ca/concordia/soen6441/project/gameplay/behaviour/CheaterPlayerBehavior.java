package ca.concordia.soen6441.project.gameplay.behaviour;

/**
 * Class which implements the Strategy design pattern for the Cheater player behavior
 */
public class CheaterPlayerBehavior extends ComputerPlayerBehavior {
    /**
     * {@inheritDoc}
     */
    @Override
    public void deployment() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attackTransfer() {

    }

    /**
     * String representing the object
     */
    @Override
    public String toString() {
        return "Cheater";
    }
}
