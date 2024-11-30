package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.json.JSONObject;

import app.Constants;
import data_access.DBCustomQuizDataAccessObject;
import entity.CommonUserFactory;
import entity.PlayerCreatedQuestion;
import entity.PlayerCreatedQuiz;
import entity.PlayerQuizDatabase;
import entity.Quiz;
import entity.User;
import interface_adapter.access_database.AccessDatabaseController;
import interface_adapter.access_database.AccessedDatabaseInfoState;
import interface_adapter.access_database.AccessedDatabaseInfoViewModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The View for when the user is accessing their database.
 */

public class QuizDatabaseView extends JPanel implements PropertyChangeListener {
    private static final String API_INFO_CALL = "http://vm003.teach.cs.toronto.edu:20112/user?username=%s";
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final int QUIZ_ROW_HEIGHT = 60;
    private static final double QUIZ_PANEL_SIZE_MODIFIER = 0.9;
    private static final double SEARCH_FIELDS_WIDTH_MODIFIER = 0.6;
    private static final int GENERAL_ELEMENT_HEIGHT = 30;
    private static final int SEARCH_KEY_GRID_WIDTH = 4;
    private static final String SEARCH_KEY_PLACEHOLDER = "Enter Quiz Key";
    private static final String SEARCH_QUIZ_PLACEHOLDER = "Enter Quiz Title";
    private static final int RIGHT_GRID_X = 5;
    private static final String CREATE_QUIZ_BUTTON_PLACEHOLDER = "Create new quiz";
    private static final String EMPTY_DATABASE_PLACEHOLDER = "Empty Database, you have no quizzes.";
    private static final double SCROLL_PANEL_HEIGHT_MODIFIER = 0.8;
    private static final String INDENTATION_PLACEHOLDER = "   ";
    private static final String COPY_PLACEHOLDER = "Copy & Share!";
    private static final String PLAY_PLACEHOLDER = "Play";
    private static final String QUIZ_TITLE_BG_COLOUR = "#a4c2f4";
    private static final String QUESTION_COUNT_BG_COLOUR = "#dd7e6b";
    private static final double ROW_MEDIUM_WIDTH_MODIFIER = 0.2;
    private static final String QUIZ_KEY_BG_COLOUR = "#fce4cd";
    private static final double ROW_LARGE_WIDTH_MODIFIER = 0.3;
    private static final double ROW_SMALL_WIDTH_MODIFIER = 0.1;
    private static final int GRIDX_MAX = 3;
    private static final String CLIPBOARD_SUCCESSFUL_PLACEHOLDER = " successfully copied to clipboard! Make sure to share with your friends!";
    private static final int MAX_ROW_SIZE = 9;
    private static final int SCROLL_PANEL_WIDTH_MODIFIER = 1;

    private final String viewName = "access database";

    private final AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel;
    private int quizMapSize;
    private Map<String, PlayerCreatedQuiz> quizMap;
    private PlayerQuizDatabase database;
    private final GridBagConstraints c = new GridBagConstraints();
    private final JPanel searchPanel = new JPanel();
    private final JTextField searchKeyField = new JTextField();
    private final JButton searchKeyButton = new JButton("Search");
    private final JTextField searchTitleField = new JTextField();
    private final JButton searchTitleButton = new JButton("Search");
    private final JButton resetButton = new JButton("Reset");
    private final JButton mainMenuButton = new JButton("Main Menu");
    private final Dimension windowSize = new Dimension(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT);
    private final int windowWidth = windowSize.width;
    private final int windowHeight = windowSize.height;
    private final JPanel bottomPanel = new JPanel();
    private final JScrollPane scrollPanel = new JScrollPane();
    private AccessDatabaseController accessDatabaseController;
    private final JLabel empty = new JLabel();

