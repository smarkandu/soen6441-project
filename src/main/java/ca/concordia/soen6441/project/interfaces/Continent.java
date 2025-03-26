package ca.concordia.soen6441.project.interfaces;

/**
 * Represents a continent in the game map.
 * A continent is a collection of countries with an associated bonus value.
 */
public interface Continent {
    /**
     * Gets the string identifier of the continent.
     *
     * @return The continent's ID as a String.
     */
    String getID();

    /**
     * Retrieves the bonus value (control value) associated with the continent.
     *
     * @return the control value (bonus value)
     */
    int getValue();

    /**
     * Gets the numeric identifier of the continent.
     *
     * @return The continent's numeric ID.
     */
    int getNumericID();

    /**
     * Provides a string representation of a continent with a specific format.
     *
     * @return A string representing the continent in the domination map format.
     */
    String toMapString();
}
