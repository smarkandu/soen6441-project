package ca.concordia.soen6441.project.gameplay.orders;


/**
 * Internal class representing the results from a battle
 */
public class BattleResult
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

    /**
     * Gets number of troops left of the attacker
     * @return Integer value
     */
    public int getPlayersTroops() {
        return d_playersTroops;
    }

    /**
     * Gets number of troops left of the opponent
     * @return Integer value
     */
    public int getOpponentsTroops() {
        return d_opponentsTroops;
    }
}
