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
    private Player d_mockPlayer;

    @Mock
    private GameContext d_mockGameContext;

    @Mock
    private Country d_mockSourceCountry;

    @Mock
    private Country d_mockTargetCountry;

    @Mock
    private HandOfCardsContext d_mockHandOfCardsManager;

    @Mock
    private CardManager<AirliftCard> d_mockAirliftCardManager;

    private Airlift d_airlift;

    @BeforeEach
    void setUp() {
        lenient().when(d_mockPlayer.getHandOfCardsManager()).thenReturn(d_mockHandOfCardsManager);
        lenient().when(d_mockHandOfCardsManager.getAirLiftCardManager()).thenReturn(d_mockAirliftCardManager);
        lenient().when(d_mockAirliftCardManager.hasCard()).thenReturn(true);
        lenient().when(d_mockSourceCountry.getOwner()).thenReturn(d_mockPlayer);
        lenient().when(d_mockTargetCountry.getOwner()).thenReturn(d_mockPlayer);
        lenient().when(d_mockSourceCountry.getTroops()).thenReturn(10);
        lenient().when(d_mockTargetCountry.getTroops()).thenReturn(10); // for execute()

        d_airlift = new Airlift(d_mockSourceCountry, d_mockTargetCountry, 5, d_mockPlayer, d_mockGameContext);
    }

    @Test
    void testAirliftValid() {
        assertNull(d_airlift.validate(), "Airlift should be valid when all conditions are met.");
    }

    @Test
    void testAirliftExecution() {
        d_airlift.execute();

        verify(d_mockSourceCountry).setTroops(5);   // 10 - 5
        verify(d_mockTargetCountry).setTroops(15);  // 10 + 5
    }
}
