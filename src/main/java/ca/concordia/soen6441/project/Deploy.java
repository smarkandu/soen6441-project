package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;

public class Deploy implements Order {
    private Country d_targetTerritory;
    private int d_toDeploy;
    private Player d_initiator;

    public Deploy(Player p_initiator, Country p_targetTerritory, int p_toDeploy) {
        this.d_targetTerritory = p_targetTerritory;
        this.d_toDeploy = p_toDeploy;
        this.d_initiator = p_initiator;
    }

    @Override
    public void execute() {
        if (valid())
            this.d_targetTerritory.setTroops(this.d_targetTerritory.getTroops() + 1);
    }

    public boolean valid() {
        if (d_targetTerritory.getOwner().equals(d_initiator))
            // the target territory must belong to the player that created the order
            return true;
        else {
            System.out.println("invalid order");
            return false;
        }
    }

   public void printOrder() {
        System.out.println("Deploy order issued by player " + this.d_initiator.getName());
        System.out.println("Deploy " + this.d_toDeploy + " to " + this.d_targetTerritory.getID());
    }

    public Country get_target_territory() {
        return d_targetTerritory;
    }

    public int get_to_deploy() {
        return d_toDeploy;
    }

    public Player get_initiator() {
        return d_initiator;
    }

    @Override
    public String toString() {
        return "{Deploy " +
                d_targetTerritory.getID() +
                "," + d_toDeploy +
                "}";
    }
}
