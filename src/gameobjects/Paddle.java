package gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Abstract class representing a paddle in the Bricker game.
 */
public abstract class Paddle extends GameObject {
    private final float minX;
    private final float maxX;

    /**
     * Constructs a new Paddle instance.
     * @param topLeftCorner Position of the paddle in window coordinates.
     * @param dimensions    Width and height of the paddle.
     * @param renderable    Image of the paddle.
     * @param minX          Minimum allowed X-coordinate boundary.
     * @param maxX          Maximum allowed X-coordinate boundary.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  float minX, float maxX) {
        super(topLeftCorner, dimensions, renderable);
        this.minX = minX;
        this.maxX = maxX;
    }

    /**
     * Updates the paddle's state for each frame and clamps its position
     * between minX and maxX boundaries.
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        float curX = getTopLeftCorner().x();
        if (curX < minX) {
            setTopLeftCorner(new Vector2(minX, getTopLeftCorner().y()));
        } else if (curX > maxX) {
            setTopLeftCorner(new Vector2(maxX, getTopLeftCorner().y()));
        }
    }
}
