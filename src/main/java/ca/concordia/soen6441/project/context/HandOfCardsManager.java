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
    private ArrayList<AirliftCard> d_airLiftCards;
    private ArrayList<BlockadeCard> d_blockadeCards;
    private ArrayList<BombCard> d_bombCards;
    private ArrayList<DiplomacyCard> d_diplomacyCards;

    public HandOfCardsManager(Player p_player) {
        this.d_player = p_player;
        d_airLiftCards = new ArrayList<AirliftCard>();
        d_blockadeCards = new ArrayList<BlockadeCard>();
        d_bombCards = new ArrayList<BombCard>();
        d_diplomacyCards = new ArrayList<DiplomacyCard>();
    }

    @Override
    public void addCard(Card p_card) {
        if (p_card instanceof AirliftCard)
        {
            d_airLiftCards.add((AirliftCard) p_card);
        }
        else if (p_card instanceof BlockadeCard)
        {
            d_blockadeCards.add((BlockadeCard) p_card);
        }
        else if (p_card instanceof BombCard)
        {
            d_bombCards.add((BombCard) p_card);
        }
        else if (p_card instanceof DiplomacyCard)
        {
            d_diplomacyCards.add((DiplomacyCard) p_card);
        }
        else
        {
            System.out.println("Unrecognized card found!");
        }
    }

    @Override
    public void removeCard(Card p_card) {
        if (hasAirliftCard() && p_card instanceof AirliftCard)
        {
            d_airLiftCards.remove(p_card);
        }
        else if (hasBlockadeCard() && p_card instanceof BlockadeCard)
        {
            d_blockadeCards.remove(p_card);
        }
        else if (hasBombCard() && p_card instanceof BombCard)
        {
            d_bombCards.remove(p_card);
        }
        else if (hasDiplomacyCard() && p_card instanceof DiplomacyCard)
        {
            d_diplomacyCards.remove(p_card);
        }
        else
        {
            System.out.println("Unrecognized card found!");
        }
    }

    @Override
    public boolean hasBombCard()
    {
        return !d_bombCards.isEmpty();
    }

    @Override
    public boolean hasBlockadeCard() {
        return !d_blockadeCards.isEmpty();
    }

    @Override
    public boolean hasAirliftCard() {
        return !d_airLiftCards.isEmpty();
    }

    @Override
    public boolean hasDiplomacyCard() {
        return !d_diplomacyCards.isEmpty();
    }

    @Override
    public String toString() {
        String l_airLiftCardsStr = "AirLift: " + d_airLiftCards.size();
        String l_blockadeCardsStr= "Blockade: " + d_blockadeCards.size();
        String l_bombCardsStr = "Bomb: " + d_bombCards.size();
        String l_diplomacyCardsStr = "Diplomacy: " + d_airLiftCards.size();
        return String.format("[%s; %s; %s; %s]", l_airLiftCardsStr, l_blockadeCardsStr, l_bombCardsStr, l_diplomacyCardsStr);
    }
}
