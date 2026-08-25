package gameobjects;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import factories.BrickStrategyFactory;
import manager.BrickerGameManager;

/**
 * A grid of bricks
 */
public class BrickGrid {
    private Brick[][] bricks;
    private static final float BRICK_HEIGHT = 15f;
    private static final float BRICK_MARGIN = 3f;
    private static final int FIRST_BRICK_ROW_HEIGHT = 20;
    private static final String BRICK_IMAGE_FILE = "assets/brick.png";
    private static final String EXPLOSION_SOUND_FILE = "assets/explosion.wav";
    private Sound explosionSound;
    private Counter activeBricks;
    private int brickRows;
    private int brickCols;
    private BrickerGameManager manager;

    /**
     * Construct a grid of bricks
     * @param manager Parent game manager
     * @param imageReader Game image reader to read brick image with
     * @param columns Number of brick columns
     * @param rows Number of brick rows
     * @param windowWidth Width of the window
     * @param borderWidth Width of border
     * @param soundReader Sound reader to read explosion sound with
     */
    public BrickGrid(BrickerGameManager manager,
                     ImageReader imageReader,
                     int columns,
                     int rows,
                     float windowWidth,
                     float borderWidth,
                     SoundReader soundReader) {
        this.manager = manager;
        ImageRenderable brickImage = imageReader.readImage(BRICK_IMAGE_FILE, false);
        explosionSound = soundReader.readSound(EXPLOSION_SOUND_FILE);
        brickCols = columns;
        brickRows = rows;
        bricks = new Brick[rows][columns];

        BrickStrategyFactory strategyFactory = new BrickStrategyFactory(manager);
        float brickWidth = (windowWidth - 2 * borderWidth - BRICK_MARGIN) / columns - BRICK_MARGIN;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Vector2 topLeftCorner = new Vector2(
                        borderWidth + (BRICK_MARGIN + brickWidth) * col + BRICK_MARGIN,
                        (BRICK_HEIGHT + BRICK_MARGIN) * row + FIRST_BRICK_ROW_HEIGHT);
                Brick brick = new Brick(
                        row,
                        col,
                        topLeftCorner,
                        new Vector2(brickWidth, BRICK_HEIGHT),
                        brickImage,
                        strategyFactory.selectBrickStrategy());
                manager.addStaticObj(brick);
                bricks[row][col] = brick;
            }
        }
        activeBricks = new Counter(rows * columns);
    }

    /**
     *
     * @param row Row of brick to blow
     * @param col Column of brick to blow
     */
    private void blowBrick(int row, int col) {
        Brick targetBrick = bricks[row][col];
        if (targetBrick != null) {
            targetBrick.pseudoCollision();
        }
    }

    /**
     *
     * @param obj Brick object to remove
     * @param isExplosive Whether the removal should blow up nearby bricks
     * @param row Row of removed brick
     * @param col Column of removed brick
     */
    public void removeBrick(GameObject obj, boolean isExplosive, int row, int col) {
        if (bricks[row][col] == null) {
            return;
        }

        manager.removeStaticObj(obj);
        activeBricks.decrement();
        bricks[row][col] = null;
        if (isExplosive) {
            explosionSound.play();
            if (row > 0) {
                blowBrick(row - 1, col);
            }
            if (row < brickRows - 1) {
                blowBrick(row + 1, col);
            }
            if (col > 0) {
                blowBrick(row, col - 1);
            }
            if (col < brickCols - 1) {
                blowBrick(row, col + 1);
            }
        }
    }

    /**
     * Get the number of active bricks (0 means game over)
     * @return Number of active bricks
     */
    public int getActiveBricks() {
        return activeBricks.value();
    }
}
