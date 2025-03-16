package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.context.ContinentManager;
import ca.concordia.soen6441.project.context.CountryManager;
import ca.concordia.soen6441.project.context.PlayerManager;

public interface GameContext {
    ContinentManager getContinentManager();
    CountryManager getCountryManager();
    PlayerManager getPlayerManager();
}
