package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.HandOfCardsContext;
import ca.concordia.soen6441.project.context.hand.CardManager;
import ca.concordia.soen6441.project.gameplay.cards.AirliftCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AirliftTest {

    @Mock
    private Player mockPlayer;

    @Mock
    private Country mockSourceCountry;

    @Mock
    private Country mockTargetCountry;

    @Mock
    private HandOfCardsContext mockHandOfCardsManager;

    @Mock
    private CardManager<AirliftCard> mockAirliftCardManager;

    @InjectMocks
    private Airlift airlift;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup default behavior for player
        when(mockPlayer.getHandOfCardsManager()).thenReturn(mockHandOfCardsManager);
        when(mockHandOfCardsManager.getAirLiftCardManager()).thenReturn(mockAirliftCardManager);

        // Ensure player has an Airlift card
        when(mockAirliftCardManager.hasCard()).thenReturn(true);

        // Setup countries and ownership
        when(mockSourceCountry.getOwner()).thenReturn(mockPlayer);
        when(mockTargetCountry.getOwner()).thenReturn(mockPlayer);
        when(mockSourceCountry.getTroops()).thenReturn(10);

        // Create the Airlift instance with mock data
        airlift = new Airlift(mockSourceCountry, mockTargetCountry, 5, mockPlayer);
    }

    @Test
    void testAirliftValid() {
        assertTrue(Boolean.parseBoolean(airlift.validate()), "Airlift should be valid with proper setup.");
    }

    @Test
    void testAirliftNotEnoughTroops() {
        when(mockSourceCountry.getTroops()).thenReturn(2); // Less than requested troops
        assertFalse(Boolean.parseBoolean(airlift.validate()), "Airlift should fail when not enough troops available.");
    }

    @Test
    void testAirliftInvalidOwner() {
        when(mockSourceCountry.getOwner()).thenReturn(mock(Player.class)); // Different owner
        assertFalse(Boolean.parseBoolean(airlift.validate()), "Airlift should fail when player doesn't own source country.");
    }

    @Test
    void testAirliftNoCard() {
        when(mockAirliftCardManager.hasCard()).thenReturn(false); // Player does not have an airlift card
        assertFalse(Boolean.parseBoolean(airlift.validate()), "Airlift should fail when player has no airlift card.");
    }

    @Test
    void testAirliftExecution() {
        airlift.execute();
        verify(mockSourceCountry).setTroops(5); // Ensure troops are deducted from source
        verify(mockTargetCountry).setTroops(15); // Ensure troops are added to target
        verify(mockAirliftCardManager).removeCard(); // Ensure card is removed
    }
}
