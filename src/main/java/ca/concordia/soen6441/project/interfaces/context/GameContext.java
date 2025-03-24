package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.context.DeckOfCards;
import ca.concordia.soen6441.project.interfaces.phases.State;
import ca.concordia.soen6441.project.map.InvalidMapFileException;
import ca.concordia.soen6441.project.phases.Phase;

import java.io.FileNotFoundException;

public interface GameContext {
    State getPhase();
    void setPhase(Phase p_phase);
    void showMap(boolean p_isDetailed);
    boolean isMapValid();
    String toMapString();
    void resetMap();
    void loadMap(String p_filename) throws InvalidMapFileException, FileNotFoundException;

    ContinentContext getContinentManager();
    CountryContext getCountryManager();
    NeighborContext getNeighborManager();
    PlayerContext getPlayerManager();
    DeckOfCards getDeckOfCards();
}
