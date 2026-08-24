package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class ExtraPaddle extends UserPaddle{
    private static final int MAX_COLLISIONS = 4;
    private int numCollisions;

    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       float minX, float maxX, UserInputListener inputListener) {
        super(topLeftCorner, dimensions, renderable, minX, maxX, inputListener);
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
        }
    }

    public boolean hitQuota() {
        return numCollisions >= MAX_COLLISIONS;
    }
}
