package view;

import app.Constants;
import interface_adapter.create_question.QuestionCreationController;
import interface_adapter.create_question.QuestionCreationState;
import interface_adapter.create_question.QuestionCreationViewModel;
import interface_adapter.create_quiz.QuizCreationController;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The view for question creation.
 */
public class QuestionCreationView extends JPanel implements PropertyChangeListener {

    private final JButton nextQuestion;
    private final JButton finish;
    private JPanel textFieldPanel = new JPanel();

    private final QuestionCreationViewModel questionCreationViewModel;

    private QuestionCreationController questionCreationController;
    private QuizCreationController quizCreationController;

    public QuestionCreationView(QuestionCreationViewModel questionCreationViewModel) {
        questionCreationViewModel.addPropertyChangeListener(this);

        this.questionCreationViewModel = questionCreationViewModel;

        final TitlePanel titlePanel = new TitlePanel(questionCreationViewModel.TITLE_LABEL);

        textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));
        textFieldPanel.setBackground(Constants.BGCOLOUR);
        createTextField("Enter question text...", textFieldPanel, "question");
        createTextField("Enter correct answer...", textFieldPanel, "answer");
        createTextField("Enter incorrect option 1...", textFieldPanel, "wrong 1");
        createTextField("Enter incorrect option 2...", textFieldPanel, "wrong 2");
        createTextField("Enter incorrect option 3...", textFieldPanel, "wrong 3");

        final JPanel buttonsPanel = new JPanel();
        nextQuestion = new JButton("Submit Question & Add Another");
        finish = new JButton("Finish Quiz");
        assembleButtons(buttonsPanel, nextQuestion, finish);
        buttonListeners();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Constants.BGCOLOUR);

        this.add(Box.createVerticalStrut(20));
        this.add(titlePanel);
        this.add(Box.createVerticalStrut(20));
        this.add(textFieldPanel);
        this.add(Box.createVerticalStrut(20));
        this.add(buttonsPanel);
        this.add(Box.createVerticalStrut(20));
        this.add(Box.createVerticalGlue());

    }

    public String getViewName() {
        return "question creation";
    }

    private void buttonListeners() {
        nextQuestion.addActionListener(
                evt -> {
                    if (evt.getSource().equals(nextQuestion)) {
                        final QuestionCreationState currentState = questionCreationViewModel.getState();
                        questionCreationController.executeCreateQuestion(currentState.getQuestionText(),
                                currentState.getCorrectAnswer(),
                                currentState.getWrongAnswer1(),
                                currentState.getWrongAnswer2(),
                                currentState.getWrongAnswer3());
                    }
                }
        );

        finish.addActionListener(
                evt -> {
                    if (evt.getSource().equals(finish)) {
                        final QuestionCreationState currentState = questionCreationViewModel.getState();
                        final String quizTitle = JOptionPane.showInputDialog("What would you like to title your quiz?");
                        quizCreationController.executeCreateQuiz(currentState.getQuestionsSoFar(), quizTitle, currentState.getUsername());
                    }
                }
        );
    }

    private void textFieldListener(JTextField textField, String text, String type) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final QuestionCreationState currentState = questionCreationViewModel.getState();

                switch (type) {
                    case "question":
                        currentState.setQuestionText(textField.getText());
                        break;
                    case "answer":
                        currentState.setCorrectAnswer(textField.getText());
                        break;
                    case "wrong 1":
                        currentState.setWrongAnswer1(textField.getText());
                        break;
                    case "wrong 2":
                        currentState.setWrongAnswer2(textField.getText());
                        break;
                    case "wrong 3":
                        currentState.setWrongAnswer3(textField.getText());
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported type: " + type);
                }

                questionCreationViewModel.setState(currentState);
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

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(text)) {
                    textField.setText("");
                    // Set text color when typing
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restore placeholder text if the field is empty
                if (textField.getText().isEmpty()) {
                    textField.setText(text);
                    // Set placeholder text color
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    @NotNull
    private static Component horizontalSpacer() {
        return Box.createHorizontalStrut(Constants.STRUTSMALLSPACER * 3);
    }

    private void assembleButtons(JPanel buttonPanel, JButton createdQuizzes, JButton tempMultiplayerButton) {
        buttonPanel.setBackground(Constants.BGCOLOUR);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(createdQuizzes);
        buttonPanel.add(horizontalSpacer());
        buttonPanel.add(tempMultiplayerButton);
        buttonPanel.add(Box.createHorizontalGlue());
    }

    private void createTextField(String text, JPanel textPanel, String type) {
        final JTextField textField = new JTextField(15);
        textField.setText(text);
        textField.setForeground(Color.GRAY);
        textField.setMargin(new Insets(0, 10, 0, 0));
        textField.setMinimumSize(new Dimension(Integer.MAX_VALUE, Constants.FIELDY));
        textPanel.add(textField);
        textFieldListener(textField, text, type);
    }

    public void setQuestionCreationController(QuestionCreationController questionCreationController) {
        this.questionCreationController = questionCreationController;
    }

    public void setQuizCreationController(QuizCreationController quizCreationController) {
        this.quizCreationController = quizCreationController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("next".equals(evt.getPropertyName())) {
            JOptionPane.showMessageDialog(this,
                    "Added question! Replace your old text for a new question, or press finish.");
        }
    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Quiz90");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new QuestionCreationView(new QuestionCreationViewModel()));
        frame.setVisible(true);
    }
}
