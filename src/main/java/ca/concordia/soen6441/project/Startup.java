package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.interfaces.Player;

import java.util.ArrayList;

public class Startup extends Play {
    private CountryAssignment d_countryAssignment;

    public Startup(GameEngine p_gameEngine)
    {
        super(p_gameEngine);
        d_countryAssignment = new CountryAssignment(d_gameEngine);
    }

    public void loadMap(String p_filename)
    {
        printInvalidCommandMessage();
    }

    @Override
    public void assignCountries() {
        d_countryAssignment.assignCountries();
        System.out.println("Countries have been assigned.");

        // ✅ Move to MainPlay phase AFTER assigning countries
        d_gameEngine.setPhase(new MainPlay(d_gameEngine) {
            @Override
            public void gamePlayerAdd(String p_playerName) {
                System.out.println("Cannot add players in MainPlay phase.");
            }

            @Override
            public void gamePlayerRemove(String p_playerName) {
                System.out.println("Cannot remove players in MainPlay phase.");
            }

            @Override
            public void next() {
                System.out.println("Proceeding to the next phase...");
            }
        });

        System.out.println("Game phase transitioned to MainPlay.");
    }

    public void gamePlayerAdd(String p_playerName)
    {
        d_gameEngine.addPlayer(p_playerName);

    }

    public void gamePlayerRemove(String p_playerName)
    {
        d_gameEngine.removePlayer(p_playerName);
    }

    public void next() {
        printInvalidCommandMessage();
    }
}
