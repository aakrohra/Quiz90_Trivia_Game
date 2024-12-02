package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import org.jetbrains.annotations.NotNull;

import app.Constants;
import entity.Question;
import interface_adapter.local_multiplayer.LocalMultiplayerController;
import interface_adapter.local_multiplayer_summary.LocalMultiplayerSummaryState;
import interface_adapter.local_multiplayer_summary.LocalMultiplayerSummaryViewModel;
import kotlin.Pair;

/**
 * The view for the Summary screen, displaying the quiz results in detail.
 */
public class LocalMultiplayerSummaryView extends JPanel implements PropertyChangeListener {

    private LocalMultiplayerController localMultiplayerController;

    private final JLabel resultText;
    private final JLabel playerOneResultText;
    private final JLabel playerTwoResultText;
    private final JButton returnButton;
    private final JPanel questionsPanel;

    public LocalMultiplayerSummaryView(LocalMultiplayerSummaryViewModel localMultiplayerSummaryViewModel) {
        localMultiplayerSummaryViewModel.addPropertyChangeListener(this);

        // Set layout and background color
        this.setBackground(Constants.BGCOLOUR);
        this.setLayout(new BorderLayout());

        // Title Panel and Text
        final JPanel headerPanel = titlePanelHelper();
        // Vertical strut to add space between title and resultText
        headerPanel.add(Box.createVerticalStrut(Constants.MARGINS));
        this.add(headerPanel, BorderLayout.NORTH);

        // Results centered under the title
        resultText = resultTextHelper(headerPanel);
        playerOneResultText = playerOneResultHelper(headerPanel);
        playerTwoResultText = playerTwoResultHelper(headerPanel);
        
        // Scrollable Questions + Results Panel
        questionsPanel = questionsPanelHelper();

        // Buttons
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Constants.BGCOLOUR);
        returnButton = returnButtonHelper();
        buttonPanel.add(returnButton);
        actionListenersHelper();
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void actionListenersHelper() {
        returnButton.addActionListener(evt -> localMultiplayerController.switchToMainMenuView());
    }

    @NotNull
    private JButton returnButtonHelper() {
        final JButton tempReturnButton;
        tempReturnButton = new JButton("Return To Main Menu");
        tempReturnButton.setPreferredSize(new Dimension(Constants.BUTTONWIDTH / 2,
                Constants.BUTTONHEIGHT / 2));
        tempReturnButton.setFont(new Font(Constants.FONTSTYLE, Font.BOLD,
                Constants.BUTTONFONTSIZE * 2 / Constants.THREE));
        return tempReturnButton;
    }

    @NotNull
    private JPanel questionsPanelHelper() {
        final JPanel tempQuestionsPanel;
        tempQuestionsPanel = new JPanel();
        tempQuestionsPanel.setLayout(new BoxLayout(tempQuestionsPanel, BoxLayout.Y_AXIS));
        tempQuestionsPanel.setBackground(Constants.BGCOLOUR);

        final JScrollPane scrollPane = new JScrollPane(tempQuestionsPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, BorderLayout.CENTER);
        return tempQuestionsPanel;
    }

    @NotNull
    private JLabel resultTextHelper(JPanel headerPanel) {
        final JLabel tempResultText = createLabel("You got: ",
                Constants.FONTPARAMETERS,
                SwingConstants.CENTER);
        tempResultText.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(tempResultText);
        return tempResultText;
    }

    @NotNull
    private JLabel playerTwoResultHelper(JPanel headerPanel) {
        final JLabel tempPlayerTwoResultText = createLabel("Player Two got: ",
                Constants.FONTPARAMETERS,
                SwingConstants.CENTER);
        tempPlayerTwoResultText.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(tempPlayerTwoResultText);
        return tempPlayerTwoResultText;
    }

    @NotNull
    private JLabel playerOneResultHelper(JPanel headerPanel) {
        final JLabel tempPlayerOneResultText = createLabel("Player One got: ",
                Constants.FONTPARAMETERS,
                SwingConstants.CENTER);
        tempPlayerOneResultText.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(tempPlayerOneResultText);
        return tempPlayerOneResultText;
    }

    @NotNull
    private JPanel titlePanelHelper() {
        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Constants.BGCOLOUR);

