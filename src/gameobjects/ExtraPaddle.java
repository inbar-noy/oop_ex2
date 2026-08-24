package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class ExtraPaddle extends UserPaddle{
    private static final int MAX_COLLISIONS = 4;
    private final GameObjectCollection gameObjects;
    private int numCollisions;

    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       float minX, float maxX, UserInputListener inputListener,
                       GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable, minX, maxX, inputListener);
        this.gameObjects = gameObjects;
        this.numCollisions = 0;
    }

    public int getNumCollisions() {
        return numCollisions;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        // make sure hitting the walls doesn't affect numCollisions
        if(other instanceof Ball) {
            this.numCollisions++;
            if (this.numCollisions >= MAX_COLLISIONS) {
                gameObjects.removeGameObject(this);
            }
        }
    }

    public boolean hitQuota() {
        return numCollisions >= MAX_COLLISIONS;
    }
}
