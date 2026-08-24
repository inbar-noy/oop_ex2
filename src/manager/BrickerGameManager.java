package manager;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import factories.BallFactory;
import factories.BrickStrategyFactory;
import factories.PaddleFactory;
import gameobjects.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static danogl.util.Vector2.ZERO;

public class BrickerGameManager extends GameManager {
    private static final float BORDER_WIDTH = 10f;
    private static final float WINDOW_WIDTH = 700f;
    private static final float WINDOW_HEIGHT = 500f;
    private static final int DEFAULT_BRICK_COLS = 8;
    private static final int DEFAULT_BRICK_ROWS = 7;
    private static final int INITIAL_LIVES_COUNT = 3;
    private static final int HUD_LAYER = 1;
    private static final int TEXT_FONT_SIZE = 20;
    private static final int TEXT_X = 15;
    private static final int TEXT_Y = 35;
    private static final int HEART_SIZE = 20;
    private static final int HEART_X = 20;
    private static final int HEART_Y = 30;
    private static final int HEART_OFFSET = 25;
    private static final int MAX_LIFE_COUNT = 4;
    private static final int HEART_FALL_SPEED = 100;

    private final int brickCols;
    private final int brickRows;

    private Ball ball;
    private WindowController windowController;
    private int livesLeft;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private Vector2 windowDimensions;
    private ImageRenderable heartImage;
    private TextRenderable livesLeftText;
    private ArrayList<GameObject> hearts;
    private UserInputListener inputListener;
    private ExtraPaddle extraPaddle;
    private BrickStrategyFactory strategyFactory;
    private UserPaddle userPaddle;
    private BrickGrid brickGrid;

    public BrickerGameManager(String windowTitle, Vector2 windowDimension, int brickCols, int brickRows) {
        super(windowTitle, windowDimension);
        this.brickCols = brickCols;
        this.brickRows = brickRows;
    }

    public BrickerGameManager(String windowTitle, Vector2 windowDimension) {
        this(windowTitle, windowDimension, DEFAULT_BRICK_COLS, DEFAULT_BRICK_ROWS);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.inputListener = inputListener;
        this.livesLeft = 0;


        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        createBackground(imageReader, windowDimensions);
        createBorder(windowDimensions);
        respawn();

        userPaddle = createUserPaddle(imageReader, inputListener, windowDimensions);

        brickGrid = new BrickGrid(this, imageReader, brickCols, brickRows, WINDOW_WIDTH, BORDER_WIDTH,
                soundReader);

        // Lives text
        livesLeftText = new TextRenderable("");
        GameObject text = new GameObject(
            new Vector2(TEXT_X, WINDOW_HEIGHT - TEXT_Y),
            new Vector2(TEXT_FONT_SIZE, TEXT_FONT_SIZE),
            livesLeftText
        );
        gameObjects().addGameObject(text, HUD_LAYER);

        // Life hearts
        hearts = new ArrayList<>();
        heartImage = imageReader.readImage("assets/heart.png", true);
        for (int i = 0; i < INITIAL_LIVES_COUNT; i++) {
            incrementHearts();
        }

    }

