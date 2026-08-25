package gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.Color;

/**
 * Helper class that constructs and adds the border's walls.
 * (left wall, right wall, and ceiling) to the Bricker game area.
 */
public class Border {
    /** The width of the border walls. */
    public static final int BORDER_WIDTH = 10;

    private final Color BORDER_COLOR = Color.LIGHT_GRAY;

    private final Vector2 topLeftCorner = Vector2.ZERO;
    private final Vector2 topRightCorner;
    private final Vector2 VerticalWallDims;
    private final Vector2 HorizontalWallDims;

    private final RectangleRenderable border = new RectangleRenderable(BORDER_COLOR);

    /**
     * Constructs a new Border instance and calculates wall dimensions based on the window size.
     * @param dimensions Dimensions (width and height) of the game window.
     */
    public Border(Vector2 dimensions) {
        topRightCorner = new Vector2(dimensions.x() - BORDER_WIDTH, 0);
        VerticalWallDims = new Vector2(BORDER_WIDTH, dimensions.y());
        HorizontalWallDims = new Vector2(dimensions.x(), BORDER_WIDTH);
    }

    /**
     * Instantiates the left, right, and ceiling Wall game objects
     * and adds them to the provided GameObjectCollection.
     * @param gameObjects The GameObjectCollection where wall objects will be added.
     * @return This Border instance.
     */
    public Border buildBorder(GameObjectCollection gameObjects) {
        Wall leftBorder = new Wall(topLeftCorner, VerticalWallDims, border);
        Wall rightBorder = new Wall(topRightCorner, VerticalWallDims, border);
        Wall ceiling = new Wall(topLeftCorner, HorizontalWallDims, border);

        gameObjects.addGameObject(leftBorder, Layer.STATIC_OBJECTS);
        gameObjects.addGameObject(rightBorder, Layer.STATIC_OBJECTS);
        gameObjects.addGameObject(ceiling, Layer.STATIC_OBJECTS);

        return this;
    }
}
