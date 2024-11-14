package view;

import app.Constants;
import interface_adapter.access_quiz.AccessQuizController;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.local_multiplayer.LocalMultiplayerController;
import interface_adapter.login.LoginState;
import interface_adapter.logout.LogoutController;
import use_case.myCreatedQuizzes.MyCreatedQuizzesController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
public class LoggedInMainMenuView extends JPanel implements PropertyChangeListener {

    private final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    private final JLabel passwordErrorField = new JLabel();
    private ChangePasswordController changePasswordController;
    private LocalMultiplayerController localMultiplayerController;
    private MyCreatedQuizzesController myCreatedQuizzesController;
    private AccessQuizController accessQuizController;
    private LogoutController logoutController;

    private final JLabel username;

    private final JTextField sharedQuizKeyField = new JTextField(23);
    private final JLabel sharedQuizKeyErrorField = new JLabel();

    private final JButton logOut;

    private final JButton normalPlay;
    private final JButton playSharedQuiz;
    private final JButton createdQuizzes;
    private final JButton localMultiplayer;
    private final JButton changePassword;

    public LoggedInMainMenuView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

//        final int WINDOW_WIDTH = this.getWidth(); // TODO implement this properly
//        final int WINDOW_HEIGHT = this.getHeight(); // TODO implement this properly

        final PanelBox titlePanelBox = new PanelBox(new JPanel(), Box.createHorizontalBox());
        titlePanelBox.setBackground(new Color(0, 0, 171));
        final JLabel title = new JLabel(LoggedInViewModel.TITLE_LABEL);
        titlePanelBox.add(title);
        title.setBorder(new EmptyBorder(20, 0, 0, 0));
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setSize(new Dimension(10, 20));

        final JPanel currentPlayerPanel = new JPanel();
        final Box currentPlayerBox = Box.createHorizontalBox();
        currentPlayerPanel.setBackground(new Color(0, 71, 0));
        currentPlayerPanel.setPreferredSize(new Dimension(1200, 100));
        currentPlayerBox.setOpaque(true);
        currentPlayerBox.setBackground(Color.WHITE);
        currentPlayerBox.setBorder(BorderFactory.createEmptyBorder(10, 35, 10, 35));
        final JLabel player = new JLabel("Current Player: ");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentPlayerPanel.add(currentPlayerBox);
        currentPlayerBox.add(player);
        username = new JLabel();
        currentPlayerBox.add(username);

        final JPanel buttons0 = new JPanel();
        normalPlay = new JButton("Normal Play");
        buttons0.setBackground(new Color(0, 71, 171));

        buttons0.setLayout(new BoxLayout(buttons0, BoxLayout.X_AXIS));
        buttons0.add(Box.createHorizontalGlue());
        buttonsSizeHelper(normalPlay);
        buttons0.add(normalPlay);
        buttons0.add(Box.createHorizontalGlue());

        final JPanel buttons1 = new JPanel();
        sharedQuizKeyField.setText("Enter quiz key...");
        sharedQuizKeyField.setForeground(Color.GRAY);
        sharedQuizKeyField.setMaximumSize(new Dimension(300, 30));
        playSharedQuiz = new JButton("Play Shared Quiz");
        buttons1.setBackground(new Color(0, 71, 171));

        buttons1.setLayout(new BoxLayout(buttons1, BoxLayout.X_AXIS));
        buttons1.add(Box.createHorizontalGlue());
        buttons1.add(sharedQuizKeyField);
        buttons1.add(Box.createHorizontalStrut(200));
        buttonsSizeHelper(playSharedQuiz);
        buttons1.add(playSharedQuiz);
        buttons1.add(Box.createHorizontalGlue());

        final JPanel buttons2 = new JPanel();
        createdQuizzes = new JButton("My Created Quizzes");
        localMultiplayer = new JButton("Local Multiplayer");
        buttons2.setBackground(new Color(0, 71, 0));

        buttons2.setLayout(new BoxLayout(buttons2, BoxLayout.X_AXIS));
        buttons2.add(Box.createHorizontalGlue());
        buttonsSizeHelper(createdQuizzes);
        buttons2.add(createdQuizzes);
        buttons2.add(Box.createHorizontalStrut(200));
        buttonsSizeHelper(localMultiplayer);
        buttons2.add(localMultiplayer);
        buttons2.add(Box.createHorizontalGlue());

        final JPanel buttons3 = new JPanel();
        changePassword = new JButton("Change Password");
        logOut = new JButton("Log Out");
        buttons3.setBackground(new Color(0, 71, 171));

        buttons3.setLayout(new BoxLayout(buttons3, BoxLayout.X_AXIS));
        buttons3.add(Box.createHorizontalGlue());
        buttonsSizeHelper(changePassword);
        buttons3.add(changePassword);
        buttons3.add(Box.createHorizontalStrut(200));
        buttonsSizeHelper(logOut);
        buttons3.add(logOut);
        buttons3.add(Box.createHorizontalGlue());

        sharedQuizKeyField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final LoggedInState currentState = loggedInViewModel.getState();
                currentState.setQuizKey(sharedQuizKeyField.getText());
                loggedInViewModel.setState(currentState);
                System.out.println(currentState.getQuizKey());
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

                        System.out.println(currentState.getQuizKey());
                        this.accessQuizController.execute(currentState.getQuizKey());
                    }
                }
        );

        localMultiplayer.addActionListener(
                evt -> {
                    if (evt.getSource().equals(changePassword)) {
                        final LoggedInState currentState = loggedInViewModel.getState();

//                        TODO implement localMultiplayerController
//                        this.localMultiplayerController.execute();
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
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(Box.createVerticalGlue());
        this.add(Box.createVerticalStrut(20));
        this.add(titlePanelBox);
        this.add(Box.createVerticalStrut(20));
        this.add(currentPlayerPanel);
        this.add(buttons0);
        this.add(Box.createVerticalStrut(20));
        this.add(buttons1);
        this.add(sharedQuizKeyErrorField);
        this.add(Box.createVerticalStrut(20));
        this.add(buttons2);
        this.add(Box.createVerticalStrut(20));
        this.add(buttons3);
        this.add(Box.createVerticalStrut(20));
        this.add(Box.createVerticalGlue());

        this.setBackground(new Color(0, 71, 171));
    }

    private void buttonsSizeHelper(JButton jbutton) {
        jbutton.setPreferredSize(new Dimension(250, 200));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            username.setText(state.getUsername());
        }
        else if (evt.getPropertyName().equals("password")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, "password updated for " + state.getUsername());
        }
    }

    public String getViewName() {
        return viewName;
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

    // Main method to run and test the MainMenuView in a JFrame
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1200, 500));
        frame.setLocationRelativeTo(null);

        final LoggedInMainMenuView loggedInMainMenuView = new LoggedInMainMenuView(new LoggedInViewModel());
        frame.add(loggedInMainMenuView);
        frame.setVisible(true);
    }
}
