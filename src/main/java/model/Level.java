package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Level} class represents a game level, including its background, decorations, animations, and enemies.
 */
public class Level {
    private BufferedImage backgroundImg;
    private BufferedImage grassImg;
    private BufferedImage shopAnimationSheet;
    private int frameWidth;
    private int frameHeight;
    private int shopAnimationIndex;
    private int shopAnimationSpeed = 30;
    private int shopAnimationFrames = 6;
    private int shopAnimationTick = 0;
    private int cameraX;
    private int cameraY;
    private BufferedImage[] decorationImages;
    private int[] decorationX;
    private int[] decorationY;
    private boolean[] decorationCollision;
    private int[] decorationWidth;
    private int[] decorationHeight;
    private List<Enemy> enemies;
    private int[][] lvlData;

    /**
     * Constructs a new {@code Level} object with the specified level data.
     *
     * @param lvlData a 2D array representing the level data
     */
    public Level(int[][] lvlData) {
        importBackground();
        importGrass();
        importShopAnimation();
        loadDecorationsFromJson();
        this.lvlData = lvlData;
        this.enemies = new ArrayList<>();
    }

    /**
     * Gets the x-coordinate of the camera.
     *
     * @return the x-coordinate of the camera
     */
    public int getCameraX() {
        return cameraX;
    }

    /**
     * Gets the y-coordinate of the camera.
     *
     * @return the y-coordinate of the camera
     */
    public int getCameraY() {
        return cameraY;
    }

    /**
     * Gets the background image.
     *
     * @return the background image
     */
    public BufferedImage getBackgroundImg() {
        return backgroundImg;
    }

    /**
     * Gets the grass image.
     *
     * @return the grass image
     */
    public BufferedImage getGrassImg() {
        return grassImg;
    }

    /**
     * Sets the camera position.
     *
     * @param x the x-coordinate of the camera
     * @param y the y-coordinate of the camera
     */
    public void setCameraPosition(int x, int y) {
        this.cameraX = x;
        this.cameraY = y;
    }

