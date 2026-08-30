package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;

import java.util.ArrayList;

/**
 * Displays the player's remaining lives using heart icons.
 */
public class GraphicLivesCounter {
    private final BrickerGameManager manager;
    private final Renderable heartImage;
    private final float heartX;
    private final float heartY;
    private final float heartOffset;
    private final float heartSize;
    private final float windowHeight;
    private final ArrayList<GameObject> hearts = new ArrayList<>();

    public GraphicLivesCounter(float heartX, float heartY, float heartOffset, float heartSize,
                               float windowHeight, Renderable heartImage,
                               BrickerGameManager manager) {
        this.heartX = heartX;
        this.heartY = heartY;
        this.heartOffset = heartOffset;
        this.heartSize = heartSize;
        this.windowHeight = windowHeight;
        this.heartImage = heartImage;
        this.manager = manager;
    }

    /**
     * Updates the displayed heart icons in accordance with the current lives count.
     * @param currentLives Current number of lives
     */
    public void updateLives(int currentLives) {
        // Case 1 - need to add hearts
        while (hearts.size() < currentLives) {
            int heartIndex = hearts.size() + 1;
            Vector2 position = new Vector2(
                    heartX + heartIndex * heartOffset,
                    windowHeight - heartY
            );

            GameObject heart = new GameObject(
                    position,
                    new Vector2(heartSize, heartSize),
                    heartImage
            );
            manager.addBackgroundObj(heart);
            hearts.add(heart);
        }

        // Case 2 - need to remove hearts
        while (hearts.size() > currentLives) {
            GameObject heart = hearts.remove(hearts.size() - 1);
            manager.removeBackgroundObj(heart);
        }
    }
}