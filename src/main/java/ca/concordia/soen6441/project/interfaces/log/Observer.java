package ca.concordia.soen6441.project.interfaces.log;


import ca.concordia.soen6441.project.log.Observable;

public interface Observer {
    /**
     * Implementing the observer pattern - updates the log file based on changes in game.
     *
     * @param p_o object of the Observable class
     */
    void update(Observable p_o);
}
