package factories;

import brick_strategies.*;
import danogl.gui.SoundReader;
import manager.BrickerGameManager;


public class BrickStrategyFactory {
    private final BrickerGameManager gameManager;
    private final SoundReader soundReader;

    public BrickStrategyFactory(BrickerGameManager manager, SoundReader soundReader) {
        this.gameManager = manager;
        this.soundReader = soundReader;
    }

    public CollisionStrategy selectBrickStrategy() {

        double rand = Math.random();
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
            return new ExplosiveCollisionStrategy(gameManager, soundReader);
        }
        else {
            return new ExtraLifeCollisionStrategy(gameManager);
        }
    }
}