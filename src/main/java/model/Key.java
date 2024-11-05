/**
 * The Key class represents a key object in the game that the player can collect.
 */
package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Key extends GameObject {
    private boolean isCollected;
    private transient Image keyImage;
    private static final int KEY_WIDTH = 20;
    private static final int KEY_HEIGHT = 32;

    /**
     * Constructs a Key object with the specified position and dimensions.
     *
     * @param x      The x-coordinate of the key.
     * @param y      The y-coordinate of the key.
     * @param width  The width of the key.
     * @param height The height of the key.
     */
    public Key(int x, int y, int width, int height) {
        super(x, y, width, height, ObjectType.KEY);
        this.isCollected = false;
        this.setHasCollision(true);
        loadAnim();
    }

    /**
     * Loads the animation image for the key.
     */
    private void loadAnim() {
        try {
            URL keyURL = getClass().getResource("/Key.gif");
            keyImage = ImageIO.read(Objects.requireNonNull(keyURL));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the key image at the specified coordinates.
     *
     * @param g The graphics context to use for drawing.
     * @param x The x-coordinate to draw the key at.
     * @param y The y-coordinate to draw the key at.
     */
    @Override
    public void draw(Graphics g, int x, int y) {
        if (!isCollected) {  // Only draw if not collected
            g.drawImage(keyImage, x, y, width, height, null);
        }
    }

    /**
     * Interacts with the player to collect the key.
     *
     * @param player The player interacting with the key.
     * @return True if the interaction was successful, false otherwise.
     */
    public boolean interact(Player player) {
        if (!isCollected) {
            collect();
        }
        return false;
    }

    /**
     * Collects the key.
     *
     * @return True if the key was successfully collected, false otherwise.
     */
    public boolean collect() {
        return isCollected = true;
    }

    // Getter and setter methods for key properties

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public Image getKeyImage() {
        return keyImage;
    }

    public void setKeyImage(Image keyImage) {
        this.keyImage = keyImage;
    }
}
