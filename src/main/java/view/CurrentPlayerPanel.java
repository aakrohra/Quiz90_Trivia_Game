package view;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that includes the username of the current player.
 */
class CurrentPlayerPanel extends JPanel {
    CurrentPlayerPanel(JLabel tempUsername) {
        this.setLayout(new BorderLayout());

        final JPanel insideBox = new JPanel(new FlowLayout(FlowLayout.CENTER));

        final JLabel currentPlayer = new JLabel("Current Player: ");

        insideBox.add(currentPlayer);
        insideBox.add(tempUsername);
        this.add(insideBox, BorderLayout.CENTER);
    }
}
