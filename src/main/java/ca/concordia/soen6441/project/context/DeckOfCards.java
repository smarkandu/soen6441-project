package ca.concordia.soen6441.project.context;

import ca.concordia.soen6441.project.gameplay.cards.AirliftCard;
import ca.concordia.soen6441.project.gameplay.cards.BlockadeCard;
import ca.concordia.soen6441.project.gameplay.cards.BombCard;
import ca.concordia.soen6441.project.gameplay.cards.DiplomacyCard;
import ca.concordia.soen6441.project.interfaces.Card;

import java.io.Serializable;
import java.util.Random;

/**
 * Class for modelling a deck of cards
 */
public class DeckOfCards implements Serializable
{
    Random d_rand;

    /**
     * Constructor
     */
    public DeckOfCards()
    {
        this.d_rand = new Random();
    }

    /**
     * Function for obtaining a card from the deck
     *
     * @return a Card object
     */
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
