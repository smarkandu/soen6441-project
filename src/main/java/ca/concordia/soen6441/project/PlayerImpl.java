package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.GameContext;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerImpl implements Player {
    private String d_name;
    private ArrayList<String> d_ownedCountries;
    private ArrayList<Order> d_Orders;
    private GameContext d_gameContext;

    public PlayerImpl(String p_name, ArrayList<String> p_ownedCountries, ArrayList<Order> p_Orders) {
        this.d_name = p_name;
        this.d_ownedCountries = p_ownedCountries;
        this.d_Orders = p_Orders;
        // this.d_gameContext = GameContextImpl.getInstance();
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
    public void issue_order() {
        Scanner l_scanner = new Scanner(System.in);

        System.out.print("Order>");
        String[] l_args = l_scanner.nextLine().split(" ");
        String l_action = l_args[0].toLowerCase();

        if (l_action.equalsIgnoreCase("deploy") && l_args.length == 3) {
            String l_countryID = l_args[1];
            int l_num = Integer.parseInt(l_args[2]);
        }

        //Order deployOrder = new DeployOrder();
        //d_Orders.add(deployOrder);
    }

    @Override
    public Order next_order() {
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
        d_ownedCountries.add(p_country.getID());  // ✅ Store country ID instead of object
    }

}

