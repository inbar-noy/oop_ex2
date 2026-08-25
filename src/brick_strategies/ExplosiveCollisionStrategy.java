package brick_strategies;

import danogl.GameObject;
import gameobjects.Brick;
import manager.BrickerGameManager;

/**
 * A collision that blows up the brick and its surrounding bricks
 */
public class ExplosiveCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager manager;

    /**
     * Construct an explosive collision strategy
     * @param manager Parent game manager
     */
    public ExplosiveCollisionStrategy(BrickerGameManager manager) {
        this.manager = manager;
    }

    /**
     * Blow up a brick
     * @param thisObj This (brick) object
     * @param otherObj Other object
     */
    public void onCollision(Brick thisObj, GameObject otherObj) {
        manager.removeBrick(thisObj, true, thisObj.getRow(), thisObj.getCol());
    }
}
