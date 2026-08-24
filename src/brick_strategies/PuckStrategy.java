package brick_strategies;

import danogl.GameObject;
import gameobjects.Brick;
import manager.BrickerGameManager;

public class PuckStrategy extends BasicCollisionStrategy{
    private final BrickerGameManager gameManager;

    public PuckStrategy(BrickerGameManager manager) {
        super(manager);
        this.gameManager = manager;
    }

    @Override
    public void onCollision(Brick thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        // get the manager to create and add the pucks to the game objects
        gameManager.createPucks(thisObj.getCenter());
    }
}
