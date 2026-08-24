package brick_strategies;

import danogl.GameObject;
import gameobjects.Brick;
import manager.BrickerGameManager;

public class BasicCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager manager;

    public BasicCollisionStrategy(BrickerGameManager manager) {
        this.manager = manager;
    }

    public void onCollision(Brick thisObj, GameObject otherObj) {
        manager.removeBrick(thisObj, false, thisObj.getRow(), thisObj.getCol());
    }
}
