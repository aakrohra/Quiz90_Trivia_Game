package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

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

        // This puts the screen in the middle
        setLocationRelativeTo(null);

        setLayout(null);
        setVisible(true);

        // TODO "Question" Label should be replaced with the actual question
        question = new JLabel("Question");
        question.setFont(new Font("Calibri", Font.BOLD, Constants.FONTSIZE));
        question.setBounds(Constants.FRAMEWIDTH / Constants.QUESTIONMARGINDIVISOR,
                Constants.FRAMEHEIGHT / Constants.QUESTIONMARGINDIVISOR,
                Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT / Constants.QUESTIONMARGINDIVISOR);

        // TODO Not entirely sure if there'll always be 4 options, also replace "Options" with answers
        button1 = createButton("Option 1");
        button2 = createButton("Option 2");
        button3 = createButton("Option 3");
        button4 = createButton("Option 4");

        // Center the buttons horizontally with 2 per row
        final int xPos = (Constants.FRAMEWIDTH - Constants.BUTTONWIDTH * 2) / 3;

        // Position for the first row
        final int yPosTop = 200;
        // Position for the second row
        final int yPosBottom = yPosTop + Constants.BUTTONHEIGHT + Constants.BUTTONMARGIN;

        button1.setBounds(xPos, yPosTop, Constants.BUTTONWIDTH, Constants.BUTTONHEIGHT);
        button2.setBounds(xPos + Constants.BUTTONWIDTH + Constants.BUTTONMARGIN, yPosTop,
                Constants.BUTTONWIDTH, Constants.BUTTONHEIGHT);
        button3.setBounds(xPos, yPosBottom, Constants.BUTTONWIDTH, Constants.BUTTONHEIGHT);
        button4.setBounds(xPos + Constants.BUTTONWIDTH + Constants.BUTTONMARGIN, yPosBottom,
                Constants.BUTTONWIDTH, Constants.BUTTONHEIGHT);

        // Add ActionListeners to buttons
        button1.addActionListener(evt -> System.out.println("Button 1 pressed"));
        button2.addActionListener(evt -> System.out.println("Button 2 pressed"));
        button3.addActionListener(evt -> System.out.println("Button 3 pressed"));
        button4.addActionListener(evt -> System.out.println("Button 4 pressed"));

        // Add everything to the frame
        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(question);
    }

    private JButton createButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Calibri", Font.BOLD, Constants.BUTTONFONTSIZE));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        return button;
    }

    public static void main(String[] args) {
        new QuestionView();
    }

}
