package gameobjects;

import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Represents the user-controlled main paddle in the Bricker game.
 * Reads user keyboard input (Left and Right arrow keys) to move the paddle horizontally.
 */
public class UserPaddle extends Paddle {
    private static final float MOVEMENT_SPEED = 300f;
    private final UserInputListener inputListener;

    /**
     * Constructs a new UserPaddle instance.
     * @param topLeftCorner Initial position of the paddle in window coordinates.
     * @param dimensions    Width and height of the paddle.
     * @param renderable    Image representation of the paddle.
     * @param minX          Minimum allowed X-coordinate boundary.
     * @param maxX          Maximum allowed X-coordinate boundary.
     * @param inputListener UserInputListener to read keyboard key presses.
     */
    public UserPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                      float minX, float maxX, UserInputListener inputListener) {
        super(topLeftCorner, dimensions, renderable, minX, maxX);
        this.inputListener = inputListener;
    }

    /**
     * Updates the paddle's velocity for each frame based on user keyboard input.
     * Sets velocity to move left when VK_LEFT is pressed, right when VK_RIGHT is pressed,
     * or stay put when no movement keys are held down.
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Vector2 movementDir = Vector2.ZERO;

        // Move left if left arrow key is held down
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        // Move right if right arrow key is held down
        else if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }

        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}