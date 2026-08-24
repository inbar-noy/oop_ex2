package gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Border {
    public static final int BORDER_WIDTH = 10;
    private final Color BORDER_COLOR = Color.LIGHT_GRAY;

    private final Vector2 topLeftCorner = Vector2.ZERO;
    private final Vector2 topRightCorner;
    private final Vector2 VerticalWallDims;
    private final Vector2 HorizontalWallDims;

    private final RectangleRenderable border = new RectangleRenderable(BORDER_COLOR);

    public Border(Vector2 dimensions) {
        topRightCorner = new Vector2(dimensions.x() - BORDER_WIDTH, 0);
        VerticalWallDims = new Vector2(BORDER_WIDTH, dimensions.y());
        HorizontalWallDims = new Vector2(dimensions.x(), BORDER_WIDTH);
    }

    public Border buildBorder(GameObjectCollection gameObjects) {
        Wall leftBorder = new Wall(topLeftCorner, VerticalWallDims, border);
        Wall rightBorder = new Wall(topRightCorner, VerticalWallDims, border);
        Wall ceiling = new Wall(topLeftCorner, HorizontalWallDims, border);

        gameObjects.addGameObject(leftBorder);
        gameObjects.addGameObject(rightBorder);
        gameObjects.addGameObject(ceiling);

        return this;
    }
}
