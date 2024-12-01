package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import app.Constants;
import interface_adapter.quiz_generation.QuizGenerationController;
import interface_adapter.quiz_generation.QuizGenerationState;
import interface_adapter.quiz_generation.QuizGenerationViewModel;

/**
 * The view for quiz generation, allowing users to select quiz parameters
 * such as category, number of question, and difficulty.
 */
public class QuizGenerationView extends JPanel implements PropertyChangeListener {
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
        this.quizGenerationViewModel.addPropertyChangeListener(this);

        this.setBackground(Constants.BGCOLOUR);
        this.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = createGbc();

        // Title
        final JLabel title = createLabel(QuizGenerationViewModel.TITLE_LABEL,
                new Font(Constants.FONTSTYLE, Font.BOLD, Constants.TITLEFONTSIZE), SwingConstants.CENTER, Color.WHITE);
        gbc.insets = new Insets(
                Constants.MARGINS, Constants.MARGINS, Constants.MARGINS * Constants.FOUR, Constants.MARGINS);
        addComponent(title, 0, 0, 2, GridBagConstraints.CENTER, gbc);

        // Labels and ComboBox boxes

        // Reset Insets
        gbc.insets = new Insets(Constants.MARGINS, Constants.MARGINS, Constants.MARGINS, Constants.MARGINS);
        // Categories
        final Font optionFont = new Font(Constants.FONTSTYLE, Font.BOLD, Constants.BUTTONFONTSIZE);
        final Dimension comboBoxSize = new Dimension(Constants.COMBOBOXWIDTH, Constants.COMBOBOXHEIGHT);
        // Create and add category label and ComboBox
        categoryComboBox = createComboBox(Constants.CATEGORIES, comboBoxSize);
        addComponent(createLabel(
                QuizGenerationViewModel.CATEGORY_LABEL, optionFont, SwingConstants.LEFT, Color.WHITE),
                0, 1, 1, GridBagConstraints.WEST, gbc);
        addComponent(categoryComboBox, 1, 1, 1, GridBagConstraints.WEST, gbc);

        // Difficulty
        difficultyComboBox = createComboBox(Constants.DIFFICULTIES, comboBoxSize);
        addComponent(createLabel(
                QuizGenerationViewModel.DIFFICULTY_LABEL, optionFont, SwingConstants.LEFT, Color.WHITE),
                0, 2, 1, GridBagConstraints.WEST, gbc);
        addComponent(difficultyComboBox, 1, 2, 1, GridBagConstraints.WEST, gbc);

        // Number of questions
        questionComboBox = createComboBox(Constants.NUM_QUESTION, comboBoxSize);
        addComponent(createLabel(
                QuizGenerationViewModel.QUESTIONS_LABEL, optionFont, SwingConstants.LEFT, Color.WHITE),
                0, Constants.THREE, 1, GridBagConstraints.WEST, gbc);
        addComponent(questionComboBox, 1, Constants.THREE, 1, GridBagConstraints.WEST, gbc);

        // Play and Cancel buttons
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Constants.BGCOLOUR);
        final Font buttonFont = new Font(Constants.FONTSTYLE, Font.BOLD, 18);

        playButton = new JButton(QuizGenerationViewModel.PLAY_BUTTON_LABEL);
        cancelButton = new JButton(QuizGenerationViewModel.CANCEL_BUTTON_LABEL);
        playButton.setPreferredSize(new Dimension(Constants.BUTTONWIDTH / Constants.THREE,
                Constants.BUTTONHEIGHT / Constants.THREE));
        cancelButton.setPreferredSize(new Dimension(Constants.BUTTONWIDTH / Constants.THREE,
                Constants.BUTTONHEIGHT / Constants.THREE));

        playButton.setFont(buttonFont);
        cancelButton.setFont(buttonFont);
        buttonPanel.add(playButton);
        buttonPanel.add(cancelButton);
        addComponent(buttonPanel, 0, Constants.FOUR, 2, GridBagConstraints.CENTER, gbc);

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
            }
        });
    }

    private GridBagConstraints createGbc() {
        final GridBagConstraints gbc = new GridBagConstraints();
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

    // Helper method for creating a JLabel with custom font size and alignment
    private JLabel createLabel(String text, Font font, int alignment) {
        final JLabel label = new JLabel(text, alignment);
        label.setFont(font);
        return label;
    }

    private JLabel createLabel(String text, Font font, int alignment, Color color) {
        final JLabel label = new JLabel(text, alignment);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    // Helper method for creating a JComboBox with custom dimension
    private JComboBox<?> createComboBox(Object[] items, Dimension dimension) {
        final JComboBox<?> comboBox = new JComboBox<>(items);
        comboBox.setPreferredSize(dimension);

        comboBox.setFont(new Font(Constants.FONTSTYLE, Font.BOLD, Constants.COMBOBOXFONTSIZE));

        return comboBox;
    }

    public String getViewName() {
        return viewName;
    }

    public void setQuizGenerationController(QuizGenerationController quizGenerationController) {
        this.quizGenerationController = quizGenerationController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final QuizGenerationState state = (QuizGenerationState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
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
