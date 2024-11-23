package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.Border;
import app.Constants;

/**
 * The {@code QuestionView} class creates a simple quiz interface extending {@link JFrame}.
 * It includes a question label and four answer buttons.
 */
public class QuestionView extends JFrame {

    private JLabel question;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;

    public QuestionView() {
        setTitle("Quiz90");
        setSize(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT);

        // Center the screen
        setLocationRelativeTo(null);

        setLayout(null);
        setVisible(true);

        // Question label
        question = new JLabel("Question");
        question.setFont(new Font("Tahoma", Font.BOLD, Constants.QUESTIONFONTSIZE));
        question.setBounds(
                Constants.FRAMEWIDTH / Constants.QUESTIONMARGINDIVISOR,
                Constants.FRAMEHEIGHT / Constants.QUESTIONMARGINDIVISOR,
                Constants.FRAMEWIDTH,
                Constants.FRAMEHEIGHT / Constants.QUESTIONMARGINDIVISOR
        );

        // Create answer buttons
        button1 = createButton("Option 1");
        button2 = createButton("Option 2");
        button3 = createButton("Option 3");
        button4 = createButton("Option 4");

        // Button margins
        final int horizontalSpacing = Constants.BUTTONMARGIN;
        final int verticalSpacing = Constants.BUTTONMARGIN;
        final int gap = (Constants.FRAMEWIDTH - 2 * Constants.BUTTONWIDTH - horizontalSpacing) / 2;

        // First row vertical position
        final int firstRowY = Constants.FRAMEHEIGHT / 3;
        final int secondRowY = firstRowY + Constants.BUTTONHEIGHT + verticalSpacing;

        // Set bounds for each button
        button1.setBounds(gap, firstRowY, Constants.BUTTONWIDTH, Constants.BUTTONHEIGHT);
        button2.setBounds(gap + horizontalSpacing + Constants.BUTTONWIDTH, firstRowY,
                Constants.BUTTONWIDTH, Constants.BUTTONHEIGHT);
        button3.setBounds(gap, secondRowY, Constants.BUTTONWIDTH, Constants.BUTTONHEIGHT);
        button4.setBounds(gap + horizontalSpacing + Constants.BUTTONWIDTH, secondRowY,
                Constants.BUTTONWIDTH, Constants.BUTTONHEIGHT);

        // Add ActionListeners to buttons
        button1.addActionListener(evt -> handleButtonClick(button1));
        button2.addActionListener(evt -> handleButtonClick(button2));
        button3.addActionListener(evt -> handleButtonClick(button3));
        button4.addActionListener(evt -> handleButtonClick(button4));

        // Add components to the frame
        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(question);
    }

    /**
     * Handles the button click event. Disables all buttons and changes the color
     * of the selected button.
     * @param selectedButton the button that was clicked
     */
    private void handleButtonClick(JButton selectedButton) {
        // Disable all buttons after one of them has been clicked
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);

        // Change the background color of the selected button
        selectedButton.setBackground(Color.GREEN);

        // Change the border color to blue
        final Border blueBorder = BorderFactory.createLineBorder(new Color(79, 165, 226), 5);
        selectedButton.setBorder(blueBorder);

        // Print out the selected button
        System.out.println("Selected: " + selectedButton.getText());
    }

    /**
     * Creates a button with default styles.
     *
     * @param text the text to display on the button
     * @return a styled {@link JButton}
     */
    private JButton createButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, Constants.BUTTONFONTSIZE));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        return button;
    }

    public static void main(String[] args) {
        new QuestionView();
    }
}
