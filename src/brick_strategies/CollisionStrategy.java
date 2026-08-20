package brick_strategies;

import danogl.GameObject;
import gameobjects.Brick;

public interface CollisionStrategy {
    void onCollision(Brick thisObj, GameObject otherObj);
}
