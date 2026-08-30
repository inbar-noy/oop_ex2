package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;

import java.awt.Color;

/**
 * Displays the player's remaining lives as numeric text.
 */
public class NumericLivesCounter {
    private final TextRenderable textRenderable;

    public NumericLivesCounter(Vector2 position, Vector2 dimensions,
                               BrickerGameManager manager) {
        this.textRenderable = new TextRenderable("");
        GameObject textObject = new GameObject(position, dimensions, textRenderable);
        manager.addBackgroundObj(textObject);
    }

    /**
     * Updates the displayed numeric text and changes color based on life count.
     * @param currentLives Current number of lives
     */
    public void updateLives(int currentLives) {
        textRenderable.setString(String.valueOf(currentLives));
        if (currentLives == 1) {
            textRenderable.setColor(Color.red);
        } else if (currentLives == 2) {
            textRenderable.setColor(Color.yellow);
        } else {
            textRenderable.setColor(Color.green);
        }
    }
}