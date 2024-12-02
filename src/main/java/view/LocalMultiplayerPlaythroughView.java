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

    private final JLabel currentPlayer;
    private final JTextPane question;
    private final JButton button1;
    private final JButton button2;
    private final JButton button3;
    private final JButton button4;
    private final JButton nextButton;

    private final String currentPlayerOneText = "Player One's Turn!";
    private final String currentPlayerTwoText = "Player Two's Turn!";

    private Boolean stealTurn = false;
    private final Map<Integer, Pair<String, Boolean>> playerOneInfo = new HashMap<>();
    private final Integer[] numMapCorrect = {0, 0};
    private final Map<Integer, Pair<String, Boolean>> playerTwoInfo = new HashMap<>();

    public LocalMultiplayerPlaythroughView(LocalMultiplayerPlaythroughViewModel localMultiplayerPlaythroughViewModel) {
        this.localMultiplayerPlaythroughViewModel = localMultiplayerPlaythroughViewModel;
        this.localMultiplayerPlaythroughViewModel.addPropertyChangeListener(this);

        this.setLayout(new GridBagLayout());
        this.setBackground(Constants.BGCOLOUR);
        final GridBagConstraints gbc = createGbc();

        currentPlayer = new JLabel("temp");
        final CurrentPlayerPanel currentPlayerPanel = new CurrentPlayerPanel(currentPlayer);

        final JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        question = questionTextPaneHelper(questionPanel);

        // Create answer buttons
        final ButtonPanel buttonRow1 = new ButtonPanel();
        button1 = createButton("Option 1");
        button2 = createButton("Option 2");

        final ButtonPanel buttonRow2 = new ButtonPanel();
        button3 = createButton("Option 3");
        button4 = createButton("Option 4");

        assemble2Buttons(buttonRow1, button1, button2);
        assemble2Buttons(buttonRow2, button3, button4);

        nextButton = new JButton("Next");
        nextButton.setVisible(false);

        // Add ActionListeners to buttons
        addActionListeners();

        // Add components to the panel
        this.addComp(questionPanel, 0, 0, Constants.THREE, gbc);
        this.addComp(currentPlayerPanel, 1, 1, 2, gbc);
        this.addComp(buttonRow1, 1, 2, 2, gbc);
        this.addComp(buttonRow2, 1, Constants.THREE, 2, gbc);
        this.addComp(nextButton, 2, Constants.FOUR, Constants.THREE, gbc);
    }

    private void addActionListeners() {
        buttonActionListeners();
        nextButtonActionListener();
    }

    private void nextButtonActionListener() {
        nextButton.addActionListener(evt -> handleNextClick());
    }

    private void buttonActionListeners() {
        button1.addActionListener(evt -> handleButtonClick(button1));
        button2.addActionListener(evt -> handleButtonClick(button2));
        button3.addActionListener(evt -> handleButtonClick(button3));
        button4.addActionListener(evt -> handleButtonClick(button4));
    }

    @NotNull
    private JTextPane questionTextPaneHelper(JPanel questionPanel) {
        final JTextPane questionTemp;
        questionTemp = new JTextPane();
        questionTemp.setEditable(false);
        questionTemp.setFocusable(false);
        questionTemp.setFont(new Font(Constants.FONTSTYLE, Font.BOLD, Constants.BUTTONFONTSIZE));
        questionTemp.setBackground(Constants.LIGHTERBGCOLOUR);
        questionTemp.setText("placeholder text");
        questionTemp.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        questionPanel.add(questionTemp);
        return questionTemp;
    }

    private GridBagConstraints createGbc() {
        final GridBagConstraints gbc = new GridBagConstraints();
        final int magicTen = 10;
        gbc.insets = new Insets(magicTen, magicTen, magicTen, magicTen);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void addComp(Component comp, int xAxis, int yAxis, int width, GridBagConstraints gbc) {
        gbc.gridx = xAxis;
        gbc.gridy = yAxis;
        gbc.gridwidth = width;
        gbc.anchor = GridBagConstraints.CENTER;
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
        return Box.createHorizontalStrut(Constants.STRUTSMALLSPACER * Constants.THREE);
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
            disableAllButtons(false);
            correctSelectedButton(selectedButton, Color.GREEN);
            if (stealTurn) {
                updateCorrectStolenAnswer(selectedButton, state);
            }
            else {
                updateCorrectAnswer(selectedButton, state);
            }
        }

        // incorrect answer
        else {

            // if wrong answer on stolen turn
            if (stealTurn) {
                disableAllButtons(false);
                showCorrectButton(state);
                correctSelectedButton(selectedButton, Color.RED);
                updateIncorrectAnswer(!state.getCurrentPlayerIsOne(), state, selectedButton);
                stealTurn = false;
            }

            // wrong answer on regular turn
            else {
                selectedButton.setEnabled(false);
                incorrectSelectedButton(selectedButton, Color.RED);
                updateIncorrectAnswer(state.getCurrentPlayerIsOne(), state, selectedButton);
                showNext = false;
                stealTurn = true;
                updateCurrentPlayerText();
            }
        }

        showNextButton(showNext);

    }

    private void showNextButton(boolean showNext) {
        if (showNext) {
            nextButton.setVisible(true);
        }
    }

    private void updateCurrentPlayerText() {
        if (currentPlayer.getText().equals(currentPlayerOneText)) {
            currentPlayer.setText(currentPlayerTwoText);
        }
        else {
            currentPlayer.setText(currentPlayerOneText);
        }
    }

    private static void incorrectSelectedButton(JButton selectedButton, Color red) {
        final Border blueBorder = BorderFactory.createLineBorder(new Color(79, 165, 226), 5);
        selectedButton.setBorder(blueBorder);
        selectedButton.setBackground(red);
    }

    private void updateIncorrectAnswer(boolean state, LocalMultiplayerPlaythroughState state1, JButton selectedButton) {
        if (state) {
            playerOneInfo.put(state1.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), false));
        }
        else {
            playerTwoInfo.put(state1.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), false));
        }
    }

    private void showCorrectButton(LocalMultiplayerPlaythroughState state) {
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
    }

    private void updateCorrectAnswer(JButton selectedButton, LocalMultiplayerPlaythroughState state) {
        if (state.getCurrentPlayerIsOne()) {
            playerOneInfo.put(state.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), true));
            numMapCorrect[0] += 1;
        }
        else {
            playerTwoInfo.put(state.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), true));
            numMapCorrect[1] += 1;
        }
    }

    private void updateCorrectStolenAnswer(JButton selectedButton, LocalMultiplayerPlaythroughState state) {
        if (state.getCurrentPlayerIsOne()) {
            playerTwoInfo.put(state.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), true));
            numMapCorrect[1] += 1;
        }
        else {
            playerOneInfo.put(state.getCurrentQuestionIndex(), new Pair<>(selectedButton.getText(), true));
            numMapCorrect[0] += 1;
        }
    }

    private static void correctSelectedButton(JButton selectedButton, Color green) {
        incorrectSelectedButton(selectedButton, green);
    }

    private void disableAllButtons(boolean temporary) {
        button1.setEnabled(temporary);
        button2.setEnabled(temporary);
        button3.setEnabled(temporary);
        button4.setEnabled(temporary);
    }

    /**
     * Handles clicking next button to go to next question.
     */
    private void handleNextClick() {
        final LocalMultiplayerPlaythroughState state = this.localMultiplayerPlaythroughViewModel.getState();
        nextButton.setVisible(false);

        // finished last question
        if (state.getCurrentQuestionIndex() == state.getQuiz().getQuestions().size() - 1) {

            System.out.println(playerOneInfo);
            System.out.println(playerTwoInfo);
            System.out.println(numMapCorrect);

            localMultiplayerController.prepareLocalMultiplayerSummaryView(state.getQuiz(),
                    playerOneInfo, playerTwoInfo, numMapCorrect);
            state.setCurrentPlayerIsOne(true);
            state.setCurrentQuestionIndex(0);
            stealTurn = false;
            playerOneInfo.clear();
            playerTwoInfo.clear();
            numMapCorrect[0] = 0;
            numMapCorrect[1] = 0;
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

        if (state.getCurrentPlayerIsOne()) {
            currentPlayer.setText(currentPlayerOneText);
        }
        else {
            currentPlayer.setText(currentPlayerTwoText);
        }

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

        disableAllButtons(true);
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
