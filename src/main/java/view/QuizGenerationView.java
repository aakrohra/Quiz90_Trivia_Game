package view;

import java.awt.*;

import javax.swing.*;

import app.Constants;
import data_access.DBTriviaDataAccessObject;
import entity.TriviaQuestion;
import entity.TriviaQuiz;
import interface_adapter.quiz_generation.QuizGenerationController;
import interface_adapter.quiz_generation.QuizGenerationViewModel;
import use_case.quiz_generation.QuizGenerationInputData;

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
    private final QuizGenerationViewModel quizGenerationViewModel;
    private QuizGenerationController quizGenerationController;

    public QuizGenerationView(QuizGenerationViewModel quizGenerationViewModel) {
        this.quizGenerationViewModel = quizGenerationViewModel;
        // quizGenerationViewModel.addPropertyChangeListener(this);

        this.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = createGbc();

        // Title
        final JLabel title = createLabel(QuizGenerationViewModel.TITLE_LABEL,
                new Font("Arial", Font.BOLD, 18), SwingConstants.CENTER);
        addComponent(title, 0, 0, 2, GridBagConstraints.CENTER, gbc);

        // Labels and ComboBox boxes
        // Categories
        final Font optionFont = new Font("Arial", Font.BOLD, 14);
        final Dimension comboBoxSize = new Dimension(225, 25);
        // Create and add category label and ComboBox
        categoryComboBox = createComboBox(Constants.CATEGORIES, comboBoxSize);
        addComponent(createLabel(QuizGenerationViewModel.CATEGORY_LABEL, optionFont, SwingConstants.LEFT),
                0, 1, 1, GridBagConstraints.WEST, gbc);
        addComponent(categoryComboBox, 1, 1, 1, GridBagConstraints.WEST, gbc);

        // Difficulty
        difficultyComboBox = createComboBox(Constants.DIFFICULTIES, comboBoxSize);
        addComponent(createLabel(QuizGenerationViewModel.DIFFICULTY_LABEL, optionFont, SwingConstants.LEFT),
                0, 2, 1, GridBagConstraints.WEST, gbc);
        addComponent(difficultyComboBox, 1, 2, 1, GridBagConstraints.WEST, gbc);

        // Number of questions
        questionComboBox = createComboBox(Constants.NUM_QUESTION, comboBoxSize);
        addComponent(createLabel(QuizGenerationViewModel.QUESTIONS_LABEL, optionFont, SwingConstants.LEFT),
                0, Constants.THREE, 1, GridBagConstraints.WEST, gbc);
        addComponent(questionComboBox, 1, Constants.THREE, 1, GridBagConstraints.WEST, gbc);

        // Play and Cancel buttons
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        playButton = new JButton(QuizGenerationViewModel.PLAY_BUTTON_LABEL);
        cancelButton = new JButton(QuizGenerationViewModel.CANCEL_BUTTON_LABEL);
        buttonPanel.add(playButton);
        buttonPanel.add(cancelButton);
        addComponent(buttonPanel, 0, Constants.FOUR, 2, GridBagConstraints.CENTER, gbc);

        // Action Listeners
        categoryComboBox.addActionListener(evt -> {
            System.out.println("Category selected: " + categoryComboBox.getSelectedItem());
        });
        difficultyComboBox.addActionListener(evt -> {
            System.out.println("Difficulty selected: " + difficultyComboBox.getSelectedItem());
        });
        questionComboBox.addActionListener(evt -> {
            System.out.println("Number of questions selected: " + questionComboBox.getSelectedItem());
        });

        playButton.addActionListener(evt -> {
            // Convert user inputs as strings/integers
            final String category = (String) categoryComboBox.getSelectedItem();
            final int numQuestions = (int) questionComboBox.getSelectedItem();
            final String difficultyUpper = (String) difficultyComboBox.getSelectedItem();
            final String difficulty = difficultyUpper.toLowerCase();

            quizGenerationController.execute(numQuestions, category, difficulty);
        });

        cancelButton.addActionListener(evt -> {
            if (evt.getSource().equals(cancelButton)) {
                quizGenerationController.switchToMainMenuView();
                System.out.println("Cancel button clicked");
            }
        });
    }

    private GridBagConstraints createGbc() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(Constants.MARGINS, Constants.MARGINS, Constants.MARGINS, Constants.MARGINS);
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

    public void setQuizGenerationController(QuizGenerationController quizGenerationController) {
        this.quizGenerationController = quizGenerationController;
    }

    // Main method to run and test the QuizGenerationView in a JFrame
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Quiz Generation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT);
        frame.setLocationRelativeTo(null);

        final QuizGenerationView quizGenerationView = new QuizGenerationView(new QuizGenerationViewModel());
        frame.add(quizGenerationView);
        frame.setVisible(true);
    }

}
