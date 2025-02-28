package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;

public class Deploy implements Order {
    private Country d_target_territory;
    private int d_to_deploy;
    private Player d_initiator;

    public Deploy(Player p_initiator, Country p_target_territory, int p_to_deploy) {
        this.d_target_territory = p_target_territory;
        this.d_to_deploy = p_to_deploy;
        this.d_initiator = p_initiator;
    }

    @Override
    public void execute() {
        if (valid())
            this.d_target_territory.setTroops(this.d_target_territory.getTroops() + 1);
    }

    public boolean valid() {
        if (d_target_territory.getOwner().equals(d_initiator))
            // the target territory must belong to the player that created the order
            return true;
        else {
            System.out.println("invalid order");
            return false;
        }
    }

   public void printOrder() {
        System.out.println("Deploy order issued by player " + this.d_initiator.getName());
        System.out.println("Deploy " + this.d_to_deploy + " to " + this.d_target_territory.getID());
    }

    public Country get_target_territory() {
        return d_target_territory;
    }

    public int get_to_deploy() {
        return d_to_deploy;
    }

    public Player get_initiator() {
        return d_initiator;
    }

    @Override
    public String toString() {
        return "{Deploy " +
                d_target_territory.getID() +
                "," + d_to_deploy +
                "}";
    }
}
