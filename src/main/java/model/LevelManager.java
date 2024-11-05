package model;

import controller.GameManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static controller.GameManager.TILES_SIZE;

/**
 * The {@code LevelManager} class manages the game levels, including loading and drawing level data.
 */
public class LevelManager {
    private GameManager gameManager;
    private BufferedImage[] levelSprite;
    private Level levelOne;
    private Level levelTwo;

    private int currentLevel = 1;
    public static final String TILES = "oak_woods_tileset.png";
    public static final String LEVEL_ONE = "level1.png";
    public static final String LEVEL_TWO = "level2.png";

    /**
     * Constructs a new {@code LevelManager} object with the specified {@code GameManager}.
     *
     * @param gameManager the game manager
     */
    public LevelManager(GameManager gameManager) {
        this.gameManager = gameManager;
        importTiles();
        levelOne = new Level(getLevelData(LEVEL_ONE));
        levelTwo = new Level(getLevelData(LEVEL_TWO));
    }

    /**
     * Gets a sprite image from the specified file.
     *
     * @param fileName the name of the file
     * @return the sprite image
     */
    public static BufferedImage GetSprite(String fileName) {
        BufferedImage img = null;
        InputStream is = LevelManager.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    /**
     * Gets the current level number.
     *
     * @return the current level number
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Gets the current level.
     *
     * @return the current level
     */
    public Level getLevel() {
        if (currentLevel == 1) {
            return levelOne;
        } else {
            return levelTwo;
        }
    }

    /**
     * Sets the current level number.
     *
     * @param currentLevel the new current level number
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * Imports tile images and slices them into individual sprites.
     */
    public void importTiles() {
        BufferedImage img = GetSprite(TILES);
        levelSprite = new BufferedImage[15];
        for (int j = 0; j < 3; j++)
            for (int i = 0; i < 5; i++) {
                int index = j * 5 + i;
                levelSprite[index] = img.getSubimage(i * 24, j * 24, 24, 24);
            }
    }

    /**
     * Draws the current level on the provided graphics context.
     *
     * @param g        the graphics context
     * @param cameraX  the x-coordinate of the camera
     * @param cameraY  the y-coordinate of the camera
     */
    public void draw(Graphics g, int cameraX, int cameraY) {
        Level currentLevel = getLevel();
        for (int j = 0; j < GameManager.TILES_IN_HEIGHT; j++)
            for (int i = 0; i < GameManager.TILES_IN_WIDTH; i++) {
                int index = currentLevel.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILES_SIZE * i - cameraX, TILES_SIZE * j - cameraY, TILES_SIZE, TILES_SIZE, null);
            }
    }

    /**
     * Updates the level manager.
     */
    public void update() {
    }

    /**
     * Loads level data from an image file.
     *
     * @param levelFile the name of the level file
     * @return a 2D array representing the level data
     */
    public static int[][] getLevelData(String levelFile) {
        int[][] lvlData = new int[GameManager.TILES_IN_HEIGHT][GameManager.TILES_IN_WIDTH];
        BufferedImage img = GetSprite(levelFile);

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 15)
                    value = 0;
                lvlData[j][i] = value;
            }
        return lvlData;
    }

    /**
     * Gets the first level.
     *
     * @return the first level
     */
    public Level getLevelOne() {
        return levelOne;
    }

    /**
     * Gets the second level.
     *
     * @return the second level
     */
    public Level getLevelTwo() {
        return levelTwo;
    }
}
