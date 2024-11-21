package view;

import app.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * A panel containing a panel which contains the title specified.
 */
class TitlePanel extends JPanel {
    TitlePanel(String title) {
        final JPanel nestedPanel = new JPanel(new GridBagLayout());
        nestedPanel.setBackground(Color.WHITE);
        nestedPanel.setPreferredSize(new Dimension(Constants.BOXX, Constants.BOXY));

        final JLabel labelTitle = new JLabel(title);
        labelTitle.setFont(new Font(labelTitle.getFont().getName(), Font.BOLD, Constants.TITLESIZE));
        labelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelTitle.setAlignmentY(Component.CENTER_ALIGNMENT);

        nestedPanel.add(labelTitle, new GridBagConstraints());
        this.add(nestedPanel);
        this.setBackground(Color.GRAY);
    }
}