        // Title centered
        final JLabel title = createLabel("Summary View",
                new Font(Constants.FONTSTYLE, Font.BOLD, Constants.TITLEFONTSIZE),
                SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(title);
        return headerPanel;
    }

    /**
     * Updates the view when the state changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LocalMultiplayerSummaryState state = (LocalMultiplayerSummaryState) evt.getNewValue();
        setResultTextHelper(state);

        questionsPanel.removeAll();

        for (int i = 0; i < state.getNumberOfQuestions(); i++) {
            // current question
            final Question question = state.getQuiz().getQuestions().get(i);

            // get who answered the question, correct answer, and each person's answer
            final String correctAnswer = question.getCorrectAnswer();
            String playerOneCurrentAnswer = "";
            String playerTwoCurrentAnswer = "";
            final Boolean[] playerOneAnswered = {false, false};
            final Boolean[] playerTwoAnswered = {false, false};
            if (state.getPlayerOneInfo().get(i) != null) {
                playerOneCurrentAnswer = getPlayerOneInfoForQuestion(state, i, playerOneAnswered, correctAnswer);
            }
            if (state.getPlayerTwoInfo().get(i) != null) {
                playerTwoCurrentAnswer = getPlayerTwoInfoForQuestion(state, i, playerTwoAnswered, correctAnswer);
            }

            // set if someone got the question correct
            final boolean isCorrect = playerOneAnswered[1] || playerTwoAnswered[1];

            // display panel for question
            final JPanel questionPanel = questionPanelUpdateHelper();
            final JLabel questionText = createLabel("Q" + (i + 1) + ": " + question.getQuestionText(),
                    Constants.FONTPARAMETERS,
                    SwingConstants.LEFT);

            final ArrayList<Boolean[]> playersAnswered = new ArrayList<>();
            playersAnswered.add(playerOneAnswered);
            playersAnswered.add(playerTwoAnswered);

            // if correct, someone answered correctly so display just both answers
            questionScrollPanelHelper(isCorrect, questionPanel, questionText,
                    correctAnswer, playersAnswered,
                    playerTwoCurrentAnswer, playerOneCurrentAnswer);

            questionScrollPaneAssembly(questionPanel);
        }
    }

    private void questionScrollPanelHelper(boolean isCorrect, JPanel questionPanel, JLabel questionText,
                                           String correctAnswer, ArrayList<Boolean[]> playersAnswered,
                                           String playerTwoCurrentAnswer, String playerOneCurrentAnswer) {
        final String correctAnswerText = "Correct Answer: ";
        if (isCorrect) {
            questionPanelHelper(questionPanel, questionText, Constants.CORRECTGREENBG);
            final JLabel correct;
            final JLabel correctText;
            final JLabel userText = userTextHelper();

            // player one was correct
            if (playersAnswered.get(0)[0] && playersAnswered.get(0)[1]) {
                correct = createLabel("Player One was correct!",
                        Constants.FONTPARAMETERS,
                        SwingConstants.LEFT);

                correctText = createLabel(correctAnswerText + correctAnswer,
                        Constants.FONTPARAMETERS,
                        SwingConstants.LEFT);

                // player two was incorrect
                bothAnswerOneCorrect(playersAnswered.get(1), userText, playerTwoCurrentAnswer);
            }

            // player two was correct
            else {
                correct = createLabel("Player Two was correct!",
                        Constants.FONTPARAMETERS,
                        SwingConstants.LEFT);

                correctText = createLabel(correctAnswerText + correctAnswer,
                        Constants.FONTPARAMETERS,
                        SwingConstants.LEFT);

                // player one was incorrect
                bothAnswerTwoCorrect(playersAnswered.get(0), userText, playerOneCurrentAnswer);
            }

            questionPanelAssembly(questionPanel, correct, correctText, userText);
        }
        else {
            questionPanelHelper(questionPanel, questionText, Constants.INCORRECTREDBG);
            final JLabel correctText = createLabel(correctAnswerText + correctAnswer,
                    Constants.FONTPARAMETERS,
                    SwingConstants.LEFT);
            final JLabel playerOneText = createLabel("Player One's Answer: " + playerOneCurrentAnswer,
                    Constants.FONTPARAMETERS,
                    SwingConstants.LEFT);
            final JLabel playerTwoText = createLabel("Player Two's answer: " + playerTwoCurrentAnswer,
                    Constants.FONTPARAMETERS,
                    SwingConstants.LEFT);

            questionPanelAssembly(questionPanel, correctText, playerOneText, playerTwoText);
        }
    }

    private static void bothAnswerTwoCorrect(Boolean[] playerOneAnswered, JLabel userText,
                                             String playerOneCurrentAnswer) {
        if (playerOneAnswered[0]) {
            userText.setText("Player One's Answer: " + playerOneCurrentAnswer);
        }
    }

    private static void bothAnswerOneCorrect(Boolean[] playerTwoAnswered, JLabel userText,
                                             String playerTwoCurrentAnswer) {
        if (playerTwoAnswered[0]) {
            userText.setText("Player Two's Answer: " + playerTwoCurrentAnswer);
        }
    }

    private static void questionPanelHelper(JPanel questionPanel, JLabel questionText, Color color) {
        questionPanel.setBackground(color);
        questionPanel.add(questionText);
    }

    private void questionScrollPaneAssembly(JPanel questionPanel) {
        questionsPanel.add(questionPanel);
        questionsPanel.add(Box.createVerticalStrut(Constants.MARGINS));
    }

    private static void questionPanelAssembly(JPanel questionPanel, JLabel correct,
                                              JLabel correctText, JLabel userText) {
        questionPanel.add(correct);
        questionPanel.add(correctText);
        questionPanel.add(userText);
    }

    @NotNull
    private static JLabel userTextHelper() {
        final JLabel userText = new JLabel();
        userText.setFont(Constants.FONTPARAMETERS);
        userText.setForeground(Color.WHITE);
        userText.setAlignmentX(SwingConstants.LEFT);
        return userText;
    }

    @NotNull
    private static JPanel questionPanelUpdateHelper() {
        final JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(Constants.MARGINS, Constants.MARGINS,
                Constants.MARGINS, Constants.MARGINS));
        return questionPanel;
    }

    @NotNull
    private static String getPlayerTwoInfoForQuestion(LocalMultiplayerSummaryState state, int iterator,
                                                      Boolean[] playerTwoAnswered, String correctAnswer) {
        final Pair<String, Boolean> playerTwoAnswer;
        final String playerTwoCurrentAnswer;
        playerTwoAnswer = state.getPlayerTwoInfo().get(iterator);
        playerTwoCurrentAnswer = playerTwoAnswer.getFirst();
        playerTwoAnswered[0] = true;
        if (playerTwoCurrentAnswer.equals(correctAnswer)) {
            playerTwoAnswered[1] = true;
        }
        return playerTwoCurrentAnswer;
    }

    @NotNull
    private static String getPlayerOneInfoForQuestion(LocalMultiplayerSummaryState state, int iterator,
                                                      Boolean[] playerOneAnswered, String correctAnswer) {
        final Pair<String, Boolean> playerOneAnswer;
        final String playerOneCurrentAnswer;
        playerOneAnswer = state.getPlayerOneInfo().get(iterator);
        playerOneCurrentAnswer = playerOneAnswer.getFirst();
        playerOneAnswered[0] = true;
        if (playerOneCurrentAnswer.equals(correctAnswer)) {
            playerOneAnswered[1] = true;
        }
        return playerOneCurrentAnswer;
    }

    private void setResultTextHelper(LocalMultiplayerSummaryState state) {
        final String magicBackslash = " / ";
        resultText.setText("You got: " + (state.getNumMapCorrect()[0] + state.getNumMapCorrect()[1])
                + magicBackslash + state.getNumberOfQuestions());
        playerOneResultText.setText("Player One got: " + (state.getNumMapCorrect()[0])
                + magicBackslash + state.getNumberOfQuestions());
        playerTwoResultText.setText("Player Two got: " + (state.getNumMapCorrect()[1])
                + magicBackslash + state.getNumberOfQuestions());
    }

    private JLabel createLabel(String text, Font font, int alignment) {
        final JLabel label = new JLabel(text, alignment);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        return label;
    }

    public String getViewName() {
        return "local multiplayer summary";
    }

    public void setLocalMultiplayerController(LocalMultiplayerController localMultiplayerController) {
        this.localMultiplayerController = localMultiplayerController;
    }
}
