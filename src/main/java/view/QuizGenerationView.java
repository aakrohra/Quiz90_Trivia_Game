package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The view for quiz generation, allowing users to select quiz parameters
 * such as category, number of questions, and difficulty.
 */
public class QuizGenerationView extends JPanel {

    private final String viewName = "quiz generation";
    private final JComboBox<String> categoryDropdown;
    private final JComboBox<Integer> questionsDropdown;
    private final JComboBox<String> difficultyDropdown;
    private final JButton playButton;
    private final JButton cancelButton;

    public QuizGenerationView() {
        // Set up labels and dropdowns for quiz options
        final JLabel title = new JLabel("Quiz Generation Screen");
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel categoryLabel = new JLabel("Select Category:");
        final String[] categories = {"General Knowledge", "Science", "History", "Sports", "Entertainment"};
        categoryDropdown = new JComboBox<>(categories);
        categoryDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel questionsLabel = new JLabel("Number of Questions:");
        final Integer[] questionOptions = {5, 10, 15, 20};
        questionsDropdown = new JComboBox<>(questionOptions);
        questionsDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel difficultyLabel = new JLabel("Select Difficulty:");
        final String[] difficulties = {"Easy", "Medium", "Hard"};
        difficultyDropdown = new JComboBox<>(difficulties);
        difficultyDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Set fixed size for dropdown boxes
        Dimension fixedSize = new Dimension(200, 25);
        categoryDropdown.setPreferredSize(fixedSize);
        categoryDropdown.setMaximumSize(fixedSize);
        questionsDropdown.setPreferredSize(fixedSize);
        questionsDropdown.setMaximumSize(fixedSize);
        difficultyDropdown.setPreferredSize(fixedSize);
        difficultyDropdown.setMaximumSize(fixedSize);

        // Set up buttons with preferred sizes and align horizontally
        playButton = new JButton("Play");
        cancelButton = new JButton("Cancel");

        // Add action listeners to print selected options and button clicks
        categoryDropdown.addActionListener(evt -> System.out.println("Category selected: " + categoryDropdown.getSelectedItem()));
        questionsDropdown.addActionListener(evt -> System.out.println("Number of questions selected: " + questionsDropdown.getSelectedItem()));
        difficultyDropdown.addActionListener(evt -> System.out.println("Difficulty selected: " + difficultyDropdown.getSelectedItem()));

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("Play button clicked");
                System.out.println("Quiz Settings - Category: " + categoryDropdown.getSelectedItem()
                        + ", Questions: " + questionsDropdown.getSelectedItem()
                        + ", Difficulty: " + difficultyDropdown.getSelectedItem());
            }
        });

        cancelButton.addActionListener(evt -> System.out.println("Cancel button clicked"));

        // Layout settings for the view
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);

        // Arrange dropdowns with labels and spacing
        this.add(categoryLabel);
        this.add(categoryDropdown);

        this.add(questionsLabel);
        this.add(questionsDropdown);

        this.add(difficultyLabel);
        this.add(difficultyDropdown);

        final JPanel buttons = new JPanel();
        buttons.add(playButton);
        buttons.add(cancelButton);
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(buttons);
    }

    public String getViewName() {
        return viewName;
    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Quiz Generation Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(new QuizGenerationView());
        frame.setVisible(true);
    }

}
