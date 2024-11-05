/**
 * Panel class represents the main drawing panel of the game.
 * It handles the rendering of players, enemies, animations, and other game elements.
 */
package view;

import controller.Controller;
import controller.GameManager;
import controller.InputManager;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import static controller.GameManager.*;
import static utils.Constants.Directions.LEFT;
import static utils.Constants.PlayConstants.*;

public class Panel extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(Panel.class.getName());

    private Player player;
    private int cameraX = 0;
    private int cameraY = 0;
    private int frameWidth;
    private int frameHeight;
    private BufferedImage enemySheet;
    private int deathAnimationIndex;
    private Timer timer;
    private Level level;
    private Enemy enemy;
    private BufferedImage playerSheet;
    private BufferedImage[][] playerAnimations;
    private BufferedImage[][] enemyAnimations;
    private int animationTick, animationIndex, animationSpeed = 30;
    private Controller controller;
    private GameManager gameManager;
    private int enemyAnimationIndex;
    private int enemyAnimationTick;
    private int enemyAnimationSpeed = 50;
    private LevelManager levelManager;
    private GameObject object;
    private BloodShard bloodShard;
    private java.util.List<Enemy> enemies;
    private java.util.List<BloodShard> bloodShards;

    /**
     * Constructor for the Panel class.
     *
     * @param player       The player object.
     * @param level        The level object.
     * @param levelManager The level manager object.
     * @param enemies      List of enemies.
     * @param object       The game object.
     * @param bloodShards  List of blood shards.
     */
    public Panel(Player player, Level level, LevelManager levelManager, java.util.List<Enemy> enemies, GameObject object, java.util.List<BloodShard> bloodShards) {
        this.enemies = enemies;
        this.player = player;
        this.level = level;
        this.levelManager = levelManager;
        this.object = object;
        this.bloodShards = bloodShards;

        if (enemies != null && !enemies.isEmpty()) {
            this.enemy = enemies.get(0);
        }
        if (bloodShards != null && !bloodShards.isEmpty()) {
            this.bloodShard = bloodShards.get(0);
        }

        this.controller = new Controller(player, enemy, level, this, gameManager);
        level.importShopAnimation();
        level.importBackground();
        importPlayerImg();
        loadPlayerAnimations();
        importEnemyImg();
        loadEnemyAnimations();
        InputManager inputManager = new InputManager(controller, player);
        addKeyListener(inputManager);
        setFocusable(true);
        requestFocusInWindow();
        setPanelSize();
        LOGGER.info("Panel initialized");
    }

    /**
     * Sets the size of the panel.
     */
    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        LOGGER.info("Panel size: " + " " + "x: " + GAME_WIDTH + "y: " +  GAME_HEIGHT);
    }

    /**
     * Loads player animations from the sprite sheet.
     */
    private void loadPlayerAnimations() {
        playerAnimations = new BufferedImage[9][22];
        for (int j = 0; j < playerAnimations.length; j++)
            for (int i = 0; i < playerAnimations[j].length; i++) {
                playerAnimations[j][i] = playerSheet.getSubimage(i * 64, j * 48, 64, 48);
            }
    }

    /**
     * Imports the player image from resources.
     */
    private void importPlayerImg() {
        InputStream is = getClass().getResourceAsStream("/ronin.png");

        try {
            playerSheet = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the current player animation based on player state.
     */
    public void setPlayerAnimation() {
        if (player.isMoving()) {
            player.setAction(RUNNING);
        } else if (player.isAttacking()) {
            player.setAction(ATTACKING);
        } else if (player.isInAir()) {
            player.setAction(LANDING);
        } else {
            player.setAction(IDLE);
        }
    }

    /**
     * Updates the player animation.
     */
    public void updatePlayerAnimation() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(player.getAction())) {
                animationIndex = 0;
                if (player.getAction() == ATTACKING && !player.isAttackingFinished()) {
                    player.setAction(IDLE);
                    player.setAttackingFinished(true);
                    player.setAttacking(false);
                }
            }
        }
        if (!player.isMoving() && !player.isAttacking()) {
            player.setAction(IDLE);
        }
    }

    /**
     * Starts the attacking animation for the player.
     */
    public void startAttackingAnimation() {
        if (!player.isAttacking() && !player.isMoving()) {
            player.setAttacking(true);
            player.setAttackingFinished(false);
            player.setAction(ATTACKING);
            animationIndex = 0;
        }
    }

    /**
     * Imports the enemy image from resources.
     */
    private void importEnemyImg() {
        InputStream enemyIs = getClass().getResourceAsStream("/enemy.png");
        try {
            enemySheet = ImageIO.read(enemyIs);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (enemyIs != null) enemyIs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads enemy animations from the sprite sheet.
     */
    private void loadEnemyAnimations() {
        enemyAnimations = new BufferedImage[9][22];
        for (int j = 0; j < enemyAnimations.length; j++) {
            for (int i = 0; i < enemyAnimations[j].length; i++) {
                enemyAnimations[j][i] = enemySheet.getSubimage(i * 64, j * 48, 64, 48);
            }
        }
    }

    /**
     * Updates the enemy animation.
     */
    public void updateEnemyAnimation() {
        enemyAnimationTick++;
        if (enemyAnimationTick >= enemyAnimationSpeed) {
            enemyAnimationTick = 0;
            enemyAnimationIndex++;

            if (enemy.getAction() == DYING) {
                if (enemyAnimationIndex >= GetSpriteAmount(DYING)) {
                    enemyAnimationIndex = GetSpriteAmount(DYING) - 1;
                }
            } else {
                if (enemyAnimationIndex >= GetSpriteAmount(enemy.getAction())) {
                    enemyAnimationIndex = 0;
                    if (enemy.getAction() == HITTING) {
                        enemy.setHit(false);
                        enemy.setAction(IDLE);
                    }
                }
            }
        }
    }


    /**
     * Sets the current animation state for the enemy based on its condition.
     * If the enemy is dead, sets the action to DYING.
     * If the enemy is hit and not already in the HITTING state, sets the action to HITTING.
     * Otherwise, sets the action to IDLE.
     */
    public void setEnemyAnimation() {
        if (enemy.isDead()) {
            enemy.setAction(DYING);
        } else if (enemy.isHit()) {
            if (enemy.getAction() != HITTING) {
                enemy.setAction(HITTING);
                enemyAnimationIndex = 0;
            }
        } else {
            enemy.setAction(IDLE);
        }
    }

    /**
     * Overrides the paintComponent method to render game elements on the panel.
     * This includes the background, player, enemies, animations, and other objects.
     *
     * @param g The Graphics object used for rendering.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        level.drawBackground(g, getWidth(), getHeight(), cameraX, cameraY);
        level.updateShopAnimation();
        level.drawShopAnimation(g, cameraX, cameraY);
        level.drawDecorations(g, cameraX, cameraY);

        updateEnemyAnimation();
        updatePlayerAnimation();
        setPlayerAnimation();
        setEnemyAnimation();
        controller.updatePlayerPosition();

        for (Enemy enemy : enemies) {
            BufferedImage currentEnemyFrame = enemyAnimations[enemy.getAction()][enemyAnimationIndex];
            int drawEnemyX = enemy.getX() - cameraX;
            int drawEnemyY = enemy.getY() - cameraY;
            int drawEnemyWidth = (int) (64 * GameManager.SCALE);
            int drawEnemyHeight = (int) (48 * GameManager.SCALE);

            if (enemy.getDirection() == LEFT) {
                g2d.translate(drawEnemyX + drawEnemyWidth, drawEnemyY);
                g2d.scale(-1, 1);
                g2d.drawImage(currentEnemyFrame, 0, 0, drawEnemyWidth, drawEnemyHeight, null);
                g2d.scale(-1, 1);
                g2d.translate(-(drawEnemyX + drawEnemyWidth), -drawEnemyY);
            } else {
                g2d.drawImage(currentEnemyFrame, drawEnemyX, drawEnemyY, drawEnemyWidth, drawEnemyHeight, null);
            }

            // Optional: draw bounding box for debugging
            g2d.setColor(Color.RED);
            g2d.drawRect(drawEnemyX, drawEnemyY, drawEnemyWidth - OFFSETX, drawEnemyHeight - OFFSETY);
        }

        BufferedImage currentPlayerFrame = playerAnimations[player.getAction()][animationIndex];
        int drawPlayerX = player.getX() - cameraX;
        int drawPlayerY = player.getY() - cameraY;
        int drawPlayerWidth = (int) (64 * GameManager.SCALE);
        int drawPlayerHeight = (int) (48 * GameManager.SCALE);

        if (player.getDirection() == LEFT) {
            g2d.translate(drawPlayerX + drawPlayerWidth, drawPlayerY);
            g2d.scale(-1, 1);
            g2d.drawImage(currentPlayerFrame, 0, 0, drawPlayerWidth, drawPlayerHeight, null);
            g2d.scale(-1, 1);
            g2d.translate(-(drawPlayerX + drawPlayerWidth), -drawPlayerY);
        } else {
            g2d.drawImage(currentPlayerFrame, drawPlayerX, drawPlayerY, drawPlayerWidth, drawPlayerHeight, null);
        }

        if (player.isAttacking()) {
            Rectangle playerAttackRange = new Rectangle(player.getX() + 40 - cameraX, player.getY() + 40 - cameraY, 100, 80);
            g2d.setColor(new Color(255, 0, 0, 100));
            g2d.fill(playerAttackRange);
        }
        for (BloodShard bloodShard : bloodShards) {
            bloodShard.draw(g, bloodShard.getX() - cameraX, bloodShard.getY() - cameraY);
        }
        levelManager.update();
        levelManager.draw(g, cameraX, cameraY);
    }

    /**
     * Sets the camera position to the specified coordinates.
     * If the coordinates are negative, they are adjusted to 0.
     *
     * @param x The x-coordinate of the camera.
     * @param y The y-coordinate of the camera.
     */


    public void setCameraPosition(int x, int y) {
        this.cameraX = x;
        this.cameraY = y;
        if (cameraX < 0)
            cameraX = 0;
        if (cameraY < 0)
            cameraY = 0;
    }

    //Getter and setter methods for panel properties

    public BufferedImage[][] getPlayerAnimations() {
        return playerAnimations;
    }

    public void setPlayerAnimations(BufferedImage[][] playerAnimations) {
        this.playerAnimations = playerAnimations;
    }

    public int getAnimationTick() {
        return animationTick;
    }

    public void setAnimationTick(int animationTick) {
        this.animationTick = animationTick;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public void setAnimationIndex(int animationIndex) {
        this.animationIndex = animationIndex;
    }

    public int getAnimationSpeed() {
        return animationSpeed;
    }

    public void setAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    public int getEnemyAnimationIndex() {
        return enemyAnimationIndex;
    }

    public void setEnemyAnimationIndex(int enemyAnimationIndex) {
        this.enemyAnimationIndex = enemyAnimationIndex;
    }

    public BufferedImage[][] getEnemyAnimations() {
        return enemyAnimations;
    }

    public void setEnemyAnimations(BufferedImage[][] enemyAnimations) {
        this.enemyAnimations = enemyAnimations;
    }
}
