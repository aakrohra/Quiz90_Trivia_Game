package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

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

    private LocalMultiplayerSummaryViewModel localMultiplayerSummaryViewModel;
    private LocalMultiplayerController localMultiplayerController;

    private final JLabel resultText;
    private final JLabel playerOneResultText;
    private final JLabel playerTwoResultText;
    private final JButton returnButton;
    private final JPanel questionsPanel;

    public LocalMultiplayerSummaryView(LocalMultiplayerSummaryViewModel localMultiplayerSummaryViewModel) {
        this.localMultiplayerSummaryViewModel = localMultiplayerSummaryViewModel;
        localMultiplayerSummaryViewModel.addPropertyChangeListener(this);

        // Set layout and background color
        this.setBackground(Constants.BGCOLOUR);
        this.setLayout(new BorderLayout());

        // Title Panel and Text
        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Constants.BGCOLOUR);

        // Title centered
        final JLabel title = createLabel("Summary View",
                new Font(Constants.FONTSTYLE, Font.BOLD, Constants.TITLEFONTSIZE),
                SwingConstants.CENTER, Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(title);

        // Vertical strut to add space between title and resultText
        headerPanel.add(Box.createVerticalStrut(Constants.MARGINS));

        // Result Text centered under the title
        resultText = createLabel("You got: ",
                new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                SwingConstants.CENTER, Color.WHITE);
        resultText.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(resultText);

        playerOneResultText = createLabel("You got: ",
                new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                SwingConstants.CENTER, Color.WHITE);
        playerTwoResultText = createLabel("You got: ",
                new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                SwingConstants.CENTER, Color.WHITE);

        headerPanel.add(playerOneResultText);
        headerPanel.add(playerOneResultText);

        this.add(headerPanel, BorderLayout.NORTH);

        // Scrollable Panel
        questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setBackground(Constants.BGCOLOUR);

        final JScrollPane scrollPane = new JScrollPane(questionsPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Constants.BGCOLOUR);

        returnButton = new JButton("Return To Main Menu");
        returnButton.setPreferredSize(new Dimension(Constants.BUTTONWIDTH / 2,
                Constants.BUTTONHEIGHT / 2));
        returnButton.setFont(new Font(Constants.FONTSTYLE, Font.BOLD,
                Constants.BUTTONFONTSIZE * 2 / Constants.THREE));
        returnButton.addActionListener(evt -> localMultiplayerController.switchToMainMenuView());

        buttonPanel.add(returnButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the view when the state changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LocalMultiplayerSummaryState state = (LocalMultiplayerSummaryState) evt.getNewValue();
        final String magicBackslash = " / ";
        resultText.setText("You got: " + (state.getNumMapCorrect()[0] + state.getNumMapCorrect()[1])
                + magicBackslash + state.getNumberOfQuestions());
        playerOneResultText.setText("You got: " + (state.getNumMapCorrect()[0])
                + magicBackslash + state.getNumberOfQuestions());
        playerTwoResultText.setText("You got: " + (state.getNumMapCorrect()[1])
                + magicBackslash + state.getNumberOfQuestions());

        // TODO: Clear the questions panel for replay? Not sure yet
        questionsPanel.removeAll();

        for (int i = 0; i < state.getNumberOfQuestions(); i++) {
            // current question
            final Question question = state.getQuiz().getQuestions().get(i);

            // get who answered the question, correct answer, and each person's answer
            final String correctAnswer = question.getCorrectAnswer();
            final Pair<String, Boolean> playerOneAnswer;
            final Pair<String, Boolean> playerTwoAnswer;
            String playerOneCurrentAnswer = "";
            String playerTwoCurrentAnswer = "";
            final Boolean[] playerOneAnswered = {false, false};
            final Boolean[] playerTwoAnswered = {false, false};
            if (state.getPlayerOneInfo().get(i) != null) {
                playerOneAnswer = state.getPlayerOneInfo().get(i);
                playerOneCurrentAnswer = playerOneAnswer.getFirst();
                playerOneAnswered[0] = true;
                if (playerOneCurrentAnswer.equals(correctAnswer)) {
                    playerOneAnswered[1] = true;
                }
            }
            if (state.getPlayerTwoInfo().get(i) != null) {
                playerTwoAnswer = state.getPlayerTwoInfo().get(i);
                playerTwoCurrentAnswer = playerTwoAnswer.getFirst();
                playerTwoAnswered[0] = true;
                if (playerTwoCurrentAnswer.equals(correctAnswer)) {
                    playerTwoAnswered[1] = true;
                }
            }

            // set if someone got the question correct
            final boolean isCorrect;
            isCorrect = playerOneAnswered[1] || playerTwoAnswered[1];

            // display panel for question
            final JPanel questionPanel = new JPanel();
            questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
            questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            if (isCorrect) {
                questionPanel.setBackground(Constants.CORRECTGREENBG);
            }
            else {
                questionPanel.setBackground(Constants.INCORRECTREDBG);
            }
            final JLabel questionText = createLabel("Q" + (i + 1) + ": " + question.getQuestionText(),
                    new Font(Constants.FONTSTYLE, Font.BOLD, Constants.BUTTONFONTSIZE),
                    SwingConstants.LEFT, Color.WHITE);
            questionPanel.add(questionText);

            // if correct, someone answered correctly so display just both answers
            final String correctAnswerTextPrompt = "Correct Answer: ";
            if (isCorrect) {
                final JLabel correct;
                final JLabel correctText;
                final JLabel userText = new JLabel();
                userText.setFont(new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE));
                userText.setForeground(Color.WHITE);
                userText.setAlignmentX(SwingConstants.LEFT);

                // player one was correct
                if (playerOneAnswered[0] && playerOneAnswered[1]) {
                    correct = createLabel("Player One was correct!",
                            new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                            SwingConstants.LEFT, Color.WHITE);

                    correctText = createLabel(correctAnswerTextPrompt + correctAnswer,
                            new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                            SwingConstants.LEFT, Color.WHITE);

                    // player two was incorrect
                    if (playerTwoAnswered[0]) {
                        userText.setText("Player Two's Answer: " + playerTwoCurrentAnswer);
                    }
                }

                // player two was correct
                else {
                    correct = createLabel("Player Two was correct!",
                            new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                            SwingConstants.LEFT, Color.WHITE);

                    correctText = createLabel(correctAnswerTextPrompt + correctAnswer,
                            new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                            SwingConstants.LEFT, Color.WHITE);

                    // player one was incorrect
                    if (playerOneAnswered[0]) {
                        userText.setText("Player One's Answer: " + playerOneCurrentAnswer);
                    }
                }

                questionPanel.add(correct);
                questionPanel.add(correctText);
                questionPanel.add(userText);

            }
            else {
                final JLabel correctText = createLabel(correctAnswerTextPrompt + correctAnswer,
                        new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                        SwingConstants.LEFT, Color.WHITE);
                final JLabel playerOneText = createLabel("Player One's Answer: " + playerOneCurrentAnswer,
                        new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                        SwingConstants.LEFT, Color.WHITE);
                final JLabel playerTwoText = createLabel("Player Two's answer: " + playerTwoCurrentAnswer,
                        new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                        SwingConstants.LEFT, Color.WHITE);

                questionPanel.add(correctText);
                questionPanel.add(playerOneText);
                questionPanel.add(playerTwoText);
            }

            questionsPanel.add(questionPanel);
            questionsPanel.add(Box.createVerticalStrut(Constants.MARGINS));
        }
    }

    private JLabel createLabel(String text, Font font, int alignment, Color color) {
        final JLabel label = new JLabel(text, alignment);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    public String getViewName() {
        return "local multiplayer summary";
    }

    public void setLocalMultiplayerController(LocalMultiplayerController localMultiplayerController) {
        this.localMultiplayerController = localMultiplayerController;
    }
}
