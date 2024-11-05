package view;

import controller.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code Menu} class represents the main menu of the game.
 */
public class Menu extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(Menu.class.getName());
    private JButton startButton;
    private JButton exitButton;
    private GameManager gameManager;
    private JFrame menuFrame;
    private JPanel menuPanel;

    /**
     * Constructs a new {@code Menu} object with the specified game manager.
     *
     * @param gameManager the game manager
     */
    public Menu(GameManager gameManager) {
        this.gameManager = gameManager;
        createMenuFrame();
        createButtons();
        addButtonsToPanel();
        LOGGER.info("Menu initialized");
    }

    /**
     * Creates the main menu frame.
     */
    private void createMenuFrame() {
        menuFrame = new JFrame("The Last Samurai");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(1000, 600);
        menuFrame.setResizable(false);
        menuFrame.setLocationRelativeTo(null);

        menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image bg = new ImageIcon(getClass().getResource("/menu.png")).getImage();
                g.drawImage(bg, 0, 0, 1000, 600, null);
            }
        };
        menuPanel.setLayout(null);

        menuFrame.add(menuPanel);
        menuFrame.setVisible(true);
    }

    /**
     * Creates the start and exit buttons for the menu.
     */
    private void createButtons() {
        URL imageUrl = getClass().getResource("/New_game.png");
        ImageIcon newGame = new ImageIcon(imageUrl);
        imageUrl = getClass().getResource("/New_Game_White.png");
        ImageIcon newGameSelected = new ImageIcon(imageUrl);
        imageUrl = getClass().getResource("/Exit_black.png");
        ImageIcon exit = new ImageIcon(imageUrl);
        imageUrl = getClass().getResource("/Exit.png");
        ImageIcon exitSelected = new ImageIcon(imageUrl);

        startButton = new JButton(newGame);
        exitButton = new JButton(exit);

        setButtonProperties(startButton, newGame, newGameSelected);
        setButtonProperties(exitButton, exit, exitSelected);

        startButton.setBounds(50, 50, newGame.getIconWidth(), newGame.getIconHeight());
        exitButton.setBounds(700, 475, exit.getIconWidth(), exit.getIconHeight());

        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LOGGER.info("Start button clicked");
                startGame();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        exitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LOGGER.info("Exit button clicked");
                new Thread(() -> {
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException ex) {
                        LOGGER.log(Level.SEVERE, "Exit button thread interrupted", ex);
                    }
                    System.exit(0);
                }).start();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    /**
     * Sets properties for the buttons.
     *
     * @param button        the button to set properties for
     * @param icon          the icon for the button
     * @param selectedIcon  the icon for the button when selected
     */
    private void setButtonProperties(JButton button, ImageIcon icon, ImageIcon selectedIcon) {
        button.setIcon(icon);
        button.setRolloverIcon(selectedIcon);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
    }

    /**
     * Adds buttons to the menu panel.
     */
    private void addButtonsToPanel() {
        menuPanel.add(startButton);
        menuPanel.add(exitButton);
    }

    /**
     * Starts the game when the start button is clicked.
     */
    private void startGame() {
        menuFrame.dispose();
        gameManager.startGame();
    }
}
