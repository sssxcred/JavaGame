/**
 * The Entity class serves as a base class for all game entities.
 * It contains common properties and methods that can be used by player characters, enemies, and other entities in the game.
 */
package model;

public class Entity {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int hitboxWidth;
    protected int hitboxHeight;
    protected int hp;
    protected boolean isDead = false;
    protected int action;
    protected int direction;
    protected boolean moving;
    protected int speed;

    /**
     * Constructs an Entity with specified position, dimensions, and health points.
     *
     * @param x      The x-coordinate of the entity.
     * @param y      The y-coordinate of the entity.
     * @param width  The width of the entity.
     * @param height The height of the entity.
     * @param hp     The health points of the entity.
     */
    public Entity(int x, int y, int width, int height, int hp) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitboxWidth = (int) (width * 0.9);
        this.hitboxHeight = (int) (height * 1);
        this.hp = hp;
    }

    /**
     * Reduces the entity's health points by the specified damage.
     * Marks the entity as dead if health points fall to zero or below.
     *
     * @param damage The amount of damage to inflict.
     */
    public void takeDamage(int damage) {
        if (!isDead) {
            hp -= damage;
            if (hp <= 0) {
                isDead = true;
            }
        }
    }

    // Getter and setter methods for the entity's properties

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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHitboxWidth() {
        return hitboxWidth;
    }

    public void setHitboxWidth(int hitboxWidth) {
        this.hitboxWidth = hitboxWidth;
    }

    public int getHitboxHeight() {
        return hitboxHeight;
    }

    public void setHitboxHeight(int hitboxHeight) {
        this.hitboxHeight = hitboxHeight;
    }
}
