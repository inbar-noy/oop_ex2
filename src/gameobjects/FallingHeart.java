package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import manager.BrickerGameManager;

public class FallingHeart extends GameObject {
    private final BrickerGameManager manager;

    public FallingHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager manager) {
        super(topLeftCorner, dimensions, renderable);
        this.manager = manager;
        this.setTag("heart");
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
