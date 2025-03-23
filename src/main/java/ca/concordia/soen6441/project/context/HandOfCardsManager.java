package ca.concordia.soen6441.project.context;

import ca.concordia.soen6441.project.gameplay.cards.AirliftCard;
import ca.concordia.soen6441.project.gameplay.cards.BlockadeCard;
import ca.concordia.soen6441.project.gameplay.cards.BombCard;
import ca.concordia.soen6441.project.gameplay.cards.DiplomacyCard;
import ca.concordia.soen6441.project.interfaces.Card;
import ca.concordia.soen6441.project.interfaces.context.HandOfCardsContext;
import ca.concordia.soen6441.project.interfaces.Player;

import java.util.ArrayList;

public class HandOfCardsManager implements HandOfCardsContext {
    private Player d_player;
    private ArrayList<Card> d_handOfCards;

    public HandOfCardsManager(Player d_player) {
        this.d_player = d_player;
        d_handOfCards = new ArrayList<Card>();
    }

    @Override
    public void addCard(Card p_card) {

    }

    @Override
    public void removeCard(Card p_card) {

    }

    @Override
    public boolean hasBombCard() {
        for (Card a : d_handOfCards) {
            if (a instanceof BombCard) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasBlockadeCard() {
        for (Card a : d_handOfCards) {
            if (a instanceof BlockadeCard) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasAirliftCard() {
        for (Card a : d_handOfCards) {
            if (a instanceof AirliftCard) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasDiplomacyCard() {
        for (Card a : d_handOfCards) {
            if (a instanceof DiplomacyCard) {
                return true;
            }
        }

        return false;
    }
}
