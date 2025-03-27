package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.phases.Phase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.Mockito.*;

/**
 * Test cases created for GameDriver class.
 */
class GameDriverTest {
    private GameContext mockGameEngine;
    private Phase mockPhase;

    /**
     * Setup method for each testcase
     */
    @BeforeEach
    void setUp() {
        mockGameEngine = mock(GameEngine.class);
        mockPhase = mock(Phase.class);
        when(mockGameEngine.getPhase()).thenReturn(mockPhase);
        GameDriver.d_gameEngine = mockGameEngine;
    }

    /**
     * Verifies that given an appropriate input, editContinentAdd will be called
     * Exit command is run afterwards to exit loop
     */
    @Test
    void testEditContinentAdd() {
        simulateUserInput("editcontinent -add \"Asia\" 5\nexit\n");
        verify(mockPhase, times(1)).editContinentAdd("Asia", 5);
    }

    /**
     * Verifies that given an appropriate input, editContinentRemove will be called
     * Exit command is run afterwards to exit loop
     */
    @Test
    void testEditContinentRemove() {
        simulateUserInput("editcontinent -remove \"Asia\"\nexit\n");
        verify(mockPhase, times(1)).editContinentRemove("Asia");
    }

    /**
     * Verifies that given an appropriate input, showMap will be called
     * Exit command is run afterwards to exit loop
     */
    @Test
    void testShowMap() {
        simulateUserInput("showmap\nexit\n");
        verify(mockPhase, times(1)).showMap();
    }

    /**
     * Verifies that given an appropriate input, deploy will be called
     * Exit command is run afterwards to exit loop
     */
    @Test
    void testDeploy() {
        simulateUserInput("deploy \"France\" 10\nexit\n");
        verify(mockPhase, times(1)).deploy("France", 10);
    }

    /**
     * Verifies that given an appropriate input, advance will be called
     * Exit command is run afterwards to exit loop
     */
    @Test
    void testAdvance() {
        simulateUserInput("advance \"Germany\" \"Belgium\" 5\nexit\n");
        verify(mockPhase, times(1)).advance("Germany", "Belgium", 5);
    }

    /**
     * Verifies that given an appropriate input, endGame will be called
     * Exit command is run afterwards to exit loop
     */
    @Test
    void testExitCommand() {
        simulateUserInput("exit\n");
        verify(mockPhase, times(1)).endGame();
    }

    /**
     * Function created to simulate Scanner input
     * @param input String normally entered for a scanner
     */
    private void simulateUserInput(String input) {
        InputStream originalInput = System.in;
        try {
            // Convert the String into binary (SetIn requires it)
            ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());

            // Pass the binary stream to SetIn
            // Redirect Standard Input Stream to our ByteStream instead
            System.setIn(testInput);

            // Start GameDriver
            GameDriver.start();
        } finally {
            System.setIn(originalInput);
        }
    }
}
