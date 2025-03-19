package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Advance implements Order {
    private Country d_sourceTerritory;
    private Country d_targetTerritory;
    private int d_toAdvance;
    private Player d_initiator;
    private class BattleResult
    {
        private boolean d_battleWonByPlayer;
        private int d_troopsLeftOfWinner;

        public BattleResult(boolean p_battleWonByPlayer, int p_troopsLeftOfWinner) {
            this.d_battleWonByPlayer = p_battleWonByPlayer;
            this.d_troopsLeftOfWinner = p_troopsLeftOfWinner;
        }
    }

    public Advance(Country p_sourceTerritory, Country p_targetTerritory, int p_toAdvance, Player p_initiator) {
        this.d_sourceTerritory = p_sourceTerritory;
        this.d_targetTerritory = p_targetTerritory;
        this.d_toAdvance = p_toAdvance;
        this.d_initiator = p_initiator;
    }

    @Override
    public void execute() {
        int actualTroopsAdvance = getActualTroopsAdvance(d_toAdvance);
        this.d_sourceTerritory.setTroops(this.d_sourceTerritory.getTroops() - actualTroopsAdvance);
        System.out.println(actualTroopsAdvance + " troops of " + d_initiator.getName() + "'s army have advanced from" + d_sourceTerritory + " to "
                + d_targetTerritory);

        if (d_targetTerritory.getTroops() == 0) {
            // Unoccupied, user takes control without difficulty
            this.d_targetTerritory.setTroops(actualTroopsAdvance);

            // If country was owned, change owner to player
            if (d_targetTerritory.getOwner() != null)
            {
                d_targetTerritory.setOwner(d_initiator);
            }
        }
        else if (d_targetTerritory.getOwner() == d_initiator)
        {
            this.d_targetTerritory.setTroops(d_targetTerritory.getTroops() + actualTroopsAdvance);
        }
        else // Presently owned, battle occurs
        {
            System.out.println("Country " + d_targetTerritory + " currently owned by " + d_targetTerritory.getOwner()
            + ".  A battle commences!");

            // Determines who wins the battle
            BattleResult battleResult = calculateBattle(actualTroopsAdvance, d_targetTerritory.getTroops());
            boolean playerWon = battleResult.d_battleWonByPlayer;
            int troopsLeftOfWinner = battleResult.d_troopsLeftOfWinner;

            if (troopsLeftOfWinner == 0)
            {
                d_targetTerritory.setOwner(null); // Now unowned
            }
            else if (playerWon)
            {
                d_targetTerritory.setOwner(d_initiator); // Now unowned
                d_targetTerritory.setTroops(troopsLeftOfWinner);
            }
            else
            {
                d_targetTerritory.setTroops(troopsLeftOfWinner);
            }
        }
    }

    /**
     * Validates whether the deploy order can be executed.
     *
     * @return true if the order is valid (i.e., the player owns the target territory), otherwise false.
     */
    public boolean valid() {
        if (d_targetTerritory.getOwner().equals(d_initiator)) {
            // The target territory must belong to the player that created the order
            return true;
        } else {
            System.out.println("invalid order");
            return false;
        }
    }

    private boolean calculateBattleWon(int p_troopsInvading, int p_troopsDefending)
    {
        double l_probabilityOfWinning = (double )p_troopsInvading / (p_troopsInvading + p_troopsDefending);
        Random random = new Random();
        return random.nextDouble() < l_probabilityOfWinning;
    }

    private BattleResult calculateBattle(int playersTroops, int opponentsTroops)
    {
        while (Math.min(playersTroops, opponentsTroops) > 0)
        {
            if (calculateBattleWon(playersTroops, opponentsTroops))
            {
                opponentsTroops -= 1;
            }
            else
            {
                playersTroops -= 1;
            }
        }

        if (playersTroops > 0)
        {
            return new BattleResult(true, playersTroops);
        }
        else if (opponentsTroops > 0)
        {
            return new BattleResult(false, playersTroops);
        }
        else
        {
            return new BattleResult(true, 0);
        }
    }

    private int getActualTroopsAdvance(int p_toAdvance)
    {
        return Math.min(p_toAdvance, d_sourceTerritory.getTroops());
    }
}
