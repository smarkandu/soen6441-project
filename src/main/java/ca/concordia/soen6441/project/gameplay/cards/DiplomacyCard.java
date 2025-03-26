package ca.concordia.soen6441.project.gameplay.cards;

import ca.concordia.soen6441.project.interfaces.Card;

public class DiplomacyCard implements Card {
    @Override
    public String toString() {
        return "Diplomacy";
    }
    //Ensures removeCard(new DiplomacyCard()) will work as expected
        @Override
    public boolean equals(Object p_obj) {
        return p_obj instanceof DiplomacyCard;
    }

    @Override
    public int hashCode() {
        return 31; // Arbitrary constant for all DiplomacyCards
    }
}