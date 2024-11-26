package view;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import app.Constants;
import interface_adapter.access_database.AccessDatabaseController;
import interface_adapter.access_quiz.AccessQuizController;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.local_multiplayer.LocalMultiplayerController;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.quiz_generation.QuizGenerationController;
import org.jetbrains.annotations.NotNull;

/**
 * The View for when the user is logged into the program.
 */
public class LoggedInView extends JPanel implements PropertyChangeListener {

    private ChangePasswordController changePasswordController;
    private LocalMultiplayerController localMultiplayerController;
    private AccessQuizController accessQuizController;
    private LogoutController logoutController;
    private QuizGenerationController quizGenerationController;
    private AccessDatabaseController accessedDatabaseController;

    private final JLabel username;

    private final JLabel sharedQuizKeyErrorField;

    private final JButton logOut;

    private final JButton normalPlay;
    private final JButton playSharedQuiz;
    private final JButton localMultiplayer;
    private final JButton changePassword;
    private final JButton createdQuizzes;

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        loggedInViewModel.addPropertyChangeListener(this);

        final TitlePanel titlePanel = new TitlePanel(LoggedInViewModel.TITLE_LABEL);
        username = new JLabel();
        final CurrentPlayerPanel currentPlayerPanel = new CurrentPlayerPanel(username);

        final JPanel buttons0 = new ButtonPanel();
        normalPlay = new CustomButton("Normal Play");
        buttons0.add(Box.createHorizontalGlue());
        buttons0.add(normalPlay);
        buttons0.add(Box.createHorizontalGlue());

        final ButtonPanel buttons1 = new ButtonPanel();
        final JPanel sharedQuizKeyFieldPanel = new JPanel();
        sharedQuizKeyFieldPanel.setLayout(new BoxLayout(sharedQuizKeyFieldPanel, BoxLayout.Y_AXIS));
        sharedQuizKeyFieldPanel.setPreferredSize(new Dimension(Constants.BUTTON1WIDTH, Constants.BUTTON1HEIGHT));
        sharedQuizKeyFieldPanel.setBackground(Constants.BGCOLOUR);
        final JTextField sharedQuizKeyField = new JTextField(15);
        sharedQuizKeyField.setText("Enter quiz key...");
        sharedQuizKeyField.setForeground(Color.GRAY);
        sharedQuizKeyField.setMargin(new Insets(0, 10, 0, 0));
        sharedQuizKeyField.setMinimumSize(new Dimension(Integer.MAX_VALUE, Constants.FIELDY));
        sharedQuizKeyFieldPanel.add(sharedQuizKeyField);
        playSharedQuiz = new CustomButton("Play Shared Quiz");

        assembleButton1(buttons1, sharedQuizKeyFieldPanel);

        final JPanel buttons2 = new ButtonPanel();
        createdQuizzes = new CustomButton("My Created Quizzes");

        localMultiplayer = new CustomButton("Local Multiplayer");

        assemble2Buttons(buttons2, createdQuizzes, localMultiplayer);

        final JPanel buttons3 = new ButtonPanel();
        changePassword = new CustomButton("Change Password");
        logOut = new CustomButton("Log Out");

        sharedQuizKeyErrorField = new JLabel();

        assemble2Buttons(buttons3, changePassword, logOut);

        actionAndInterfaceHelpers(loggedInViewModel, sharedQuizKeyField, createdQuizzes);

