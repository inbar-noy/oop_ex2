package factories;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.Puck;

import java.util.Random;

/**
 * Factory class responsible for initiating Ball and Puck game objects
 * with their respective assets, dimensions, positions, and initial velocities.
 */
public class BallFactory {
    private static final Vector2 MAIN_BALL_DIMENSIONS = new Vector2(20, 20);
    private static final float PUCK_SIZE_RATIO = 0.75f;
    private static final Vector2 PUCK_DIMENSIONS = MAIN_BALL_DIMENSIONS.mult(PUCK_SIZE_RATIO);

    private static final String MAIN_BALL_IMAGE_PATH = "assets/ball.png";
    private static final String PUCK_IMAGE_PATH = "assets/mockBall.png";
    private static final String COLLISION_SOUND_PATH = "assets/blop.wav";

    private static final float BALL_SPEED = 200f;
    private static final Random random = new Random();

    /**
     * Initializes the main game ball positioned at the center of the window
     * with a random initial diagonal velocity.
     * @param imageReader      ImageReader instance for loading the ball image asset.
     * @param soundReader      SoundReader instance for loading collision sound asset.
     * @param windowDimensions Dimensions of the game window to calculate center placement.
     * @return An initialized Ball instance.
     */
    public static Ball createMainBall(ImageReader imageReader,
                                      SoundReader soundReader,
                                      Vector2 windowDimensions) {
        Renderable ballImage = imageReader.readImage(MAIN_BALL_IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound(COLLISION_SOUND_PATH);

        Ball ball = new Ball(Vector2.ZERO, MAIN_BALL_DIMENSIONS, ballImage, collisionSound);
        ball.setCenter(windowDimensions.mult(0.5f));
        ball.setInitialVelocity(BALL_SPEED);

        return ball;
    }

    /**
     * initializes a Puck ball at the specified center position
     * with a random initial velocity angle.
     * @param imageReader    ImageReader instance for loading the puck image asset.
     * @param soundReader    SoundReader instance for loading collision sound asset.
     * @param centerPosition Coordinates where the puck should be centered.
     * @param gameObjects    GameObjectCollection passed to the Puck for self-removal.
     * @param windowHeight   Height of the game window used for out-of-bounds detection.
     * @return An initialized Puck instance.
     */
    public static Puck createPuck(ImageReader imageReader,
                                  SoundReader soundReader,
                                  Vector2 centerPosition,
                                  GameObjectCollection gameObjects,
                                  float windowHeight) {
        Renderable puckImage = imageReader.readImage(PUCK_IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound(COLLISION_SOUND_PATH);

        Puck puck = new Puck(Vector2.ZERO, PUCK_DIMENSIONS, puckImage, collisionSound,
                gameObjects, windowHeight);
        puck.setCenter(centerPosition);

        // Calculate random initial velocity in upper unit circle
        double angle = random.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * BALL_SPEED;
        float velocityY = (float) Math.sin(angle) * BALL_SPEED;
        puck.setVelocity(new Vector2(velocityX, velocityY));

        return puck;
    }
}
