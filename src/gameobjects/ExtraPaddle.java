package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents an extra paddle in the Bricker game.
 * Inherits movement controls from UserPaddle, but tracks its own ball collisions
 * and automatically removes itself from the game after 4 collisions.
 */
public class ExtraPaddle extends UserPaddle {
    private static final int MAX_COLLISIONS = 4;
    private final GameObjectCollection gameObjects;
    private int numCollisions;

    /**
     * Constructs a new ExtraPaddle instance and adds it to the provided GameObjectCollection.
     * @param topLeftCorner     Initial position of the extra paddle in window coordinates.
     * @param dimensions        Width and height of the extra paddle.
     * @param renderable        Image of the paddle.
     * @param minX              Minimum allowed X-coordinate boundary.
     * @param maxX              Maximum allowed X-coordinate boundary.
     * @param inputListener     UserInputListener to read keyboard movement input.
     * @param gameObjects       GameObjectCollection used for self-addition and self-removal.
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       float minX, float maxX, UserInputListener inputListener,
                       GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable, minX, maxX, inputListener);
        this.gameObjects = gameObjects;
        this.numCollisions = 0;
        this.gameObjects.addGameObject(this);
    }

    /**
     * Handles collision events for the extra paddle.
     * Increments the collision counter when colliding with a Ball instance
     * and removes itself from gameObjects when reaching the maximum collision quota.
     * @param other     The GameObject colliding with this extra paddle.
     * @param collision Collision data containing normal vectors and collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        // Increment counter for Ball/Puck collisions (ignore collisions with walls).
        // Remove paddle if quota has been met.
        if (other instanceof Ball) {
            this.numCollisions++;
            if (this.numCollisions == MAX_COLLISIONS) {
                gameObjects.removeGameObject(this);
            }
        }
    }

    /**
     * Checks whether the extra paddle has reached its maximum collision quota.
     * @return True if the collision quota has been reached, false otherwise.
     */
    public boolean hitQuota() {
        return numCollisions == MAX_COLLISIONS;
    }
}