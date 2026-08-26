package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import danogl.GameObject;

/**
 * Strategy class for a collision with a brick.
 */
public interface CollisionStrategy {
    /**
     * Method to call when something collides with a brick.
     * @param thisObj This (brick) object
     * @param otherObj Unused
     */
    void onCollision(Brick thisObj, GameObject otherObj);
}
