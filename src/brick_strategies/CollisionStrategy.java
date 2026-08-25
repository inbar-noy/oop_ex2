package brick_strategies;

import danogl.GameObject;
import gameobjects.Brick;

/**
 * Strategy class for a collision with a brick.
 */
public interface CollisionStrategy {
    /**
     * Method to call when something collides with a brick.
     * @param thisObj This (brick) object
     * @param otherObj Other object
     */
    void onCollision(Brick thisObj, GameObject otherObj);
}
