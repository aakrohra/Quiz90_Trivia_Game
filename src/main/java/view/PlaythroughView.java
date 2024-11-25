package view;

import app.Constants;
import entity.Question;
import entity.Quiz;
import interface_adapter.playthrough.PlaythroughState;
import interface_adapter.playthrough.PlaythroughViewModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The PlaythroughView class creates a simple quiz interface extending JPanel.
 * It includes a question label and four answer buttons.
 */
public class PlaythroughView extends JPanel implements PropertyChangeListener {

    private final String viewName = "playthrough";
    private final PlaythroughViewModel playthroughViewModel;

    private final JLabel question;
    private final JButton button1;
    private final JButton button2;
    private final JButton button3;
    private final JButton button4;
    private final JButton nextButton;

    public PlaythroughView(PlaythroughViewModel playthroughViewModel) {
        this.playthroughViewModel = playthroughViewModel;
        this.playthroughViewModel.addPropertyChangeListener(this);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = createGbc();

        // Question label
        question = new JLabel("");
        question.setFont(new Font(Constants.FONTSTYLE, Font.BOLD, Constants.QUESTIONFONTSIZE));
        question.setBounds(
                Constants.FRAMEWIDTH / Constants.QUESTIONMARGINDIVISOR,
                Constants.FRAMEHEIGHT / Constants.QUESTIONMARGINDIVISOR,
                Constants.FRAMEWIDTH,
                Constants.FRAMEHEIGHT / Constants.QUESTIONMARGINDIVISOR
        );

        final ButtonPanel buttonRow1 = new ButtonPanel();

        final ButtonPanel buttonRow2 = new ButtonPanel();

        // Create answer buttons
        button1 = createButton("Option 1");
        button2 = createButton("Option 2");
        button3 = createButton("Option 3");
        button4 = createButton("Option 4");

        assemble2Buttons(buttonRow1, button1, button2);
        assemble2Buttons(buttonRow2, button3, button4);

        // Add ActionListeners to buttons
        button1.addActionListener(evt -> handleButtonClick(button1));
        button2.addActionListener(evt -> handleButtonClick(button2));
        button3.addActionListener(evt -> handleButtonClick(button3));
        button4.addActionListener(evt -> handleButtonClick(button4));

        nextButton = new JButton("Next");

        // Add components to the panel
        this.addComp(question, 0, 0, 5, GridBagConstraints.CENTER, gbc);
        this.addComp(buttonRow1, 0, 1, 2, GridBagConstraints.CENTER, gbc);
        this.addComp(buttonRow2, 0, 2, 2, GridBagConstraints.CENTER, gbc);
        this.addComp(nextButton, 2, 3, 3, GridBagConstraints.CENTER, gbc);

    }

    private GridBagConstraints createGbc() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void addComp(Component comp, int x_axis, int y_axis, int width, int anchor, GridBagConstraints gbc) {
        gbc.gridx = x_axis;
        gbc.gridy = y_axis;
        gbc.gridwidth = width;
        gbc.anchor = anchor;
        this.add(comp, gbc);
    }

    private void assemble2Buttons(JPanel buttonPanel, JButton button1, JButton button2) {
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(button1);
        buttonPanel.add(horizontalSpacer());
        buttonPanel.add(button2);
        buttonPanel.add(Box.createHorizontalGlue());
    }

    @NotNull
    private static Component horizontalSpacer() {
        return Box.createHorizontalStrut(Constants.STRUTSMALLSPACER * 3);
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
        button.setSize(new Dimension(10000, 1000));
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
