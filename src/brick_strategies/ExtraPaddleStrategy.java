package brick_strategies;

import danogl.GameObject;
import gameobjects.Brick;
import manager.BrickerGameManager;

public class ExtraPaddleStrategy extends BasicCollisionStrategy{
    private final BrickerGameManager gameManager;

    public ExtraPaddleStrategy(BrickerGameManager manager) {
        super(manager);
        this.gameManager = manager;
    }

    @Override
    public void onCollision(Brick thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        gameManager.createExtraPaddle();
    }
}
