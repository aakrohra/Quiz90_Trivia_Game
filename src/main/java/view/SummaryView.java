package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import app.Constants;
import interface_adapter.summary.SummaryController;
import interface_adapter.summary.SummaryState;
import interface_adapter.summary.SummaryViewModel;

/**
 * The view for the Summary screen, displaying the quiz results.
 */
public class SummaryView extends JPanel implements PropertyChangeListener {

    private final String viewName = "summary";
    private SummaryViewModel summaryViewModel;
    private SummaryController summaryController;

    private final JLabel resultText;
    private final JButton returnButton;


    public SummaryView(SummaryViewModel summaryViewModel) {
        this.summaryViewModel = summaryViewModel;
        summaryViewModel.addPropertyChangeListener(this);

        // Set the layout and background color
        this.setBackground(Constants.BGCOLOUR);
        this.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = createGbc();

        // Title
        final JLabel title = createLabel("Summary View",
                new Font(Constants.FONTSTYLE, Font.BOLD, Constants.TITLEFONTSIZE),
                SwingConstants.CENTER, Color.WHITE);
        gbc.insets = new Insets(
                Constants.MARGINS, Constants.MARGINS, Constants.MARGINS * 2, Constants.MARGINS);
        addComponent(title, 0, 0, 2, GridBagConstraints.CENTER, gbc);

        // Result Text
        resultText = createLabel("You got: ",
                new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE),
                SwingConstants.CENTER, Color.WHITE);
        gbc.insets = new Insets(Constants.MARGINS, Constants.MARGINS, Constants.MARGINS * 2, Constants.MARGINS);
        addComponent(resultText, 0, 1, 2, GridBagConstraints.CENTER, gbc);

        // Return to Main Menu Button
        returnButton = new JButton("Return To Main Menu");
        returnButton.setPreferredSize(new Dimension(Constants.BUTTONWIDTH, Constants.BUTTONHEIGHT));
        returnButton.setFont(new Font(Constants.FONTSTYLE, Font.BOLD, Constants.BUTTONFONTSIZE));
        gbc.insets = new Insets(Constants.MARGINS, Constants.MARGINS, Constants.MARGINS, Constants.MARGINS);
        addComponent(returnButton, 0, 2, 2, GridBagConstraints.CENTER, gbc);

        // Action Listeners
        returnButton.addActionListener(evt -> {
            System.out.println("Return to Main Menu button clicked");
            // Call controller action (if implemented)
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SummaryState state = (SummaryState) evt.getNewValue();
        resultText.setText("You got: " + state.getNumberOfCorrectAnswers() + " / " + state.getNumberOfQuestions());
    }

    // Main method to test SummaryView
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Summary View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT);
        frame.setLocationRelativeTo(null);

        final SummaryView summaryView = new SummaryView(new SummaryViewModel());
        frame.add(summaryView);
        frame.setVisible(true);
    }

}
