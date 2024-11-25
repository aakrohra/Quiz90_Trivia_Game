package view;

import app.Constants;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.*;

/**
 * JPanel used for containing JButtons.
 */
class ButtonPanel extends JPanel {
    ButtonPanel() {
        this.setBackground(Constants.BGCOLOUR);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }
}
