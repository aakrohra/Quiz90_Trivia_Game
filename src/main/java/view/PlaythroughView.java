package view;

import app.Constants;
import entity.Question;
import entity.Quiz;
import interface_adapter.playthrough.PlaythroughState;
import interface_adapter.playthrough.PlaythroughViewModel;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

/**
 * The PlaythroughView class creates a simple quiz interface extending JPanel.
 * It includes a question label and four answer buttons.
 */
public class PlaythroughView extends JPanel implements PropertyChangeListener {

    private static final int OPTION_ONE_INDEX = 0;
    private static final int OPTION_TWO_INDEX = 1;
    private static final int OPTION_THREE_INDEX = 2;
    private static final int OPTION_FOUR_INDEX = 3;

    private final String viewName = "playthrough";
    private final PlaythroughViewModel playthroughViewModel;

    private final JLabel question;
    private final JButton button1;
    private final JButton button2;
    private final JButton button3;
    private final JButton button4;
    private final JButton nextButton;

    private Map<Integer, Pair<String, Boolean>> playerInfo = new HashMap<>();

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
        nextButton.setOpaque(false);

        nextButton.addActionListener(evt -> handleNextClick());

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
        final PlaythroughState state = this.playthroughViewModel.getState();

        // Disable all buttons after one of them has been clicked
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);

        if (state.getCurrentQuestion().getCorrectAnswer().equals(selectedButton.getText())) {
            selectedButton.setBackground(Color.GREEN);
            playerInfo.put(state.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), true));
        }
        else {
            selectedButton.setBackground(Color.RED);
            playerInfo.put(state.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), false));
        }



        // Change the border color to blue
        final Border blueBorder = BorderFactory.createLineBorder(new Color(79, 165, 226), 5);
        selectedButton.setBorder(blueBorder);

        nextButton.setOpaque(true);

        // Print out the selected button
        System.out.println("Selected: " + selectedButton.getText());
    }

    /**
     * Handles clicking next button to go to next question.
     */
    private void handleNextClick() {
        final PlaythroughState state = this.playthroughViewModel.getState();
        nextButton.setOpaque(false);
        if (state.getCurrentQuestionIndex() == state.getQuiz().getListOfQuestions().length()) {
            System.out.println("done");
            // this is where you would call a controller for a summary use case and pass in the updated map of data
        }
        else {
            state.setCurrentQuestionIndex(state.getCurrentQuestionIndex() + 1);
            this.playthroughViewModel.firePropertyChanged();
        }
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

    /**
     * Property change handler.
     * Prints a message when the state changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final PlaythroughState state = (PlaythroughState) evt.getNewValue();
        final Quiz quiz = state.getCurrentQuiz();
        final Question question1 = quiz.getQuestions().get(state.getCurrentQuestionIndex());

        question.setText(question1.getQuestionText());

        final List<String> options = new ArrayList<>();
        options.add(question1.getCorrectAnswer());
        options.add(question1.getIncorrectAnswers().get(0));
        options.add(question1.getIncorrectAnswers().get(1));
        options.add(question1.getIncorrectAnswers().get(2));

        Collections.shuffle(options);
        button1.setText(options.get(OPTION_ONE_INDEX));
        button2.setText(options.get(OPTION_TWO_INDEX));
        button3.setText(options.get(OPTION_THREE_INDEX));
        button4.setText(options.get(OPTION_FOUR_INDEX));

        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button1.setBackground(Color.WHITE);
        button2.setBackground(Color.WHITE);
        button3.setBackground(Color.WHITE);
        button4.setBackground(Color.WHITE);
        button1.setBorder(BorderFactory.createEmptyBorder());
        button2.setBorder(BorderFactory.createEmptyBorder());
        button3.setBorder(BorderFactory.createEmptyBorder());
        button4.setBorder(BorderFactory.createEmptyBorder());
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
