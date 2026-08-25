package gameobjects;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents the background of the Bricker game.
 */
public class Background extends GameObject {

    /**
     * Constructs a new Background instance.
     * @param topLeftCorner Position of the background's top left corner in window coordinates.
     * @param dimensions    Width and height of the background image (matching window dimensions).
     * @param renderable    Image for the background.
     */
    public Background(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }
}