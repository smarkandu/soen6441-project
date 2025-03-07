package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;

/**
 * Represents a Deploy order in the game where a player deploys troops to a target territory.
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
     * @param p_toDeploy       The number of troops to deploy.
     */
    public Deploy(Player p_initiator, Country p_targetTerritory, int p_toDeploy) {
        this.d_targetTerritory = p_targetTerritory;
        this.d_toDeploy = p_toDeploy;
        this.d_initiator = p_initiator;
    }

    /**
     * Executes the deploy order, adding troops to the target territory if the order is valid.
     */
    @Override
    public void execute() {
        if (valid()) {
            this.d_targetTerritory.setTroops(this.d_targetTerritory.getTroops() + d_toDeploy);
            System.out.println(d_toDeploy + " troops of " + d_initiator.getName() + "'s army have deployed to "
                    + d_targetTerritory);
        }
    }

    /**
     * Validates whether the deploy order can be executed.
     * The target territory must be owned by the player who issued the order.
     *
     * @return {@code true} if the order is valid, {@code false} otherwise.
     */
    public boolean valid() {
        if (d_targetTerritory.getOwner().equals(d_initiator)) {
            return true;
        } else {
            System.out.println("Invalid order");
            return false;
        }
    }

    /**
     * Prints the details of the deploy order.
     */
    public void printOrder() {
        System.out.println("Deploy order issued by player " + this.d_initiator.getName());
        System.out.println("Deploy " + this.d_toDeploy + " to " + this.d_targetTerritory.getID());
    }

    /**
     * Gets the target territory for the deploy order.
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
     * Gets the player who issued the deploy order.
     *
     * @return The player issuing the order.
     */
    public Player get_initiator() {
        return d_initiator;
    }

    /**
     * Returns a string representation of the deploy order.
     *
     * @return A string representing the deploy order.
     */
    @Override
    public String toString() {
        return "{Deploy " +
                d_targetTerritory.getID() +
                ", " + d_toDeploy +
                "}";
    }
}
