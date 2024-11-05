package model;

import controller.GameManager;
import view.Panel;

import static utils.Constants.Directions.RIGHT;
import static utils.Constants.PlayConstants.IDLE;

/**
 * The {@code Player} class represents the player entity in the game.
 */
public class Player extends Entity {
    private boolean attacking = false;
    private boolean attackingFinished = true;
    private int attackDamage = 10;
    private Panel panel;
    private int[][] lvlData;

    // Jumping/Gravity
    private boolean jump;
    private float airSpeed = 0f;
    private float gravity = 0.04f * GameManager.SCALE;
    private float jumpSpeed = -2.25f * GameManager.SCALE;
    private float fallSpeedAfterCollision = 0.5f * GameManager.SCALE;
    private boolean inAir = false;

    /**
     * Constructs a new {@code Player} object with the specified position, dimensions, and health points.
     *
     * @param x      the x-coordinate of the player
     * @param y      the y-coordinate of the player
     * @param width  the width of the player
     * @param height the height of the player
     * @param hp     the health points of the player
     */
    public Player(int x, int y, int width, int height, int hp) {
        super(x, y, width, height, hp);
        this.action = IDLE;
        this.direction = RIGHT;
    }

    /**
     * Initiates a jump action for the player.
     */
    public void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    /**
     * Resets the in-air state of the player.
     */
    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isAttackingFinished() {
        return attackingFinished;
    }

    public void setAttackingFinished(boolean attackingFinished) {
        this.attackingFinished = attackingFinished;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public float getAirSpeed() {
        return airSpeed;
    }

    public void setAirSpeed(float airSpeed) {
        this.airSpeed = airSpeed;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public float getJumpSpeed() {
        return jumpSpeed;
    }

    public void setJumpSpeed(float jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }

    public float getFallSpeedAfterCollision() {
        return fallSpeedAfterCollision;
    }

    public void setFallSpeedAfterCollision(float fallSpeedAfterCollision) {
        this.fallSpeedAfterCollision = fallSpeedAfterCollision;
    }

    public boolean isInAir() {
        return inAir;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    /**
     * Loads the level data for the player.
     *
     * @param lvlData the level data to load
     */
    public void loadLevelData(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    /**
     * Gets the level data associated with the player.
     *
     * @return the level data
     */
    public int[][] getLvlData() {
        return lvlData;
    }

    /**
     * Sets the level data associated with the player.
     *
     * @param lvlData the level data to set
     */
    public void setLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
    }
}
