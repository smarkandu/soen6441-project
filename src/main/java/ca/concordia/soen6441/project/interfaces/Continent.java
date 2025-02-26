package ca.concordia.soen6441.project.interfaces;

import java.util.List;

public interface Continent {
    String getID();
    int getValue();
    int getNumericID();
    void addCountry(Country country);
    void removeCountry(Country country);
}
