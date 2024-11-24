package view;

import app.Constants;
import entity.Question;
import entity.Quiz;
import interface_adapter.playthrough.PlaythroughState;
import interface_adapter.playthrough.PlaythroughViewModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The PlaythroughView class creates a simple quiz interface extending JPanel.
 * It includes a question label and four answer buttons.
 */
public class PlaythroughView extends JPanel implements PropertyChangeListener {

    private final String viewName = "playthrough";
    private final PlaythroughViewModel playthroughViewModel;

    private JLabel question;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;

    public PlaythroughView(PlaythroughViewModel playthroughViewModel) {
        this.playthroughViewModel = playthroughViewModel;
        this.playthroughViewModel.addPropertyChangeListener(this);

        setLayout(null);

        // Question label
        question = new JLabel("Question");
        question.setFont(new Font(Constants.FONTSTYLE, Font.BOLD, Constants.QUESTIONFONTSIZE));
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

        // Add components to the panel
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
        button.setFont(new Font(Constants.FONTSTYLE, Font.BOLD, Constants.BUTTONFONTSIZE));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        return button;
    }

    public String getViewName() {
        return viewName;
    }

    // TODO: fully implement propertyChange (the code right now is temporary showing the state change worked)
    /**
     * Property change handler.
     * Prints a message when the state changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final PlaythroughState state = (PlaythroughState) evt.getNewValue();
        final Quiz quiz = state.getCurrentQuiz();
        final Question question1 = quiz.getQuestions().get(0);

        question.setText(question1.getQuestionText());
        button1.setText(question1.getCorrectAnswer());
        button2.setText(question1.getIncorrectAnswers().get(0));
        button3.setText(question1.getIncorrectAnswers().get(1));
        button4.setText(question1.getIncorrectAnswers().get(2));
    }

    // TODO: Whoever makes playthroughController implement this
    public void setPlaythroughController(){

    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Quiz90");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new PlaythroughView(new PlaythroughViewModel()));
        frame.setVisible(true);
    }
}
