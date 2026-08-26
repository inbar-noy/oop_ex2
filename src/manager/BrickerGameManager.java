package manager;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import factories.BallFactory;
import factories.PaddleFactory;
import gameobjects.*;

import java.awt.event.KeyEvent;

import static danogl.util.Vector2.ZERO;

/**
 * Main game manager for Bricker. Manages game initialization, the game loop,
 * win/loss conditions, HUD/lives display, and callbacks for brick strategies.
 */
public class BrickerGameManager extends GameManager {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //                      CONSTANTS
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Layout constants
    private static final float WINDOW_WIDTH = 700f;
    private static final float WINDOW_HEIGHT = 500f;
    private static final float BORDER_WIDTH = Border.BORDER_WIDTH;
    private static final int DEFAULT_BRICK_COLS = 8;
    private static final int DEFAULT_BRICK_ROWS = 7;
    private static final int HEART_SIZE = 20;

    // File paths
    private static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String HEART_IMAGE_PATH = "assets/heart.png";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //                    INSTANCE FIELDS
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Game configuration
    private final int brickCols;
    private final int brickRows;
    private WindowController windowController;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    // Game objects
    private Ball ball;
    private UserPaddle userPaddle;
    private ExtraPaddle extraPaddle;
    private BrickGrid brickGrid;
    private Lives lives;
    private ImageRenderable heartImage;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //                    CONSTRUCTORS
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public BrickerGameManager(String windowTitle, Vector2 windowDimension,
                              int brickCols, int brickRows) {
        super(windowTitle, windowDimension);
        this.brickCols = brickCols;
        this.brickRows = brickRows;
    }

    public BrickerGameManager(String windowTitle, Vector2 windowDimension) {
        this(windowTitle, windowDimension, DEFAULT_BRICK_COLS, DEFAULT_BRICK_ROWS);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //                       OVERRIDES
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.inputListener = inputListener;
        this.extraPaddle = null;

        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // Background game setup
        createBackground(imageReader, windowDimensions);
        createBorder(windowDimensions);
        respawn();

        // Setup game objects & grid
        userPaddle = createUserPaddle(imageReader, inputListener, windowDimensions);
        brickGrid = new BrickGrid(this, imageReader, brickCols, brickRows,
                WINDOW_WIDTH, BORDER_WIDTH, soundReader);

        // Setup HUD
        heartImage = imageReader.readImage(HEART_IMAGE_PATH, true);
        lives = new Lives(HEART_SIZE, WINDOW_HEIGHT, heartImage, this);
    }

    @Override
    public void update(float timeDelta) {
        super.update(timeDelta);

        float ballHeight = ball.getCenter().y();

        // Check Win Condition (All bricks broken or 'W' key shortcut)
        if (brickGrid.getActiveBricks() == 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            gameOver("win");
        }

        // Check Ball Out-Of-Bounds
        if (ballHeight > WINDOW_HEIGHT) {
            lives.decrementHearts();
            if (lives.isGameOver()) {
                gameOver("lose");
            } else {
                respawn();
            }
        }

        // update this.extraPaddle back to null if it has been removed from the game
        if (extraPaddle != null && extraPaddle.hitQuota()) {
            this.extraPaddle = null;
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //          PUBLIC API: GAME OBJECTS & STRATEGIES
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** Creates 2 Puck balls at the specified center location. */
    public void createPucks(Vector2 center) {
        BallFactory.createPuck(imageReader, soundReader, center, this, WINDOW_HEIGHT);
        BallFactory.createPuck(imageReader, soundReader, center, this, WINDOW_HEIGHT);
    }

    /** Creates an Extra Paddle at the center of the screen. */
    public void createExtraPaddle() {
        if (this.extraPaddle != null) {
            return;
        }
        this.extraPaddle = PaddleFactory.createExtraPaddle(imageReader, inputListener,
                windowDimensions, BORDER_WIDTH, this);
    }

    /** Creates a falling heart at the specified location. */
    public void startFallingHeart(Vector2 location) {
        FallingHeart heart = new FallingHeart(location,
                new Vector2(HEART_SIZE, HEART_SIZE),
                heartImage,
                this,
                userPaddle,
                WINDOW_HEIGHT);
        gameObjects().addGameObject(heart);
    }

    /** Removes a falling heart from the game. */
    public void removeHeart(FallingHeart heart) {
        gameObjects().removeGameObject(heart);
    }

    /**
     * Increment the number of lives the player has
     */
    public void incrementHearts() {
        this.lives.incrementHearts();
    }

    /**
     * Remove a background object
     * @param obj Object to remove
     */
    public void removeBackgroundObj(GameObject obj) {
        gameObjects().removeGameObject(obj, Layer.BACKGROUND);
    }

    /**
     * Add a background object
     * @param obj Object to add
     */
    public void addBackgroundObj(GameObject obj) {
        gameObjects().addGameObject(obj, Layer.BACKGROUND);
    }

    /** Removes a brick via BrickGrid. */
    public void removeBrick(GameObject obj, boolean isExplosive, int row, int col) {
        this.brickGrid.removeBrick(obj, isExplosive, row, col);
    }

    /** Removes a static object from Layer.STATIC_OBJECTS. */
    public void removeStaticObj(GameObject obj) {
        gameObjects().removeGameObject(obj, Layer.STATIC_OBJECTS);
    }

    public void addExtraPaddle(GameObject obj) {
        gameObjects().addGameObject(obj);
    }

    public void removeExtraPaddle(GameObject obj) {
        gameObjects().removeGameObject(obj);
    }

    public void addPuck(GameObject obj) {
        gameObjects().addGameObject(obj);
    }

    public void removePuck(GameObject obj) {
        gameObjects().removeGameObject(obj);
    }

    /** Adds a static object to Layer.STATIC_OBJECTS. */
    public void addStaticObj(GameObject obj) {
        gameObjects().addGameObject(obj, Layer.STATIC_OBJECTS);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //       PRIVATE: INITIALIZATION & GAME-STATE HELPERS
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Creators
    private void createBackground(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable backgroundImage = imageReader.readImage(BACKGROUND_IMAGE_PATH, false);
        Background background = new Background(ZERO, windowDimensions, backgroundImage);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createBorder(Vector2 windowDimensions) {
        Border border = new Border(windowDimensions);
        border.buildBorder(gameObjects());
    }

    private Ball createBall(ImageReader imageReader,
                            SoundReader soundReader, Vector2
                                    windowDimensions) {
        Ball mainBall = BallFactory.createMainBall(imageReader, soundReader, windowDimensions);
        gameObjects().addGameObject(mainBall);
        return mainBall;
    }

    private UserPaddle createUserPaddle(ImageReader imageReader,
                                        UserInputListener inputListener,
                                        Vector2 windowDimensions) {
        UserPaddle userPaddle = PaddleFactory.createUserPaddle(
                imageReader, inputListener, windowDimensions, BORDER_WIDTH);
        gameObjects().addGameObject(userPaddle);
        return userPaddle;
    }

    // Game-state helpers
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

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //                      MAIN
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Main entry point for running the Bricker game application.
     * Parses optional command-line arguments to customize the brick grid
     * columns and rows, then initializes and starts the game.
     * @param args Command-line arguments. Optional: [brickCols, brickRows].
     */
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
