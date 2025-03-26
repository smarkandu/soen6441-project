package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test Class to test the Advance Class
 */
class AdvanceTest {
    private Country d_sourceTerritory;
    private Country d_targetTerritory;
    private Player d_initiator;
    private GameContext d_GameEngine;

    /**
     * Set up method of all testcases in file
     */
    @BeforeEach
    void setUp() {
        d_sourceTerritory = mock(Country.class);
        d_targetTerritory = mock(Country.class);
        d_initiator = mock(Player.class);
        d_GameEngine = mock(GameEngine.class);

        when(d_sourceTerritory.getID()).thenReturn("A");
        when(d_targetTerritory.getID()).thenReturn("B");
        when(d_initiator.getName()).thenReturn("Player1");
    }

    /**
     * Testcase for verifying that an Advance order can be executed when it has correct input
     */
    @Test
    public void testValidAdvanceOrder() {
        when(d_sourceTerritory.getOwner()).thenReturn(d_initiator);
        String l_temp = d_targetTerritory.getID();
        Mockito.when(d_sourceTerritory.getNeighborIDs()).thenReturn(Arrays.asList(l_temp));
        int l_numberOfTroops = 5;
        Mockito.when(d_sourceTerritory.getTroops()).thenReturn(l_numberOfTroops);

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, l_numberOfTroops, d_initiator, d_GameEngine);
        assertEquals(null, l_advanceOrder.validate(), "Valid advance order should pass validation.");
    }

    /**
     * Testcase to verify that an error is generated if the source and destination are the same
     */
    @Test
    public void testSourceAndTargetAreSame() {
        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_sourceTerritory, 5, d_initiator, d_GameEngine);
        assertEquals("Error: Source and target territories cannot be the same.", l_advanceOrder.validate(),
                "Validation should fail the source and the target cannot be the same.");
    }

    /**
     * Testcase to verify that an error is generated if the source location is not owned by the attacker
     */
    @Test
    public void testSourceNotOwnedByPlayer() {
        Mockito.when(d_sourceTerritory.getOwner()).thenReturn(Mockito.mock(Player.class)); // Different owner
        Mockito.when(d_sourceTerritory.getOwner().getName()).thenReturn("DifferentPlayer"); // Different owner

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, 5, d_initiator, d_GameEngine);
        assertEquals("Error: Player does not own the source territory.", l_advanceOrder.validate(),
                "Validation should fail because the player does not own the source territory.");
    }


    /**
     * Testcase to verify that an error is generated if there are insufficient troops to execute
     * the advance
     */
    @Test
    public void testInsufficientTroops() {
        Mockito.when(d_sourceTerritory.getTroops()).thenReturn(3); // Less than requested
        when(d_sourceTerritory.getOwner()).thenReturn(d_initiator);
        String l_temp = d_targetTerritory.getID();
        Mockito.when(d_sourceTerritory.getNeighborIDs()).thenReturn(Arrays.asList(l_temp));
        int l_numberOfTroops = 5;
        Mockito.when(d_sourceTerritory.getTroops()).thenReturn(l_numberOfTroops -1);

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, l_numberOfTroops,
                d_initiator, d_GameEngine);
        assertEquals("Error: No longer enough troops in the source territory.", l_advanceOrder.validate(),
                "Validation should fail due to not enough troops.");
    }

    /**
     * Testcase to verify that an error is generated if the neighbors selected are not neighbors
     */
    @Test
    public void testNotNeighbors() {
        Mockito.when(d_sourceTerritory.getNeighborIDs()).thenReturn(Collections.emptyList()); // No adjacency
        when(d_sourceTerritory.getOwner()).thenReturn(d_initiator);

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, 5, d_initiator, d_GameEngine);
        assertEquals("Error: Source and target territories are not neighbors.", l_advanceOrder.validate(),
                "Validation should fail since they are not neighbors");    }
}