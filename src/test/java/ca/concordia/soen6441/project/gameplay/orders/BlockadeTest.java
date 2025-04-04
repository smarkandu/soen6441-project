package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.context.PlayerManager;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Test Class to test the Blockade Class
 */
public class BlockadeTest {
    @Mock
    private Country d_mockTerritory;

    @Mock
    private Player d_mockInitiator;

    @Mock
    private Player d_mockNeutralPlayer;


    @Mock
    private PlayerManager d_mockPlayerManager;

    private Blockade d_blockadeOrder;

    private AutoCloseable d_autoCloseable;

    /**
     * Set up method of all testcases in file
     */
    @BeforeEach
    void setUp() {
        GameEngine l_mockGameEngine = mock(GameEngine.class);
        GameDriver.setGameEngine(l_mockGameEngine);
        d_autoCloseable =MockitoAnnotations.openMocks(this);

        when(GameDriver.getGameEngine().getPlayerManager()).thenReturn(d_mockPlayerManager);
        when(d_mockPlayerManager.getNeutralPlayer()).thenReturn(d_mockNeutralPlayer);

        d_blockadeOrder = new Blockade(d_mockTerritory, d_mockInitiator);
    }

    /**
     * Runs after testcases are done (for cleanup, etc.)
     * @throws Exception If an exception occurs
     */
    @AfterEach
    void tearDown() throws Exception {
        d_autoCloseable.close();  // Close the mocks after each test
    }

    /**
     * Testcase for verifying that a Blockade order can be executed when it has correct input
     */
    @Test
    void testExecute_SuccessfulBlockade() {
        when(d_mockInitiator.getName()).thenReturn("Player1");
        when(d_mockTerritory.getOwner()).thenReturn(d_mockInitiator);
        when(d_mockTerritory.getTroops()).thenReturn(5);
        when(d_mockTerritory.getID()).thenReturn("Territory1");

        d_blockadeOrder.execute();

        // Verify that territory belongs to neutral side
        verify(GameDriver.getGameEngine()).assignCountryToPlayer(d_mockTerritory, d_mockNeutralPlayer);

        // Verify troops were tripled after blockade was initiated
        verify(d_mockTerritory).setTroops(15);
    }

    /**
     * Testcase for verifying that no change occurs when player doesn't own territory for blockage
     */
    @Test
    void testExecute_PlayerDoesNotOwnTerritory() {
        Player l_mockDifferentOwner = mock(Player.class);
        when(l_mockDifferentOwner.getName()).thenReturn("Player2");
        when(d_mockInitiator.getName()).thenReturn("Player1");
        when(d_mockTerritory.getOwner()).thenReturn(l_mockDifferentOwner);

        d_blockadeOrder.execute();

        // Verify that no change occurred, due to the incorrect input
        verify(GameDriver.getGameEngine(), never()).assignCountryToPlayer(any(), any());
        verify(d_mockTerritory, never()).setTroops(anyInt());
    }
}