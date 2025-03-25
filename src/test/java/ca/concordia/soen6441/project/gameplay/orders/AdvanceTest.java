package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdvanceTest {
    private Country d_sourceTerritory;
    private Country d_targetTerritory;
    private Player d_initiator;

    @BeforeEach
    void setUp() {
        d_sourceTerritory = mock(Country.class);
        d_targetTerritory = mock(Country.class);
        d_initiator = mock(Player.class);

        when(d_sourceTerritory.getID()).thenReturn("A");
        when(d_targetTerritory.getID()).thenReturn("B");
        when(d_initiator.getName()).thenReturn("Player1");
    }

    @Test
    public void testValidAdvanceOrder() {
        when(d_sourceTerritory.getOwner()).thenReturn(d_initiator);
        String l_temp = d_targetTerritory.getID();
        Mockito.when(d_sourceTerritory.getNeighborIDs()).thenReturn(Arrays.asList(l_temp));
        int l_number_of_troops_order = 5;
        Mockito.when(d_sourceTerritory.getTroops()).thenReturn(l_number_of_troops_order);

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, l_number_of_troops_order, d_initiator);
        assertEquals(null, l_advanceOrder.validate(), "Valid advance order should pass validation.");
    }

    @Test
    public void testSourceAndTargetAreSame() {
        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_sourceTerritory, 5, d_initiator);
        assertEquals("Error: Source and target territories cannot be the same.", l_advanceOrder.validate(),
                "Validation should fail the source and the target cannot be the same.");
    }

    @Test
    public void testSourceNotOwnedByPlayer() {
        Mockito.when(d_sourceTerritory.getOwner()).thenReturn(Mockito.mock(Player.class)); // Different owner
        Mockito.when(d_sourceTerritory.getOwner().getName()).thenReturn("DifferentPlayer"); // Different owner

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, 5, d_initiator);
        assertEquals("Error: Player does not own the source territory.", l_advanceOrder.validate(),
                "Validation should fail because the player does not own the source territory.");
    }


    @Test
    public void testInsufficientTroops() {
        Mockito.when(d_sourceTerritory.getTroops()).thenReturn(3); // Less than requested
        when(d_sourceTerritory.getOwner()).thenReturn(d_initiator);
        String l_temp = d_targetTerritory.getID();
        Mockito.when(d_sourceTerritory.getNeighborIDs()).thenReturn(Arrays.asList(l_temp));
        int l_number_of_troops_order = 5;
        Mockito.when(d_sourceTerritory.getTroops()).thenReturn(l_number_of_troops_order -1);

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, l_number_of_troops_order,
                d_initiator);
        assertEquals("Error: No longer enough troops in the source territory.", l_advanceOrder.validate(),
                "Validation should fail due to not enough troops.");
    }

    @Test
    public void testNotNeighbors() {
        Mockito.when(d_sourceTerritory.getNeighborIDs()).thenReturn(Collections.emptyList()); // No adjacency
        when(d_sourceTerritory.getOwner()).thenReturn(d_initiator);

        Advance l_advanceOrder = new Advance(d_sourceTerritory, d_targetTerritory, 5, d_initiator);
        assertEquals("Error: Source and target territories are not neighbors.", l_advanceOrder.validate(),
                "Validation should fail since they are not neighbors");    }
}