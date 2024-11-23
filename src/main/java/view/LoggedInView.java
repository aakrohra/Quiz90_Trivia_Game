package view;

import app.Constants;
import interface_adapter.access_quiz.AccessQuizController;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.local_multiplayer.LocalMultiplayerController;
import interface_adapter.logout.LogoutController;
import interface_adapter.quiz_generation.QuizGenerationController;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logged into the program.
 */
public class LoggedInView extends JPanel implements PropertyChangeListener {

    private ChangePasswordController changePasswordController;
    private LocalMultiplayerController localMultiplayerController;
    private AccessQuizController accessQuizController;
    private LogoutController logoutController;
    private QuizGenerationController quizGenerationController;

    private final JLabel username;
    private int userLength;

    private final JLabel sharedQuizKeyErrorField = new JLabel();

    private final JButton logOut;

    private final JButton normalPlay;
    private final JButton playSharedQuiz;
    private final JButton localMultiplayer;
    private final JButton changePassword;

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        loggedInViewModel.addPropertyChangeListener(this);

        final TitlePanel titlePanel = new TitlePanel(LoggedInViewModel.TITLE_LABEL);
        username = new JLabel();
        final CurrentPlayerPanel currentPlayerPanel = new CurrentPlayerPanel(username);

        final JPanel buttons0 = new ButtonPanel();
        normalPlay = new CustomButton("Normal Play");
        buttons0.add(normalPlay);

        final JPanel buttons1 = new ButtonPanel();
        final JPanel sharedQuizKeyFieldPanel = new JPanel();
        final JTextField sharedQuizKeyField = new JTextField(23);
        sharedQuizKeyField.setText("Enter quiz key...");
        sharedQuizKeyField.setForeground(Color.GRAY);
        sharedQuizKeyField.setPreferredSize(new Dimension(Constants.FIELDX, Constants.FIELDY));
        final JPanel playSharedQuizPanel = new JPanel();
        playSharedQuiz = new CustomButton("Play Shared Quiz");

        buttons1.add(Box.createHorizontalGlue());
        sharedQuizKeyFieldPanel.add(sharedQuizKeyField);
        buttons1.add(sharedQuizKeyFieldPanel);
        buttons1.add(Box.createHorizontalStrut(Constants.STRUTSMALLSPACER));
        playSharedQuizPanel.add(playSharedQuiz);
        buttons1.add(playSharedQuizPanel);
        buttons1.add(Box.createHorizontalGlue());

        final JPanel buttons2 = new ButtonPanel();
        final JButton createdQuizzes = new CustomButton("My Created Quizzes");
        localMultiplayer = new CustomButton("Local Multiplayer");

        buttons2.add(Box.createHorizontalGlue());
        buttons2.add(createdQuizzes);
        buttons2.add(Box.createHorizontalStrut(Constants.STRUTSMALLSPACER));
        buttons2.add(localMultiplayer);
        buttons2.add(Box.createHorizontalGlue());

        final JPanel buttons3 = new ButtonPanel();
        changePassword = new CustomButton("Change Password");
        logOut = new CustomButton("Log Out");

        buttons3.add(Box.createHorizontalGlue());
        buttons3.add(changePassword);
        buttons3.add(Box.createHorizontalStrut(Constants.STRUTSMALLSPACER));
        buttons3.add(logOut);
        buttons3.add(Box.createHorizontalGlue());

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
                        this.accessQuizController.execute(currentState.getQuizKey());
                    }
                }
        );

        createdQuizzes.addActionListener(
                evt -> {
                    if (evt.getSource().equals(changePassword)) {
                        final LoggedInState currentState = loggedInViewModel.getState();

//                        TODO implement myCreatedQuizzesController
//                        this.myCreatedQuizzesController.execute();
                    }
                }
        );

        changePassword.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(changePassword)) {
                        final LoggedInState currentState = loggedInViewModel.getState();
                        this.changePasswordController.execute(
                                currentState.getUsername(),
                                currentState.getPassword()
                        );
                    }
                }
        );

        logOut.addActionListener(evt -> {
            if (evt.getSource().equals(logOut)) {
                // 1. get the state out of the loggedInViewModel. It contains the username.
                // 2. Execute the logout Controller.
                final LoggedInState state = loggedInViewModel.getState();
                final String user = state.getUsername();
                logoutController.execute(user);
            }
        });

        normalPlay.addActionListener(evt -> {
            if (evt.getSource().equals(normalPlay)) {
                quizGenerationController.switchToQuizGenerationView();
            }
        });

        localMultiplayer.addActionListener(evt -> {
            if (evt.getSource().equals(localMultiplayer)) {
                localMultiplayerController.switchToLocalMultiplayerView();
            }
        });

        final JPanel keyErrorPanel = new JPanel();
        keyErrorPanel.add(sharedQuizKeyErrorField);
        keyErrorPanel.setBackground(Color.green);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(Box.createVerticalGlue());
        this.add(verticalSpacer());
        this.add(titlePanel);
        this.add(verticalSpacer());
        this.add(currentPlayerPanel);
        this.add(verticalSpacer());
        this.add(buttons0);
        this.add(verticalSpacer());
        this.add(buttons1);
        this.add(verticalSpacer());
        this.add(buttons2);
        this.add(verticalSpacer());
        this.add(buttons3);
        this.add(verticalSpacer());
        this.add(Box.createVerticalGlue());
        this.setBackground(Constants.BGCOLOUR);
    }

    @NotNull
    private static Component verticalSpacer() {
        return Box.createVerticalStrut(Constants.STRUTSMALLSPACER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
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
