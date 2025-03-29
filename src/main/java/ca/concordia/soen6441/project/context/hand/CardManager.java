package ca.concordia.soen6441.project.context.hand;

import ca.concordia.soen6441.project.gameplay.cards.AirliftCard;
import ca.concordia.soen6441.project.gameplay.cards.BlockadeCard;
import ca.concordia.soen6441.project.gameplay.cards.BombCard;
import ca.concordia.soen6441.project.gameplay.cards.DiplomacyCard;
import ca.concordia.soen6441.project.interfaces.Card;

import java.util.ArrayList;

/**
 * Generic Class to deal with the various types of cards
 * @param <T> The object type you want to use
 */
public class CardManager<T> {
    private ArrayList<T> d_cards;

    /**
     * Constructor
     */
    public CardManager() {
        d_cards = new ArrayList<T>();
    }

    /**
     * Add T card to hand
     * @param p_card T type object
     */
    public void addCard(T p_card) {
        d_cards.add(p_card);
    }

    /**
     * Checks if there is a T type object in hand
     * @boolean if true, false otherwise
     */
    public boolean hasCard() {
        return !d_cards.isEmpty();
    }

    /**
     * Remove T card from hand
     * @return T card that was removed
     */
    public T removeCard()
    {
        if (!d_cards.isEmpty())
        {
            return d_cards.remove(0);
        }

        return null;
    }

    /**
     * Obtain the # of T cards
     * @return integer
     */
    public int size()
    {
        return d_cards.size();
    }
}
