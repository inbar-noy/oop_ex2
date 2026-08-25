package gameobjects;

import brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represent a game brick
 */
public class Brick extends GameObject {
    private final CollisionStrategy collisionStrategy;
    private final Vector2 centerCoordinates;

    private final int row;
    private final int col;

    /**
     * Construct a brick
     * @param row Brick row
     * @param col Brick column
     * @param topLeftCorner Top left corner of the brick
     * @param dimensions Dimensions of the brick
     * @param brickImage Image of the brick
     * @param collisionStrategy Strategy to call when the brick is hit by a ball
     */
    public Brick(int row, int col, Vector2 topLeftCorner, Vector2 dimensions, Renderable brickImage, CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, brickImage);
        this.collisionStrategy = collisionStrategy;
        this.centerCoordinates = topLeftCorner.add(dimensions.mult(0.5f));
        this.row = row;
        this.col = col;
    }

    /**
     * Get the row of the brick
     * @return Row of the brick
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the column of the brick
     * @return Column of the brick
     */
    public int getCol() {
        return col;
    }

    /**
     * Called when the brick is hit
     * @param other Other object
     * @param collision Collision object
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.collisionStrategy.onCollision(this, other);
    }

    /**
     * Get the center coordinates of the brick
     * @return Center coordinates
     */
    public Vector2 getCenterCoordinates() {
        return this.centerCoordinates;
    }

    /**
     * Called when a brick should be popped, but not because of a collision.
     * This happens when the brick is blown up by a neighbor brick.
     */
    public void pseudoCollision() {
        this.collisionStrategy.onCollision(this, null);
    }
}
