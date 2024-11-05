/**
 * The Enemy class represents an enemy entity in the game.
 * It extends the Entity class and includes specific behaviors for enemies, such as taking damage.
 */
package model;

import java.awt.*;
import java.util.logging.Logger;

import static controller.GameManager.*;
import static utils.Constants.Directions.LEFT;
import static utils.Constants.PlayConstants.IDLE;

public class Enemy extends Entity {
    private static final Logger LOGGER = Logger.getLogger(Enemy.class.getName());
    private boolean isHit = false;

    /**
     * Constructs an Enemy object with the specified position, dimensions, and health points.
     *
     * @param x      The x-coordinate of the enemy.
     * @param y      The y-coordinate of the enemy.
     * @param width  The width of the enemy.
     * @param height The height of the enemy.
     * @param hp     The health points of the enemy.
     */
    public Enemy(int x, int y, int width, int height, int hp) {
        super(x, y, width, height, hp);
        this.action = IDLE;
        this.direction = LEFT;
    }

    /**
     * Reduces the enemy's health points when it takes damage.
     * If the enemy's health points reach zero or below, it is marked as dead.
     */
    public void takeDamage() {
        if (!isDead) {
            hp -= PLAYER_DAMAGE;
            setHit(true);
            if (hp <= 0) {
                setDead(true);
                setHit(false);
            }
        }
    }

    /**
     * Checks if the enemy is currently hit.
     *
     * @return True if the enemy is hit, otherwise false.
     */
    public boolean isHit() {
        return isHit;
    }

    /**
     * Sets the hit state of the enemy.
     *
     * @param hit The hit state to set.
     */
    public void setHit(boolean hit) {
        isHit = hit;
    }

    /**
     * Retrieves the hitbox of the enemy.
     *
     * @return The hitbox of the enemy as a Rectangle object.
     */
    public Rectangle getHitbox() {
        return new Rectangle(x, y, (int) (64 * SCALE - OFFSETX), (int) (48 * SCALE - OFFSETY));
    }
}
