package ca.concordia.soen6441.project.interfaces;

/**
 * Defines a command structure for executing game-related actions.
 * It allows different types of game actions to be encapsulated and executed later.
 * It follows the Command Pattern
 */
public interface Order {
    /**
     * Carry out a specific game action, such as deploying troops, attacking, or fortifying.
     */
    void execute();
}
