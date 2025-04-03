package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.gameplay.behavior.PlayerBehavior;

public abstract class ComputerPlayerBehavior implements PlayerBehavior {
    /**
     * {@inheritDoc}
     */
    @Override
    public void issue_order(Player p_player) {
        deployment(p_player);
        attackTransfer(p_player);
        p_player.getPlayerManager().getGameEngine().getPhase().next();
    }

    /**
     * Issue deployment orders
     * @param p_player The player to whom the order corresponds to
     */
    public abstract void deployment(Player p_player);

    /**
     * Issue attack / transfer orders
     * @param p_player The player to whom the order corresponds to
     */
    public abstract void attackTransfer(Player p_player);
}
