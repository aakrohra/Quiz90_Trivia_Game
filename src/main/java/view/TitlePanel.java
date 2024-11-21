package view;

import app.Constants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A panel containing a panel which contains the title specified.
 */
class TitlePanel extends JPanel {
    TitlePanel(String title) {
        final JPanel nestedPanel = new JPanel(new GridBagLayout());
        nestedPanel.setBackground(Color.WHITE);
        nestedPanel.setPreferredSize(new Dimension(Constants.BOXX, Constants.BOXY));

        final JLabel labelTitle = new JLabel(title);
        labelTitle.setFont(new Font(labelTitle.getFont().getName(), Font.BOLD, Constants.FONTSIZE));
        labelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelTitle.setAlignmentY(Component.CENTER_ALIGNMENT);

        nestedPanel.add(labelTitle, new GridBagConstraints());
        this.add(nestedPanel);
        this.setBackground(Constants.BGCOLOUR);
    }
}
