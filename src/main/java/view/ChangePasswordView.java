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
import interface_adapter.logout.LogoutController;

/**
 * The View for changing the password.
 */
public class ChangePasswordView extends JPanel implements PropertyChangeListener {

    private final String viewName = "change password";
    private final ChangePasswordViewModel changePasswordViewModel;
    private final JLabel passwordErrorField = new JLabel();
    private ChangePasswordController changePasswordController;
    private LogoutController logoutController;

    private final JLabel usernameLabel;
    private final JTextField passwordInputField = new JTextField(15);
    private final JButton cancelButton;
    private final JButton changePasswordButton;

    public ChangePasswordView(ChangePasswordViewModel changePasswordViewModel) {
        this.changePasswordViewModel = changePasswordViewModel;
        this.changePasswordViewModel.addPropertyChangeListener(this);

        // Set up the layout and background color
        this.setLayout(new GridBagLayout());
        this.setBackground(Constants.BGCOLOUR);
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(Constants.MARGINS, Constants.MARGINS, Constants.MARGINS, Constants.MARGINS);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        final JLabel title = new JLabel(ChangePasswordViewModel.CHANGE_PASSWORD_LABEL, SwingConstants.CENTER);
        title.setFont(new Font(Constants.FONTSTYLE, Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(title, gbc);

        // Username information
        gbc.gridy++;
        gbc.gridwidth = 1;
        final JLabel usernameInfoLabel = new JLabel("Currently logged in: ");
        usernameInfoLabel.setFont(new Font(Constants.FONTSTYLE, Font.PLAIN, 14));
        usernameInfoLabel.setForeground(Color.WHITE);
        this.add(usernameInfoLabel, gbc);

        usernameLabel = new JLabel();
        usernameLabel.setFont(new Font(Constants.FONTSTYLE, Font.BOLD, 14));
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 1;
        this.add(usernameLabel, gbc);

        // Password field
        gbc.gridy++;
        gbc.gridx = 0;
        final JLabel passwordLabel = new JLabel("New Password:");
        passwordLabel.setFont(new Font(Constants.FONTSTYLE, Font.PLAIN, 14));
        passwordLabel.setForeground(Color.WHITE);
        this.add(passwordLabel, gbc);

        gbc.gridx = 1;
        this.add(passwordInputField, gbc);

        // Error message
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        passwordErrorField.setForeground(Color.RED);
        passwordErrorField.setFont(new Font(Constants.FONTSTYLE, Font.ITALIC, 12));
        this.add(passwordErrorField, gbc);

        // Buttons
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Constants.BGCOLOUR);

        cancelButton = new JButton("Cancel");
        changePasswordButton = new JButton(ChangePasswordViewModel.CHANGE_PASSWORD_LABEL);

        buttonPanel.add(cancelButton);
        buttonPanel.add(changePasswordButton);
        this.add(buttonPanel, gbc);

        // Document listener for password input field
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final ChangePasswordState currentState = changePasswordViewModel.getState();
                currentState.setPassword(passwordInputField.getText());
                changePasswordViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        // Action listeners
        changePasswordButton.addActionListener(evt -> {
            final ChangePasswordState currentState = changePasswordViewModel.getState();
            changePasswordController.execute(currentState.getUsername(), currentState.getPassword());
        });

        cancelButton.addActionListener(evt -> changePasswordController.switchToMainMenuView());
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
