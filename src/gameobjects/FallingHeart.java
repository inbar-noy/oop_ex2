package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import manager.BrickerGameManager;

/**
 * A class representing the +1 life heart
 */
public class FallingHeart extends GameObject {
    private final BrickerGameManager manager;
    private final GameObject mainPaddle;

    /**
     * Construct a falling heart
     * @param topLeftCorner Top left corner coordinate
     * @param dimensions Dimensions of heart
     * @param renderable Heart image
     * @param manager Parent game manager
     * @param mainPaddle Main paddle, the only object that can collide with a heart
     */
    public FallingHeart(Vector2 topLeftCorner,
                        Vector2 dimensions,
                        Renderable renderable,
                        BrickerGameManager manager,
                        GameObject mainPaddle) {
        super(topLeftCorner, dimensions, renderable);
        this.manager = manager;
        this.mainPaddle = mainPaddle;
    }

    /**
     * Override shouldCollideWith so that only the main paddle will collide with a heart
     * @param other Other object of collision
     * @return Whether a collision should occur
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return super.shouldCollideWith(other) && (other == mainPaddle);
    }

    /**
     * Override onCollisionEnter to override the collision behavior
     * @param other Other object the collision is with
     * @param collision Collision object
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.manager.incrementHearts();
        this.manager.removeHeart(this);
    }
}
