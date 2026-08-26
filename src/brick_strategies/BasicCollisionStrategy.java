package brick_strategies;

import danogl.GameObject;
import gameobjects.Brick;
import manager.BrickerGameManager;

/**
 * Collision strategy that simply removes the brick.
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager manager;

    /**
     * Construct a basic collision strategy
     * @param manager Parent game manager
     */
    public BasicCollisionStrategy(BrickerGameManager manager) {
        this.manager = manager;
    }

    /**
     * Remove the brick
     * @param thisObj This (brick) object
     */
    public void onCollision(Brick thisObj) {
        manager.removeBrick(thisObj, false, thisObj.getRow(), thisObj.getCol());
    }
}
