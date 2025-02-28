package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerImpl implements Player {
    private String d_name;
    private ArrayList<String> d_ownedCountries;
    private ArrayList<Order> d_Orders;
    int d_Reinforcements;

    public PlayerImpl(String p_name, ArrayList<String> p_ownedCountries, ArrayList<Order> p_Orders) {
        this.d_name = p_name;
        this.d_ownedCountries = p_ownedCountries;
        this.d_Orders = p_Orders;
        this.d_Reinforcements = 0;
    }

    @Override
    public String getName() {
        return d_name;
    }

    @Override
    public List<String> getOwnedCountries() {
        return d_ownedCountries;
    }

    @Override
    public List<Order> getOrders() {
        return d_Orders;
    }

    @Override
    public void issue_order(Order order) {
        d_Orders.add(order);
    }

    @Override
    public Order next_order() {
        if (!d_Orders.isEmpty()) {
            Order l_to_return = this.d_Orders.get(0);
            this.d_Orders.remove(0);
            return l_to_return;
        } else
            return null;
    }

    @Override
    public int getTotalNumberOfReinforcementsPerTurn() {
        int l_returnValue = 5;

        // TODO: Get Bonuses if any continents are fully owned

        return l_returnValue;
    }
    @Override
    public void assignCountry(Country p_country) {
        d_ownedCountries.add(p_country.getID());
    }

    @Override
    public int getReinforcements() {
        return d_Reinforcements;
    }

    @Override
    public void setReinforcements(int p_Reinforcements) {
        d_Reinforcements = p_Reinforcements;
    }

    @Override
    public int getNumberOfTroopsOrderedToDeploy() {
        int returnValue = 0;
        for (int i = 0; i < d_Orders.size(); i++)
        {
            if (d_Orders.get(i).getClass().getSimpleName().equals("Deploy"))
            {
                Deploy deploy = (Deploy) d_Orders.get(i);
                returnValue += deploy.get_to_deploy();
            }
        }

        return returnValue;
    }
}

