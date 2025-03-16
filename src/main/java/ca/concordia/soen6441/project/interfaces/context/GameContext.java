package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.context.ContinentManager;
import ca.concordia.soen6441.project.context.CountryManager;

public interface GameContext extends PlayerContext {
    ContinentManager getContinentManager();
    CountryManager getCountryManager();
}