    private void createBackground(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        Background background = new Background(ZERO, windowDimensions, backgroundImage);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createBorder(Vector2 windowDimensions) {
        Border border = new Border(windowDimensions);
        border.buildBorder(gameObjects());
    }

    private Ball createBall(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions) {
        Ball mainBall = BallFactory.createMainBall(imageReader, soundReader, windowDimensions);
        gameObjects().addGameObject(mainBall);
        return mainBall;
    }

    private UserPaddle createUserPaddle(
            ImageReader imageReader, UserInputListener inputListener, Vector2 windowDimensions) {
        UserPaddle userPaddle = PaddleFactory.createUserPaddle(
                imageReader, inputListener, windowDimensions, BORDER_WIDTH);
        gameObjects().addGameObject(userPaddle);
        return userPaddle;
    }

    public void createPucks(Vector2 center) {
        Puck puck1 = BallFactory.createPuck(imageReader, soundReader, center, gameObjects(), WINDOW_HEIGHT);
        Puck puck2 = BallFactory.createPuck(imageReader, soundReader, center, gameObjects(), WINDOW_HEIGHT);
        gameObjects().addGameObject(puck1);
        gameObjects().addGameObject(puck2);
    }

    public void createExtraPaddle() {
        if (this.extraPaddle != null) {
            gameObjects().removeGameObject(this.extraPaddle);
        }
        ExtraPaddle extra = PaddleFactory.createExtraPaddle(imageReader, inputListener,
                windowDimensions, BORDER_WIDTH, gameObjects());
        this.extraPaddle = extra;
        gameObjects().addGameObject(extra);
    }

    private boolean isGameOver() {
        return this.livesLeft == 0;
    }

    private void respawn() {
        if (this.ball != null) {
            gameObjects().removeGameObject(this.ball);
        }
        this.ball = createBall(imageReader, soundReader, windowDimensions);
    }

    private void gameOver(String state) {
        String prompt = "You " + state + "! Another game?";
        if (this.windowController.openYesNoDialog(prompt)) {
            this.windowController.resetGame();
        } else {
            this.windowController.closeWindow();
        }
    }

    @Override
    public void update(float timeDelta) {
        super.update(timeDelta);

        float ballHeight = ball.getCenter().y();

        if (brickGrid.getActiveBricks() == 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            gameOver("win");
        }
        if (ballHeight > WINDOW_HEIGHT) {
            decrementHearts();
            if (isGameOver()) {
                gameOver("lose");
            } else {
                respawn();
            }
        }
    }

    public void incrementHearts() {
        if (livesLeft < MAX_LIFE_COUNT) {
            livesLeft += 1;
            GameObject heart = new GameObject(
                    new Vector2(HEART_X + livesLeft * HEART_OFFSET, WINDOW_HEIGHT - HEART_Y),
                    new Vector2(HEART_SIZE, HEART_SIZE),
                    heartImage
            );
            gameObjects().addGameObject(heart, Layer.BACKGROUND);
            hearts.add(heart);
            updateLivesText();
        }
    }

    private void decrementHearts() {
        livesLeft -= 1;
        GameObject heart = hearts.remove(hearts.size() - 1);
        gameObjects().removeGameObject(heart, Layer.BACKGROUND);
        updateLivesText();
    }

    private void updateLivesText() {
        livesLeftText.setString(String.valueOf(livesLeft));
        if (livesLeft == 1) {
            livesLeftText.setColor(Color.red);
        } else if (livesLeft == 2) {
            livesLeftText.setColor(Color.yellow);
        } else {
            livesLeftText.setColor(Color.green);
        }
    }

    public void startFallingHeart(Vector2 location) {
        FallingHeart heart = new FallingHeart(location,
                new Vector2(HEART_SIZE, HEART_SIZE),
                heartImage,
                this,
                userPaddle
                );
        heart.setVelocity(new Vector2(0, HEART_FALL_SPEED));
        gameObjects().addGameObject(heart);
    }

    public void removeHeart(FallingHeart heart) {
        gameObjects().removeGameObject(heart);
    }


    public void removeBrick(GameObject obj, boolean isExplosive, int row, int col) {
        this.brickGrid.removeBrick(obj, isExplosive, row, col);
    }

    public void removeStaticObj(GameObject obj) {
        gameObjects().removeGameObject(obj, Layer.STATIC_OBJECTS);
    }

    public void addStaticObj(GameObject obj) {
        gameObjects().addGameObject(obj, Layer.STATIC_OBJECTS);
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            int brickCols = Integer.parseInt(args[0]);
            int brickRows = Integer.parseInt(args[1]);
            BrickerGameManager manager = new BrickerGameManager(
                    "Bouncing Ball",
                    new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT),
                    brickCols,
                    brickRows);
            manager.run();
        } else {
            BrickerGameManager manager = new BrickerGameManager(
                    "Bouncing Ball",
                    new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT));
            manager.run();
        }
    }
}
