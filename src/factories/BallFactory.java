package factories;

import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.Ball;

public class BallFactory {
    private static final Vector2 MAIN_BALL_DIMENSIONS = new Vector2(20, 20);
    private static final String BALL_IMAGE_PATH = "assets/ball.png";
    private static final String COLLISION_SOUND_PATH = "assets/blop.wav";
    private static final float BALL_SPEED = 200f;

    public static Ball createMainBall(ImageReader imageReader,
                                      SoundReader soundReader,
                                      WindowController windowController) {
        Vector2 windowDimensions = windowController.getWindowDimensions();

        Renderable ballImage = imageReader.readImage(BALL_IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound(COLLISION_SOUND_PATH);

        Ball ball = new Ball(Vector2.ZERO, MAIN_BALL_DIMENSIONS, ballImage, collisionSound);
        ball.setCenter(windowDimensions.mult(0.5f));
        ball.setInitialVelocity(BALL_SPEED);

        return ball;
    }
}