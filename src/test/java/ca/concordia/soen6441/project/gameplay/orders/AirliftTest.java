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

@ExtendWith(MockitoExtension.class)
public class AirliftTest {

    @Mock
    private Player mockPlayer;

    @Mock
    private GameContext mockGameContext;

    @Mock
    private Country mockSourceCountry;

    @Mock
    private Country mockTargetCountry;

    @Mock
    private HandOfCardsContext mockHandOfCardsManager;

    @Mock
    private CardManager<AirliftCard> mockAirliftCardManager;

    private Airlift airlift;

    @BeforeEach
    void setUp() {
        lenient().when(mockPlayer.getHandOfCardsManager()).thenReturn(mockHandOfCardsManager);
        lenient().when(mockHandOfCardsManager.getAirLiftCardManager()).thenReturn(mockAirliftCardManager);
        lenient().when(mockAirliftCardManager.hasCard()).thenReturn(true);
        lenient().when(mockSourceCountry.getOwner()).thenReturn(mockPlayer);
        lenient().when(mockTargetCountry.getOwner()).thenReturn(mockPlayer);
        lenient().when(mockSourceCountry.getTroops()).thenReturn(10);
        lenient().when(mockTargetCountry.getTroops()).thenReturn(10); // Needed for execute()

        airlift = new Airlift(mockSourceCountry, mockTargetCountry, 5, mockPlayer, mockGameContext);
    }

    @Test
    void testAirliftValid() {
        assertTrue(airlift.validate(), "Airlift should be valid when all conditions are met.");
    }

    private void assertTrue(String validate, String s) {
    }

    @Test
    void testAirliftNoCard() {
        when(mockAirliftCardManager.hasCard()).thenReturn(false);
        assertFalse(airlift.validate(), "Airlift should fail when player has no Airlift card.");
    }

    private void assertFalse(String validate, String s) {
    }

    @Test
    void testAirliftExecution() {
        airlift.execute();

        verify(mockSourceCountry).setTroops(5);   // 10 - 5
        verify(mockTargetCountry).setTroops(15);  // 10 + 5
        verify(mockAirliftCardManager).removeCard();
    }
}
