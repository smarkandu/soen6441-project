package ca.concordia.soen6441.project.log;

import ca.concordia.soen6441.project.interfaces.log.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Observable in the Observer Pattern
 */
public class Observable {
    private List<Observer> d_observers = new ArrayList<>();

    /**
     * This method adds an observer in the observer list
     * @param p_observer an observer
     */
    public void attach(Observer p_observer) {
        d_observers.add(p_observer);
    }

    /**
     * This method removes an observer from the observer list
     * @param p_observer an observer
     */
    public void detach(Observer p_observer) {
        if(!d_observers.isEmpty()) {
            d_observers.remove(p_observer);
        }
    }

    /**
     * This method notify all the observer of this change of state of this object
     * @param p_observable an object observable
     */
    public void notifyObservers(Observable p_observable) {
        for(Observer l_observer : d_observers) {
            l_observer.update(p_observable);
        }
    }
}
