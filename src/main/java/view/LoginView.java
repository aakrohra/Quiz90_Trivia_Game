package view;

// TODO fix order of imports
import app.Constants;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jetbrains.annotations.NotNull;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements PropertyChangeListener {

    private final JTextField usernameField = new JTextField(30);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordField = new JPasswordField(30);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton logIn;
    private final JButton cancel;
    private LoginController loginController;

    public LoginView(LoginViewModel loginViewModel) {
        loginViewModel.addPropertyChangeListener(this);

        final TitlePanel titlePanel = new TitlePanel(LoginViewModel.TITLE_LABEL);

        final JPanel usernamePanel = usernamePanelHelper();
        final JPanel passwordPanel = passwordPanelHelper();
        final JPanel infoPanel = infoPanelHelper(usernamePanel, passwordPanel);
        final JPanel errorPanel = errorPanelHelper();

        final JPanel buttons = new JPanel();
        logIn = new CustomButton("Log in");
        cancel = new CustomButton("Cancel");
        buttonsHelper(buttons);

        actionAndDocumentListeners(loginViewModel);

        assembleFinalPanel(titlePanel, infoPanel, errorPanel, buttons);
    }

    @NotNull
    private JPanel usernamePanelHelper() {
        final JPanel usernamePanel = new JPanel();
        usernamePanel.add(usernameField);
        usernameField.setText("Enter your username here...");
        usernameField.setForeground(Color.GRAY);
        usernamePanel.setBackground(Constants.BGCOLOUR);
        return usernamePanel;
    }

    @NotNull
    private JPanel passwordPanelHelper() {
        final JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordField);
        passwordPanel.setBackground(Constants.BGCOLOUR);
        return passwordPanel;
    }

    @NotNull
    private static JPanel infoPanelHelper(JPanel usernamePanel, JPanel passwordPanel) {
        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(usernamePanel);
        infoPanel.add(passwordPanel);
        return infoPanel;
    }

    private void buttonsHelper(JPanel buttons) {
        buttons.setBackground(Constants.BGCOLOUR);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.add(Box.createHorizontalGlue());
        buttons.add(logIn);
        buttons.add(horizontalSpacer());
        buttons.add(cancel);
        buttons.add(Box.createHorizontalGlue());
    }

    private void actionAndDocumentListeners(LoginViewModel tempLoginViewModel) {
        usernameFieldListener(tempLoginViewModel);
        passwordFieldListener(tempLoginViewModel);
        logInActionListener(tempLoginViewModel);
        cancelActionListener();
    }

    @NotNull
    private JPanel errorPanelHelper() {
        final JPanel errorPanel = new JPanel();
        errorPanel.setBackground(Constants.BGCOLOUR);
        errorPanel.add(usernameErrorField);
        errorPanel.add(passwordErrorField);
        return errorPanel;
    }

    private void usernameFieldListener(LoginViewModel tempLoginViewModel) {
        usernameFieldInfoListener(tempLoginViewModel);
        usernameFieldFocusAndInterface();
    }

    private void usernameFieldInfoListener(LoginViewModel tempLoginViewModel) {
        usernameField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = tempLoginViewModel.getState();
                currentState.setUsername(usernameField.getText());
                tempLoginViewModel.setState(currentState);
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
    }

    private void usernameFieldFocusAndInterface() {
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals(Constants.USERNAMEPLACEHOLDER)) {
                    usernameField.setText("");
                    // Set text color when typing
                    usernameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restore placeholder text if the field is empty
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText(Constants.USERNAMEPLACEHOLDER);
                    // Set placeholder text color
                    usernameField.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void passwordFieldListener(LoginViewModel tempLoginViewModel) {
        passwordFieldInfoListener(tempLoginViewModel);
        passwordFieldFocusAndInterface();
    }

    private void passwordFieldInfoListener(LoginViewModel tempLoginViewModel) {
        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final LoginState currentState = tempLoginViewModel.getState();
                currentState.setPassword(new String(passwordField.getPassword()));
                tempLoginViewModel.setState(currentState);
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
    }

    private void passwordFieldFocusAndInterface() {
        passwordField.setBackground(Color.WHITE);
        // Default echo character
        passwordField.setEchoChar('*');
        passwordField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                // Repaint to hide the placeholder when focused
                passwordField.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Repaint to show the placeholder if empty
                passwordField.repaint();
            }
        });
        // Custom paint component to display placeholder
        passwordField.setOpaque(true);
        // Ensures consistent background color
        passwordField.setBackground(Color.WHITE);
        passwordField.setUI(new javax.swing.plaf.basic.BasicPasswordFieldUI() {
            @Override
            protected void paintSafely(Graphics g) {
                super.paintSafely(g);
                if (passwordField.getPassword().length == 0 && !passwordField.hasFocus()) {
                    final Graphics2D g2 = (Graphics2D) g.create();
                    // Placeholder text color
                    g2.setColor(Color.GRAY);
                    // Position the placeholder text
                    g2.drawString("Enter your password here...", Constants.POSITIONX,
                            passwordField.getHeight() - Constants.POSITIONY);
                    g2.dispose();
                }
            }
        });
    }

    private void logInActionListener(LoginViewModel tempLoginViewModel) {
        logIn.addActionListener(evt -> {
            if (evt.getSource().equals(logIn)) {
                final LoginState currentState = tempLoginViewModel.getState();
                loginController.execute(
                        currentState.getUsername(),
                        currentState.getPassword()
                );
            }
        });
    }

    private void cancelActionListener() {
        cancel.addActionListener(evt -> loginController.switchToSignupView());
    }

    private void assembleFinalPanel(TitlePanel titlePanel, JPanel infoPanel, JPanel errorPanel, JPanel buttons) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalGlue());
        this.add(verticalSpacer());
        this.add(titlePanel);
        this.add(verticalSpacer());
        this.add(infoPanel);
        this.add(errorPanel);
        this.add(verticalSpacer());
        this.add(buttons);
        this.add(verticalSpacer());
        this.add(Box.createVerticalGlue());
        this.setBackground(Constants.BGCOLOUR);
    }

    private Component horizontalSpacer() {
        return Box.createHorizontalStrut(Constants.STRUTSMALLSPACER);
    }

    private Component verticalSpacer() {
        return Box.createVerticalStrut(Constants.STRUTSMALLSPACER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        usernameErrorField.setText(state.getLoginError());
    }

    private void setFields(LoginState state) {
        usernameField.setText(state.getUsername());
        passwordField.setText(state.getPassword());
    }

    public String getViewName() {
        return "log in";
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