    public QuizDatabaseView(AccessedDatabaseInfoViewModel accessDatabaseViewModel) {
        this.accessedDatabaseInfoViewModel = accessDatabaseViewModel;
        accessedDatabaseInfoViewModel.addPropertyChangeListener(this);
        this.setBackground(Constants.BGCOLOUR);

        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setPreferredSize(new Dimension((int) (windowWidth * QUIZ_PANEL_SIZE_MODIFIER), QUIZ_ROW_HEIGHT));

        searchKeyField.setPreferredSize(new Dimension((int) (windowWidth * SEARCH_FIELDS_WIDTH_MODIFIER), GENERAL_ELEMENT_HEIGHT));
        searchTitleField.setPreferredSize(new Dimension((int) (windowWidth * SEARCH_FIELDS_WIDTH_MODIFIER), GENERAL_ELEMENT_HEIGHT));

        searchKeyField.setText(SEARCH_KEY_PLACEHOLDER);
        searchTitleField.setText(SEARCH_QUIZ_PLACEHOLDER);
        searchKeyField.setForeground(Color.GRAY);
        searchTitleField.setForeground(Color.GRAY);

        keyFieldActionListeners();

        searchPanelOrganisation();

        final JButton createQuizButton = new JButton(CREATE_QUIZ_BUTTON_PLACEHOLDER);
        final JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout());

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(createQuizButton, BorderLayout.WEST);

    }

    private void searchPanelOrganisation() {
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = SEARCH_KEY_GRID_WIDTH;
        searchPanel.add(searchKeyField, c);

        c.gridx = SEARCH_KEY_GRID_WIDTH;
        c.gridy = 0;
        c.gridwidth = 1;
        searchPanel.add(searchKeyButton, c);

        c.gridx = RIGHT_GRID_X;
        c.gridy = 0;
        c.gridwidth = 1;
        searchPanel.add(resetButton, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = SEARCH_KEY_GRID_WIDTH;
        searchPanel.add(searchTitleField, c);

        c.gridx = SEARCH_KEY_GRID_WIDTH;
        c.gridy = 1;
        c.gridwidth = 1;
        searchPanel.add(searchTitleButton, c);

        c.gridx = RIGHT_GRID_X;
        c.gridy = 1;
        c.gridwidth = 1;
        searchPanel.add(mainMenuButton, c);

        mainMenuButton.addActionListener(evt -> backToMainMenu());
        searchKeyButton.addActionListener(evt -> searchByKey());
        resetButton.addActionListener(evt -> reset());
        searchTitleButton.addActionListener(evt -> searchByTitle());
    }

    private void keyFieldActionListeners() {
        focusKeyFieldAdapter(searchKeyField, SEARCH_KEY_PLACEHOLDER);

        focusKeyFieldAdapter(searchTitleField, SEARCH_QUIZ_PLACEHOLDER);
    }

    private void focusKeyFieldAdapter(JTextField textField, String textFieldPlaceholder) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(textFieldPlaceholder)) {
                    textField.setText("");
                    textField.setForeground(Color.GRAY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(textFieldPlaceholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final AccessedDatabaseInfoState state = (AccessedDatabaseInfoState) evt.getNewValue();
        this.removeAll();
        this.database = state.getDatabase();
        this.quizMap = this.database.getAll();
        this.quizMapSize = this.database.getNumberOfItems();
        this.add(searchPanel, BorderLayout.NORTH);
        defaultDatabaseView();
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void defaultDatabaseView() {
        createDatabaseView(quizMap);
    }

    private void createDatabaseView(Map<String, PlayerCreatedQuiz> quizToKeyMap) {
        final int mapSize = quizToKeyMap.size();
        final String[][] quizzes = new String[mapSize][GRIDX_MAX];
        int temp = 0;
        final List<String> keys = new ArrayList<>(quizToKeyMap.keySet());
        for (PlayerCreatedQuiz quiz : quizToKeyMap.values()) {
            quizzes[temp][0] = quiz.getTitle();
            quizzes[temp][1] = "Questions: " + quiz.getNumQuestions();
            quizzes[temp][2] = keys.get(temp);
            temp++;
        }

        final JPanel quizListPanel = new JPanel();
        quizListPanel.setLayout(new BoxLayout(quizListPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < quizzes.length; i++) {
            final JPanel row = rowItemFromQuizString(quizzes[i]);
            row.setPreferredSize(new Dimension(windowWidth, QUIZ_ROW_HEIGHT));
            quizListPanel.add(row);
            quizListPanel.add(Box.createVerticalStrut(RIGHT_GRID_X));
        }

        if (mapSize <= MAX_ROW_SIZE && mapSize > 0) {
            quizListPanel.add(Box.createVerticalStrut((int) (windowHeight * SCROLL_PANEL_HEIGHT_MODIFIER - mapSize * QUIZ_ROW_HEIGHT)));
        }

        if (mapSize == 0) {
            empty.setText(EMPTY_DATABASE_PLACEHOLDER);
            this.add(empty, BorderLayout.CENTER);
        }

        else {
            scrollPanel.setViewportView(quizListPanel);
            quizListPanel.setAutoscrolls(true);
            scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPanel.setPreferredSize(new Dimension((int) (windowWidth * SCROLL_PANEL_WIDTH_MODIFIER), (int) (windowHeight * SCROLL_PANEL_HEIGHT_MODIFIER)));
            scrollPanel.repaint();
            this.add(scrollPanel, BorderLayout.CENTER);
        }
    }

    private void backToMainMenu() {
        accessDatabaseController.switchToMainMenuView();
    }

    public void setAccessDatabaseController(AccessDatabaseController accessDatabaseController) {
        this.accessDatabaseController = accessDatabaseController;
    }

    private JPanel rowItemFromQuizString(String[] quizString) {
        final JPanel row = new JPanel();
        row.setLayout(new GridBagLayout());
        final JLabel quizTitle = new JLabel(INDENTATION_PLACEHOLDER + quizString[0]);
        final JLabel quizQuestionCount = new JLabel(INDENTATION_PLACEHOLDER + quizString[1]);
        final JLabel quizKey = new JLabel(INDENTATION_PLACEHOLDER + quizString[2]);
        final JButton copyButton = new JButton(COPY_PLACEHOLDER);
        final JButton playButton = new JButton(PLAY_PLACEHOLDER);
        row.setPreferredSize(new Dimension((int) (windowWidth * QUIZ_PANEL_SIZE_MODIFIER), GENERAL_ELEMENT_HEIGHT));

        c.gridheight = 1;
        c.weighty = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = SEARCH_KEY_GRID_WIDTH;
        quizTitle.setPreferredSize(new Dimension((int) (windowWidth * QUIZ_PANEL_SIZE_MODIFIER), GENERAL_ELEMENT_HEIGHT));
        quizTitle.setBackground(Color.decode(QUIZ_TITLE_BG_COLOUR));
        quizTitle.setOpaque(true);
        row.add(quizTitle, c);

        quizQuestionCount.setPreferredSize(new Dimension((int) (windowWidth * ROW_MEDIUM_WIDTH_MODIFIER), GENERAL_ELEMENT_HEIGHT));
        quizQuestionCount.setBackground(Color.decode(QUESTION_COUNT_BG_COLOUR));
        quizQuestionCount.setOpaque(true);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        row.add(quizQuestionCount, c);

        quizKey.setPreferredSize(new Dimension((int) (windowWidth * ROW_LARGE_WIDTH_MODIFIER), GENERAL_ELEMENT_HEIGHT));
        quizKey.setBackground(Color.decode(QUIZ_KEY_BG_COLOUR));
        quizKey.setOpaque(true);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        row.add(quizKey, c);

        copyButton.setBackground(Color.decode(QUIZ_KEY_BG_COLOUR));
        copyButton.setPreferredSize(new Dimension((int) (windowWidth * ROW_SMALL_WIDTH_MODIFIER), GENERAL_ELEMENT_HEIGHT));
        copyButton.setOpaque(true);
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        row.add(copyButton, c);

        playButton.setPreferredSize(new Dimension((int) (windowWidth * ROW_MEDIUM_WIDTH_MODIFIER), GENERAL_ELEMENT_HEIGHT));
        playButton.setBackground(Color.decode("#f9cb9c"));
        playButton.setOpaque(true);
        c.gridx = GRIDX_MAX;
        c.gridy = 1;
        c.gridwidth = 1;
        row.add(playButton, c);

        final String titleCopy = quizString[0];
        final String message = quizString[2];

        copyButton.addActionListener(event -> {
            final String textToCopy = message;
            final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(textToCopy), null);

            JOptionPane.showMessageDialog(QuizDatabaseView.this, textToCopy + CLIPBOARD_SUCCESSFUL_PLACEHOLDER);
        });

        playButton.addActionListener(event -> {
            final String key = message;
            final Quiz quiz = quizMap.get(key);
            accessDatabaseController.switchToPlaythroughView(quiz);
        });
        return row;
    }

    private void searchByKey() {
        final String key = searchKeyField.getText();
        PlayerCreatedQuiz quiz = database.getByKey(key);
        if (quiz != null) {
            final String[] quizString = new String[GRIDX_MAX];
            quizString[0] = quiz.getTitle();
            quizString[1] = "Questions: " + quiz.getNumQuestions();
            quizString[2] = key;
            final JPanel jPanel = new JPanel();
            jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
            final JPanel rowItem = rowItemFromQuizString(quizString);
            rowItem.setPreferredSize(new Dimension((int) (windowWidth * QUIZ_PANEL_SIZE_MODIFIER), QUIZ_ROW_HEIGHT));
            jPanel.add(rowItem);
            jPanel.add(Box.createVerticalStrut((int) (windowHeight * SCROLL_PANEL_HEIGHT_MODIFIER - QUIZ_ROW_HEIGHT)));
            scrollPanel.setViewportView(jPanel);
            scrollPanel.revalidate();
        }
    }

    private void searchByTitle() {
        final String title = searchTitleField.getText();
        final Map<String, PlayerCreatedQuiz> titleMap = database.getByTitle(title);
        if (titleMap != null) {
            createDatabaseView(titleMap);
        }
    }

    private void reset() {
        defaultDatabaseView();
        searchKeyField.setText(SEARCH_KEY_PLACEHOLDER);
        searchTitleField.setText(SEARCH_QUIZ_PLACEHOLDER);
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
        String quizTitle = "snoopysnoopy";
        List<PlayerCreatedQuestion> listOfQuestions = new ArrayList<>();
        List<String> answerOptions = new ArrayList<>();
        answerOptions.add("Yes");
        answerOptions.add("No");
        answerOptions.add("Yes1");
        answerOptions.add("No1");
        listOfQuestions.add(new PlayerCreatedQuestion("Question", answerOptions, "Yes"));
        listOfQuestions.add(new PlayerCreatedQuestion("Question", answerOptions, "Yes"));
        listOfQuestions.add(new PlayerCreatedQuestion("Question", answerOptions, "Yes"));
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
    }
}