        assembleFinalPanelHelper(titlePanel, currentPlayerPanel, buttons0, buttons1, buttons2, buttons3);
    }

    private void assemble2Buttons(JPanel buttonPanel, JButton createdQuizzes, JButton tempMultiplayerButton) {
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(createdQuizzes);
        buttonPanel.add(horizontalSpacer());
        buttonPanel.add(tempMultiplayerButton);
        buttonPanel.add(Box.createHorizontalGlue());
    }

    private void assembleButton1(ButtonPanel buttons1, JPanel sharedQuizKeyFieldPanel) {
        buttons1.add(Box.createHorizontalGlue());
        buttons1.add(sharedQuizKeyFieldPanel);
        buttons1.add(horizontalSpacer());
        buttons1.add(playSharedQuiz);
        buttons1.add(Box.createHorizontalGlue());
    }

    private void assembleFinalPanelHelper(TitlePanel titlePanel, CurrentPlayerPanel currentPlayerPanel, JPanel buttons0, JPanel buttons1, JPanel buttons2, JPanel buttons3) {

        this.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = createGbc();

        this.addComp(titlePanel, 0, 0, 2, GridBagConstraints.CENTER, gbc);
        this.addComp(currentPlayerPanel, 0, 1, 3, GridBagConstraints.CENTER, gbc);
        this.addComp(buttons0, 0, 2, 3, GridBagConstraints.CENTER, gbc);
        this.addComp(horizontalSpacer(), 0, 3, 0, GridBagConstraints.CENTER, gbc);
        this.addComp(buttons1, 0, 4, 2, GridBagConstraints.CENTER, gbc);
        this.addComp(buttons2, 0, 5, 2, GridBagConstraints.WEST, gbc);
        this.addComp(buttons3, 0, 6, 2, GridBagConstraints.WEST, gbc);

        this.setBackground(Constants.BGCOLOUR);
    }

    private GridBagConstraints createGbc() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void addComp(Component comp, int x_axis, int y_axis, int width, int anchor, GridBagConstraints gbc) {
        gbc.gridx = x_axis;
        gbc.gridy = y_axis;
        gbc.gridwidth = width;
        gbc.anchor = anchor;
        this.add(comp, gbc);
    }

    private void actionAndInterfaceHelpers(LoggedInViewModel loggedInViewModel, JTextField sharedQuizKeyField, JButton createdQuizzes) {
        sharedQuizKeyActionAndInterface(loggedInViewModel, sharedQuizKeyField);
        createdQuizzesAction(loggedInViewModel);
        changePasswordAction(loggedInViewModel);
        logOutAction(loggedInViewModel);
        normalPlayAction();
        localMultiplayerAction();
    }

    private void localMultiplayerAction() {
        localMultiplayer.addActionListener(evt -> {
            if (evt.getSource().equals(localMultiplayer)) {
                localMultiplayerController.switchToLocalMultiplayerView();
            }
        });
    }

    private void normalPlayAction() {
        normalPlay.addActionListener(evt -> {
            if (evt.getSource().equals(normalPlay)) {
                quizGenerationController.switchToQuizGenerationView();
            }
        });
    }

    private void logOutAction(LoggedInViewModel loggedInViewModel) {
        logOut.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(logOut)) {
                        // 1. get the state out of the loggedInViewModel. It contains the username.
                        // 2. Execute the logout Controller.
                        final LoggedInState state = loggedInViewModel.getState();
                        final String user = state.getUsername();
                        logoutController.execute(user);
                    }
                }
        );
    }

    private void createdQuizzesAction(LoggedInViewModel loggedInViewModel) {
        createdQuizzes.addActionListener(
                evt -> {
                    if (evt.getSource().equals(createdQuizzes)) {
                        System.out.println("button");
                        final LoggedInState currentState = loggedInViewModel.getState();
                        final String user = currentState.getUsername();
                        System.out.println("user: " + user);
                        accessedDatabaseController.execute(user);
                    }
                }
        );
    }

    private void changePasswordAction(LoggedInViewModel loggedInViewModel) {
        changePassword.addActionListener(
                evt -> {
                    if (evt.getSource().equals(changePassword)) {
                        final LoggedInState currentState = loggedInViewModel.getState();

                        changePasswordController.switchToPasswordView(
                                currentState.getPassword(), currentState.getUsername());
                    }
                }
        );
    }

    private void sharedQuizKeyActionAndInterface(LoggedInViewModel loggedInViewModel, JTextField sharedQuizKeyField) {
        sharedQuizKeyField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final LoggedInState currentState = loggedInViewModel.getState();
                currentState.setQuizKey(sharedQuizKeyField.getText());
                loggedInViewModel.setState(currentState);
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

        sharedQuizKeyField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (sharedQuizKeyField.getText().equals("Enter quiz key...")) {
                    sharedQuizKeyField.setText("");
                    // Set text color when typing
                    sharedQuizKeyField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restore placeholder text if the field is empty
                if (sharedQuizKeyField.getText().isEmpty()) {
                    sharedQuizKeyField.setText("Enter quiz key...");
                    // Set placeholder text color
                    sharedQuizKeyField.setForeground(Color.GRAY);
                }
            }
        });

        playSharedQuiz.addActionListener(
                evt -> {
                    if (evt.getSource().equals(playSharedQuiz)) {
                        final LoggedInState currentState = loggedInViewModel.getState();
                        this.accessQuizController.accessQuizFromKey(currentState.getQuizKey());
                    }
                }
        );
    }

    @NotNull
    private static Component horizontalSpacer() {
        return Box.createHorizontalStrut(Constants.STRUTSMALLSPACER * 3);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("HELLO");
        if (evt.getPropertyName().equals("state")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            username.setText(state.getUsername());
        }
        final LoggedInState state = (LoggedInState) evt.getNewValue();
        if (state.getQuizKeyError() != null) {
            JOptionPane.showMessageDialog(this, state.getQuizKeyError());
        }
    }

    public String getViewName() {
        return "logged in";
    }

    public void setChangePasswordController(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    public void setAccessedQuizDatabaseController(AccessDatabaseController accessedDatabaseController) {
        this.accessedDatabaseController = accessedDatabaseController;
    }

    public void setAccessQuizController(AccessQuizController accessQuizController) {
        this.accessQuizController = accessQuizController;
    }

    public void setQuizGenerationController(QuizGenerationController quizGenerationController) {
        this.quizGenerationController = quizGenerationController;
    }

    public void setLocalMultiplayerController(LocalMultiplayerController localMultiplayerController) {
        this.localMultiplayerController = localMultiplayerController;
    }

    // Main method to run and test the MainMenuView in a JFrame
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT));
        frame.setLocationRelativeTo(null);

        final LoggedInView loggedInView = new LoggedInView(new LoggedInViewModel());
        frame.add(loggedInView);
        frame.setVisible(true);
    }
}
