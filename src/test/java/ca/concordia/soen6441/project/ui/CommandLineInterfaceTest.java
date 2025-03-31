package ca.concordia.soen6441.project.ui;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.phases.Phase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType.HUMAN;
import static org.mockito.Mockito.*;

/**
 * Test cases created for GameDriver class.
 */
class CommandLineInterfaceTest {
    private GameContext d_mockGameEngine;
    private Phase d_mockPhase;
    private CommandLineInterface d_commandLineInterface;

    /**
     * Setup method for each testcase
     */
    @BeforeEach
    void setUp() {
        d_mockGameEngine = mock(GameEngine.class);
        d_mockPhase = mock(Phase.class);
        when(d_mockGameEngine.getPhase()).thenReturn(d_mockPhase);
        d_commandLineInterface = new CommandLineInterface(d_mockGameEngine);
    }

    /**
     * Verifies that given an appropriate input, editContinentAdd will be called
     * Exit command is run afterward to exit loop
     */
    @Test
    void testEditContinentAdd() {
        simulateUserInput("editcontinent -add \"Asia\" 5\nexit\n");
        verify(d_mockPhase, times(1)).editContinentAdd("Asia", 5);
    }

    /**
     * Verifies that given an appropriate input, editContinentRemove will be called
     * Exit command is run afterward to exit loop
     */
    @Test
    void testEditContinentRemove() {
        simulateUserInput("editcontinent -remove \"Asia\"\nexit\n");
        verify(d_mockPhase, times(1)).editContinentRemove("Asia");
    }

    /**
     * Verifies that given an appropriate input, testEditCountryAdd will be called
     * Exit command is run afterward to exit loop
     */
    @Test
    void testEditCountryAdd() {
        simulateUserInput("editcountry -add \"UnitedStates\" \"NorthAmerica\"\nexit\n");
        verify(d_mockPhase, times(1)).editCountryAdd("UnitedStates", "NorthAmerica");
    }

    /**
     * Verifies that given an appropriate input, testEditCountryRemove will be called
     * Exit command is run afterward to exit loop
     */
    @Test
    public void testEditCountryRemove() {
        simulateUserInput("editcountry -remove \"Canada\"\nexit\n");
        verify(d_mockPhase, times(1)).editCountryRemove("Canada");
    }

    /**
     * Verifies that given an appropriate input, testEditNeighborAdd will be called
     * Exit command is run afterward to exit loop
     */
    @Test
    void testEditNeighborAdd() {
        simulateUserInput("editneighbor -add \"UnitedStates\" \"Canada\"\nexit\n");
        verify(d_mockPhase, times(1)).editNeighborAdd("UnitedStates", "Canada");
    }

    /**
     * Verifies that given an appropriate input, testEditNeighborRemove will be called
     * Exit command is run afterward to exit loop
     */
    @Test
    public void testEditNeighborRemove() {
        simulateUserInput("editneighbor -remove \"UnitedStates\" \"Canada\"\nexit\n");
        verify(d_mockPhase, times(1)).editNeighborRemove("UnitedStates", "Canada");
    }

    /**
     * Verifies that given an appropriate input, showMap will be called
     * Exit command is run afterward to exit loop
     */
    @Test
    void testShowMap() {
        simulateUserInput("showmap\nexit\n");
        verify(d_mockPhase, times(1)).showMap();
    }

    /**
     * Verifies that given an appropriate input, deploy will be called
     * Exit command is run afterward to exit loop
     */
    @Test
    void testDeploy() {
        simulateUserInput("deploy \"France\" 10\nexit\n");
        verify(d_mockPhase, times(1)).deploy("France", 10);
    }

    /**
     * Verifies that given an appropriate input, advance will be called
     * Exit command is run afterward to exit loop
     */
    @Test
    void testAdvance() {
        simulateUserInput("advance \"Germany\" \"Belgium\" 5\nexit\n");
        verify(d_mockPhase, times(1)).advance("Germany", "Belgium", 5);
    }

    /**
     * Verifies that given an appropriate input, testAirlift will be called
     * Exit command is run afterward to exit loop
     */
    @Test
    void testAirlift() {
        simulateUserInput("airlift \"Germany\" \"Belgium\" 5\nexit\n");
        verify(d_mockPhase, times(1)).airlift("Germany", "Belgium", 5);
    }

    /**
     * Verifies that given an appropriate input, testGamePlayerAdd will be called
     * Exit command is run afterward to exit loop
     */
    @Test
    public void testGamePlayerAdd() {
        simulateUserInput("gameplayer -add Steven\nexit\n");
        verify(d_mockPhase, times(1)).gamePlayerAdd("Steven", HUMAN);
    }

    /**
     * Verifies that given an appropriate input, testGamePlayerRemove will be called
     * Exit command is run afterward to exit loop
     */
    @Test
    void testGamePlayerRemove() {
        simulateUserInput("gameplayer -remove Steven\nexit\n");
        verify(d_mockPhase, times(1)).gamePlayerRemove("Steven");
    }

    /**
     * Verifies that given an appropriate input, endGame will be called
     * Exit command is run afterward to exit loop
     */
    @Test
    void testExitCommand() {
        simulateUserInput("exit\n");
        verify(d_mockPhase, times(1)).endGame();
    }

    /**
     * Function created to simulate Scanner input
     * @param p_input String normally entered for a scanner
     */
    private void simulateUserInput(String p_input) {
        InputStream l_originalInput = System.in;
        try {
            // Convert the String into binary (SetIn requires it)
            ByteArrayInputStream l_testInput = new ByteArrayInputStream(p_input.getBytes());

            // Pass the binary stream to SetIn
            // Redirect Standard Input Stream to our ByteStream instead
            System.setIn(l_testInput);

            // Start GameDriver
            d_commandLineInterface.start();
        } finally {
            System.setIn(l_originalInput);
        }
    }
}
