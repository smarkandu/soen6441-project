package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.Country;

import java.util.List;

public class ContinentImpl implements Continent {
    private String d_ID;
    private int d_Value;

    public ContinentImpl(String p_ID, int p_Value) {
        this.d_ID = p_ID;
        this.d_Value = p_Value;
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
    public String toString() {
        return d_ID + "=" + d_Value;
    }
}
