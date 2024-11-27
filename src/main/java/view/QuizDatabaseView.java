package view;

import app.Constants;
import data_access.DBCustomQuizDataAccessObject;
import entity.*;
import interface_adapter.access_database.AccessDatabaseController;
import interface_adapter.access_database.AccessedDatabaseInfoState;
import interface_adapter.access_database.AccessedDatabaseInfoViewModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

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
import java.io.IOException;
import java.sql.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import static data_access.DBCustomQuizDataAccessObject.*;

public class QuizDatabaseView extends JPanel implements PropertyChangeListener {

    private static final String API_INFO_CALL = "http://vm003.teach.cs.toronto.edu:20112/user?username=%s";
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";


    private final String viewName = "access database";

    private final AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel;
    private int quizMapSize;
    private int numberOfPages;
    private ArrayList<String[][]> displayInfo;
    private Map<String, PlayerCreatedQuiz> quizMap;
    private Database database;
    GridBagConstraints c = new GridBagConstraints();
    final JPanel searchPanel = new JPanel();
    final JTextField searchKeyField = new JTextField();
    final JButton searchKeyButton = new JButton("Search");
    final JTextField searchTitleField = new JTextField();
    final JButton searchTitleButton = new JButton("Search");
    final JButton resetButton = new JButton("Reset");
    final JButton mainMenuButton = new JButton("Main Menu");
    final Dimension windowSize = new Dimension(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT);
    private Map<String, String> titleToKeyMap;
    private ArrayList<JPanel> quizPanels = new ArrayList<>();
    private JPanel currentPage = new JPanel(new GridLayout(7, 1, 5, 5));
    int windowWidth = windowSize.width;
    int windowHeight = windowSize.height;
    JPanel bottomPanel = new JPanel();
    private int pageNumber = 0;
    private JButton pageIndicator = new JButton(Integer.toString(pageNumber + 1));
    private AccessDatabaseController accessDatabaseController;

    public QuizDatabaseView(AccessedDatabaseInfoViewModel accessDatabaseViewModel) {
        this.accessedDatabaseInfoViewModel = accessDatabaseViewModel;
        accessedDatabaseInfoViewModel.addPropertyChangeListener(this);
        this.setBackground(Constants.BGCOLOUR);

        searchPanel.setLayout(new GridBagLayout());

        System.out.println(windowSize);
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

        System.out.println(quizMapSize);

        mainMenuButton.addActionListener(evt -> backToMainMenu());

        JButton createQuizButton = new JButton("Create new quiz");
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout());

        pageIndicator.setEnabled(false);

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(createQuizButton, BorderLayout.WEST);

        this.add(searchPanel, BorderLayout.NORTH);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("working");
        final AccessedDatabaseInfoState state = (AccessedDatabaseInfoState) evt.getNewValue();

        this.database = state.getDatabase();
        this.quizMap = this.database.getAll();
        this.quizMapSize = this.database.getNumberOfItems();
        this.numberOfPages = (int) Math.ceil(this.quizMapSize / 2);
        this.titleToKeyMap = this.database.getTitleByKey();
        System.out.println(quizMapSize);
        System.out.println(quizMap.toString());
        System.out.println("QUIZ MAPPPP" + quizMap);
        System.out.println("NUMBEROFPAGES" + numberOfPages);

        String[][] quizzes = new String[quizMapSize][3];
        int temp = 0;
        List<String> keys = new ArrayList<>(quizMap.keySet());
        for (PlayerCreatedQuiz quiz : quizMap.values()) {
            quizzes[temp][0] = quiz.getTitle();
            quizzes[temp][1] = "Questions: " + Integer.toString(quiz.getNumQuestions());
            quizzes[temp][2] = keys.get(temp);
            temp++;
        }
        for (int j = 0; j < quizzes.length; j++) {
            for (int k = 0; k < quizzes[j].length; k++) {
                System.out.println("Element at [" + j + "][" + k + "] = " + quizzes[j][k]);
            }
        }

