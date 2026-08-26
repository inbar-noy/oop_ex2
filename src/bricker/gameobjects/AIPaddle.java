package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents an AI-controlled paddle in the Bricker game.
 * The AI paddle tracks the horizontal (X) position of a target GameObject
 * (will be used to follow ball)
 * and moves left or right to follow it.
 */
public class AIPaddle extends Paddle {
    private static final float MOVEMENT_SPEED = 300f;
    private static final float EPSILON = 5f; //  prevents paddle jittering
    private final GameObject objectToFollow;

    /**
     * Constructs a new AIPaddle instance.
     * @param topLeftCorner  Initial position of the AI paddle in window coordinates.
     * @param dimensions     Width and height of the AI paddle.
     * @param renderable     Image of the paddle.
     * @param minX           Minimum allowed X-coordinate boundary.
     * @param maxX           Maximum allowed X-coordinate boundary.
     * @param objectToFollow The GameObject target (e.g. Ball) that the AI paddle tracks.
     */
    public AIPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                    float minX, float maxX, GameObject objectToFollow) {
        super(topLeftCorner, dimensions, renderable, minX, maxX);
        this.objectToFollow = objectToFollow;
    }

    /**
     * Updates the AI paddle's velocity for each frame by comparing the paddle's
     * center X-position to the target object's center X-position.
     * Uses an EPSILON constant to prevent jittering when centered on the target.
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        float paddleCenterX = getCenter().x();
        float targetCenterX = objectToFollow.getCenter().x();

        // Move left if target is to the left of paddle center
        if (targetCenterX < paddleCenterX - EPSILON) {
            setVelocity(Vector2.LEFT.mult(MOVEMENT_SPEED));
        }
        // Move right if target is to the right of paddle center
        else if (targetCenterX > paddleCenterX + EPSILON) {
            setVelocity(Vector2.RIGHT.mult(MOVEMENT_SPEED));
        }
        // Stand still if within deadzone threshold
        else {
            setVelocity(Vector2.ZERO);
        }
    }
}
