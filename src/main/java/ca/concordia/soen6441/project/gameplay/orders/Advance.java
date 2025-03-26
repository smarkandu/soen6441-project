package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.util.Random;

public class Advance implements Order {
    private class BattleResult
    {
        private final int d_playersTroops;
        private final int d_opponentsTroops;

        public BattleResult(int p_playersTroops, int p_opponentsTroops) {
            this.d_playersTroops = p_playersTroops;
            this.d_opponentsTroops = p_opponentsTroops;
        }

        public int getPlayersTroops() {
            return d_playersTroops;
        }

        public int getOpponentsTroops() {
            return d_opponentsTroops;
        }
    }


    private Country d_sourceTerritory;
    private Country d_targetTerritory;
    private int d_toAdvance;
    private Player d_initiator;
    private Random d_random;
    private boolean d_conquersTerritory;


    public Advance(Country p_sourceTerritory, Country p_targetTerritory, int p_toAdvance, Player p_initiator) {
        this.d_sourceTerritory = p_sourceTerritory;
        this.d_targetTerritory = p_targetTerritory;
        this.d_toAdvance = p_toAdvance;
        this.d_initiator = p_initiator;
        this.d_random = new Random();
        this.d_conquersTerritory = false;
    }

    @Override
    public void execute() {
        int l_actualTroopsAdvance = getActualTroopsAdvance(d_toAdvance);
        this.d_sourceTerritory.setTroops(this.d_sourceTerritory.getTroops() - l_actualTroopsAdvance);

        if (l_actualTroopsAdvance == 0)
        {
            LogEntryBuffer.getInstance().appendToBuffer("No troops exist anymore in " + d_sourceTerritory.getID() + " for " + d_initiator.getName()
            + " to advance.  Command cancelled.", true);
            return;
        }

        LogEntryBuffer.getInstance().appendToBuffer(d_initiator.getName() + "'s army have advanced " + l_actualTroopsAdvance + " troops from "
                + d_sourceTerritory.getID() + " to "
                + d_targetTerritory.getID(), true);

        if (d_targetTerritory.getTroops() == 0) {
            // Unoccupied, user takes control without difficulty
            this.d_targetTerritory.setTroops(l_actualTroopsAdvance);

            // If country was owned, change owner to player
            if (d_targetTerritory.getOwner() != null)
            {
                d_targetTerritory.setOwner(d_initiator);
                LogEntryBuffer.getInstance().appendToBuffer(d_targetTerritory.getOwner().getName() + " conquers undefended " + d_targetTerritory.getID(), true);
                d_conquersTerritory = true;
            }
            else
            {
                d_targetTerritory.setOwner(d_initiator);
                LogEntryBuffer.getInstance().appendToBuffer(d_targetTerritory.getOwner().getName() + " conquers unowned " + d_targetTerritory.getID(), true); // Now unowned
                d_conquersTerritory = true;
            }
        }
        else if (d_targetTerritory.getOwner() == d_initiator)
        {
            this.d_targetTerritory.setTroops(d_targetTerritory.getTroops() + l_actualTroopsAdvance);
        }
        else // Presently owned, battle occurs
        {
            // Determines who wins the battle
            BattleResult l_battleResult = calculateBattle(l_actualTroopsAdvance, d_targetTerritory.getTroops());

            if (l_battleResult.d_playersTroops > 0)
            {
                d_targetTerritory.setOwner(d_initiator); // Now unowned
                d_targetTerritory.setTroops(l_battleResult.d_playersTroops);
                LogEntryBuffer.getInstance().appendToBuffer(d_targetTerritory.getOwner().getName()
                        + " wins the battle and conquers " + d_targetTerritory.getID() + "!\nRemaining survivors: "
                        + d_targetTerritory.getTroops(), true);
                d_conquersTerritory = true;
            }
            else if (l_battleResult.d_opponentsTroops > 0)
            {
                d_targetTerritory.setTroops(l_battleResult.d_opponentsTroops);
                LogEntryBuffer.getInstance().appendToBuffer(d_targetTerritory.getOwner().getName()
                        + " fends of attacker at " + d_targetTerritory.getID() + " and wins the battle!\nRemaining survivors: "
                        + d_targetTerritory.getTroops(), true);
            }
        }
    }

    private boolean calculateBattleWon(double p_probabilityOfWinning)
    {
        return d_random.nextDouble() < p_probabilityOfWinning;
    }


    private BattleResult calculateBattle(int p_playersTroops, int p_opponentsTroops)
    {
        final double l_probability_winning_attacker = 0.60;
        final double l_probability_winning_defender = 0.70;

        String l_battleMessage = "*** Country " + d_targetTerritory.getID() + " currently owned by " + d_targetTerritory.getOwner().getName()
                + ".  A battle commences! ***";
        LogEntryBuffer.getInstance().appendToBuffer(l_battleMessage + " " + getTroopStatsInfo(p_playersTroops, p_opponentsTroops), true);

        boolean l_isInvader = true; // Invader goes first
        while (Math.min(p_playersTroops, p_opponentsTroops) > 0)
        {
            String l_message = "";
            if (l_isInvader)
            {
                l_message = d_initiator.getName() + " attacks and ";
                if (calculateBattleWon(l_probability_winning_attacker))
                {
                    p_opponentsTroops -= 1;
                    l_message += "kills 1 soldier of " + d_targetTerritory.getOwner().getName() + "!";
                }
                else
                {
                    l_message += "misses!";
                }
            }
            else
            {
                l_message = d_targetTerritory.getOwner().getName() + " retaliates and ";
                if (calculateBattleWon(l_probability_winning_defender))
                {
                    p_playersTroops -= 1;
                    l_message += "kills 1 soldier of " + d_initiator.getName() + "!";
                }
                else
                {
                    l_message += "misses!";
                }
            }

            l_message += " " + getTroopStatsInfo(p_playersTroops, p_opponentsTroops);
            LogEntryBuffer.getInstance().appendToBuffer(l_message, true);
            l_isInvader = !l_isInvader; // Alternate between each side
        }

        return new BattleResult(p_playersTroops, p_opponentsTroops);
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

    public String getTroopStatsInfo(int p_playersTroops, int p_opponentsTroops)
    {

        return "(" + d_initiator.getName() + ": " + p_playersTroops + ";"
                + d_targetTerritory.getOwner().getName() + ": " + p_opponentsTroops + ")";
    }

    @Override
    public String toString() {
        return "{Advance " + d_sourceTerritory.getID() +
                " " + d_targetTerritory.getID() +
                " " + d_toAdvance + "}";
    }

    public boolean conquersTerritory() {
        return d_conquersTerritory;
    }
}
