package factories;

import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.Puck;

import java.util.Random;

public class BallFactory {
    private static final Vector2 MAIN_BALL_DIMENSIONS = new Vector2(20, 20);
    private static final float PUCK_SIZE_RATIO = 0.75f;
    private static final Vector2 PUCK_DIMENSIONS = MAIN_BALL_DIMENSIONS.mult(PUCK_SIZE_RATIO);

    private static final String MAIN_BALL_IMAGE_PATH = "assets/ball.png";
    private static final String PUCK_IMAGE_PATH = "assets/mockBall.png";
    private static final String COLLISION_SOUND_PATH = "assets/blop.wav";

    private static final float BALL_SPEED = 200f;
    private static final Random random = new Random();


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


    public static Puck createPuck(ImageReader imageReader,
                                  SoundReader soundReader,
                                  Vector2 centerPosition) {
        Renderable puckImage = imageReader.readImage(PUCK_IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound(COLLISION_SOUND_PATH);

        Puck puck = new Puck(Vector2.ZERO, PUCK_DIMENSIONS, puckImage, collisionSound);
        puck.setCenter(centerPosition);

        double angle = random.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * BALL_SPEED;
        float velocityY = (float) Math.sin(angle) * BALL_SPEED;
        puck.setVelocity(new Vector2(velocityX, velocityY));

        return puck;
    }
}