package ca.concordia.soen6441.project.context.hand;

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
    private CardManager<AirliftCard> d_airLiftCardManager;
    private CardManager<BlockadeCard> d_blockadeCardManager;
    private CardManager<BombCard> d_bombCardManager;
    private CardManager<DiplomacyCard> d_diplomacyCardManager;

    public HandOfCardsManager(Player p_player) {
        this.d_player = p_player;
        d_airLiftCardManager = new CardManager<AirliftCard>();
        d_blockadeCardManager = new CardManager<BlockadeCard>();
        d_bombCardManager = new CardManager<BombCard>();
        d_diplomacyCardManager = new CardManager<DiplomacyCard>();
    }

    public Player getPlayer() {
        return d_player;
    }

    @Override
    public void addCard(Card p_card) {
        if (p_card instanceof AirliftCard)
        {
            d_airLiftCardManager.addCard((AirliftCard) p_card);
        }
        else if (p_card instanceof BlockadeCard)
        {
            d_blockadeCardManager.addCard((BlockadeCard) p_card);
        }
        else if (p_card instanceof BombCard)
        {
            d_bombCardManager.addCard((BombCard) p_card);
        }
        else if (p_card instanceof DiplomacyCard)
        {
            d_diplomacyCardManager.addCard((DiplomacyCard) p_card);
        }
        else
        {
            System.out.println("Unrecognized card found!");
        }
    }

    public CardManager<AirliftCard> getAirLiftCardManager() {
        return d_airLiftCardManager;
    }

    public CardManager<BlockadeCard> getBlockadeCardManager() {
        return d_blockadeCardManager;
    }

    public CardManager<BombCard> getBombCardManager() {
        return d_bombCardManager;
    }

    public CardManager<DiplomacyCard> getDiplomacyCardManager() {
        return d_diplomacyCardManager;
    }

    @Override
    public String toString() {
        String l_airLiftCardsStr = "AirLift: " + d_airLiftCardManager.size();
        String l_blockadeCardsStr= "Blockade: " + d_blockadeCardManager.size();
        String l_bombCardsStr = "Bomb: " + d_bombCardManager.size();
        String l_diplomacyCardsStr = "Diplomacy: " + d_diplomacyCardManager.size();
        return String.format("[%s; %s; %s; %s]", l_airLiftCardsStr, l_blockadeCardsStr, l_bombCardsStr, l_diplomacyCardsStr);
    }
}
