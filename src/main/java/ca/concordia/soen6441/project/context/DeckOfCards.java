package ca.concordia.soen6441.project.context;

import ca.concordia.soen6441.project.gameplay.cards.AirliftCard;
import ca.concordia.soen6441.project.gameplay.cards.BlockadeCard;
import ca.concordia.soen6441.project.gameplay.cards.BombCard;
import ca.concordia.soen6441.project.gameplay.cards.DiplomacyCard;
import ca.concordia.soen6441.project.interfaces.Card;

import java.util.Random;

public class DeckOfCards {
    Random d_rand;

    public DeckOfCards() {
        this.d_rand = new Random();
    }

    public Card getCardFromDeck()
    {
        int l_randomNumber = d_rand.nextInt(4);  // get a value from 0 to 3

        Card l_returnValue = null;
        switch (l_randomNumber)
        {
            case 0:
                l_returnValue = new AirliftCard();
                break;
            case 1:
                l_returnValue = new BlockadeCard();
                break;
            case 2:
                l_returnValue = new BombCard();
                break;
            case 3:
                l_returnValue = new DiplomacyCard();
                break;
            default:
                System.out.println("Not supposed to be here!");
                break;

        }

        return l_returnValue;
    }
}
