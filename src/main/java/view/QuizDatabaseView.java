package view;

import app.Constants;
import entity.Database;
import entity.Quiz;
import interface_adapter.access_database.AccessDatabaseController;
import interface_adapter.access_database.AccessedDatabaseInfoState;
import interface_adapter.access_database.AccessedDatabaseInfoViewModel;
import interface_adapter.access_quiz.AccessedQuizInfoState;
import interface_adapter.logged_in.LoggedInViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class QuizDatabaseView extends JPanel implements PropertyChangeListener {

    private final String viewName = "access database";

    private AccessDatabaseController accessDatabaseController;

    private final AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel;
    GridBagConstraints c = new GridBagConstraints();
    final JPanel searchPanel = new JPanel();
    final JTextField searchKeyField = new JTextField();
    final JButton searchKeyButton = new JButton("Search");
    final JTextField searchTitleField = new JTextField();
    final JButton searchTitleButton = new JButton("Search");
    final JButton resetButton = new JButton("Reset");
    final JButton mainMenuButton = new JButton("Main Menu");
    final Dimension windowSize = new Dimension(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT);
    private JButton createQuizButton = new JButton("Create New Quiz");
    private Database database;


    public QuizDatabaseView(AccessedDatabaseInfoViewModel accessDatabaseViewModel) {
        this.accessedDatabaseInfoViewModel = accessDatabaseViewModel;
        accessedDatabaseInfoViewModel.addPropertyChangeListener(this);
        this.setBackground(Constants.BGCOLOUR);

        searchPanel.setLayout(new GridBagLayout());

        System.out.println(windowSize);
        int windowWidth = windowSize.width;
        int windowHeight = windowSize.height;
        searchPanel.setPreferredSize(new Dimension((int) (windowWidth * 0.9), 60));

        searchKeyField.setPreferredSize(new Dimension((int) (windowWidth * 0.6), 30));
        searchTitleField.setPreferredSize(new Dimension((int) (windowWidth * 0.6), 30));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        searchPanel.add(searchKeyField, c);

        searchKeyField.setText("Enter Quiz Key");
        searchTitleField.setText("Enter Quiz Title");

        c.gridx = 4;
        c.gridy = 0;
        c.gridwidth = 1;
        searchPanel.add(searchKeyButton, c);

        c.gridx = 5;
        c.gridy = 0;
        c.gridwidth = 1;
        searchPanel.add(resetButton, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        searchPanel.add(searchTitleField, c);

        c.gridx = 4;
        c.gridy = 1;
        c.gridwidth = 1;
        searchPanel.add(searchTitleButton, c);

        c.gridx = 5;
        c.gridy = 1;
        c.gridwidth = 1;
        searchPanel.add(mainMenuButton, c);

        final JPanel quizListPanel = new JPanel();
        quizListPanel.setLayout(new GridLayout(7, 1, 5, 5));
        //System.out.print(database.getNumberOfItems());
        String[][] quizzes = {
                {"Solar Systems", "Questions: 14", "BigJohn42_98erynsedf8943"},
                {"Albert Einstein’s Life", "Questions: 50", "BigJohn42_nwead9823"},
                {"Music Quiz", "Questions: 4", "BigJohn42_2435yinsfd"},
                {"CSC207 Midterm", "Questions: 20", "BigJohn42_nv9r8w42"},
                {"Plants", "Questions: 50", "BigJohn42_9gq430k"},
                {"test", "test", "test"},
                {"test", "test", "test"}
        };

        for (int i = 0; i<quizzes.length; i++) {
            JPanel row = new JPanel();
            row.setLayout(new GridBagLayout());
            JLabel quizTitle = new JLabel("   " + quizzes[i][0]);
            JLabel quizQuestionCount = new JLabel("   " + quizzes[i][1]);
            JLabel quizKey = new JLabel("   " + quizzes[i][2]);
            JButton copyButton = new JButton("Copy & Share!");
            JButton playButton = new JButton("Play");

            row.setPreferredSize(new Dimension((int) (windowWidth*0.9), 60));
            c.gridheight = 1;
            c.weighty = 2;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 4;
            quizTitle.setPreferredSize(new Dimension((int) (windowWidth * 0.9), 30));
            quizTitle.setBackground(Color.decode("#a4c2f4"));
            quizTitle.setOpaque(true);
            row.add(quizTitle, c);

            quizQuestionCount.setPreferredSize(new Dimension((int) (windowWidth * 0.2), 29));
            quizQuestionCount.setBackground(Color.decode("#dd7e6b"));
            quizQuestionCount.setOpaque(true);
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 1;
            row.add(quizQuestionCount, c);

            quizKey.setPreferredSize(new Dimension((int) (windowWidth * 0.3), 29));
            quizKey.setBackground(Color.decode("#fce4cd"));
            quizKey.setOpaque(true);
            c.gridx = 1;
            c.gridy = 1;
            c.gridwidth = 1;
            row.add(quizKey, c);

            copyButton.setBackground(Color.decode("#fce4cd"));
            copyButton.setOpaque(true);
            c.gridx = 2;
            c.gridy = 1;
            c.gridwidth = 1;
            row.add(copyButton, c);

            playButton.setPreferredSize(new Dimension((int) (windowWidth * 0.2), 29));
            playButton.setBackground(Color.decode("#f9cb9c"));
            playButton.setOpaque(true);
            c.gridx = 3;
            c.gridy = 1;
            c.gridwidth = 1;
            row.add(playButton, c);

            String message = quizzes[i][2];

            copyButton.addActionListener(e -> {
                String textToCopy = message;
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(textToCopy), null);

                JOptionPane.showMessageDialog(QuizDatabaseView.this, textToCopy + " successfully copied to clipboard! Make sure to share with your friends!");
            });

            quizListPanel.add(row);

        }

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout());
        JButton prevButton = new JButton("⬅");
        JButton pageIndicator = new JButton("1");
        JButton nextButton = new JButton("➡");
        pageIndicator.setEnabled(false);

        navigationPanel.add(prevButton);
        navigationPanel.add(pageIndicator);
        navigationPanel.add(nextButton);
        bottomPanel.add(createQuizButton, BorderLayout.WEST);
        bottomPanel.add(navigationPanel, BorderLayout.EAST);

        this.add(searchPanel, BorderLayout.NORTH);
        this.add(quizListPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        createQuizButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(createQuizButton)) {
                        final AccessedDatabaseInfoState currentState = accessDatabaseViewModel.getState();
                        accessDatabaseController.switchToCreateQuestionView(currentState.getUsername());
                    }
                }
        );
    }

    public void setAccessDatabaseController(AccessDatabaseController accessDatabaseController) {
        this.accessDatabaseController = accessDatabaseController;
    }

    public String getViewName(){
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("propetychanged");
        final AccessedDatabaseInfoState state = (AccessedDatabaseInfoState) evt.getNewValue();
        database = state.getDatabase();
        System.out.println(state.getDatabase());
    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT));
        frame.setLocationRelativeTo(null);

        final Dimension windowSize = frame.getSize();

        final QuizDatabaseView quizDatabaseView = new QuizDatabaseView(new AccessedDatabaseInfoViewModel());
        frame.add(quizDatabaseView);
        frame.setVisible(true);
    }
}
