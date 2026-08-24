package brick_strategies;

import danogl.GameObject;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import gameobjects.Brick;
import manager.BrickerGameManager;

public class ExplosiveCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager manager;

    public ExplosiveCollisionStrategy(BrickerGameManager manager) {
        this.manager = manager;
    }

    public void onCollision(Brick thisObj, GameObject otherObj) {
        manager.removeBrick(thisObj, true, thisObj.getRow(), thisObj.getCol());
    }
}
