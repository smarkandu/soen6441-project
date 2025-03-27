package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.context.hand.CardManager;
import ca.concordia.soen6441.project.gameplay.cards.AirliftCard;
import ca.concordia.soen6441.project.gameplay.cards.BlockadeCard;
import ca.concordia.soen6441.project.gameplay.cards.BombCard;
import ca.concordia.soen6441.project.gameplay.cards.DiplomacyCard;
import ca.concordia.soen6441.project.interfaces.Card;

public interface HandOfCardsContext {
    void addCard(Card p_card);

    CardManager<AirliftCard> getAirLiftCardManager();
    CardManager<BlockadeCard> getBlockadeCardManager();
    CardManager<BombCard> getBombCardManager();
    CardManager<DiplomacyCard> getDiplomacyCardManager();
}
