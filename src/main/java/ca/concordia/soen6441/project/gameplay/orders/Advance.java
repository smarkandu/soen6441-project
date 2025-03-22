package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;

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
        int l_actualTroopsAdvance = getActualTroopsAdvance(d_toAdvance);
        this.d_sourceTerritory.setTroops(this.d_sourceTerritory.getTroops() - l_actualTroopsAdvance);

        if (l_actualTroopsAdvance == 0)
        {
            System.out.println("No troops exist anymore in " + d_sourceTerritory.getID() + " for " + d_initiator.getName()
            + " to advance.  Command cancelled.");
            return;
        }

        System.out.println(d_initiator.getName() + "'s army have advanced " + l_actualTroopsAdvance + " troops from "
                + d_sourceTerritory.getID() + " to "
                + d_targetTerritory.getID());

        if (d_targetTerritory.getTroops() == 0) {
            // Unoccupied, user takes control without difficulty
            this.d_targetTerritory.setTroops(l_actualTroopsAdvance);

            // If country was owned, change owner to player
            if (d_targetTerritory.getOwner() != null)
            {
                d_targetTerritory.setOwner(d_initiator);
                System.out.println(d_targetTerritory.getOwner().getName() + " conquers undefended " + d_targetTerritory.getID());
            }
            else
            {
                System.out.println(d_targetTerritory.getOwner().getName() + " conquers unowned " + d_targetTerritory.getID()); // Now unowned
            }
        }
        else if (d_targetTerritory.getOwner() == d_initiator)
        {
            this.d_targetTerritory.setTroops(d_targetTerritory.getTroops() + l_actualTroopsAdvance);
        }
        else // Presently owned, battle occurs
        {
            System.out.println("Country " + d_targetTerritory.getID() + " currently owned by " + d_targetTerritory.getOwner().getName()
            + ".  A battle commences!");

            // Determines who wins the battle
            BattleResult l_battleResult = calculateBattle(l_actualTroopsAdvance, d_targetTerritory.getTroops());
            boolean l_playerWon = l_battleResult.d_battleWonByPlayer;
            int l_troopsLeftOfWinner = l_battleResult.d_troopsLeftOfWinner;

            if (l_troopsLeftOfWinner == 0)
            {
                d_targetTerritory.setOwner(null); // Now unowned
                d_targetTerritory.setTroops(0);
                System.out.println("No troops survived.  Country " + d_targetTerritory.getID() + " is now neutral");
            }
            else if (l_playerWon)
            {
                d_targetTerritory.setOwner(d_initiator); // Now unowned
                d_targetTerritory.setTroops(l_troopsLeftOfWinner);
                System.out.println(d_targetTerritory.getOwner().getName() + " wins the battle and now owns " + d_targetTerritory.getID() + "!\nRemaining survivors: " + d_targetTerritory.getTroops()); // Now unowned
            }
            else
            {
                System.out.println(d_targetTerritory.getOwner().getName() + " fends of attacker at " + d_targetTerritory.getID() + " and wins the battle!\nRemaining survivors: " + d_targetTerritory.getTroops()); // Now unowned
                d_targetTerritory.setTroops(l_troopsLeftOfWinner);
            }
        }
    }

    private boolean calculateBattleWon(int p_troopsInvading, int p_troopsDefending)
    {
        double l_probabilityOfWinning = (double )p_troopsInvading / (p_troopsInvading + p_troopsDefending);
        Random l_random = new Random();
        return l_random.nextDouble() < l_probabilityOfWinning;
    }

    private BattleResult calculateBattle(int p_playersTroops, int p_opponentsTroops)
    {
        while (Math.min(p_playersTroops, p_opponentsTroops) > 0)
        {
            if (calculateBattleWon(p_playersTroops, p_opponentsTroops))
            {
                p_opponentsTroops -= 1;
            }
            else
            {
                p_playersTroops -= 1;
            }
        }

        if (p_playersTroops > 0)
        {
            return new BattleResult(true, p_playersTroops);
        }
        else if (p_opponentsTroops > 0)
        {
            return new BattleResult(false, p_playersTroops);
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

    public Country getSourceTerritory() {
        return d_sourceTerritory;
    }

    public Country getTargetTerritory() {
        return d_targetTerritory;
    }

    public int getToAdvance() {
        return d_toAdvance;
    }

    public Player getInitiator() {
        return d_initiator;
    }

    @Override
    public String toString() {
        return "{Advance " + d_sourceTerritory.getID() +
                " " + d_targetTerritory.getID() +
                " " + d_toAdvance + "}";
    }
}
