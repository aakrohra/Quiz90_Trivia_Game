package view;

import app.Constants;
import entity.Question;
import entity.Quiz;
import interface_adapter.local_multiplayer.LocalMultiplayerController;
import interface_adapter.playthrough.PlaythroughState;
import interface_adapter.playthrough.PlaythroughViewModel;
import interface_adapter.summary.SummaryController;
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

    private final PlaythroughViewModel playthroughViewModel;
    private SummaryController summaryController;

    private final JTextArea question;
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
        final GridBagConstraints gbc = createGbc();

        final GridBagConstraints tempGbc = new GridBagConstraints();
        tempGbc.gridx = 0;
        tempGbc.gridy = 0;
        tempGbc.weightx = 1.0;
        tempGbc.weighty = 1.0;
        tempGbc.anchor = GridBagConstraints.CENTER;
        tempGbc.fill = GridBagConstraints.NONE;
        tempGbc.insets = new Insets(Constants.MARGINS, Constants.MARGINS, Constants.MARGINS, Constants.MARGINS);

        // Create the JTextField or JTextPane
        question = new JTextArea();
        question.setEditable(false);
        question.setFocusable(false);
        question.setFont(new Font(Constants.FONTSTYLE, Font.BOLD, Constants.BUTTONFONTSIZE));

        question.setBackground(Constants.LIGHTERBGCOLOUR);
        question.setText("Question");
        question.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        question.setLineWrap(true);
        question.setWrapStyleWord(true);

        // Wrap in a JPanel with BoxLayout for better size enforcement
        final JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.add(question);

        this.setBackground(Constants.BGCOLOUR);

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
        nextButton.setVisible(false);

        nextButton.addActionListener(evt -> handleNextClick());

        // Add components to the panel
        this.addComp(questionPanel, 0, 0, 3, GridBagConstraints.CENTER, gbc);
        this.addComp(buttonRow1, 1, 1, 2, GridBagConstraints.CENTER, gbc);
        this.addComp(buttonRow2, 1, 2, 2, GridBagConstraints.CENTER, gbc);
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

    private void assemble2Buttons(JPanel buttonPanel, JButton firstButton, JButton secondButton) {
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(firstButton);
        buttonPanel.add(horizontalSpacer());
        buttonPanel.add(secondButton);
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
            state.updateNumberOfCorrectAnswers();
            playerInfo.put(state.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), true));
        }
        else {
            selectedButton.setBackground(Constants.INCORRECTREDBG);

            if (button1.getText().equals(state.getCurrentQuestion().getCorrectAnswer())) {
                button1.setBackground(Color.GREEN);
            }
            else if (button2.getText().equals(state.getCurrentQuestion().getCorrectAnswer())) {
                button2.setBackground(Color.GREEN);
            }
            else if (button3.getText().equals(state.getCurrentQuestion().getCorrectAnswer())) {
                button3.setBackground(Color.GREEN);
            }
            else if (button4.getText().equals(state.getCurrentQuestion().getCorrectAnswer())) {
                button4.setBackground(Color.GREEN);
            }
            playerInfo.put(state.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), false));
        }

        System.out.println(playerInfo.toString());

        // Change the border color to blue
        final Border blueBorder = BorderFactory.createLineBorder(new Color(79, 165, 226), 5);
        selectedButton.setBorder(blueBorder);

        nextButton.setVisible(true);

        // Print out the selected button
        System.out.println("Selected: " + selectedButton.getText());
    }

    /**
     * Handles clicking next button to go to next question.
     */
    private void handleNextClick() {
        final PlaythroughState state = this.playthroughViewModel.getState();
        nextButton.setVisible(false);
        if (state.getCurrentQuestionIndex() == state.getQuiz().getQuestions().size() - 1) {
            summaryController.execute(state.getQuiz(), state.getNumberOfCorrectAnswers(), playerInfo);
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
        button.setMaximumSize(new Dimension(10000, 1000));
        return button;
    }

    public String getViewName() {
        return "playthrough";
    }

    public void setSummaryController(SummaryController summaryController) {
        this.summaryController = summaryController;
    }

    /**
     * Property change handler.
     * Prints a message when the state changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final PlaythroughState state = (PlaythroughState) evt.getNewValue();
        final Quiz quiz = state.getQuiz();
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
