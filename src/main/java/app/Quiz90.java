package app;

import javax.swing.*;
import java.awt.*;

public class Quiz90 extends JFrame {

    int frameHeight = 800;
    int frameWidth = 1000;
    int fontSize = frameHeight/20;

    JLabel question;
    JButton button1, button2, button3, button4;

    public Quiz90() {
        setTitle("Quiz90");
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);  // This puts the screen in the middle
        setLayout(null);
        setVisible(true);

        // TODO "Question" Label should be replaced with the actual question
        question = new JLabel("Question");
        question.setFont(new Font("Calibri", Font.BOLD, fontSize));
        question.setBounds(frameWidth/20, frameHeight/20, frameWidth, 50);

        // TODO Not entirely sure if there'll always be 4 options, also replace "Options" with answers
        button1 = createButton("Option 1");
        button2 = createButton("Option 2");
        button3 = createButton("Option 3");
        button4 = createButton("Option 4");

        int buttonWidth = 400;
        int buttonHeight = 100;
        int buttonMargin = 20;
        int xPos = (frameWidth - buttonWidth * 2) / 3; // Center the buttons horizontally with 2 per row
        int yPosTop = 200; // Position for the first row
        int yPosBottom = yPosTop + buttonHeight + buttonMargin; // Position for the second row

        button1.setBounds(xPos, yPosTop, buttonWidth, buttonHeight);
        button2.setBounds(xPos + buttonWidth + buttonMargin, yPosTop, buttonWidth, buttonHeight);
        button3.setBounds(xPos, yPosBottom, buttonWidth, buttonHeight);
        button4.setBounds(xPos + buttonWidth + buttonMargin, yPosBottom, buttonWidth, buttonHeight);

        // Add everything to the frame

        add(button1);
        add(button2);
        add(button3);
        add(button4);

        add(question);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Calibri", Font.BOLD, fontSize * 2 / 3));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        return button;
    }

    public static void main(String[] args) {
        new Quiz90();
    }
}
