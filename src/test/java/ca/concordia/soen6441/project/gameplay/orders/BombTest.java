package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

/**
 * Test Class to test the Bomb Class
 */
public class BombTest {

    @Mock
    private Player d_mockInitiator;

    @Mock
    private Country d_enemyTerritoryToBomb;

    @Mock
    private Bomb d_bombOrder;

    private List<String> d_initiatorOwnedCountries;

    private List<String> d_enemyNeighborsCountries;



    /**
     * Set up method of all testcases in file
     */
    @BeforeEach
    void setUp() {
        d_mockInitiator = mock(Player.class);
        when(d_mockInitiator.getName()).thenReturn("Initiator");
        d_initiatorOwnedCountries = new ArrayList<>();
        d_initiatorOwnedCountries.add("Paris");
        when(d_mockInitiator.getOwnedCountries()).thenReturn(d_initiatorOwnedCountries);

        d_enemyTerritoryToBomb = mock(Country.class);
        d_enemyNeighborsCountries = new ArrayList<>();
    }

    /**
     * Testcase to verify that an error is generated when the player try to bomb its own territory.
     */
    @Test
    public void testTargetTerritoryBelongsToPlayerInitiator() {

        when(d_enemyTerritoryToBomb.getID()).thenReturn("Paris");

        d_bombOrder = new Bomb(d_mockInitiator, d_enemyTerritoryToBomb);

        assertEquals("Error: Player cannot bomb his territory.", d_bombOrder.validate(),
                "Validation should fail because the player cannot bombed his own territory.");
    }

    /**
     * Testcase to verify that an error is generated when the player try to bomb a territory is not adjacent.
     */
    @Test
    public void testTargetTerritoryIsNotAdjacent() {

        when(d_enemyTerritoryToBomb.getID()).thenReturn("Canada");

        d_bombOrder = new Bomb(d_mockInitiator, d_enemyTerritoryToBomb);

        assertEquals("Error: Player's territory is not adjacent to the target territory.", d_bombOrder.validate(),
                "Validation should fail because the player should bomb an adjacent enemy territory.");
    }

    /**
     * Testcase to verify that an error is generated when the player try to bomb a territory that has no troop.
     */
    @Test
    public void testTargetTerritoryHaveTroops() {

        when(d_enemyTerritoryToBomb.getID()).thenReturn("Canada");
        d_enemyNeighborsCountries.add("Paris");
        when(d_enemyTerritoryToBomb.getNeighborIDs()).thenReturn(d_enemyNeighborsCountries);
        when(d_enemyTerritoryToBomb.getTroops()).thenReturn(0);

        d_bombOrder = new Bomb(d_mockInitiator, d_enemyTerritoryToBomb);

        assertEquals("Error: Player cannot bomb a territory without troop.", d_bombOrder.validate(),
                "Validation should fail because the player should bombed an enemy territory containing troops.");
    }

    /**
     * Testcase for verifying that an Order can be executed when it has correct input
     */
    @Test
    public void testValidBombOrder() {

        when(d_enemyTerritoryToBomb.getID()).thenReturn("Canada");
        d_enemyNeighborsCountries.add("Paris");
        when(d_enemyTerritoryToBomb.getNeighborIDs()).thenReturn(d_enemyNeighborsCountries);
        when(d_enemyTerritoryToBomb.getTroops()).thenReturn(10);

        d_bombOrder = new Bomb(d_mockInitiator, d_enemyTerritoryToBomb);

        assertNull(d_bombOrder.validate(), "Valid order order should pass validation.");

    }

}