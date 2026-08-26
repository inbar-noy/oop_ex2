package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * Collision strategy that simply removes the brick.
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager manager;

    /**
     * Construct a basic collision strategy
     * @param manager Parent game Bricker.manager
     */
    public BasicCollisionStrategy(BrickerGameManager manager) {
        this.manager = manager;
    }

    /**
     * Remove the brick
     * @param thisObj This (brick) object
     * @param otherObj Unused
     */
    public void onCollision(Brick thisObj, GameObject otherObj) {
        manager.removeBrick(thisObj, false, thisObj.getRow(), thisObj.getCol());
    }
}
