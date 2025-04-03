package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Test Class to test the Advance Class
 */
public class AdvanceTest {
    private Country d_sourceTerritory;
    private Country d_targetTerritory;
    private Player d_initiator;
    private Player d_defender;
    private GameEngine d_mockGameEngine;

    /**
     * Set up method of all testcases in file
     */
    @BeforeEach
    void setUp() {
        d_sourceTerritory = mock(Country.class);
        d_targetTerritory = mock(Country.class);
        d_initiator = mock(Player.class);
        d_defender = mock(Player.class);
        d_mockGameEngine = mock(GameEngine.class);
        GameDriver.setGameEngine(d_mockGameEngine);

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

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, l_numberOfTroops, d_initiator);
        assertEquals(null, l_advanceOrder.validate(), "Valid advance order should pass validation.");
    }

    /**
     * Testcase to verify that an error is generated if the source and destination are the same
     */
    @Test
    public void testSourceAndTargetAreSame() {
        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_sourceTerritory, 5, d_initiator);
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

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, 5, d_initiator);
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
        Mockito.when(d_sourceTerritory.getTroops()).thenReturn(l_numberOfTroops - 1);

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, l_numberOfTroops,
                d_initiator);
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

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, 5, d_initiator);
        assertEquals("Error: Source and target territories are not neighbors.", l_advanceOrder.validate(),
                "Validation should fail since they are not neighbors");
    }

    /**
     * Testcase When Conquering a Country
     */
    @Test
    public void testConqueringACountry() {
        // Setup mock behaviors
        when(d_initiator.getName()).thenReturn("Player1");
        when(d_defender.getName()).thenReturn("Player2");
        when(d_sourceTerritory.getOwner()).thenReturn(d_initiator);
        when(d_sourceTerritory.getTroops()).thenReturn(3);
        when(d_targetTerritory.getOwner()).thenReturn(d_defender);
        when(d_targetTerritory.getTroops()).thenReturn(5);
        when(d_targetTerritory.getID()).thenReturn("Country2");
        List<String> l_neighbors = new ArrayList<>();
        l_neighbors.add("Country2");
        when(d_sourceTerritory.getNeighborIDs()).thenReturn(l_neighbors);

        // Create Advance instance
        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, 3, d_initiator);

        // Ensure that attacker always wins
        l_advanceOrder.setProbabilityWinningAttacker(1.00);
        l_advanceOrder.setProbabilityWinningDefender(0.00);

        // Simulate battle
        BattleResult l_mockBattleResult = mock(BattleResult.class);
        when(l_mockBattleResult.getPlayersTroops()).thenReturn(3);  // Player1 wins with 3 troops remaining
        when(l_mockBattleResult.getOpponentsTroops()).thenReturn(0); // Player2 loses all troops

        // Simulate battle outcome
        l_advanceOrder.execute();

        // Verify that winning attacker is assigned country
        // and that the number of troops has changed
        verify(GameDriver.getGameEngine()).assignCountryToPlayer(d_targetTerritory, d_initiator);
        assertTrue(l_advanceOrder.conquersTerritory(), "Player should conquer the territory.");
        verify(d_targetTerritory).setTroops(3);  // Player1 should have 3 troops left after battle
    }

    /**
     * Testcase when moving armies in a conquered country after conquering it
     */
    @Test
    public void testMovingOfArmiesInConqueredCountryAfterConqueringIt() {
        // Setup mock behaviors
        when(d_initiator.getName()).thenReturn("Player1");
        when(d_sourceTerritory.getOwner()).thenReturn(d_initiator);
        when(d_sourceTerritory.getTroops()).thenReturn(3);
        when(d_targetTerritory.getOwner()).thenReturn(d_initiator);
        when(d_targetTerritory.getTroops()).thenReturn(5);
        when(d_targetTerritory.getID()).thenReturn("Country2");
        List<String> l_neighbors = new ArrayList<>();
        l_neighbors.add("Country2");
        when(d_sourceTerritory.getNeighborIDs()).thenReturn(l_neighbors);

        // Create Advance instance
        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, 3, d_initiator);

        // Simulate battle outcome
        l_advanceOrder.execute();

        // Verify that winning attacker is assigned country
        // and that the number of troops has changed
        Mockito.verify(GameDriver.getGameEngine(), Mockito.never()).assignCountryToPlayer(Mockito.any(), Mockito.any());
        verify(d_targetTerritory).setTroops(8);  // Player1 should have 3 troops left after battle
    }
}