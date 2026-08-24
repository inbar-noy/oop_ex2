package gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public abstract class Paddle extends GameObject {
    private final float minX;
    private final float maxX;

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  float minX, float maxX) {
        super(topLeftCorner, dimensions, renderable);
        this.minX = minX;
        this.maxX = maxX;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Clamp paddle position to be within bounds
        float curX = getTopLeftCorner().x();
        if (curX < minX) {
            setTopLeftCorner(new Vector2(minX, getTopLeftCorner().y()));
        } else if (curX > maxX) {
            setTopLeftCorner(new Vector2(maxX, getTopLeftCorner().y()));
        }
    }

    
}