package factories;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.AIPaddle;
import gameobjects.ExtraPaddle;
import gameobjects.UserPaddle;

public class PaddleFactory {
    private static final Vector2 PADDLE_DIMENSIONS = new Vector2(100, 15);
    private static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    private static final float DIST_FROM_BORDER = 50f;


    public static UserPaddle createUserPaddle(ImageReader imageReader,
                                              UserInputListener inputListener,
                                              Vector2 windowDimensions,
                                              float borderWidth) {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH, true);
        Vector2 topLeft = getInitialTopLeft(windowDimensions, windowDimensions.y() - DIST_FROM_BORDER);
        float maxX = getMaxX(windowDimensions.x(), borderWidth);

        return new UserPaddle(topLeft, PADDLE_DIMENSIONS, paddleImage,
                borderWidth, maxX, inputListener);
    }

    public static ExtraPaddle createExtraPaddle(ImageReader imageReader,
                                                UserInputListener inputListener,
                                                Vector2 windowDimensions,
                                                float borderWidth) {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH, true);
        float maxX = getMaxX(windowDimensions.x(), borderWidth);

        ExtraPaddle extra = new ExtraPaddle(Vector2.ZERO, PADDLE_DIMENSIONS, paddleImage,
                borderWidth, maxX, inputListener);
        extra.setCenter(windowDimensions.mult(0.5f));
        return extra;
    }
    public static AIPaddle createAIPaddle(ImageReader imageReader,
                                          Vector2 windowDimensions,
                                          float borderWidth,
                                          GameObject objectToFollow) {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH, true);
        Vector2 topLeft = getInitialTopLeft(windowDimensions, DIST_FROM_BORDER);
        float maxX = getMaxX(windowDimensions.x(), borderWidth);

        return new AIPaddle(topLeft, PADDLE_DIMENSIONS, paddleImage,
                borderWidth, maxX, objectToFollow);
    }

    private static Vector2 getInitialTopLeft(Vector2 windowDimensions, float yPos) {
        float xPos = (windowDimensions.x() - PADDLE_DIMENSIONS.x()) / 2f;
        return new Vector2(xPos, yPos);
    }

    private static float getMaxX(float windowWidth, float borderWidth) {
        return windowWidth - borderWidth - PADDLE_DIMENSIONS.x();
    }
}