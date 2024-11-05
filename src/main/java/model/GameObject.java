/**
 * The GameObject class serves as a base class for all game objects.
 * It contains common properties and methods that can be used by various objects in the game.
 */
package model;

import java.awt.*;
import java.io.Serializable;

public abstract class GameObject implements Serializable {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private boolean isInteracted = false;
    private boolean hasCollision = false;

    private final ObjectType type;

    /**
     * Constructs a GameObject with specified position, dimensions, and type.
     *
     * @param x      The x-coordinate of the game object.
     * @param y      The y-coordinate of the game object.
     * @param width  The width of the game object.
     * @param height The height of the game object.
     * @param type   The type of the game object.
     */
    public GameObject(int x, int y, int width, int height, ObjectType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isInteracted = false;
        this.hasCollision = true;
        this.type = type;
    }

    // Getter and setter methods for the game object's properties

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isInteracted() {
        return isInteracted;
    }

    public void setInteracted(boolean interacted) {
        isInteracted = interacted;
    }

    public boolean isHasCollision() {
        return hasCollision;
    }

    public void setHasCollision(boolean hasCollision) {
        this.hasCollision = hasCollision;
    }

    public ObjectType getType() {
        return type;
    }

    /**
     * Draws the game object at the specified position.
     *
     * @param g The Graphics object used for drawing.
     * @param x The x-coordinate where the object should be drawn.
     * @param y The y-coordinate where the object should be drawn.
     */
    public void draw(Graphics g, int x, int y) {
        // Implementation for drawing the game object
    }
}
