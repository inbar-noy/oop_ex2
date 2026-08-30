package bricker.brick_strategies;

import danogl.GameObject;
import danogl.util.Vector2;
import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;

/**
 * A collision strategy that drops another life
 */
public class ExtraLifeCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager manager;

    /**
     * Construct an extra life collision strategy
     * @param manager Parent game Bricker.manager
     */
    public ExtraLifeCollisionStrategy(BrickerGameManager manager) {
        this.manager = manager;
    }

    /**
     * Drop an extra life
     * @param thisObj This (brick) object
     * @param otherObj Unused
     */
    public void onCollision(Brick thisObj, GameObject otherObj) {
        Vector2 location = thisObj.getCenterCoordinates();
        manager.removeBrick(thisObj, false, thisObj.getRow(), thisObj.getCol());
        manager.startFallingHeart(location);
    }
}
