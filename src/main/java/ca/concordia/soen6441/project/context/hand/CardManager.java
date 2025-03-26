package ca.concordia.soen6441.project.context.hand;

import ca.concordia.soen6441.project.gameplay.cards.AirliftCard;
import ca.concordia.soen6441.project.gameplay.cards.BlockadeCard;
import ca.concordia.soen6441.project.gameplay.cards.BombCard;
import ca.concordia.soen6441.project.gameplay.cards.DiplomacyCard;
import ca.concordia.soen6441.project.interfaces.Card;

import java.util.ArrayList;

public class CardManager<T> {
    private ArrayList<T> d_cards;

    public CardManager() {
        d_cards = new ArrayList<T>();
    }

    public void addCard(T p_card) {
        d_cards.add(p_card);
    }

    public boolean hasCard() {
        return !d_cards.isEmpty();
    }

    public T removeCard()
    {
        if (!d_cards.isEmpty())
        {
            return d_cards.remove(0);
        }

        return null;
    }
}
