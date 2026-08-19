package factories;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.AIPaddle;
import gameobjects.UserPaddle;

public class PaddleFactory {
    private static final Vector2 PADDLE_DIMENSIONS = new Vector2(100, 15);
    private static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    private static final float DIST_FROM_BORDER = 30f;

    public static UserPaddle createUserPaddle(ImageReader imageReader,
                                              WindowController windowController,
                                              UserInputListener inputListener,
                                              float borderWidth) {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH, true);
        Vector2 windowDimensions = windowController.getWindowDimensions();

        float x = (windowDimensions.x() - PADDLE_DIMENSIONS.x()) / 2f;
        float y = windowDimensions.y() - DIST_FROM_BORDER;
        Vector2 topLeftCorner = new Vector2(x, y);

        float minX = borderWidth;
        float maxX = windowDimensions.x() - borderWidth - PADDLE_DIMENSIONS.x();

        return new UserPaddle(topLeftCorner, PADDLE_DIMENSIONS, paddleImage,
                minX, maxX, inputListener);
    }

    public static AIPaddle createAIPaddle(ImageReader imageReader,
                                          WindowController windowController,
                                          float borderWidth,
                                          GameObject objectToFollow) {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH, true);
        Vector2 windowDimensions = windowController.getWindowDimensions();

        float x = (windowDimensions.x() - PADDLE_DIMENSIONS.x()) / 2f;
        float y = DIST_FROM_BORDER;
        Vector2 topLeftCorner = new Vector2(x, y);

        float minX = borderWidth;
        float maxX = windowDimensions.x() - borderWidth - PADDLE_DIMENSIONS.x();

        return new AIPaddle(topLeftCorner, PADDLE_DIMENSIONS, paddleImage,
                minX, maxX, objectToFollow);
    }
}