package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;

import java.util.ArrayList;
import java.util.List;

public class ContinentImpl implements Continent {
    private final String d_ID;
    private final int d_Value;
    private static int d_Counter = 0;
    private final int d_numericID;
    private final String d_color;
    private List<Country> d_countries;

    /**
     * Constructor (to be used when loading a .map file)
     * @param p_numericID
     * @param p_ID
     * @param p_Value
     */
    public ContinentImpl(int p_numericID, String p_ID, int p_Value, String p_color) {
        this.d_ID = p_ID;
        this.d_Value = p_Value;
        this.d_numericID = p_numericID;
        if (p_numericID > d_Counter)
        {
            d_Counter = p_numericID;
        }
        this.d_color = p_color;
    }

    public ContinentImpl(String p_ID, int p_Value) {
        this.d_ID = p_ID;
        this.d_Value = p_Value;
        this.d_numericID = ++d_Counter;
        this.d_color = "yellow"; // Hardcoded
    }

    public void addCountry(Country country) {d_countries.add(country);}

    public void removeCountry(Country country){
        d_countries.remove(country);
    }

    @Override
    public String getID() {
        return d_ID;
    }

    @Override
    public int getValue() {
        return d_Value;
    }

    @Override
    public int getNumericID() {
        return d_numericID;
    }

    @Override
    public String toString() {
        return d_ID + "=" + d_Value;
    }

    public static void resetCounter()
    {
        d_Counter = 0;
    }
}
