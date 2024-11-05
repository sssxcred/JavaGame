/**
 * The BloodShard class represents a collectible item in the game.
 * It extends the GameObject class and defines properties specific to blood shards.
 */
package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class BloodShard extends GameObject {
    private int value;
    private boolean isCollected;
    private transient Image bloodShardImg;
    private static final int COIN_WIDTH = 15;
    private static final int COIN_HEIGHT = 15;

    /**
     * Constructs a BloodShard object with the specified position and dimensions.
     *
     * @param x      The x-coordinate of the blood shard.
     * @param y      The y-coordinate of the blood shard.
     * @param width  The width of the blood shard.
     * @param height The height of the blood shard.
     */
    public BloodShard(int x, int y, int width, int height) {
        super(x, y, width, height, ObjectType.BLOOD_SHARD);
        this.value = 1;
        this.isCollected = false;
        this.setHasCollision(true);
        loadImg();
    }

    /**
     * Loads the image of the blood shard.
     */
    private void loadImg() {
        try {
            URL bloodShardURL = getClass().getResource("/bloodShard.gif");
            bloodShardImg = ImageIO.read(Objects.requireNonNull(bloodShardURL));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the value of the blood shard.
     *
     * @return The value of the blood shard.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the blood shard.
     *
     * @param value The value to set.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Checks if the blood shard is collected.
     *
     * @return True if the blood shard is collected, otherwise false.
     */
    public boolean isCollected() {
        return isCollected;
    }

    /**
     * Collects the blood shard.
     *
     * @return True if the blood shard is successfully collected, otherwise false.
     */
    public boolean collect() {
        return isCollected = true;
    }

    /**
     * Sets the collected state of the blood shard.
     *
     * @param collected The collected state to set.
     */
    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    /**
     * Retrieves the image of the blood shard.
     *
     * @return The image of the blood shard.
     */
    public Image getBloodShardImg() {
        return bloodShardImg;
    }

    /**
     * Sets the image of the blood shard.
     *
     * @param bloodShardImg The image to set.
     */
    public void setBloodShardImg(Image bloodShardImg) {
        this.bloodShardImg = bloodShardImg;
    }

    /**
     * Draws the blood shard on the screen.
     *
     * @param g The graphics context.
     * @param x The x-coordinate to draw the blood shard.
     * @param y The y-coordinate to draw the blood shard.
     */
    @Override
    public void draw(Graphics g, int x, int y) {
        if (!isCollected) {  // Only draw if not collected
            g.drawImage(bloodShardImg, x, y, width, height, null);
        }
    }

    /**
     * Handles interactions with the blood shard.
     *
     * @param player The player interacting with the blood shard.
     * @return Always returns false, indicating that the interaction does not affect the player.
     */
    public boolean interact(Player player) {
        if (!isCollected) {
            collect();
        }
        return false;
    }
}
