package brick_strategies;

import danogl.GameObject;
import gameobjects.Brick;
import manager.BrickerGameManager;

/**
 * Brick collision strategy that triggers the creation of two Puck balls
 * at the center of a destroyed brick.
 * Inherits behavior from BasicCollisionStrategy.
 */
public class PuckStrategy implements CollisionStrategy {
    private final BrickerGameManager gameManager;

    /**
     * Constructs a new PuckStrategy instance.
     * @param manager Reference to the BrickerGameManager used to create pucks.
     */
    public PuckStrategy(BrickerGameManager manager) {
        this.gameManager = manager;
    }

    /**
     * Handles the collision event between a ball and a brick.
     * Removes the brick via BasicCollisionStrategy and triggers the creation of two pucks.
     * @param thisObj  The Brick object involved in the collision.
     */
    @Override
    public void onCollision(Brick thisObj) {
        gameManager.removeBrick(thisObj, false, thisObj.getRow(), thisObj.getCol());
        gameManager.createPucks(thisObj.getCenter());
    }
}
