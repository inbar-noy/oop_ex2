package manager;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import factories.BallFactory;
import factories.PaddleFactory;
import gameobjects.*;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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

    // Lives and HUD constants
    private static final int INITIAL_LIVES_COUNT = 3;
    private static final int MAX_LIFE_COUNT = 4;
    private static final int HEART_FALL_SPEED = 100;
    private static final int HUD_LAYER = 1;
    private static final int TEXT_FONT_SIZE = 20;
    private static final int TEXT_X = 15;
    private static final int TEXT_Y = 35;
    private static final int HEART_SIZE = 20;
    private static final int HEART_X = 20;
    private static final int HEART_Y = 30;
    private static final int HEART_OFFSET = 25;

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

    // Lives and HUD state
    private int livesLeft;
    private ArrayList<GameObject> hearts;
    private ImageRenderable heartImage;
    private TextRenderable livesLeftText;

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
        this.livesLeft = 0;
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
        createLiveText();
        createHearts();
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
            decrementHearts();
            if (isGameOver()) {
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
        BallFactory.createPuck(imageReader, soundReader, center, gameObjects(), WINDOW_HEIGHT);
        BallFactory.createPuck(imageReader, soundReader, center, gameObjects(), WINDOW_HEIGHT);
    }

    /** Creates an Extra Paddle at the center of the screen. */
    public void createExtraPaddle() {
        if (this.extraPaddle != null) {
            return;
        }
        this.extraPaddle = PaddleFactory.createExtraPaddle(imageReader, inputListener,
                windowDimensions, BORDER_WIDTH, gameObjects());
    }

    /** Creates a falling heart at the specified location. */
    public void startFallingHeart(Vector2 location) {
        FallingHeart heart = new FallingHeart(location,
                new Vector2(HEART_SIZE, HEART_SIZE),
                heartImage,
                this,
                userPaddle);
        heart.setVelocity(new Vector2(0, HEART_FALL_SPEED));
        gameObjects().addGameObject(heart);
    }

    /** Removes a falling heart from the game. */
    public void removeHeart(FallingHeart heart) {
        gameObjects().removeGameObject(heart);
    }

    /** Increases player lives count and adds a heart icon if under MAX_LIFE_COUNT. */
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

    /** Removes a brick via BrickGrid. */
    public void removeBrick(GameObject obj, boolean isExplosive, int row, int col) {
        this.brickGrid.removeBrick(obj, isExplosive, row, col);
    }

    /** Removes a static object from Layer.STATIC_OBJECTS. */
    public void removeStaticObj(GameObject obj) {
        gameObjects().removeGameObject(obj, Layer.STATIC_OBJECTS);
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

    private void createLiveText() {
        livesLeftText = new TextRenderable("");
        GameObject text = new GameObject(
                new Vector2(TEXT_X, WINDOW_HEIGHT - TEXT_Y),
                new Vector2(TEXT_FONT_SIZE, TEXT_FONT_SIZE),
                livesLeftText
        );
        gameObjects().addGameObject(text, HUD_LAYER);
    }

    private void createHearts() {
        hearts = new ArrayList<>();
        heartImage = imageReader.readImage(HEART_IMAGE_PATH, true);
        for (int i = 0; i < INITIAL_LIVES_COUNT; i++) {
            incrementHearts();
        }
    }

    // Game-state helpers
    private void respawn() {
        if (this.ball != null) {
            gameObjects().removeGameObject(this.ball);
        }
        this.ball = createBall(imageReader, soundReader, windowDimensions);
    }

    private boolean isGameOver() {
        return this.livesLeft == 0;
    }

    private void gameOver(String state) {
        String prompt = "You " + state + "! Another game?";
        if (this.windowController.openYesNoDialog(prompt)) {
            this.windowController.resetGame();
        } else {
            this.windowController.closeWindow();
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