package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.Border;

import org.jetbrains.annotations.NotNull;

import app.Constants;
import entity.Question;
import entity.Quiz;
import interface_adapter.local_multiplayer.LocalMultiplayerController;
import interface_adapter.local_multiplayer_playthrough.LocalMultiplayerPlaythroughState;
import interface_adapter.local_multiplayer_playthrough.LocalMultiplayerPlaythroughViewModel;
import kotlin.Pair;

/**
 * The PlaythroughView class creates a simple quiz interface extending JPanel.
 * It includes a question label and four answer buttons.
 */
public class LocalMultiplayerPlaythroughView extends JPanel implements PropertyChangeListener {

    private static final int OPTION_ONE_INDEX = 0;
    private static final int OPTION_TWO_INDEX = 1;
    private static final int OPTION_THREE_INDEX = 2;
    private static final int OPTION_FOUR_INDEX = 3;

    private final LocalMultiplayerPlaythroughViewModel localMultiplayerPlaythroughViewModel;
    private LocalMultiplayerController localMultiplayerController;

    private final JTextPane question;
    private final JButton button1;
    private final JButton button2;
    private final JButton button3;
    private final JButton button4;
    private final JButton nextButton;

    private Boolean stealTurn = false;
    private JLabel currentPlayer;
    private Map<Integer, Pair<String, Boolean>> playerOneInfo = new HashMap<>();
    private Integer[] numMapCorrect = {0, 0};
    private Map<Integer, Pair<String, Boolean>> playerTwoInfo = new HashMap<>();

    public LocalMultiplayerPlaythroughView(LocalMultiplayerPlaythroughViewModel localMultiplayerPlaythroughViewModel) {
        this.localMultiplayerPlaythroughViewModel = localMultiplayerPlaythroughViewModel;
        this.localMultiplayerPlaythroughViewModel.addPropertyChangeListener(this);

        this.setLayout(new GridBagLayout());
        currentPlayer = new JLabel("temp");
        this.add(currentPlayer);
        final GridBagConstraints gbc = createGbc();

        GridBagConstraints tempGbc = new GridBagConstraints();
        tempGbc.gridx = 0;
        tempGbc.gridy = 0;
        tempGbc.weightx = 1.0;  // Allow horizontal growth
        tempGbc.weighty = 1.0;  // Center vertically
        tempGbc.anchor = GridBagConstraints.CENTER;  // Center the component
        tempGbc.fill = GridBagConstraints.NONE;  // Don't stretch
        tempGbc.insets = new Insets(10, 10, 10, 10);

        // Create the JTextField or JTextPane
        question = new JTextPane();
        question.setEditable(false);
        question.setFocusable(false);
        question.setFont(new Font(Constants.FONTSTYLE, Font.BOLD, Constants.BUTTONFONTSIZE));

        // TODO: Should this be removed?
        question.setBackground(Constants.LIGHTERBGCOLOUR);
        question.setText("placeholder text");
        question.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

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
        final LocalMultiplayerPlaythroughState state = this.localMultiplayerPlaythroughViewModel.getState();

        boolean showNext = true;
        // correct answer
        if (state.getCurrentQuestion().getCorrectAnswer().equals(selectedButton.getText())) {
            // disable all buttons
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);

            // update state and playerInfo
            selectedButton.setBackground(Color.GREEN);
//            state.updateNumberOfCorrectAnswers();  // TODO implement
            if (state.getCurrentPlayerIsOne()) {
                playerOneInfo.put(state.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), true));
                numMapCorrect[0] += 1;
            }
            else {
                playerTwoInfo.put(state.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), true));
                numMapCorrect[1] += 1;
            }
            // Change the border color of selected button to blue
            final Border blueBorder = BorderFactory.createLineBorder(new Color(79, 165, 226), 5);
            selectedButton.setBorder(blueBorder);

            // shows next button
            showNext = true;
        }

        // incorrect answer
        else {
            // if wrong answer on stolen turn
            if (stealTurn) {
                // disable all buttons
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);

                // show correct button
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

                // save who got it wrong
                if (!state.getCurrentPlayerIsOne()) {
                    playerOneInfo.put(state.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), false));
                }
                else {
                    playerTwoInfo.put(state.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), false));
                }
                // Change the border color of selected button to blue
                final Border blueBorder = BorderFactory.createLineBorder(new Color(79, 165, 226), 5);
                selectedButton.setBorder(blueBorder);
                selectedButton.setBackground(Color.RED);

                // stealTurn is false
                stealTurn = false;

                // show next button
                showNext = true;
            }

            // wrong answer on regular turn
            else {
                // disable the selected button
                selectedButton.setBackground(Color.RED);
                selectedButton.setEnabled(false);
                // TODO enable "steal available" text

                // Change the border color of selected button to blue
                final Border blueBorder = BorderFactory.createLineBorder(new Color(79, 165, 226), 5);
                selectedButton.setBorder(blueBorder);

                // set + update player that got wrong answer
                if (state.getCurrentPlayerIsOne()) {
                    playerOneInfo.put(state.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), false));
                }
                else {
                    playerTwoInfo.put(state.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), false));
                }
                showNext = false;
                stealTurn = true;
                if (currentPlayer.getText().equals("true")) {
                    currentPlayer.setText("false");
                }
                else {
                    currentPlayer.setText("true");
                }
            }
        }

        if (showNext) {
            nextButton.setVisible(true);
        }

    }

    /**
     * Handles clicking next button to go to next question.
     */
    private void handleNextClick() {
        final LocalMultiplayerPlaythroughState state = this.localMultiplayerPlaythroughViewModel.getState();
        nextButton.setVisible(false);

        // finished last question
        if (state.getCurrentQuestionIndex() == state.getQuiz().getQuestions().size() - 1) {
            localMultiplayerController.prepareLocalMultiplayerSummaryView(state.getQuiz(),
                    playerOneInfo, playerTwoInfo, numMapCorrect);
        }

        // continue to next question
        else {
            state.setCurrentQuestionIndex(state.getCurrentQuestionIndex() + 1);
            state.setCurrentPlayerIsOne(!state.getCurrentPlayerIsOne());
            this.localMultiplayerPlaythroughViewModel.firePropertyChanged();
            stealTurn = false;
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
        button.setMaximumSize(new Dimension(10000, 1000));
        return button;
    }

    public String getViewName() {
        return "local multiplayer playthrough";
    }

    /**
     * Property change handler.
     * Prints a message when the state changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LocalMultiplayerPlaythroughState state = (LocalMultiplayerPlaythroughState) evt.getNewValue();

        //TODO do this properly
        //update current player in the box
        currentPlayer.setText(state.getCurrentPlayerIsOne().toString());

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

    public void setLocalMultiplayerController(LocalMultiplayerController localMultiplayerController) {
        this.localMultiplayerController = localMultiplayerController;
    }
}