    /**
     * Imports the background images and combines them into a single image.
     */
    public void importBackground() {
        InputStream backgroundStream1 = getClass().getResourceAsStream("/background_layer_1.png");
        InputStream backgroundStream2 = getClass().getResourceAsStream("/background_layer_2.png");
        InputStream backgroundStream3 = getClass().getResourceAsStream("/background_layer_3.png");

        try {
            BufferedImage backgroundImg1 = ImageIO.read(backgroundStream1);
            BufferedImage backgroundImg2 = ImageIO.read(backgroundStream2);
            BufferedImage backgroundImg3 = ImageIO.read(backgroundStream3);
            backgroundImg = combineBackgrounds(backgroundImg1, backgroundImg2, backgroundImg3);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                backgroundStream1.close();
                backgroundStream2.close();
                backgroundStream3.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Combines three background images into a single image.
     *
     * @param background1 the first background image
     * @param background2 the second background image
     * @param background3 the third background image
     * @return the combined background image
     */
    private BufferedImage combineBackgrounds(BufferedImage background1, BufferedImage background2, BufferedImage background3) {
        int width = Math.max(Math.max(background1.getWidth(), background2.getWidth()), background3.getWidth());
        int height = Math.max(Math.max(background1.getHeight(), background2.getHeight()), background3.getHeight());

        BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combined.createGraphics();

        g.drawImage(background1, 0, 0, null);
        g.drawImage(background2, 0, 0, null);
        g.drawImage(background3, 0, 0, null);

        g.dispose();
        return combined;
    }

    /**
     * Imports the grass image.
     */
    private void importGrass() {
        InputStream grassStream = getClass().getResourceAsStream("/trava2.png");
        try {
            grassImg = ImageIO.read(grassStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                grassStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Imports the shop animation sheet and calculates frame dimensions.
     */
    public void importShopAnimation() {
        InputStream shopStream = getClass().getResourceAsStream("/shop_anim.png");
        try {
            shopAnimationSheet = ImageIO.read(shopStream);
            frameWidth = (shopAnimationSheet.getWidth() / 6);
            frameHeight = shopAnimationSheet.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                shopStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Draws the background image on the provided graphics context.
     *
     * @param g           the graphics context
     * @param panelWidth  the width of the panel
     * @param panelHeight the height of the panel
     * @param cameraX     the x-coordinate of the camera
     * @param cameraY     the y-coordinate of the camera
     */
    public void drawBackground(Graphics g, int panelWidth, int panelHeight, int cameraX, int cameraY) {
        if (backgroundImg != null) {
            int bgWidth = panelWidth;
            int bgHeight = panelHeight;

            int horizontalCount = (panelWidth / bgWidth) + 2;
            int verticalCount = (panelHeight / bgHeight) + 1;

            horizontalCount++;

            for (int i = -1; i < horizontalCount; i++) {
                for (int j = -1; j < verticalCount; j++) {
                    int drawX = i * bgWidth - cameraX;
                    int drawY = j * bgHeight - cameraY;
                    g.drawImage(backgroundImg, drawX, drawY, bgWidth, bgHeight, null);
                }
            }
        }
    }

    /**
     * Draws the grass image on the provided graphics context.
     *
     * @param g           the graphics context
     * @param panelWidth  the width of the panel
     * @param panelHeight the height of the panel
     * @param cameraX     the x-coordinate of the camera
     * @param cameraY     the y-coordinate of the camera
     */
    public void drawGrass(Graphics g, int panelWidth, int panelHeight, int cameraX, int cameraY) {
        if (grassImg != null) {
            int grassWidth = grassImg.getWidth();
            int grassHeight = grassImg.getHeight();

            int scaledWidth = grassWidth * 2;
            int scaledHeight = grassHeight * 2;

            for (int i = -1; i < panelWidth / scaledWidth + 2; i++) {
                int drawX = i * scaledWidth - cameraX % scaledWidth;
                int drawY = panelHeight - scaledHeight - cameraY;
                g.drawImage(grassImg, drawX, drawY, scaledWidth, scaledHeight, null);
            }
        }
    }

    /**
     * Draws the shop animation on the provided graphics context.
     *
     * @param g        the graphics context
     * @param cameraX  the x-coordinate of the camera
     * @param cameraY  the y-coordinate of the camera
     */
    public void drawShopAnimation(Graphics g, int cameraX, int cameraY) {
        if (shopAnimationSheet != null) {
            int shopX = 900 - cameraX;
            int shopY = 320 - cameraY;
            BufferedImage currentFrame = shopAnimationSheet.getSubimage(shopAnimationIndex * frameWidth, 0, frameWidth, frameHeight);
            int scaledWidth = frameWidth * 2;
            int scaledHeight = frameHeight * 2;
            g.drawImage(currentFrame, shopX, shopY, scaledWidth, scaledHeight, null);
        }
    }

    /**
     * Updates the shop animation, progressing to the next frame if necessary.
     */
    public void updateShopAnimation() {
        shopAnimationTick++;
        if (shopAnimationTick >= shopAnimationSpeed) {
            shopAnimationTick = 0;
            shopAnimationIndex++;
            if (shopAnimationIndex >= shopAnimationFrames) {
                shopAnimationIndex = 0;
            }
        }
    }

    /**
     * Loads decoration data from a JSON file and initializes the decoration images and properties.
     */
    private void loadDecorationsFromJson() {
        Gson gson = new Gson();
        try (JsonReader reader = new JsonReader(new FileReader("level.json"))) {
            JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            JsonArray decorationsArray = jsonObject.getAsJsonArray("decorations");
            int numDecorations = decorationsArray.size();

            decorationImages = new BufferedImage[numDecorations];
            decorationX = new int[numDecorations];
            decorationY = new int[numDecorations];
            decorationWidth = new int[numDecorations];
            decorationHeight = new int[numDecorations];
            decorationCollision = new boolean[numDecorations];

            for (int i = 0; i < numDecorations; i++) {
                JsonObject decorationObject = decorationsArray.get(i).getAsJsonObject();

                String imagePath = decorationObject.get("imagePath").getAsString();
                InputStream inputStream = getClass().getResourceAsStream(imagePath);
                try {
                    decorationImages[i] = ImageIO.read(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (inputStream != null)
                            inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                decorationX[i] = decorationObject.get("x").getAsInt();
                decorationY[i] = decorationObject.get("y").getAsInt();
                decorationCollision[i] = decorationObject.get("collision").getAsBoolean();
                if (decorationObject.has("width")) {
                    decorationWidth[i] = decorationObject.get("width").getAsInt();
                } else {
                    decorationWidth[i] = decorationImages[i].getWidth();
                }

                if (decorationObject.has("height")) {
                    decorationHeight[i] = decorationObject.get("height").getAsInt();
                } else {
                    decorationHeight[i] = decorationImages[i].getHeight();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the decorations on the provided graphics context.
     *
     * @param g        the graphics context
     * @param cameraX  the x-coordinate of the camera
     * @param cameraY  the y-coordinate of the camera
     */
    public void drawDecorations(Graphics g, int cameraX, int cameraY) {
        for (int i = 0; i < decorationImages.length; i++) {
            int drawX = decorationX[i] - cameraX;
            int drawY = decorationY[i] - cameraY;
            int width = decorationWidth[i];
            int height = decorationHeight[i];

            g.drawImage(decorationImages[i], drawX, drawY, width, height, null);
        }
    }

    /**
     * Adds an enemy to the level.
     *
     * @param enemy the enemy to add
     */
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    /**
     * Gets the sprite index at the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the sprite index
     */
    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    /**
     * Gets the level data.
     *
     * @return a 2D array representing the level data
     */
    public int[][] getLvlData() {
        return lvlData;
    }

    /**
     * Sets the level data.
     *
     * @param lvlData a 2D array representing the level data
     */
    public void setLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    public int getShopAnimationIndex() {
        return shopAnimationIndex;
    }

    public void setShopAnimationIndex(int shopAnimationIndex) {
        this.shopAnimationIndex = shopAnimationIndex;
    }

    public int getShopAnimationSpeed() {
        return shopAnimationSpeed;
    }

    public void setShopAnimationSpeed(int shopAnimationSpeed) {
        this.shopAnimationSpeed = shopAnimationSpeed;
    }

    public int getShopAnimationFrames() {
        return shopAnimationFrames;
    }

    public void setShopAnimationFrames(int shopAnimationFrames) {
        this.shopAnimationFrames = shopAnimationFrames;
    }

    public int getShopAnimationTick() {
        return shopAnimationTick;
    }

    public void setShopAnimationTick(int shopAnimationTick) {
        this.shopAnimationTick = shopAnimationTick;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void setBackgroundImg(BufferedImage backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

}
