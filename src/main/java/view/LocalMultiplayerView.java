package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import app.Constants;
import interface_adapter.local_multiplayer.LocalMultiplayerController;
import interface_adapter.local_multiplayer.LocalMultiplayerState;
import interface_adapter.local_multiplayer.LocalMultiplayerViewModel;
import interface_adapter.quiz_generation.QuizGenerationViewModel;

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
        this.addComponent(title, 0, 0, Constants.THREE, GridBagConstraints.CENTER, gbc);

        final JPanel twoPlayerModePanel = new RoundPanel();
        final JLabel twoPlayerModeLabel = new JLabel("Local Two Player Mode!");
        twoPlayerModePanel.add(twoPlayerModeLabel);
        this.addComponent(twoPlayerModePanel, 0, 1, Constants.THREE, GridBagConstraints.CENTER, gbc);

        // Categories
        final Font optionFont = new Font("Arial", Font.BOLD, 14);
        final Dimension comboBoxSize = new Dimension(225, 25);

        categoryComboBox = createComboBox(Constants.CATEGORIES, comboBoxSize);
        addComponent(createLabel(QuizGenerationViewModel.CATEGORY_LABEL, optionFont),
                0, 2, 1, GridBagConstraints.WEST, gbc);

        // Difficulties
        difficultyComboBox = createComboBox(Constants.DIFFICULTIES, comboBoxSize);
        addComponent(createLabel(QuizGenerationViewModel.DIFFICULTY_LABEL, optionFont),
                0, Constants.THREE, 1, GridBagConstraints.WEST, gbc);

        // Number of questions
        questionComboBox = createComboBox(Constants.NUM_QUESTION, comboBoxSize);
        addComponent(createLabel(QuizGenerationViewModel.QUESTIONS_LABEL, optionFont),
                0, Constants.FOUR, 1, GridBagConstraints.WEST, gbc);

        // Play and Cancel buttons
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Constants.BGCOLOUR);
        playButton = new JButton(QuizGenerationViewModel.PLAY_BUTTON_LABEL);
        cancelButton = new JButton(QuizGenerationViewModel.CANCEL_BUTTON_LABEL);
        buttonPanel.add(playButton);
        buttonPanel.add(cancelButton);

        assembleHelper(gbc, buttonPanel);

        // Action Listeners
        addActionListeners();

        this.setBackground(Constants.BGCOLOUR);

    }

    private void assembleHelper(GridBagConstraints gbc, JPanel buttonPanel) {
        addComponent(categoryComboBox, 1, 2, 1, GridBagConstraints.WEST, gbc);
        addComponent(difficultyComboBox, 1, Constants.THREE, 1, GridBagConstraints.WEST, gbc);
        addComponent(questionComboBox, 1, Constants.FOUR, 1, GridBagConstraints.WEST, gbc);
        addComponent(buttonPanel, 0, Constants.FIVE, 2, GridBagConstraints.CENTER, gbc);
    }

    private void addActionListeners() {
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
    }

    private GridBagConstraints createGbc() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(Constants.MARGINS, Constants.MARGINS, Constants.MARGINS, Constants.MARGINS);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void addComponent(Component comp, int xAxis, int yAxis, int width, int anchor, GridBagConstraints gbc) {
        gbc.gridx = xAxis;
        gbc.gridy = yAxis;
        gbc.gridwidth = width;
        gbc.anchor = anchor;
        this.add(comp, gbc);
    }

    // Helper method for creating a JLabel with custom font size and alignment
    private JLabel createLabel(String text, Font font) {
        final JLabel label = new JLabel(text, SwingConstants.LEFT);
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
        return "local multiplayer";
    }

    public void setLocalMultiplayerController(LocalMultiplayerController localMultiplayerController) {
        this.localMultiplayerController = localMultiplayerController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LocalMultiplayerState state = (LocalMultiplayerState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
