package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.gameplay.behavior.PlayerBehavior;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.io.Serializable;

public abstract class ComputerPlayerBehavior implements PlayerBehavior, Serializable
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void issue_order(Player p_player)
    {
        deployment(p_player);
        attackTransfer(p_player);
        LogEntryBuffer.getInstance().appendToBuffer(GameDriver.getGameEngine().getPhase().getPhaseName(), true);
        GameDriver.getGameEngine().getPhase().next();
    }

    /**
     * Issue deployment orders
     *
     * @param p_player The player to whom the order corresponds to
     */
    public abstract void deployment(Player p_player);

    /**
     * Issue attack / transfer orders
     *
     * @param p_player The player to whom the order corresponds to
     */
    public abstract void attackTransfer(Player p_player);
}
