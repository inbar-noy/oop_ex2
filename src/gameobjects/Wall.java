package gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

/**
 * Represents a wall object in the Bricker game.
 * Used to build screen boundaries.
 */
public class Wall extends GameObject {

    /**
     * Constructs a new Wall instance.
     * @param topLeftCorner Position of the wall's top-left corner in window coordinates.
     * @param dimensions    Width and height of the wall.
     * @param border        Renderable rectangle for rendering the wall.
     */
    public Wall(Vector2 topLeftCorner, Vector2 dimensions, RectangleRenderable border) {
        super(topLeftCorner, dimensions, border);
    }
}