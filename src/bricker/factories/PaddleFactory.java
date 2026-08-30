package bricker.factories;

import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.ExtraPaddle;
import bricker.gameobjects.UserPaddle;
import bricker.main.BrickerGameManager;

/**
 * Factory class responsible for initiating UserPaddle, ExtraPaddle, and AIPaddle
 * game objects with dimensions, initial positions, and movement bounds.
 */
public class PaddleFactory {
    private static final Vector2 PADDLE_DIMENSIONS = new Vector2(100, 15);
    private static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    private static final float DIST_FROM_BORDER = 50f;

    /**
     * Creates and initializes the main user-controlled paddle positioned near the
     * bottom center of the game window.
     * @param imageReader      ImageReader instance for loading the paddle image asset.
     * @param inputListener    UserInputListener to read user keyboard movement input.
     * @param windowDimensions Dimensions of the game window to calculate layout coordinates.
     * @param borderWidth      Width of side borders to calculate movement boundaries.
     * @return An initialized UserPaddle instance.
     */
    public static UserPaddle createUserPaddle(ImageReader imageReader,
                                              UserInputListener inputListener,
                                              Vector2 windowDimensions,
                                              float borderWidth) {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH,
                true);
        Vector2 topLeft = getInitialTopLeft(
                windowDimensions, windowDimensions.y() - DIST_FROM_BORDER);
        float maxX = getMaxX(windowDimensions.x(), borderWidth);

        return new UserPaddle(topLeft, PADDLE_DIMENSIONS, paddleImage,
                borderWidth, maxX, inputListener);
    }

    /**
     * Creates and initializes an ExtraPaddle positioned at the center of the game window.
     * @param imageReader      ImageReader instance for loading the paddle image asset.
     * @param inputListener    UserInputListener to read user keyboard movement input.
     * @param windowDimensions Dimensions of the game window to calculate center placement.
     * @param borderWidth      Width of side borders to calculate movement boundaries.
     * @param manager          Parent game Bricker.manager
     * @return An initialized ExtraPaddle instance.
     */
    public static ExtraPaddle createExtraPaddle(ImageReader imageReader,
                                                UserInputListener inputListener,
                                                Vector2 windowDimensions,
                                                float borderWidth,
                                                BrickerGameManager manager) {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH,
                true);
        float maxX = getMaxX(windowDimensions.x(), borderWidth);

        ExtraPaddle extra = new ExtraPaddle(Vector2.ZERO, PADDLE_DIMENSIONS, paddleImage,
                borderWidth, maxX, inputListener, manager);
        extra.setCenter(windowDimensions.mult(0.5f));
        return extra;
    }

    // Private helper functions
    private static Vector2 getInitialTopLeft(Vector2 windowDimensions, float yPos) {
        float xPos = (windowDimensions.x() - PADDLE_DIMENSIONS.x()) / 2f;
        return new Vector2(xPos, yPos);
    }

    private static float getMaxX(float windowWidth, float borderWidth) {
        return windowWidth - borderWidth - PADDLE_DIMENSIONS.x();
    }
}
