package gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

public class Wall extends GameObject{

    public Wall(Vector2 topLeftCorner, Vector2 dimensions, RectangleRenderable border) {
        super(topLeftCorner, dimensions, border);
    }

}
