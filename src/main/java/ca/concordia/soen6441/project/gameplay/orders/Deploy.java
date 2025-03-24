package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

/**
 * The Deploy class represents a deploy order where a player moves a specific number of troops
 * to a target territory they own.
 */
public class Deploy implements Order {
    private Country d_targetTerritory;
    private int d_toDeploy;
    private Player d_initiator;

    /**
     * Constructs a Deploy order.
     *
     * @param p_initiator      The player issuing the deploy order.
     * @param p_targetTerritory The target territory where troops will be deployed.
     * @param p_toDeploy       The number of troops to be deployed.
     */
    public Deploy(Player p_initiator, Country p_targetTerritory, int p_toDeploy) {
        this.d_targetTerritory = p_targetTerritory;
        this.d_toDeploy = p_toDeploy;
        this.d_initiator = p_initiator;
    }

    /**
     * Executes the deploy order if it is valid.
     * Adds the specified number of troops to the target territory.
     */
    @Override
    public void execute() {
        if (valid()) {
            this.d_targetTerritory.setTroops(this.d_targetTerritory.getTroops() + d_toDeploy);
            LogEntryBuffer.getInstance().appendToBuffer(d_initiator.getName() + "'s army have deployed " + d_toDeploy + " troops to "
                    + d_targetTerritory.getID(), true);
        }
    }

    /**
     * Validates whether the deploy order can be executed.
     *
     * @return true if the order is valid (i.e., the player owns the target territory), otherwise false.
     */
    public boolean valid() {
        if (d_targetTerritory.getOwner().equals(d_initiator)) {
            // The target territory must belong to the player that created the order
            return true;
        } else {
            System.out.println("invalid order");
            return false;
        }
    }

    /**
     * Prints the deploy order details.
     */
    public void printOrder() {
        System.out.println("Deploy order issued by player " + this.d_initiator.getName());
        System.out.println("Deploy " + this.d_toDeploy + " to " + this.d_targetTerritory.getID());
    }

    /**
     * Gets the target territory where troops will be deployed.
     *
     * @return The target territory.
     */
    public Country get_target_territory() {
        return d_targetTerritory;
    }

    /**
     * Gets the number of troops to be deployed.
     *
     * @return The number of troops.
     */
    public int get_to_deploy() {
        return d_toDeploy;
    }

    /**
     * Gets the player issuing the deploy order.
     *
     * @return The player initiating the deploy order.
     */
    public Player get_initiator() {
        return d_initiator;
    }

    /**
     * Returns a string representation of the deploy order.
     *
     * @return A formatted string describing the deploy order.
     */
    @Override
    public String toString() {
        return "{Deploy " +
                d_targetTerritory.getID() +
                "," + d_toDeploy +
                "}";
    }
}
