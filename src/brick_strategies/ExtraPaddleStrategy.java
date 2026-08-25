package brick_strategies;

import danogl.GameObject;
import gameobjects.Brick;
import manager.BrickerGameManager;

/**
 * Brick collision strategy that triggers the creation of an ExtraPaddle
 * at the center of the game window upon brick destruction.
 * Inherits behavior from BasicCollisionStrategy.
 */
public class ExtraPaddleStrategy extends BasicCollisionStrategy {
    private final BrickerGameManager gameManager;

    /**
     * Constructs a new ExtraPaddleStrategy instance.
     * @param manager Reference to the BrickerGameManager used to create the extra paddle.
     */
    public ExtraPaddleStrategy(BrickerGameManager manager) {
        super(manager);
        this.gameManager = manager;
    }

    /**
     * Handles the collision event between a ball or puck and a brick.
     * Removes the brick via BasicCollisionStrategy and triggers the creation of an extra paddle.
     * @param thisObj  The Brick object involved in the collision.
     * @param otherObj The GameObject colliding with the brick.
     */
    @Override
    public void onCollision(Brick thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        gameManager.createExtraPaddle();
    }
}