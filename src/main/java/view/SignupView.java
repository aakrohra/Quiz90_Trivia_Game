package view;

import app.Constants;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

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
 * The View for the Signup Use Case.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private SignupController signupController;

    private final JButton cancel;
    private final JButton toLogin;

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        final TitlePanel titlePanel = new TitlePanel(SignupViewModel.TITLE_LABEL);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.USERNAME_LABEL), usernameInputField);
        usernameInfo.setBackground(Constants.BGCOLOUR);

        final LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.PASSWORD_LABEL), passwordInputField);
        passwordInfo.setBackground(Constants.BGCOLOUR);
        final LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordInputField);
        repeatPasswordInfo.setBackground(new Color(0, 71, 171));

        final JPanel buttons0 = new JPanel();
        buttons0.setBackground(Constants.BGCOLOUR);
        final JButton signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);

        buttons0.setLayout(new BoxLayout(buttons0, BoxLayout.X_AXIS));
        buttons0.add(Box.createHorizontalGlue());
        buttonsSizeHelper(signUp);
        buttons0.add(signUp);
        buttons0.add(Box.createHorizontalGlue());

        final JPanel buttons1 = new JPanel();
        buttons1.setBackground(new Color(0, 71, 171));
        toLogin = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
        cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);

        buttons1.setLayout(new BoxLayout(buttons1, BoxLayout.X_AXIS));
        buttons1.add(Box.createHorizontalStrut(300));
        buttonsSizeHelper(toLogin);
        buttons1.add(toLogin);
        buttons1.add(Box.createHorizontalGlue());
        buttonsSizeHelper(cancel);
        buttons1.add(cancel);
        buttons1.add(Box.createHorizontalStrut(300));

        signUp.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            final SignupState currentState = signupViewModel.getState();

                            signupController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword(),
                                    currentState.getRepeatPassword()
                            );
                        }
                    }
                }
        );

        toLogin.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        signupController.switchToLoginView();
                    }
                }
        );

        cancel.addActionListener(this);

        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(Box.createVerticalGlue());
        this.add(Box.createVerticalStrut(10));
        this.add(titlePanel);
        this.add(Box.createVerticalStrut(30));
        this.add(usernameInfo);
        this.add(Box.createVerticalStrut(10));
        this.add(passwordInfo);
        this.add(Box.createVerticalStrut(10));
        this.add(repeatPasswordInfo);
        this.add(Box.createVerticalStrut(50));
        this.add(buttons0);
        this.add(Box.createVerticalGlue());
        this.add(buttons1);
        this.add(Box.createVerticalStrut(10));
        this.add(Box.createVerticalGlue());

        this.setBackground(new Color(0, 71, 171));
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
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

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
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

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(currentState);
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

    private void buttonsSizeHelper(JButton jbutton) {
        jbutton.setPreferredSize(new Dimension(250, 200));
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(cancel)) {
            System.exit(0);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}
