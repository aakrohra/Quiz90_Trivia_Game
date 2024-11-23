package view;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import app.Constants;

/**
 * JPanel used for containing JButtons.
 */
class ButtonPanel extends JPanel {
    ButtonPanel() {
        this.setBackground(Constants.BGCOLOUR);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }
}
