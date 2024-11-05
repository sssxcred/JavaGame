/**
 * The Controller class manages the game logic and player interactions.
 * It handles player movement, collisions, interactions with game objects,
 * and updates the game state accordingly.
 */
package controller;

import model.BloodShard;
import model.Enemy;
import model.Level;
import model.Player;
import view.Panel;

import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Logger;

import static utils.Constants.Directions.LEFT;
import static utils.Constants.Directions.RIGHT;

public class Controller {
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());


    private Player player;
    private Enemy enemy;
    private Level level;
    private Panel panel;
    private GameManager gameManager;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<BloodShard> bloodShards = new ArrayList<>();

    /**
     * Constructs a Controller object with the specified player, enemy, level, panel, and game manager.
     *
     * @param player      The player object.
     * @param enemy       The enemy object.
     * @param level       The level object.
     * @param panel       The panel object.
     * @param gameManager The game manager object.
     */
    public Controller(Player player, Enemy enemy, Level level, Panel panel, GameManager gameManager) {
        this.player = player;
        this.enemy = enemy;
        this.level = level;
        this.panel = panel;
        this.gameManager = gameManager;
    }

    /**
     * Handles the player's jump action.
     * Sets the player's jump state and manages air movement.
     */
    public void handleJump() {
        if (!player.isInAir()) {
            LOGGER.info("Player is jumping.");
            player.setJump(true);
            player.setInAir(true);
            player.setAirSpeed(player.getJumpSpeed());
        }
    }

    /**
     * Updates the player's position based on user input and game state.
     */
    public void updatePlayerPosition() {
        int newX = player.getX();
        int newY = player.getY();

        if (player.isJump()) {
            handleJump();
            player.setJump(false);
        }

        if (player.isInAir()) {
            player.setAirSpeed(player.getAirSpeed() + player.getGravity());
            newY = (int) (player.getY() + player.getAirSpeed());

            if (!CanMoveHere(player.getX(), newY, player.getHitboxWidth(), player.getHitboxHeight(), player.getLvlData())) {
                LOGGER.info("Player landed on a surface.");
                player.setInAir(false);
                player.setAirSpeed(0);
            }
        }

        if (player.isMoving()) {
            switch (player.getDirection()) {
                case RIGHT:
                    if (player.getX() < (level.getBackgroundImg().getWidth() * GameManager.SCALE * 5)) {
                        newX = player.getX() + 3;
                    }
                    break;
                case LEFT:
                    newX = player.getX() - 3;
                    break;
            }
        }

        if (!player.isInAir() && !isPlayerOnFloor(player, player.getLvlData())) {
            player.setInAir(true);
        }

        if (CanMoveHere(newX, newY, player.getHitboxWidth(), player.getHitboxHeight(), player.getLvlData())) {
            player.setX(newX);
            player.setY(newY);
        }

        panel.setCameraPosition(player.getX() - (1920 / 2), player.getY() - (970 / 2));
        panel.repaint();
    }

    /**
     * Handles interactions between the player and game objects.
     * Detects collisions and triggers appropriate actions.
     */
    public void handleInteraction() {
        Rectangle playerHitbox = new Rectangle(player.getX(), player.getY(), player.getHitboxWidth(), player.getHitboxHeight());

        for (BloodShard bloodShard : bloodShards) {
            Rectangle shardHitbox = new Rectangle(bloodShard.getX(), bloodShard.getY(), bloodShard.getWidth(), bloodShard.getHeight());
            if (playerHitbox.intersects(shardHitbox) && !bloodShard.isCollected()) {
                bloodShard.collect();
                System.out.println("BloodShard collected!");
            }
        }
    }

    /**
     * Checks if a given position is movable based on level data.
     *
     * @param x       The x-coordinate of the position.
     * @param y       The y-coordinate of the position.
     * @param width   The width of the object.
     * @param height  The height of the object.
     * @param lvlData The level data representing collidable areas.
     * @return True if the position is movable, false otherwise.
     */
    public static boolean CanMoveHere(int x, int y, int width, int height, int[][] lvlData) {
        return !IsCollidable(x, y, lvlData) &&
                !IsCollidable(x + width, y, lvlData) &&
                !IsCollidable(x, y + height, lvlData) &&
                !IsCollidable(x + width, y + height, lvlData);
    }

    /**
     * Checks if a given position is collidable based on level data.
     *
     * @param x       The x-coordinate of the position.
     * @param y       The y-coordinate of the position.
     * @param lvlData The level data representing collidable areas.
     * @return True if the position is collidable, false otherwise.
     */
    private static boolean IsCollidable(int x, int y, int[][] lvlData) {
        if (x < 0 || x >= GameManager.GAME_WIDTH)
            return true;
        if (y < 0 || y >= GameManager.GAME_HEIGHT)
            return true;

        int xIndex = x / GameManager.TILES_SIZE;
        int yIndex = y / GameManager.TILES_SIZE;

        int value = lvlData[yIndex][xIndex];

        return value >= 15 || value <= 0 || value != 4;
    }

    /**
     * Checks if the player is on the floor based on level data.
     *
     * @param player  The player object.
     * @param lvlData The level data representing collidable areas.
     * @return True if the player is on the floor, false otherwise.
     */
    public static boolean isPlayerOnFloor(Player player, int[][] lvlData) {
        int x = player.getX();
        int y = player.getY();
        int width = player.getHitboxWidth();
        int height = player.getHitboxHeight();

        return IsCollidable(x, y + height + 1, lvlData) || IsCollidable(x + width, y + height + 1, lvlData);
    }

    /**
     Getters and Setters
     */

    public ArrayList<BloodShard> getBloodShards() {
        return bloodShards;
    }

    public void setBloodShards(ArrayList<BloodShard> bloodShards) {
        this.bloodShards = bloodShards;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public GameManager getGameManager() {
        return gameManager;
    }


}
