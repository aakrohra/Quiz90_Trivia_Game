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
 * The View for when the user is logged into the program.
 */
public class ChangePasswordView extends JPanel implements PropertyChangeListener {

    private final String viewName = "Change Password";
    private final ChangePasswordViewModel changePasswordViewModel;
    private final JLabel passwordErrorField = new JLabel();
    private ChangePasswordController changePasswordController;

    private final JLabel username;

    private final JButton cancel;

    private final JTextField passwordInputField;
    private final JButton changePassword;

    public ChangePasswordView(ChangePasswordViewModel changePasswordViewModel) {
        this.changePasswordViewModel = changePasswordViewModel;
        this.changePasswordViewModel.addPropertyChangeListener(this);

        // Title
        final JLabel title = createLabel("Change Password Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username Info
        final JLabel usernameInfo = createLabel("Currently logged in: ");
        usernameInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        username = new JLabel();
        username.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Text Field
        passwordInputField = new JTextField(15);
        passwordInputField.setPreferredSize(new Dimension(Constants.TEXTPANELWIDTH,
                Constants.TEXTPANELHEIGHT / 3));
        passwordInputField.setFont(new Font(Constants.FONTSTYLE, Font.PLAIN, Constants.BUTTONFONTSIZE));

        // Password Input
        final JLabel passwordInfoLabel = createLabel("Password");
        final LabelTextPanel passwordInfo = new LabelTextPanel(passwordInfoLabel, passwordInputField);
        passwordInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons Panel
        final JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);

        cancel = createButton("Cancel");
        changePassword = createButton("Change Password");

        buttons.add(Box.createHorizontalGlue());
        buttons.add(changePassword);
        buttons.add(Box.createHorizontalStrut(Constants.BUTTONMARGIN));
        buttons.add(cancel);
        buttons.add(Box.createHorizontalGlue());

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(usernameInfo);
        this.add(username);
        this.add(passwordInfo);
        this.add(buttons);

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

        changePassword.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(changePassword)) {
                        final ChangePasswordState currentState = changePasswordViewModel.getState();

                        this.changePasswordController.execute(
                                currentState.getUsername(),
                                currentState.getPassword()
                        );
                    }
                }
        );

        cancel.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(cancel)) {
                        changePasswordController.switchToMainMenuView();
                    }
                }
        );
        this.add(Box.createVerticalGlue());
        this.add(title);
        this.add(usernameInfo);
        this.add(username);
        this.add(passwordInfo);
        this.add(passwordErrorField);
        this.add(buttons);
        this.add(Box.createVerticalGlue());
    }

    private JButton createButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(new Font(Constants.FONTSTYLE, Font.BOLD, Constants.BUTTONFONTSIZE / Constants.THREE * 2));
        button.setForeground(Color.BLACK);
        return button;
    }

    private JLabel createLabel(String text) {
        final JLabel label = new JLabel(text);
        label.setFont(new Font(Constants.FONTSTYLE, Font.BOLD, Constants.BUTTONFONTSIZE));
        label.setForeground(Color.BLACK);
        return label;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final ChangePasswordState state = (ChangePasswordState) evt.getNewValue();
            username.setText(state.getUsername());
        }
        else if (evt.getPropertyName().equals("password")) {
            final ChangePasswordState state = (ChangePasswordState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, "Password updated for " + state.getUsername());
        }

    }

    public String getViewName() {
        return viewName;
    }

    public void setChangePasswordController(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Quiz Generation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT);
        frame.setLocationRelativeTo(null);

        final ChangePasswordView changePasswordView = new ChangePasswordView(new ChangePasswordViewModel());
        frame.add(changePasswordView);
        frame.setVisible(true);
    }
}

