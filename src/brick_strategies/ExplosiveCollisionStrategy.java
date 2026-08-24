package brick_strategies;

import danogl.GameObject;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import gameobjects.Brick;
import manager.BrickerGameManager;

public class ExplosiveCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager manager;
    private final Sound explosionSound;

    public ExplosiveCollisionStrategy(BrickerGameManager manager, SoundReader soundReader) {
        this.explosionSound = soundReader.readSound("assets/explosion.wav");
        this.manager = manager;
    }

    public void onCollision(Brick thisObj, GameObject otherObj) {
        manager.removeBrick(thisObj, true, thisObj.getRow(), thisObj.getCol());
        this.explosionSound.play();
    }
}
