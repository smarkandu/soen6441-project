package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.map.InvalidMapFileException;

import java.io.FileNotFoundException;

public interface MapContext {
    // Required
    void loadMap(String p_filename) throws InvalidMapFileException, FileNotFoundException;
    void showMap(boolean p_isDetailed);
    boolean isMapValid();

    void resetMap();
    boolean isMapEmpty();
}
