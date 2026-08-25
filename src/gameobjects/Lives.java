package gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import manager.BrickerGameManager;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents the lives of the player
 */
public class Lives {
    private static final int INITIAL_LIVES_COUNT = 3;
    private static final int MAX_LIFE_COUNT = 4;
    private static final int TEXT_FONT_SIZE = 20;
    private static final int TEXT_X = 15;
    private static final int TEXT_Y = 35;
    private static final int HEART_X = 20;
    private static final int HEART_Y = 30;
    private static final int HEART_OFFSET = 25;

    private int livesLeft;
    private ArrayList<GameObject> hearts;
    private ImageRenderable heartImage;
    private TextRenderable livesLeftText;
    private final int heartSize;
    private final float windowHeight;
    private BrickerGameManager manager;

    /**
     * Construct a lives object
     * @param heartSize Size of the heart image (both X and Y)
     * @param windowHeight Height of the window
     * @param heartImage Image of the heart
     * @param manager Parent game manager
     */
    public Lives(int heartSize, float windowHeight, ImageRenderable heartImage, BrickerGameManager manager) {
        this.livesLeft = 0;
        this.heartSize = heartSize;
        this.windowHeight = windowHeight;
        this.heartImage = heartImage;
        this.manager = manager;
        this.hearts = new ArrayList<>();

        createLiveText();
        createHearts();
    }

    /** Increases player lives count and adds a heart icon if under MAX_LIFE_COUNT. */
    public void incrementHearts() {
        if (livesLeft < MAX_LIFE_COUNT) {
            livesLeft += 1;
            GameObject heart = new GameObject(
                    new Vector2(HEART_X + livesLeft * HEART_OFFSET, windowHeight - HEART_Y),
                    new Vector2(heartSize, heartSize),
                    heartImage
            );
            manager.addBackgroundObj(heart);
            hearts.add(heart);
            updateLivesText();
        }
    }

    /**
     * Creates the lives text at the bottom of the screen
     */
    private void createLiveText() {
        livesLeftText = new TextRenderable("");
        GameObject text = new GameObject(
                new Vector2(TEXT_X, windowHeight - TEXT_Y),
                new Vector2(TEXT_FONT_SIZE, TEXT_FONT_SIZE),
                livesLeftText
        );
        manager.addBackgroundObj(text);
    }

    /**
     * Creates the heart display at the bottom of the screen
     */
    private void createHearts() {
        hearts = new ArrayList<>();
        for (int i = 0; i < INITIAL_LIVES_COUNT; i++) {
            incrementHearts();
        }
    }

    /**
     * Decrement the amount of hearts the player has
     */
    public void decrementHearts() {
        livesLeft -= 1;
        GameObject heart = hearts.remove(hearts.size() - 1);
        manager.removeBackgroundObj(heart);
        updateLivesText();
    }

    /**
     * Update the lives text
     */
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

    /**
     * Check if the player has lives
     * @return True if the player has zero lives
     */
    public boolean isGameOver() {
        return livesLeft == 0;
    }
}
