package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import app.Constants;
import entity.Question;
import interface_adapter.summary.SummaryController;
import interface_adapter.summary.SummaryState;
import interface_adapter.summary.SummaryViewModel;
import kotlin.Pair;

/**
 * The view for the Summary screen, displaying the quiz results in detail.
 */
public class SummaryView extends JPanel implements PropertyChangeListener {

    private final String viewName = "summary";
    private SummaryViewModel summaryViewModel;
    private SummaryController summaryController;

    private final JLabel resultText;
    private final JButton returnButton;
    private final JPanel questionsPanel;

    public SummaryView(SummaryViewModel summaryViewModel) {
        this.summaryViewModel = summaryViewModel;
        summaryViewModel.addPropertyChangeListener(this);

        // Set layout and background color
        this.setBackground(Constants.BGCOLOUR);
        this.setLayout(new BorderLayout());

        // Title Panel and Text
        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Constants.BGCOLOUR);
        final JLabel title = createLabel("Summary View",
                new Font(Constants.FONTSTYLE, Font.BOLD, Constants.TITLEFONTSIZE),
                SwingConstants.LEFT, Color.WHITE);
        headerPanel.add(title);

        // Return to Main Menu Button
        resultText = createLabel("You got: ",
                new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                SwingConstants.CENTER, Color.WHITE);
        resultText.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(resultText);

        this.add(headerPanel, BorderLayout.NORTH);

        // Scrollable Panel
        questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setBackground(Constants.BGCOLOUR);

        final JScrollPane scrollPane = new JScrollPane(questionsPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Constants.BGCOLOUR);

        returnButton = new JButton("Return To Main Menu");
        returnButton.setPreferredSize(new Dimension(Constants.BUTTONWIDTH / 2,
                Constants.BUTTONHEIGHT / 2));
        returnButton.setFont(new Font(Constants.FONTSTYLE, Font.BOLD,
                Constants.BUTTONFONTSIZE * 2 / Constants.THREE));
        returnButton.addActionListener(evt -> summaryController.switchToMainMenuView());

        buttonPanel.add(returnButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the view when the state changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SummaryState state = (SummaryState) evt.getNewValue();
        resultText.setText("You got: " + state.getNumberOfCorrectAnswers() + " / " + state.getNumberOfQuestions());

        // TODO: Clear the questions panel for replay? Not sure yet
        questionsPanel.removeAll();

        for (int i = 0; i < state.getNumberOfQuestions(); i++) {
            final Question question = state.getQuiz().getQuestions().get(i);
            final Pair<String, Boolean> playerAnswer = state.getPlayerInfo().get(i);

            final String correctAnswer = question.getCorrectAnswer();
            final String playerCurrentAnswer = playerAnswer.getFirst();
            final boolean isCorrect = playerAnswer.getSecond();

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

            if (isCorrect) {
                final JLabel correct = createLabel("Correct!",
                        new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                        SwingConstants.LEFT, Color.WHITE);

                final JLabel correctText = createLabel("Correct Answer: " + correctAnswer,
                        new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                        SwingConstants.LEFT, Color.WHITE);

                questionPanel.add(correct);
                questionPanel.add(correctText);
            }
            else {
                final JLabel correctText = createLabel("Correct Answer: " + correctAnswer,
                        new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                        SwingConstants.LEFT, Color.WHITE);

                final JLabel userText = createLabel("Your Answer: " + playerCurrentAnswer,
                        new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                        SwingConstants.LEFT, Color.WHITE);

                questionPanel.add(correctText);
                questionPanel.add(userText);
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
        return viewName;
    }

    public void setSummaryController(SummaryController summaryController) {
        this.summaryController = summaryController;
    }
}
