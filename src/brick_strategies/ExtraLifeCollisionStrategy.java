package brick_strategies;

import danogl.GameObject;
import danogl.util.Vector2;
import gameobjects.Brick;
import manager.BrickerGameManager;

public class ExtraLifeCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager manager;

    public ExtraLifeCollisionStrategy(BrickerGameManager manager) {
        this.manager = manager;
    }

    public void onCollision(Brick thisObj, GameObject otherObj) {
        Vector2 location = thisObj.getCenterCoordinates();
        manager.removeBrick(thisObj);
        manager.startFallingHeart(location);
    }
}
