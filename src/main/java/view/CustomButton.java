package view;

import java.awt.*;

import javax.swing.*;

import app.Constants;

/**
 * A custom JButton used in the program.
 */
class CustomButton extends JButton {
    CustomButton(String text) {
        this.setMaximumSize(new Dimension(Constants.BUTTON1WIDTH, Constants.BUTTON1HEIGHT));
        this.setPreferredSize(new Dimension(Constants.BUTTON1WIDTH, Constants.BUTTON1HEIGHT));
        this.setText(text);
    }
}
