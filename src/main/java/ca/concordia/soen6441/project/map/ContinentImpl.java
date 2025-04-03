package ca.concordia.soen6441.project.map;

import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.MapComponent;

import java.io.Serializable;

/**
 * Implementation of the Continent interface, representing a continent in the game map.
 */
public class ContinentImpl implements Continent, MapComponent, Serializable {
    private final String d_ID;
    private final int d_Value;
    private static int d_Counter = 0;
    private final int d_numericID;
    private final String d_color;

    /**
     * Constructor to create a ContinentImpl object.
     *
     * @param p_numericID The numeric identifier of the continent.
     * @param p_ID        The string identifier of the continent.
     * @param p_Value     The control value of the continent.
     * @param p_color     The color representation of the continent.
     */
    public ContinentImpl(int p_numericID, String p_ID, int p_Value, String p_color) {
        this.d_ID = p_ID;
        this.d_Value = p_Value;
        this.d_numericID = p_numericID;
        if (p_numericID > d_Counter) {
            d_Counter = p_numericID;
        }
        this.d_color = p_color;
    }

    /**
     * Constructor for a new continent instance with an auto-incremented numeric ID.
     *
     * @param p_ID    The string identifier of the continent.
     * @param p_Value The control value of the continent.
     */
    public ContinentImpl(String p_ID, int p_Value) {
        this.d_ID = p_ID;
        this.d_Value = p_Value;
        this.d_numericID = ++d_Counter;
        this.d_color = "yellow"; // Hardcoded
    }

    /**
     * Gets the string identifier of the continent.
     *
     * @return The continent's ID.
     */
    @Override
    public String getID() {
        return d_ID;
    }

    /**
     * Gets the control value of the continent.
     *
     * @return The continent's control value.
     */
    @Override
    public int getValue() {
        return d_Value;
    }

    /**
     * Gets the numeric identifier of the continent.
     *
     * @return The continent's numeric ID.
     */
    @Override
    public int getNumericID() {
        return d_numericID;
    }

    /**
     * Provides a string representation of the continent in the format "ID=Value".
     *
     * @return A formatted string representing the continent.
     */
    @Override
    public String toString() {
        return d_ID + "=" + d_Value;
    }

    /**
     * Resets the numeric ID counter for continent instances.
     */
    public static void resetCounter() {
        d_Counter = 0;
    }

    /**
     * Provides a string representation of a continent with a specific format.


     *
     * @return A string representing the continent in the domination map format.
     */
    @Override
    public String toMapString() {
        //Converts the continent's details into the required domination format.
        return d_ID + " " + d_Value + " " + d_color;
    }
}
