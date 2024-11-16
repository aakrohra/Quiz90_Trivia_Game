package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    private final JTextField usernameField = new JTextField(30);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordField = new JPasswordField(30);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton logIn;
    private final JButton cancel;
    private LoginController loginController;

    public LoginView(LoginViewModel loginViewModel) {

        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        final PanelBox titlePanelBox = new PanelBox(new JPanel(), Box.createHorizontalBox());
        final JLabel title = new JLabel(LoginViewModel.TITLE_LABEL);
        titlePanelBox.setBackground(new Color(0, 71, 171));
        titlePanelBox.add(title);
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel usernamePanel = new JPanel();
        usernamePanel.add(usernameField);
        usernameField.setText("Enter your username here...");
        usernameField.setForeground(Color.GRAY);
        usernamePanel.setBackground(new Color(0, 71, 171));

        final JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordField);
        passwordPanel.setBackground(new Color(0, 71, 171));

        final JPanel buttons = new JPanel();
        logIn = new JButton("Log in");
        cancel = new JButton("Cancel");
        buttons.setBackground(new Color(0, 71, 171));

        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.add(Box.createHorizontalGlue());
        buttonsSizeHelper(logIn);
        buttons.add(logIn);
        buttons.add(Box.createHorizontalStrut(100));
        buttonsSizeHelper(cancel);
        buttons.add(cancel);
        buttons.add(Box.createHorizontalGlue());

        logIn.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logIn)) {
                            final LoginState currentState = loginViewModel.getState();

                            loginController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword()
                            );
                        }
                    }
                }
        );

        cancel.addActionListener(this);

        usernameField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameField.getText());
                loginViewModel.setState(currentState);
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

        passwordField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordField.getPassword()));
                loginViewModel.setState(currentState);
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

        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Enter your username here...")) {
                    usernameField.setText("");
                    // Set text color when typing
                    usernameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restore placeholder text if the field is empty
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText("Enter your username here...");
                    // Set placeholder text color
                    usernameField.setForeground(Color.GRAY);
                }
            }
        });

        passwordField.setBackground(Color.WHITE);
        // Default echo character
        passwordField.setEchoChar('\u2022');

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
                    g2.drawString("Enter your password here...", 5, passwordField.getHeight() - 7);
                    g2.dispose();
                }
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(Box.createVerticalGlue());
        this.add(Box.createVerticalStrut(10));
        this.add(titlePanelBox);
        this.add(Box.createVerticalStrut(10));
        this.add(usernamePanel);
        this.add(usernameErrorField);
        this.add(passwordPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(buttons);
        this.add(Box.createVerticalStrut(10));
        this.add(Box.createVerticalGlue());

        this.setBackground(new Color(0, 71, 171));
    }

    private void buttonsSizeHelper(JButton jbutton) {
        jbutton.setPreferredSize(new Dimension(250, 200));
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        loginController.switchToSignupView();
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
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
