package ca.concordia.soen6441.project.gameplay.behaviour;

import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.interfaces.gameplay.behavior.PlayerBehavior;

/**
 * Class implementing the Factory design pattern for creating a PlayerBehavior object
 */
public class PlayerBehaviorFactory {
    public PlayerBehaviorFactory(GameContext p_GameEngine) {
    }

    public PlayerBehaviorFactory() {

    }

    /**
     * Creates a PlayerBehavior object
     *
     * @param p_playerBehaviorType PlayerBehavior enum value stating the player behavior type
     * @return A PlayerBehavior object
     */
    public PlayerBehavior createPlayerBehavior(PlayerBehaviorType p_playerBehaviorType) {
        switch (p_playerBehaviorType) {
            case HUMAN:
                return new HumanPlayerBehavior();
            case AGGRESSIVE:
                return new AggressivePlayerBehavior();
            case BENEVOLENT:
                return new BenevolentPlayerBehavior();
            case RANDOM:
                return new RandomPlayerBehavior();
            case CHEATER:
                return new CheaterPlayerBehavior();
            default:
                throw new IllegalArgumentException("Unknown behavior type: " + p_playerBehaviorType);
        }
    }
}
