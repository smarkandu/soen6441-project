package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.interfaces.Card;

public interface HandOfCardsContext {
    void addCard(Card p_card);
    void removeCard(Card p_card);
    boolean hasBombCard();
    boolean hasBlockadeCard();
    boolean hasAirliftCard();
    boolean hasDiplomacyCard();
    void useDiplomacyCard();
}
