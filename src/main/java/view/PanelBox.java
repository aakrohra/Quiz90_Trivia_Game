package view;

import java.awt.*;

import javax.swing.*;

/**
 * A panel containing a box.
 */
class PanelBox extends JPanel {
    PanelBox(JPanel panel, Box box) {
        panel.setBackground(new Color(0, 71, 171));
        box.setOpaque(true);
        box.setBackground(Color.WHITE);
        box.setMaximumSize(new Dimension(400, 100));
        panel.add(box);
    }
}
