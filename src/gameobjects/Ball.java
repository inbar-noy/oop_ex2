package gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Ball extends GameObject {
    private static final Random random = new Random();
    private final Sound collisionSound;

    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.setTag("ball");
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (other.getTag().equals("heart")) return;

        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
    }

    public void setInitialVelocity(float speed) {
        float velocityX = speed;
        float velocityY = speed;

        if (random.nextBoolean()) { velocityX *= -1; }
        if (random.nextBoolean()) { velocityY *= -1; }

        this.setVelocity(new Vector2(velocityX, velocityY));
    }
}