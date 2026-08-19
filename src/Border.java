import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import gameobjects.Wall;

import java.awt.*;

public class Border {
    public static final int BORDER_WIDTH = 10;
    private final Color BORDER_COLOR = Color.LIGHT_GRAY;

    private final Vector2 topLeftCorner = Vector2.ZERO;
    private final Vector2 topRightCorner;
    private final Vector2 bottomLeftCorner;

    private final Vector2 leftWallDims;
    private final Vector2 topWallDims;
    private final Vector2 rightWallDims;
    private final Vector2 bottomWallDims;

    private final RectangleRenderable border = new RectangleRenderable(BORDER_COLOR);

    public Border(Vector2 dimensions) {
        topRightCorner = new Vector2(dimensions.x() - BORDER_WIDTH, 0);
        bottomLeftCorner = new Vector2(0, dimensions.y() - BORDER_WIDTH);

        leftWallDims = new Vector2(BORDER_WIDTH, dimensions.y());
        topWallDims = new Vector2(dimensions.x(), BORDER_WIDTH);
        rightWallDims = new Vector2(BORDER_WIDTH, dimensions.y());
        bottomWallDims = new Vector2(dimensions.x(), BORDER_WIDTH);
    }

    public Border buildBorder(GameObjectCollection gameObjects) {
        Wall leftBorder = new Wall(topLeftCorner, leftWallDims, border);
//        Wall topBorder = new Wall(topLeftCorner, topWallDims, border);
        Wall rightBorder = new Wall(topRightCorner, rightWallDims, border);
//        Wall bottomBorder = new Wall(bottomLeftCorner, bottomWallDims, border);

        gameObjects.addGameObject(leftBorder);
//        gameObjects.addGameObject(topBorder);
        gameObjects.addGameObject(rightBorder);
//        gameObjects.addGameObject(bottomBorder);

        return this;
    }

}
