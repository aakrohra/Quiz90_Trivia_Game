package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import app.Constants;
import interface_adapter.change_password.ChangePasswordState;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordViewModel;

/**
 * The View for changing the password.
 */
public class ChangePasswordView extends JPanel implements PropertyChangeListener {

    private final String viewName = "change password";
    private final ChangePasswordViewModel changePasswordViewModel;
    private ChangePasswordController changePasswordController;

    private final JLabel usernameLabel;
    private final JTextField passwordInputField;
    private final JButton cancelButton;
    private final JButton changePasswordButton;

    public ChangePasswordView(ChangePasswordViewModel changePasswordViewModel) {
        this.changePasswordViewModel = changePasswordViewModel;
        this.changePasswordViewModel.addPropertyChangeListener(this);

        // Set layout and background
        this.setLayout(new GridBagLayout());
        this.setBackground(Constants.BGCOLOUR);
        final GridBagConstraints gbc = createGbc();

        // Title
        final JLabel title = createLabel(ChangePasswordViewModel.CHANGE_PASSWORD_LABEL,
                new Font(Constants.FONTSTYLE, Font.BOLD, 20),
                SwingConstants.CENTER, Color.WHITE);
        addComponent(title, 0, 0, 2, GridBagConstraints.CENTER, gbc);

        // Username information
        final JLabel usernameInfoLabel = createLabel("Currently logged in: ",
                new Font(Constants.FONTSTYLE, Font.PLAIN, 14),
                SwingConstants.LEFT, Color.WHITE);
        addComponent(usernameInfoLabel, 0, 1, 1, GridBagConstraints.WEST, gbc);

        usernameLabel = createLabel("", new Font(Constants.FONTSTYLE, Font.BOLD, 14),
                SwingConstants.LEFT, Color.WHITE);
        addComponent(usernameLabel, 1, 1, 1, GridBagConstraints.WEST, gbc);

        // Password field
        final JLabel passwordLabel = createLabel("New Password:",
                new Font(Constants.FONTSTYLE, Font.PLAIN, 14),
                SwingConstants.LEFT, Color.WHITE);
        addComponent(passwordLabel, 0, 2, 1, GridBagConstraints.WEST, gbc);

        passwordInputField = new JTextField(15);
        addComponent(passwordInputField, 1, 2, 1, GridBagConstraints.WEST, gbc);

        // Buttons
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Constants.BGCOLOUR);

        cancelButton = new JButton("Cancel");
        changePasswordButton = new JButton(ChangePasswordViewModel.CHANGE_PASSWORD_LABEL);

        buttonPanel.add(changePasswordButton);
        buttonPanel.add(cancelButton);
        addComponent(buttonPanel, 0, Constants.THREE, 2, GridBagConstraints.CENTER, gbc);

        // Add listeners
        setupListeners();
    }

    private void setupListeners() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                final ChangePasswordState currentState = changePasswordViewModel.getState();
                currentState.setPassword(passwordInputField.getText());
                changePasswordViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateState();
            }
        });

        changePasswordButton.addActionListener(evt -> {
            final ChangePasswordState currentState = changePasswordViewModel.getState();
            changePasswordController.execute(currentState.getUsername(), currentState.getPassword());
        });

        cancelButton.addActionListener(evt -> changePasswordController.switchToMainMenuView());
    }

    private GridBagConstraints createGbc() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(Constants.MARGINS, Constants.MARGINS, Constants.MARGINS, Constants.MARGINS);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private JLabel createLabel(String text, Font font, int alignment, Color color) {
        final JLabel label = new JLabel(text, alignment);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private void addComponent(Component comp, int x_axis, int y_axis, int width, int anchor, GridBagConstraints gbc) {
        gbc.gridx = x_axis;
        gbc.gridy = y_axis;
        gbc.gridwidth = width;
        gbc.anchor = anchor;
        this.add(comp, gbc);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final ChangePasswordState state = (ChangePasswordState) evt.getNewValue();
            usernameLabel.setText(state.getUsername());
        } else if (evt.getPropertyName().equals("password")) {
            final ChangePasswordState state = (ChangePasswordState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, "Password updated for " + state.getUsername());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setChangePasswordController(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Change Password");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT);
        frame.setLocationRelativeTo(null);

        final ChangePasswordView changePasswordView = new ChangePasswordView(new ChangePasswordViewModel());
        frame.add(changePasswordView);
        frame.setVisible(true);
    }
}
