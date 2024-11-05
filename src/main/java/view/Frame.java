package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import static controller.GameManager.*;

/**
 * The {@code Frame} class represents the main frame of the game.
 */
public class Frame {
    private JFrame jframe;

    /**
     * Constructs a new {@code Frame} object with the specified panel.
     *
     * @param panel the panel to be added to the frame
     */
    public Frame(Panel panel) {
        jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(GAME_WIDTH, GAME_HEIGHT);
        jframe.add(panel);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(true);
        jframe.pack();
        jframe.setVisible(true);
        jframe.setTitle("The Last Samurai");

        try {
            InputStream iconStream = getClass().getResourceAsStream("/icon.png");
            Image icon = ImageIO.read(iconStream);
            jframe.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
