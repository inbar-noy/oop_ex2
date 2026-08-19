package gameobjects;

import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class UserPaddle extends Paddle {
    private static final float MOVEMENT_SPEED = 300f;
    private final UserInputListener inputListener;

    public UserPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                      float minX, float maxX, UserInputListener inputListener) {
        super(topLeftCorner, dimensions, renderable, minX, maxX);
        this.inputListener = inputListener;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        else if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}
