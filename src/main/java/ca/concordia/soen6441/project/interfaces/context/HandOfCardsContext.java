package ca.concordia.soen6441.project.interfaces.context;

import ca.concordia.soen6441.project.interfaces.Card;

public interface HandOfCardsContext {
    /**
     * Adds new card to the deck of card
     */
    void addCard(Card p_card);

    /**
     * Removes an instance of existing card
     *
     * @param p_card
     */
    void removeCard(Card p_card);

    /**
     * Checks if Bomb Card exists
     *
     * @return true if card exists, else false
     */
    boolean hasBombCard();

    /**
     * Checks if Blockade Card exists
     *
     * @return true if card exists, else false
     */
    boolean hasBlockadeCard();

    /**
     * Checks if Air Life Card Card exists
     *
     * @return true if card exists, else false
     */
    boolean hasAirliftCard();

    /**
     * Checks if Diplomacy Card exists
     *
     * @return true if card exists, else false
     */
    boolean hasDiplomacyCard();
}