        final JPanel quizListPanel = new JPanel();
        quizListPanel.setLayout(new BoxLayout(quizListPanel, BoxLayout.Y_AXIS));
        // quizListPanel.setLayout(new GridLayout(quizMapSize, 1, 5, 5));
        for (int i = 0; i < quizzes.length; i++) {
            JPanel row = new JPanel();
            row.setLayout(new GridBagLayout());
            JLabel quizTitle = new JLabel("   " + quizzes[i][0]);
            JLabel quizQuestionCount = new JLabel("   " + quizzes[i][1]);
            JLabel quizKey = new JLabel("   " + quizzes[i][2]);
            JButton copyButton = new JButton("Copy & Share!");
            JButton playButton = new JButton("Play");

            row.setPreferredSize(new Dimension((int) (windowWidth * 0.9), 60));
            c.gridheight = 1;
            c.weighty = 2;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 4;
            quizTitle.setPreferredSize(new Dimension((int) (windowWidth * 0.9), 30));
            quizTitle.setBackground(Color.decode("#a4c2f4"));
            quizTitle.setOpaque(true);
            row.add(quizTitle, c);

            quizQuestionCount.setPreferredSize(new Dimension((int) (windowWidth * 0.2), 30));
            quizQuestionCount.setBackground(Color.decode("#dd7e6b"));
            quizQuestionCount.setOpaque(true);
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 1;
            row.add(quizQuestionCount, c);

            quizKey.setPreferredSize(new Dimension((int) (windowWidth * 0.3), 30));
            quizKey.setBackground(Color.decode("#fce4cd"));
            quizKey.setOpaque(true);
            c.gridx = 1;
            c.gridy = 1;
            c.gridwidth = 1;
            row.add(quizKey, c);

            copyButton.setBackground(Color.decode("#fce4cd"));
            copyButton.setPreferredSize(new Dimension((int) (windowWidth * 0.1), 30));
            copyButton.setOpaque(true);
            c.gridx = 2;
            c.gridy = 1;
            c.gridwidth = 1;
            row.add(copyButton, c);

            playButton.setPreferredSize(new Dimension((int) (windowWidth * 0.2), 30));
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
        if (quizMapSize <= 9) {
            quizListPanel.add(Box.createVerticalStrut((int) (windowHeight * 0.8 - quizMapSize * 60)));
        }
        if (quizMapSize == 0) {
            System.out.println("EMPTY");
            JLabel empty = new JLabel("Empty Database, you have no quizzes.");
            this.add(empty, BorderLayout.CENTER);
        }
        else {
            currentPage = quizListPanel;
            System.out.println(currentPage);
            JScrollPane scrollPane = new JScrollPane(currentPage);
            currentPage.setAutoscrolls(true);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setPreferredSize(new Dimension(windowWidth, (int) (windowHeight * 0.8)));
            this.add(scrollPane, BorderLayout.CENTER);
        }
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void backToMainMenu() {
        accessDatabaseController.switchToMainMenuView();
    }

    public void setAccessDatabaseController(AccessDatabaseController accessDatabaseController) {
        this.accessDatabaseController = accessDatabaseController;
    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT));
        frame.setLocationRelativeTo(null);

        final Dimension windowSize = frame.getSize();

//        final QuizDatabaseView quizDatabaseView = new QuizDatabaseView(new AccessedDatabaseInfoViewModel());
//        frame.add(quizDatabaseView);
//        frame.setVisible(true);

        DBCustomQuizDataAccessObject quizDataAccessObject = new DBCustomQuizDataAccessObject();
        CommonUserFactory commonUserFactory = new CommonUserFactory();
        User user = commonUserFactory.create("loop2", "loop2");
        String quizTitle = "snoopy";
        List<PlayerCreatedQuestion> listOfQuestions = new ArrayList<>();
        List<String> answerOptions = new ArrayList<>();
        answerOptions.add("Yes");
        answerOptions.add("No");
        answerOptions.add("Yes1");
        answerOptions.add("No1");
        listOfQuestions.add(new PlayerCreatedQuestion("Question", answerOptions, "Yes"));
        String author = "loop2";
        PlayerCreatedQuiz quizz = new PlayerCreatedQuiz(quizTitle, listOfQuestions, author);
        JSONObject jsonQuiz = quizDataAccessObject.quizObjectToJSONObject(quizz);
        quizDataAccessObject.addQuiz(jsonQuiz, user);

        final String username = user.getName();
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format(API_INFO_CALL, username))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final String responseBody = response.body().string();

            System.out.println(responseBody);
        }
        catch (IOException e) {
            throw new RuntimeException();
        }

        System.out.println(quizDataAccessObject.getAllUserQuizzes("loop2"));
    }
}
