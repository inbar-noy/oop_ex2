package gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a Puck ball in the Bricker game.
 * Inherits ball behavior from Ball.
 */
public class Puck extends Ball {
    private final GameObjectCollection gameObjects;
    private final float windowHeight;

    /**
     * Constructs a new Puck instance.
     * @param topLeftCorner  Initial position of the puck in window coordinates.
     * @param dimensions     Width and height (diameter) of the puck.
     * @param renderable     Image of the puck.
     * @param collisionSound Sound effect to play upon collision.
     * @param gameObjects    GameObjectCollection used for self-removal when falling offscreen.
     * @param windowHeight   The height of the game window to detect when the puck falls offscreen.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions,
                Renderable renderable, Sound collisionSound,
                GameObjectCollection gameObjects, float windowHeight) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        this.gameObjects = gameObjects;
        this.windowHeight = windowHeight;
        this.gameObjects.addGameObject(this);
    }

    /**
     * Updates the puck's state per frame and checks if it has fallen
     * past the bottom of the screen. If out of bounds, removes itself from gameObjects.
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Remove puck from game if it falls past the bottom window boundary
        if (getTopLeftCorner().y() > windowHeight) {
            gameObjects.removeGameObject(this);
        }
    }
}
