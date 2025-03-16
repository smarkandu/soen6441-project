package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.context.ContinentManager;

public interface GameContext extends CountryContext, NeighborContext, PlayerContext {
    public ContinentManager getContinentManager();
}
