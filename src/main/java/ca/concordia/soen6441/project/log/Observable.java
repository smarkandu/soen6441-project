package ca.concordia.soen6441.project.log;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private List<Observer> d_observers = new ArrayList<>();

    public void attach(Observer p_observer) {
        d_observers.add(p_observer);
    }

    public void detach(Observer p_observer) {
        if(!d_observers.isEmpty()) {
            d_observers.remove(p_observer);
        }
    }

    public void notifyObservers(Observable p_observable) {
        for(Observer l_observer : d_observers) {
            l_observer.update(p_observable);
        }
    }
}
