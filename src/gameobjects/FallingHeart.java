package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import manager.BrickerGameManager;

public class FallingHeart extends GameObject {
    private final BrickerGameManager manager;
    private final GameObject mainPaddle;

    public FallingHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager manager,
                        GameObject mainPaddle) {
        super(topLeftCorner, dimensions, renderable);
        this.manager = manager;
        this.mainPaddle = mainPaddle;
        this.setTag("heart");
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return super.shouldCollideWith(other) && (other == mainPaddle);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (!other.getTag().equals("ball")) {
            super.onCollisionEnter(other, collision);
            this.manager.incrementHearts();
            this.manager.removeHeart(this);
        }
    }

}
