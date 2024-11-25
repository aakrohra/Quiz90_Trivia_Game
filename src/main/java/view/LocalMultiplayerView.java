package view;

import app.Constants;
//import data_access.TriviaApp;
//import entity.TriviaQuestion;
//import entity.TriviaResponse;
import interface_adapter.local_multiplayer.LocalMultiplayerController;
import interface_adapter.local_multiplayer.LocalMultiplayerViewModel;
import interface_adapter.quiz_generation.QuizGenerationViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

/**
 * The View for when the user is playing Local Multiplayer.
 */
public class LocalMultiplayerView extends JPanel implements PropertyChangeListener {

    private LocalMultiplayerController localMultiplayerController;

    private final LocalMultiplayerViewModel localMultiplayerViewModel;

    private final JComboBox<?> categoryComboBox;
    private final JComboBox<?> questionComboBox;
    private final JComboBox<?> difficultyComboBox;
    private final JButton playButton;
    private final JButton cancelButton;

    public LocalMultiplayerView(LocalMultiplayerViewModel localMultiplayerViewModel) {
        this.localMultiplayerViewModel = localMultiplayerViewModel;
        this.localMultiplayerViewModel.addPropertyChangeListener(this);

        final GridBagConstraints gbc = createGbc();
        this.setLayout(new GridBagLayout());

        final TitlePanel title = new TitlePanel("Local Multiplayer");
        this.addComponent(title, 0, 0 , 3, GridBagConstraints.CENTER, gbc);

        final JPanel twoPlayerModePanel = new RoundPanel();
        final JLabel twoPlayerModeLabel = new JLabel("Local Two Player Mode!");
        twoPlayerModePanel.add(twoPlayerModeLabel);
        this.addComponent(twoPlayerModePanel, 0, 1 , 3, GridBagConstraints.CENTER, gbc);

        // Categories
        final Font optionFont = new Font("Arial", Font.BOLD, 14);
        final Dimension comboBoxSize = new Dimension(225, 25);

        categoryComboBox = createComboBox(Constants.CATEGORIES, comboBoxSize);
        addComponent(createLabel(QuizGenerationViewModel.CATEGORY_LABEL, optionFont, SwingConstants.LEFT),
                0, 2, 1, GridBagConstraints.WEST, gbc);
        addComponent(categoryComboBox, 1, 2, 1, GridBagConstraints.WEST, gbc);

        // Number of questions
        questionComboBox = createComboBox(Constants.NUM_QUESTION, comboBoxSize);
        addComponent(createLabel(QuizGenerationViewModel.QUESTIONS_LABEL, optionFont, SwingConstants.LEFT),
                0, 3, 1, GridBagConstraints.WEST, gbc);
        addComponent(questionComboBox, 1, 3, 1, GridBagConstraints.WEST, gbc);

        // Difficulties
        difficultyComboBox = createComboBox(Constants.DIFFICULTIES, comboBoxSize);
        addComponent(createLabel(QuizGenerationViewModel.DIFFICULTY_LABEL, optionFont, SwingConstants.LEFT),
                0, 4, 1, GridBagConstraints.WEST, gbc);
        addComponent(difficultyComboBox, 1, 4, 1, GridBagConstraints.WEST, gbc);

        // Play and Cancel buttons
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Constants.BGCOLOUR);
        playButton = new JButton(QuizGenerationViewModel.PLAY_BUTTON_LABEL);
        cancelButton = new JButton(QuizGenerationViewModel.CANCEL_BUTTON_LABEL);
        buttonPanel.add(playButton);
        buttonPanel.add(cancelButton);
        addComponent(buttonPanel, 0, 5, 2, GridBagConstraints.CENTER, gbc);

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
            // Convert user inputs as strings/integers
            final String category = (String) categoryComboBox.getSelectedItem();
            final int numQuestions = (int) questionComboBox.getSelectedItem();
            final String difficultyUpper = (String) difficultyComboBox.getSelectedItem();
            final String difficulty = difficultyUpper.toLowerCase();

            localMultiplayerController.execute(numQuestions, category, difficulty);
        });

        cancelButton.addActionListener(evt -> {
            if (evt.getSource().equals(cancelButton)) {
                localMultiplayerController.switchToMainMenuView();
            }
        });

        final JButton testButton = new CustomButton("hi");

        testButton.addActionListener(evt -> {
            if (evt.getSource().equals(testButton)) {
                System.out.println("test button clicked");
            }
        });

        this.setBackground(Constants.BGCOLOUR);

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


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("hi");
    }

    public String getViewName() {
        return "local multiplayer";
    }

    public void setLocalMultiplayerController(LocalMultiplayerController localMultiplayerController) {
        this.localMultiplayerController = localMultiplayerController;
    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Local Multiplayer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT));
        frame.setLocationRelativeTo(null);

        final LocalMultiplayerView localMultiplayerView = new LocalMultiplayerView(new LocalMultiplayerViewModel());
        frame.add(localMultiplayerView);
        frame.setVisible(true);
    }
}
