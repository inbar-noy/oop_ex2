package bricker.gameobjects;

import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;

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
    private final GraphicLivesCounter graphicCounter;
    private final NumericLivesCounter numericCounter;

    /**
     * Construct a lives object
     * @param heartSize Size of the heart image (both X and Y)
     * @param windowHeight Height of the window
     * @param heartImage Image of the heart
     * @param manager Parent game Bricker.manager
     */
    public Lives(int heartSize, float windowHeight,
                 ImageRenderable heartImage, BrickerGameManager manager) {
        this.livesLeft = 0;

        this.graphicCounter = new GraphicLivesCounter(
                HEART_X,
                HEART_Y,
                HEART_OFFSET,
                heartSize,
                windowHeight,
                heartImage,
                manager
        );

        this.numericCounter = new NumericLivesCounter(
                new Vector2(TEXT_X, windowHeight - TEXT_Y),
                new Vector2(TEXT_FONT_SIZE, TEXT_FONT_SIZE),
                manager
        );

        createHearts();
    }

    /**
     * Increase the player's lives count and adds a heart icon if under MAX_LIFE_COUNT.
     */
    public void incrementHearts() {
        if (livesLeft < MAX_LIFE_COUNT) {
            livesLeft += 1;
            updateCounters();
        }
    }

    /**
     * Decrease the amount of hearts the player has
     */
    public void decrementHearts() {
        if (livesLeft > 0) {
            livesLeft -= 1;
            updateCounters();
        }
    }

    /**
     * Check if the player has lives
     * @return True if the player has zero lives
     */
    public boolean isGameOver() {
        return livesLeft == 0;
    }


    // Private helper - create the initial heart's display at the bottom of the screen
    private void createHearts() {
        for (int i = 0; i < INITIAL_LIVES_COUNT; i++) {
            incrementHearts();
        }
    }

     // Private helper - update the graphic and numeric lives counters
    private void updateCounters() {
        graphicCounter.updateLives(livesLeft);
        numericCounter.updateLives(livesLeft);
    }

}