package brick_strategies;

import danogl.GameObject;
import gameobjects.Brick;
import manager.BrickerGameManager;

/**
 * Brick collision strategy that triggers the creation of two Puck balls
 * at the center of a destroyed brick.
 * Inherits behavior from BasicCollisionStrategy.
 */
public class PuckStrategy extends BasicCollisionStrategy {
    private final BrickerGameManager gameManager;

    /**
     * Constructs a new PuckStrategy instance.
     * @param manager Reference to the BrickerGameManager used to create pucks.
     */
    public PuckStrategy(BrickerGameManager manager) {
        super(manager);
        this.gameManager = manager;
    }

    /**
     * Handles the collision event between a ball and a brick.
     * Removes the brick via BasicCollisionStrategy and triggers the creation of two pucks.
     * @param thisObj  The Brick object involved in the collision.
     * @param otherObj The GameObject colliding with the brick.
     */
    @Override
    public void onCollision(Brick thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        gameManager.createPucks(thisObj.getCenter());
    }
}