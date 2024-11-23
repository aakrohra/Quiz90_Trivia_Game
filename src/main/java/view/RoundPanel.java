package view;

import app.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel with rounded edges.
 */
class RoundPanel extends JPanel {
    RoundPanel() {
        this.setOpaque(false);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // Enable anti-aliasing for smoother edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw a rounded rectangle as the background
        g2.setColor(getBackground()); // Use the panel's background color
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), Constants.RANDOMKEYSIZE, Constants.RANDOMKEYSIZE);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable anti-aliasing for smoother edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the border of the rounded rectangle
        g2.setColor(getBackground()); // Use the panel's foreground color for the border
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, Constants.RANDOMKEYSIZE,
                Constants.RANDOMKEYSIZE);

        g2.dispose();
    }

}
