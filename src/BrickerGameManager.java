import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import factories.BallFactory;
import factories.PaddleFactory;
import gameobjects.*;

import static danogl.util.Vector2.ZERO;

public class BrickerGameManager extends GameManager {
    private static final float BORDER_WIDTH = 10f;
    private static final float WINDOW_WIDTH = 700f;
    private static final float WINDOW_HEIGHT = 500f;

    public BrickerGameManager(String windowTitle, Vector2 windowDimension) {
        super(windowTitle, windowDimension);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        Vector2 windowDimensions = windowController.getWindowDimensions();

        // Background
        Background background = createBackground(imageReader, windowDimensions);

        // Border
        Border border = createBorder(windowDimensions);

        // Main ball
        Ball mainBall = createBall(imageReader, soundReader, windowController);

        // User paddle
        UserPaddle userPaddle = createUserPaddle(imageReader, windowController, inputListener);

        // AI paddle
        AIPaddle aiPaddle = createAIPaddle(imageReader, windowController, mainBall);
    }

    private Background createBackground(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        Background background = new Background(ZERO, windowDimensions, backgroundImage);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
        return background;
    }

    private Border createBorder(Vector2 windowDimensions) {
        Border border = new Border(windowDimensions);
        border.buildBorder(gameObjects());
        return border;
    }

    private Ball createBall(ImageReader imageReader, SoundReader soundReader, WindowController windowController) {
        Ball mainBall = BallFactory.createMainBall(imageReader, soundReader, windowController);
        gameObjects().addGameObject(mainBall);
        return mainBall;
    }

    private UserPaddle createUserPaddle(ImageReader imageReader, WindowController windowController, UserInputListener inputListener) {
        UserPaddle userPaddle = PaddleFactory.createUserPaddle(
                imageReader, windowController, inputListener, BORDER_WIDTH);
        gameObjects().addGameObject(userPaddle);
        return userPaddle;
    }

    private AIPaddle createAIPaddle(ImageReader imageReader, WindowController windowController, GameObject objectToFollow) {
        AIPaddle aiPaddle = PaddleFactory.createAIPaddle(
                imageReader, windowController, BORDER_WIDTH, objectToFollow);
        gameObjects().addGameObject(aiPaddle);
        return aiPaddle;
    }

    public static void main(String[] args) {
        BrickerGameManager manager = new BrickerGameManager(
                "Bouncing Ball",
                new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT));
        manager.run();
    }
}