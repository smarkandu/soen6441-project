package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.interfaces.context.HandOfCardsContext;
import ca.concordia.soen6441.project.context.hand.CardManager;
import ca.concordia.soen6441.project.gameplay.cards.AirliftCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Airlift} order class.
 * These tests verify the validation and execution logic
 * of the Airlift order within the gameplay context.
 */
@ExtendWith(MockitoExtension.class)
public class AirliftTest {

    /** Mock player instance. */
    @Mock
    private Player d_mockPlayer;

    /** Mock game context instance. */
    @Mock
    private GameContext d_mockGameContext;

    /** Mock source country. */
    @Mock
    private Country d_mockSourceCountry;

    /** Mock target country. */
    @Mock
    private Country d_mockTargetCountry;

    /** Mock hand of cards context. */
    @Mock
    private HandOfCardsContext d_mockHandOfCardsManager;

    /** Mock Airlift card manager. */
    @Mock
    private CardManager<AirliftCard> d_mockAirliftCardManager;

    /** Airlift order instance under test. */
    private Airlift d_airlift;

    /**
     * Sets up the mock context and initializes the Airlift order
     * before each test is run.
     */
    @BeforeEach
    void setUp() {
        lenient().when(d_mockPlayer.getHandOfCardsManager()).thenReturn(d_mockHandOfCardsManager);
        lenient().when(d_mockHandOfCardsManager.getAirLiftCardManager()).thenReturn(d_mockAirliftCardManager);
        lenient().when(d_mockAirliftCardManager.hasCard()).thenReturn(true);
        lenient().when(d_mockSourceCountry.getOwner()).thenReturn(d_mockPlayer);
        lenient().when(d_mockTargetCountry.getOwner()).thenReturn(d_mockPlayer);
        lenient().when(d_mockSourceCountry.getTroops()).thenReturn(10);
        lenient().when(d_mockTargetCountry.getTroops()).thenReturn(10);

        d_airlift = new Airlift(d_mockSourceCountry, d_mockTargetCountry, 5, d_mockPlayer, d_mockGameContext);
    }

    /**
     * Tests if the Airlift order is valid under correct conditions.
     */
    @Test
    void testAirliftValid() {
        assertNull(d_airlift.validate(), "Airlift should be valid when all conditions are met.");
    }

    /**
     * Tests the correct execution of the Airlift order.
     * Verifies troop movement from source to target country.
     */
    @Test
    void testAirliftExecution() {
        d_airlift.execute();

        verify(d_mockSourceCountry).setTroops(5);   // 10 - 5
        verify(d_mockTargetCountry).setTroops(15);  // 10 + 5
    }

    /**
     * Tests that validation fails if the player does not own the source country.
     */
    @Test
    void testValidate_ReturnsError_WhenPlayerDoesNotOwnSourceCountry() {
        Player l_anotherMockPlayer = mock(Player.class);
        when(d_mockSourceCountry.getOwner()).thenReturn(l_anotherMockPlayer);

        Airlift l_invalidAirlift = new Airlift(d_mockSourceCountry, d_mockTargetCountry, 5, d_mockPlayer, d_mockGameContext);

        String l_result = l_invalidAirlift.validate();

        assertEquals("ERROR: Player does not own the source country!", l_result);
    }
}
