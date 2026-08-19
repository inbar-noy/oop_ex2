package gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class AIPaddle extends Paddle {
    private static final float MOVEMENT_SPEED = 300f;
    private static final float EPSILON = 5f; // to handle jittering
    private final GameObject objectToFollow;

    public AIPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                    float minX, float maxX, GameObject objectToFollow) {
        super(topLeftCorner, dimensions, renderable, minX, maxX);
        this.objectToFollow = objectToFollow;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        float paddleCenterX = getCenter().x();
        float targetCenterX = objectToFollow.getCenter().x();

        if (targetCenterX < paddleCenterX - EPSILON) {
            setVelocity(Vector2.LEFT.mult(MOVEMENT_SPEED));
        } else if (targetCenterX > paddleCenterX + EPSILON) {
            setVelocity(Vector2.RIGHT.mult(MOVEMENT_SPEED));
        } else {
            setVelocity(Vector2.ZERO);
        }
    }
}