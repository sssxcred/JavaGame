/**
 * The GameManager class controls the game flow and manages game elements.
 * It handles game initialization, level transitions, player actions, and game loop execution.
 */
package controller;

import model.*;
import view.Frame;
import view.Menu;
import view.Panel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GameManager implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(GameManager.class.getName());

    private Thread gameThread;
    private final int FPS_SET = 120;
    public final static float SCALE = 2.5f;
    public final static float TILES_SCALE = 1.5f;
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static int TILES_IN_WIDTH = 90;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * TILES_SCALE);
    public final static int GAME_WIDTH = TILES_IN_WIDTH * TILES_SIZE;
    public final static int GAME_HEIGHT = TILES_IN_HEIGHT * TILES_SIZE;
    public final static int PLAYER_DAMAGE = 25;
    private static final int PLAYER_START_X = 160;
    private static final int PLAYER_START_Y = 455;
    private static final int TRANSITION_X = 2000;
    private static final int TRANSITION_Y = 455;
    private static final int FALL_Y_LIMIT = 600;
    public final static int OFFSETX = 50;
    public final static int OFFSETY = 5;
    private Player player;
    private Frame frame;
    private Panel panel;
    private Level level;
    private Controller controller;
    private LevelManager levelManager;
    private GameObject object;
    private List<BloodShard> bloodShards;
    private List<Enemy> enemies;
    private int currentLevel = 1;
    private volatile boolean running = false;

    /**
     * Constructs a GameManager object and initiates the game menu.
     */
    public GameManager() {
        showMenu();
    }

    /**
     * Displays the game menu.
     */
    public void showMenu() {
        new Menu(this);
    }

    /**
     * Starts the game by initializing game elements and starting the game loop.
     */
    public synchronized void startGame() {
        bloodShards = new ArrayList<>();
        enemies = new ArrayList<>();

        // Example of adding multiple blood shards and enemies
        bloodShards.add(new BloodShard(400, 550, (int) (8 * SCALE), (int) (8 * SCALE)));
        bloodShards.add(new BloodShard(500, 550, (int) (8 * SCALE), (int) (8 * SCALE)));


        levelManager = new LevelManager(this);
        level = levelManager.getLevel();
        int[][] lvlData = level.getLvlData();

        enemies.add(new Enemy(400, 455, (int) (64 * SCALE - OFFSETX), (int) (48 * SCALE - OFFSETY), 100));
        enemies.add(new Enemy(1000, 455, (int) (64 * SCALE - OFFSETX), (int) (48 * SCALE - OFFSETY), 100));

        player = new Player(PLAYER_START_X, PLAYER_START_Y, (int) (64 * SCALE - OFFSETX), (int) (48 * SCALE - OFFSETY), 100);
        player.loadLevelData(lvlData);

        this.panel = new Panel(player, level, levelManager, enemies, object, bloodShards);
        frame = new Frame(panel);
        panel.requestFocus();
        start();
    }

    /**
     * Starts the game loop.
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    /**
     * Stops the game loop.
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Restarts the game.
     */
    public synchronized void restartGame() {
        stop();
        levelManager = new LevelManager(this);
        levelManager.setCurrentLevel(currentLevel);
        level = levelManager.getLevel();
        int[][] lvlData = level.getLvlData();
        player.loadLevelData(lvlData);

        panel.repaint();
        panel.requestFocus();
        start();
    }

    /**
     * Moves to the next level.
     */
    public synchronized void nextLevel() {
        if (currentLevel < 2) {
            currentLevel++;
        } else {
            currentLevel = 1;
        }
        restartGame();
    }

    /**
     * Retrieves the current level number.
     *
     * @return The current level number.
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Sets the current level number.
     *
     * @param currentLevel The current level number.
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        levelManager.setCurrentLevel(currentLevel);
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();
        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        while (running) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                panel.repaint();
                lastFrame = now;
                frames++;
            }

            // Check for reaching transition point
            if (player.getX() >= TRANSITION_X && player.getY() >= TRANSITION_Y) {
                nextLevel();
            }

            if (player.getY() >= FALL_Y_LIMIT) {
                restartGame();
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                LOGGER.info("FPS: " + frames );
                frames = 0;
            }
        }
    }

    /**
     * Retrieves the game width.
     *
     * @return The game width.
     */
    public int getGameWidth() {
        return GAME_WIDTH;
    }

    /**
     * Retrieves the game height.
     *
     * @return The game height.
     */
    public int getGameHeight() {
        return GAME_HEIGHT;
    }

    /**
     * Retrieves the list of blood shards in the game.
     *
     * @return The list of blood shards.
     */
    public List<BloodShard> getBloodShards() {
        return bloodShards;
    }

    /**
     * Retrieves the list of enemies in the game.
     *
     * @return The list of enemies.
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }
}