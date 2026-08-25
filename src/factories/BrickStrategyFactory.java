package factories;

import brick_strategies.*;
import manager.BrickerGameManager;

/**
 * Factory class responsible for randomly selecting and instantiating
 * brick collision strategies based on defined probability distributions.
 */
public class BrickStrategyFactory {
    private final BrickerGameManager gameManager;

    /**
     * Constructs a new BrickStrategyFactory instance.
     * @param manager Reference to the BrickerGameManager passed to created strategies.
     */
    public BrickStrategyFactory(BrickerGameManager manager) {
        this.gameManager = manager;
    }

    /**
     * Randomly selects and returns a CollisionStrategy for a brick according to
     * specified probabilities: 50% BasicCollisionStrategy, 12.5% ExtraPaddleStrategy,
     * 12.5% PuckStrategy, 12.5% ExplosiveCollisionStrategy, and 12.5% ExtraLifeCollisionStrategy.
     * @return A randomly selected CollisionStrategy instance.
     */
    public CollisionStrategy selectBrickStrategy() {
        double rand = Math.random();

        // 50% probability: BasicCollisionStrategy
        if (rand < 0.5) {
            return new BasicCollisionStrategy(gameManager);
        }
        // 12.5% probability: ExtraPaddleStrategy
        else if (rand < 0.625) {
            return new ExtraPaddleStrategy(gameManager);
        }
        // 12.5% probability: PuckStrategy
        else if (rand < 0.75) {
            return new PuckStrategy(gameManager);
        }
        // 12.5% probability: ExplosiveCollisionStrategy
        else if (rand < 0.875) {
            return new ExplosiveCollisionStrategy(gameManager);
        }
        // 12.5% probability: ExtraLifeCollisionStrategy
        else {
            return new ExtraLifeCollisionStrategy(gameManager);
        }
    }
}
