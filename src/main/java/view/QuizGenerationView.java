package view;

import app.Constants;
import data_access.TriviaApp;
import entity.TriviaQuestion;
import entity.TriviaResponse;

import javax.swing.*;
import java.awt.*;

/**
 * The view for quiz generation, allowing users to select quiz parameters
 * such as category, number of question, and difficulty.
 */
public class QuizGenerationView extends JPanel {

    private final String viewName = "quiz generation";
    private final JComboBox<?> categoryComboBox;
    private final JComboBox<?> questionComboBox;
    private final JComboBox<?> difficultyComboBox;
    private final JButton playButton;
    private final JButton cancelButton;

    public QuizGenerationView() {
        this.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = createGbc();

        // Title
        final JLabel title = createLabel("Quiz Generation Screen", new Font("Arial", Font.BOLD, 18),
                SwingConstants.CENTER);
        addComponent(title, 0, 0, 2, GridBagConstraints.CENTER, gbc);

        // Labels and ComboBox boxes
        // Categories
        final Font optionFont = new Font("Arial", Font.BOLD, 14);
        final Dimension comboBoxSize = new Dimension(200, 25);
        // Create and add category label and ComboBox
        categoryComboBox = createComboBox(Constants.CATEGORIES, comboBoxSize);
        addComponent(createLabel("Select Category:", optionFont, SwingConstants.LEFT),
                0, 1, 1, GridBagConstraints.WEST, gbc);
        addComponent(categoryComboBox, 1, 1, 1, GridBagConstraints.WEST, gbc);

        // Number of questions
        questionComboBox = createComboBox(Constants.NUM_QUESTION, comboBoxSize);
        addComponent(createLabel("Number of Questions:", optionFont, SwingConstants.LEFT),
                0, 2, 1, GridBagConstraints.WEST, gbc);
        addComponent(questionComboBox, 1, 2, 1, GridBagConstraints.WEST, gbc);

        // Difficulties
        difficultyComboBox = createComboBox(Constants.DIFFICULTIES, comboBoxSize);
        addComponent(createLabel("Select Difficulty:", optionFont, SwingConstants.LEFT),
                0, 3, 1, GridBagConstraints.WEST, gbc);
        addComponent(difficultyComboBox, 1, 3, 1, GridBagConstraints.WEST, gbc);

        // Play and Cancel buttons
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        playButton = new JButton("Play");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(playButton);
        buttonPanel.add(cancelButton);
        addComponent(buttonPanel, 0, 4, 2, GridBagConstraints.CENTER, gbc);

        // Action Listeners
        categoryComboBox.addActionListener(evt -> {
            System.out.println("Category selected: " + categoryComboBox.getSelectedItem());
        });
        questionComboBox.addActionListener(evt -> {
            System.out.println("Number of questions selected: " + questionComboBox.getSelectedItem());
        });
        difficultyComboBox.addActionListener(evt -> {
            System.out.println("Difficulty selected: " + difficultyComboBox.getSelectedItem());
        });

        playButton.addActionListener(evt -> {
            try {
                // Convert user inputs as strings/integers
                String category = (String) categoryComboBox.getSelectedItem();
                int numQuestions = (int) questionComboBox.getSelectedItem();
                String difficultyUpper = (String) difficultyComboBox.getSelectedItem();
                String difficulty = difficultyUpper.toLowerCase();

                // Fetch trivia
                TriviaApp triviaApp = new TriviaApp();
                TriviaResponse trivia = triviaApp.fetchTrivia(numQuestions, category, difficulty);

                for (TriviaQuestion question : trivia.getQuestions()) {
                    System.out.println("Question: " + question.getQuestion());
                    System.out.println("Correct Answer: " + question.getCorrectAnswer());
                    System.out.println("Incorrect Answers: " + String.join(", ", question.getIncorrectAnswers()));
                    System.out.println();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        cancelButton.addActionListener(evt -> System.out.println("Cancel button clicked"));
    }

    private GridBagConstraints createGbc() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void addComponent(Component comp, int x_axis, int y_axis, int width, int anchor, GridBagConstraints gbc) {
        gbc.gridx = x_axis;
        gbc.gridy = y_axis;
        gbc.gridwidth = width;
        gbc.anchor = anchor;
        this.add(comp, gbc);
    }

    // TODO: The createLabel and createComboBox methods are useful to other views.
    // TODO: Refactor the code to make them accessible to all views.

    // Helper method for creating a JLabel with custom font size and alignment
    private JLabel createLabel(String text, Font font, int alignment) {
        final JLabel label = new JLabel(text, alignment);
        label.setFont(font);
        return label;
    }

    // Helper method for creating a JComboBox with custom dimension
    private JComboBox<?> createComboBox(Object[] items, Dimension dimension) {
        final JComboBox<?> comboBox = new JComboBox<>(items);
        comboBox.setPreferredSize(dimension);
        return comboBox;
    }

    public String getViewName() {
        return viewName;
    }

    // Main method to run and test the QuizGenerationView in a JFrame
    public static void main(String[] args) {
        JFrame frame = new JFrame("Quiz Generation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);

        QuizGenerationView quizGenerationView = new QuizGenerationView();
        frame.add(quizGenerationView);
        frame.setVisible(true);
    }

}
