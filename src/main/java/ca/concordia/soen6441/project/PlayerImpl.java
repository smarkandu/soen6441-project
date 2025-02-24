package ca.concordia.soen6441.project; // Correct package path

import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.Order;

import java.util.ArrayList;
import java.util.List;

public class PlayerImpl implements Player {
    private String d_name;  // Player's name
    private List<String> d_ownedCountries;  // List of owned countries
    private List<Order> d_orders;  // List of issued orders
    private int d_reinforcements; //  Track reinforcements per player

    // Constructor: Initializes the player with a name
    public PlayerImpl(String p_name) {
        this.d_name = p_name;
        this.d_ownedCountries = new ArrayList<>();
        this.d_orders = new ArrayList<>();
        this.d_reinforcements = 0; // Default reinforcements start at 0
    }

    @Override
    public List<String> getOwnedCountries() {
        return d_ownedCountries;
    }

    @Override
    public List<Order> getOrders() {
        return d_orders;
    }

    @Override
    public void issue_order() {
        System.out.println(d_name + " is issuing an order...");
    }

    @Override
    public Order next_order() {
        return d_orders.isEmpty() ? null : d_orders.remove(0);
    }
    @Override
    public void addCountry(String p_country) {
        d_ownedCountries.add(p_country);
    }

    // Retrieves the number of reinforcements assigned to the player
     
    @Override
    public void addReinforcements(int p_reinforcements) {
        d_reinforcements += p_reinforcements;
    }
    @Override
    public int getReinforcements() {
        return d_reinforcements;
    }
    
    // Assign reinforcements to the player
    @Override        
    public void setReinforcements(int p_reinforcements) {
        this.d_reinforcements = p_reinforcements;
    }
    
    // Reduce reinforcements after deployment
    @Override
    public void reduceReinforcements(int p_armies) {
        if (p_armies > d_reinforcements) {
            throw new IllegalArgumentException("Insufficient reinforcements!");
        }
        d_reinforcements -= p_armies;
    }
    @Override
    // Get Player Name
    public String getName() {
        return d_name;
    }
}
