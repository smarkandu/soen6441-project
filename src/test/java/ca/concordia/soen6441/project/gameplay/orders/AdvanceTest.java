package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test class for the {@link Advance} order.
 * Validates the correctness of order execution and validation logic
 * including edge cases such as invalid troop movement, non-neighboring territories,
 * and country conquests.
 */
class AdvanceTest {

    private Country d_sourceTerritory;
    private Country d_targetTerritory;
    private Player d_initiator;
    private Player d_defender;
    private GameContext d_GameEngine;

    /**
     * Sets up the mocks before each test case.
     */
    @BeforeEach
    void setUp() {
        d_sourceTerritory = mock(Country.class);
        d_targetTerritory = mock(Country.class);
        d_initiator = mock(Player.class);
        d_defender = mock(Player.class);
        d_GameEngine = mock(GameContext.class);

        when(d_sourceTerritory.getID()).thenReturn("A");
        when(d_targetTerritory.getID()).thenReturn("B");
        when(d_initiator.getName()).thenReturn("Player1");
    }

    /**
     * Tests if a valid advance order passes validation.
     */
    @Test
    public void testValidAdvanceOrder() {
        when(d_sourceTerritory.getOwner()).thenReturn(d_initiator);
        String l_temp = d_targetTerritory.getID();
        when(d_sourceTerritory.getNeighborIDs()).thenReturn(Arrays.asList(l_temp));
        int l_numberOfTroops = 5;
        when(d_sourceTerritory.getTroops()).thenReturn(l_numberOfTroops);

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, l_numberOfTroops, d_initiator, d_GameEngine);
        assertEquals(null, l_advanceOrder.validate(), "Valid advance order should pass validation.");
    }

    /**
     * Tests if validation fails when source and target territories are the same.
     */
    @Test
    public void testSourceAndTargetAreSame() {
        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_sourceTerritory, 5, d_initiator, d_GameEngine);
        assertEquals("Error: Source and target territories cannot be the same.", l_advanceOrder.validate(),
                "Validation should fail the source and the target cannot be the same.");
    }

    /**
     * Tests validation failure when the source territory is not owned by the player.
     */
    @Test
    public void testSourceNotOwnedByPlayer() {
        when(d_sourceTerritory.getOwner()).thenReturn(mock(Player.class));
        when(d_sourceTerritory.getOwner().getName()).thenReturn("DifferentPlayer");

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, 5, d_initiator, d_GameEngine);
        assertEquals("Error: Player does not own the source territory.", l_advanceOrder.validate(),
                "Validation should fail because the player does not own the source territory.");
    }

    /**
     * Tests if validation fails due to insufficient troops in the source territory.
     */
    @Test
    public void testInsufficientTroops() {
        when(d_sourceTerritory.getTroops()).thenReturn(3);
        when(d_sourceTerritory.getOwner()).thenReturn(d_initiator);
        String l_temp = d_targetTerritory.getID();
        when(d_sourceTerritory.getNeighborIDs()).thenReturn(Arrays.asList(l_temp));
        int l_numberOfTroops = 5;
        when(d_sourceTerritory.getTroops()).thenReturn(l_numberOfTroops - 1);

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, l_numberOfTroops,
                d_initiator, d_GameEngine);
        assertEquals("Error: No longer enough troops in the source territory.", l_advanceOrder.validate(),
                "Validation should fail due to not enough troops.");
    }

    /**
     * Tests validation failure when source and target territories are not neighbors.
     */
    @Test
    public void testNotNeighbors() {
        when(d_sourceTerritory.getNeighborIDs()).thenReturn(Collections.emptyList());
        when(d_sourceTerritory.getOwner()).thenReturn(d_initiator);

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, 5, d_initiator, d_GameEngine);
        assertEquals("Error: Source and target territories are not neighbors.", l_advanceOrder.validate(),
                "Validation should fail since they are not neighbors");
    }

    /**
     * Tests the scenario when a player conquers a country after a successful advance.
     */
    @Test
    public void testConqueringACountry() {
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

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, 3, d_initiator, d_GameEngine);
        l_advanceOrder.setProbabilityWinningAttacker(1.00);
        l_advanceOrder.setProbabilityWinningDefender(0.00);

        BattleResult l_mockBattleResult = mock(BattleResult.class);
        when(l_mockBattleResult.getPlayersTroops()).thenReturn(3);
        when(l_mockBattleResult.getOpponentsTroops()).thenReturn(0);

        l_advanceOrder.execute();

        verify(d_GameEngine).assignCountryToPlayer(d_targetTerritory, d_initiator);
        assertTrue(l_advanceOrder.conquersTerritory(), "Player should conquer the territory.");
        verify(d_targetTerritory).setTroops(3);
    }

    /**
     * Tests moving troops between two territories owned by the same player (non-attack).
     */
    @Test
    public void testMovingOfArmiesInConqueredCountryAfterConqueringIt() {
        when(d_initiator.getName()).thenReturn("Player1");
        when(d_sourceTerritory.getOwner()).thenReturn(d_initiator);
        when(d_sourceTerritory.getTroops()).thenReturn(3);
        when(d_targetTerritory.getOwner()).thenReturn(d_initiator);
        when(d_targetTerritory.getTroops()).thenReturn(5);
        when(d_targetTerritory.getID()).thenReturn("Country2");
        List<String> l_neighbors = new ArrayList<>();
        l_neighbors.add("Country2");
        when(d_sourceTerritory.getNeighborIDs()).thenReturn(l_neighbors);

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, 3, d_initiator, d_GameEngine);
        l_advanceOrder.execute();

        verify(d_GameEngine, never()).assignCountryToPlayer(any(), any());
        verify(d_targetTerritory).setTroops(8);
    }
}
