package factories;

import brick_strategies.BasicCollisionStrategy;
import brick_strategies.CollisionStrategy;
import brick_strategies.ExtraPaddleStrategy;
import brick_strategies.PuckStrategy;
import manager.BrickerGameManager;


public class BrickStrategyFactory {
    private final BrickerGameManager gameManager;
    private final double rand;

    public BrickStrategyFactory(BrickerGameManager manager) {
        this.gameManager = manager;
        this.rand = Math.random();
    }

    public CollisionStrategy selectBrickStrategy() {

        if (rand < 0.5) {
            return new BasicCollisionStrategy(gameManager);
        }
        else if (rand < 0.625) {
            return new ExtraPaddleStrategy(gameManager);
        }
        else if (rand < 0.75) {
            return new PuckStrategy(gameManager);
        }
        else if (rand < 0.875) {
            return new BasicCollisionStrategy(gameManager);
        }
        else {
            return new BasicCollisionStrategy(gameManager);
        }
    }
}
