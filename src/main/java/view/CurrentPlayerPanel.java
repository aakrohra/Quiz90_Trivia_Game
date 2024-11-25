package view;

import app.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * A JPanel that includes the username of the current player.
 */
class CurrentPlayerPanel extends JPanel {
    CurrentPlayerPanel(JLabel tempUsername) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final JPanel insideBox = new RoundPanel();

        insideBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        final JPanel outsideBox = new JPanel(new FlowLayout(FlowLayout.CENTER));
        outsideBox.setBackground(Constants.BGCOLOUR);
        final JLabel currentPlayer = new JLabel("Current Player: ");
        insideBox.setBackground(Constants.LIGHTERBGCOLOUR);

        insideBox.add(currentPlayer);
        currentPlayer.setBorder(new EmptyBorder(5, 10, 5, 0));

        insideBox.add(tempUsername);
        tempUsername.setBorder(new EmptyBorder(5, 0, 5, 10));
        outsideBox.add(insideBox);
        this.add(outsideBox);
    }
}
