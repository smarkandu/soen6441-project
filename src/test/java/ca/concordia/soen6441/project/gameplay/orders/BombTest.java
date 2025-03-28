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

    @Test
    public void testTargetTerritoryBelongsToPlayerInitiator() {

        when(d_enemyTerritoryToBomb.getID()).thenReturn("Paris");

        d_bombOrder = new Bomb(d_mockInitiator, d_enemyTerritoryToBomb);

        assertEquals("Error: Player cannot bombeb his territory.", d_bombOrder.validate(),
                "Validation should fail because the player cannot bombed his own territory.");
    }

    @Test
    public void testTargetTerritoryIsNotAjacent() {

        when(d_enemyTerritoryToBomb.getID()).thenReturn("Canada");

        d_bombOrder = new Bomb(d_mockInitiator, d_enemyTerritoryToBomb);

        assertEquals("Error: Player's territory is not adjacent to the target territory.", d_bombOrder.validate(),
                "Validation should fail because the player should bombed an ajacent enemy territory.");
    }

    @Test
    public void testTargetTerritoryHaveTroops() {

        when(d_enemyTerritoryToBomb.getID()).thenReturn("Canada");
        d_enemyNeighborsCountries.add("Paris");
        when(d_enemyTerritoryToBomb.getNeighborIDs()).thenReturn(d_enemyNeighborsCountries);
        when(d_enemyTerritoryToBomb.getTroops()).thenReturn(0);

        d_bombOrder = new Bomb(d_mockInitiator, d_enemyTerritoryToBomb);

        assertEquals("Error: Player cannot bombed a territory without troop.", d_bombOrder.validate(),
                "Validation should fail because the player should bombed an enemy territory containing troops.");
    }

    @Test
    public void testValidBombOrder() {

        when(d_enemyTerritoryToBomb.getID()).thenReturn("Canada");
        d_enemyNeighborsCountries.add("Paris");
        when(d_enemyTerritoryToBomb.getNeighborIDs()).thenReturn(d_enemyNeighborsCountries);
        when(d_enemyTerritoryToBomb.getTroops()).thenReturn(10);

        d_bombOrder = new Bomb(d_mockInitiator, d_enemyTerritoryToBomb);

        assertEquals(null, d_bombOrder.validate(), "Valid order order should pass validation.");

    }

}