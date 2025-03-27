package ca.concordia.soen6441.project.gameplay.orders;

import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Order;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.log.LogEntryBuffer;

import java.util.Random;

/**
 * This class represents moving armies from one of the current player’s territories (source) to an
 * adjacent territory (target). If the target territory belongs to the current player,
 * the armies are moved to the target territory. If the target territory belongs to
 * another player, an attack happens between the two territories.
 */
public class Advance implements Order {

    /**
     * Internal class representing the results from a battle
     */
    private class BattleResult
    {
        private final int d_playersTroops;
        private final int d_opponentsTroops;

        /**
         * Constructor For BattleResult
         * @param p_playersTroops Number of troops the player has
         * @param p_opponentsTroops Number of troops the opponent has
         */
        public BattleResult(int p_playersTroops, int p_opponentsTroops) {
            this.d_playersTroops = p_playersTroops;
            this.d_opponentsTroops = p_opponentsTroops;
        }
    }

    private Country d_sourceTerritory;
    private Country d_targetTerritory;
    private int d_toAdvance;
    private Player d_initiator;
    private Random d_random;
    private boolean d_conquersTerritory;
    private GameContext d_gameEngine;

    /**
     * Constructor for Advance
     * @param p_sourceTerritory The country the player wants his army to move from
     * @param p_targetTerritory The country the player wants his army to move to
     * @param p_toAdvance Number of troops the player wants to advance
     * @param p_initiator The player that initiated the command
     * @param p_gameEngine The game engine object
     */
    public Advance(Country p_sourceTerritory, Country p_targetTerritory, int p_toAdvance, Player p_initiator, GameContext p_gameEngine) {
        this.d_sourceTerritory = p_sourceTerritory;
        this.d_targetTerritory = p_targetTerritory;
        this.d_toAdvance = p_toAdvance;
        this.d_initiator = p_initiator;
        this.d_random = new Random();
        this.d_conquersTerritory = false;
        d_gameEngine = p_gameEngine;
    }

    /**
     * Execute method for Advance command
     */
    @Override
    public void execute() {
        int l_actualTroopsAdvance = getActualTroopsAdvance(d_toAdvance);
        this.d_sourceTerritory.setTroops(this.d_sourceTerritory.getTroops() - l_actualTroopsAdvance);

        // Cancel attack if diplomacy pact exists
        if (d_targetTerritory.getOwner() != null && d_initiator.hasNegotiatedWith(d_targetTerritory.getOwner())) {
            LogEntryBuffer.getInstance().appendToBuffer("Attack canceled due to diplomacy pact between "
                + d_initiator.getName() + " and " + d_targetTerritory.getOwner().getName(), true);
            return;
        }

        String l_errorMsg = null;
        if ((l_errorMsg = validate()) != null)
        {
            // Error found, write to screen and add to log
            LogEntryBuffer.getInstance().appendToBuffer(l_errorMsg, true);
        }
        else
        {
            LogEntryBuffer.getInstance().appendToBuffer(d_initiator.getName() + "'s army have advanced " + l_actualTroopsAdvance + " troops from "
                    + d_sourceTerritory.getID() + " to "
                    + d_targetTerritory.getID(), true);

            if (d_targetTerritory.getTroops() == 0) {
                // Unoccupied, user takes control without difficulty
                this.d_targetTerritory.setTroops(l_actualTroopsAdvance);

                // If country was owned, change owner to player

                d_gameEngine.assignCountryToPlayer(d_targetTerritory, d_initiator);
                d_conquersTerritory = true;
                if (d_targetTerritory.getOwner() != null)
                {
                    LogEntryBuffer.getInstance().appendToBuffer(d_targetTerritory.getOwner().getName() + " conquers undefended " + d_targetTerritory.getID(), true);
                }
                else
                {
                    LogEntryBuffer.getInstance().appendToBuffer(d_targetTerritory.getOwner().getName() + " conquers unowned " + d_targetTerritory.getID(), true);
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
                    d_gameEngine.assignCountryToPlayer(d_targetTerritory, d_initiator);
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
    }

    /**
     * Calculates if a battle was won for a given soldier
     * @param p_probabilityOfWinning The probability that a given soldier has of winning
     * @return true if won, false otherwise
     */
    private boolean calculateBattleWon(double p_probabilityOfWinning)
    {
        return d_random.nextDouble() < p_probabilityOfWinning;
    }

    /**
     * Method that loops through the each solider fought until one side finally wins
     * @param p_playersTroops Number of troops the current player has
     * @param p_opponentsTroops Number of troops the defender has
     * @return A BattleResult object with stats on the outcome of the battle
     */
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

    /**
     * Obtain the actual number of troops available from the source
     * (Could change between the issue order and when executed)
     * @param p_toAdvance Number of troops requested when order was issued
     * @return Actual number of troops available ( <= # requested)
     */
    private int getActualTroopsAdvance(int p_toAdvance)
    {
        return Math.min(p_toAdvance, d_sourceTerritory.getTroops());
    }

    /**
     * Gets the Source country where the troops will move form
     * @return A country object representing the source country
     */
    public Country getSourceTerritory() {
        return d_sourceTerritory;
    }

    /**
     * Gets the Destination country where the troops will move form
     * @return A country object representing the destination country
     */
    public Country getTargetTerritory() {
        return d_targetTerritory;
    }

    /**
     * Get the number of troops requested to advance when the order was issued
     * @return An integer value representing the # of troops
     */
    public int getToAdvance() {
        return d_toAdvance;
    }

    /**
     * Get the player that issued the order
     * @return A Player object representing the player that initiated the order
     */
    public Player getInitiator() {
        return d_initiator;
    }

    /**
     * Get a string representing the # of troops of the attacker vs. the defender
     * @param p_playersTroops # of troops of the attacker
     * @param p_opponentsTroops # of troops of the defender
     * @return String value representing the # of troops of the attacker vs. the defender
     */
    public String getTroopStatsInfo(int p_playersTroops, int p_opponentsTroops)
    {

        return "(" + d_initiator.getName() + ": " + p_playersTroops + ";"
                + d_targetTerritory.getOwner().getName() + ": " + p_opponentsTroops + ")";
    }

    /**
     * Get a String Representation representing this object
     * @return A String value
     */
    @Override
    public String toString() {
        return "{Advance " + d_sourceTerritory.getID() +
                " " + d_targetTerritory.getID() +
                " " + d_toAdvance + "}";
    }

    /**
     * Get Method for determining if the attacker won the battle
     * @return true if atacker won battle, false if battle lost or didnt take place
     */
    public boolean conquersTerritory() {
        return d_conquersTerritory;
    }

    /**
     * Used to validate whether there are any issues prior to executing an Order
     * @return A string if an error occurs, null otherwise.
     */
    public String validate() {
        if (d_sourceTerritory.equals(d_targetTerritory)) {
            return "Error: Source and target territories cannot be the same.";
        }
        if (!d_sourceTerritory.getOwner().getName().equals(d_initiator.getName())) {
            return "Error: Player does not own the source territory.";
        }
        if (!d_sourceTerritory.getNeighborIDs().contains(d_targetTerritory.getID())) {
            return "Error: Source and target territories are not neighbors.";
        }
        if (d_sourceTerritory.getTroops() < d_toAdvance) {
            return "Error: No longer enough troops in the source territory.";
        }

        return null;
    }
}
