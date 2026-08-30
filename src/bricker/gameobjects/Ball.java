package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * Represents a ball in the Bricker game.
 */
public class Ball extends GameObject {
    public static final String BALL_TAG = "Ball";
    private static final Random random = new Random();
    private final Sound collisionSound;

    /**
     * Constructs a new Ball instance.
     * @param topLeftCorner  Initial position of the ball in window coordinates.
     * @param dimensions     Width and height of the ball.
     * @param renderable     Image of the ball.
     * @param collisionSound Sound effect to play upon collision.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.setTag(BALL_TAG);
    }

    /**
     * Handles collision events for the ball.
     * Rebounds (flips) the ball's velocity vector relative to the collision normal vector
     * and plays the collision sound effect.
     * @param other     The GameObject that collided with this ball.
     * @param collision Collision data containing normal vector and collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {

        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
    }

    /**
     * Sets a random initial diagonal velocity for the ball with the specified speed magnitude.
     * Randomly chooses positive or negative directions for both X and Y components.
     * @param speed The speed magnitude for the initial velocity components.
     */
    public void setInitialVelocity(float speed) {
        float velocityX = speed;
        float velocityY = speed;

        if (random.nextBoolean()) { velocityX *= -1; }
        if (random.nextBoolean()) { velocityY *= -1; }

        this.setVelocity(new Vector2(velocityX, velocityY));
    }
}
