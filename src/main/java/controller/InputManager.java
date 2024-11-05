/**
 * The InputManager class handles user input from keyboard and mouse events.
 * It controls player movement, jumping, attacking, and interactions with the game environment.
 */
package controller;

import model.Player;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;

import static utils.Constants.Directions.LEFT;
import static utils.Constants.Directions.RIGHT;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener {
    private static final Logger LOGGER = Logger.getLogger(InputManager.class.getName());
    private Controller controller;
    private Player player;
    private boolean spaceKeyPressed = false;

    /**
     * Constructs an InputManager object with the specified controller and player.
     *
     * @param controller The game controller.
     * @param player     The player object.
     */
    public InputManager(Controller controller, Player player) {
        this.controller = controller;
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D:
                player.setDirection(RIGHT);
                player.setMoving(true);
                LOGGER.info("Player moving right");
                break;
            case KeyEvent.VK_A:
                player.setDirection(LEFT);
                player.setMoving(true);
                LOGGER.info("Player moving left");
                break;
            case KeyEvent.VK_W:
                if (!player.isInAir()) {
                    controller.handleJump();
                }
                break;
            case KeyEvent.VK_SPACE:
                if (!spaceKeyPressed) {
                    spaceKeyPressed = true;
                    controller.getPanel().startAttackingAnimation();
                    LOGGER.info("Player started attacking");
                }
                break;
            case KeyEvent.VK_F:
                controller.handleInteraction();
                LOGGER.info("Player interacted");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D:
            case KeyEvent.VK_A:
                player.setMoving(false);
                LOGGER.info("Player stopped moving");
                break;
            case KeyEvent.VK_W:
                break;
            case KeyEvent.VK_SPACE:
                if (spaceKeyPressed) {
                    spaceKeyPressed = false;
                    if (player.isAttacking()) {
                        Rectangle playerAttackRange = new Rectangle(player.getX() + 40, player.getY() + 40, 100, 80);
                        if (playerAttackRange.intersects(controller.getEnemy().getHitbox())) {
                            controller.getEnemy().takeDamage();
                            if (controller.getEnemy().getHp() <= 0) {
                                controller.getEnemy().setDead(true);
                                LOGGER.info("Enemy is dead");
                            }
                            LOGGER.info("Enemy took damage. Health: " + controller.getEnemy().getHp());
                        }
                    }
                }
                break;
        }
        LOGGER.fine("Is player attacking: " + player.isAttacking());
        LOGGER.fine("Is player finished attack: " + player.isAttackingFinished());
    }

    // Unused methods for mouse events
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Checks if the space key is currently pressed.
     *
     * @return True if the space key is pressed, otherwise false.
     */
    public boolean isSpaceKeyPressed() {
        return spaceKeyPressed;
    }

    /**
     * Sets the state of the space key.
     *
     * @param spaceKeyPressed The state of the space key (pressed or not).
     */
    public void setSpaceKeyPressed(boolean spaceKeyPressed) {
        this.spaceKeyPressed = spaceKeyPressed;
    }
}
