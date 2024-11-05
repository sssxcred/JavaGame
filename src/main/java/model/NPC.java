package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * The {@code NPC} class represents a non-playable character in the game.
 */
public class NPC extends GameObject {
    private boolean isTalking = false;
    private Image dialogue1, dialogue2;
    private boolean isSoldKeyAndNewCoolSword = false;

    /**
     * Constructs a new {@code NPC} object with the specified position and dimensions.
     *
     * @param x      the x-coordinate of the NPC
     * @param y      the y-coordinate of the NPC
     * @param width  the width of the NPC
     * @param height the height of the NPC
     */
    public NPC(int x, int y, int width, int height) {
        super(x, y, width, height, ObjectType.NPC);
        loadImages();
    }

    /**
     * Loads the dialogue images for the NPC.
     */
    private void loadImages() {
        try {
            dialogue1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/dialogue1.png")));
            dialogue2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/dialogue2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the NPC is currently talking.
     *
     * @return {@code true} if the NPC is talking, {@code false} otherwise
     */
    public boolean isTalking() {
        return isTalking;
    }

    /**
     * Sets the talking state of the NPC.
     *
     * @param talking {@code true} if the NPC is talking, {@code false} otherwise
     */
    public void setTalking(boolean talking) {
        isTalking = talking;
    }

    /**
     * Gets the first dialogue image of the NPC.
     *
     * @return the first dialogue image
     */
    public Image getDialogue1() {
        return dialogue1;
    }

    /**
     * Sets the first dialogue image of the NPC.
     *
     * @param dialogue1 the first dialogue image
     */
    public void setDialogue1(Image dialogue1) {
        this.dialogue1 = dialogue1;
    }

    /**
     * Gets the second dialogue image of the NPC.
     *
     * @return the second dialogue image
     */
    public Image getDialogue2() {
        return dialogue2;
    }

    /**
     * Sets the second dialogue image of the NPC.
     *
     * @param dialogue2 the second dialogue image
     */
    public void setDialogue2(Image dialogue2) {
        this.dialogue2 = dialogue2;
    }

    /**
     * Checks if the NPC has sold the key and new cool sword.
     *
     * @return {@code true} if the NPC has sold the key and new cool sword, {@code false} otherwise
     */
    public boolean isSoldKeyAndNewCoolSword() {
        return isSoldKeyAndNewCoolSword;
    }

    /**
     * Sets the state of whether the NPC has sold the key and new cool sword.
     *
     * @param soldKeyAndNewCoolSword {@code true} if the NPC has sold the key and new cool sword, {@code false} otherwise
     */
    public void setSoldKeyAndNewCoolSword(boolean soldKeyAndNewCoolSword) {
        isSoldKeyAndNewCoolSword = soldKeyAndNewCoolSword;
    }

    /**
     * Interacts with the player.
     *
     * @param player the player object
     * @return {@code true} if interaction was successful, {@code false} otherwise
     */
    public boolean interact(Player player) {
        if (!isTalking) {
            isTalking = true;
            return true;
        }
        return false;
    }
}
