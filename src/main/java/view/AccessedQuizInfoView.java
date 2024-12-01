package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import app.Constants;
import entity.Quiz;
import entity.RetrievedQuiz;
import interface_adapter.access_quiz.AccessQuizController;
import interface_adapter.access_quiz.AccessedQuizInfoState;
import interface_adapter.access_quiz.AccessedQuizInfoViewModel;

/**
 * The view for when a quiz has been accessed and its information must be shown.
 */
public class AccessedQuizInfoView extends JPanel implements PropertyChangeListener {

    private Quiz quizObject;

    private final JLabel quizName;
    private final JLabel quizAuthor;
    private final JLabel quizNumQuestions;
    private AccessQuizController accessQuizController;

    public AccessedQuizInfoView(AccessedQuizInfoViewModel accessedQuizInfoViewModel) {
        accessedQuizInfoViewModel.addPropertyChangeListener(this);

        final JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Constants.BGCOLOUR);
        infoPanel.setPreferredSize(new Dimension(1200, 200));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        final Box nameBox = Box.createHorizontalBox();
        nameBox.setBorder(BorderFactory.createEmptyBorder(10, 35, 10, 35));
        final Box authorBox = Box.createHorizontalBox();
        authorBox.setBorder(BorderFactory.createEmptyBorder(10, 35, 10, 35));
        final Box numBox = Box.createHorizontalBox();
        numBox.setBorder(BorderFactory.createEmptyBorder(10, 35, 10, 35));
        final JLabel quizNameLabel = new JLabel("Quiz Name: ");
        quizNameLabel.setForeground(Color.WHITE);
        quizNameLabel.setFont(new Font(quizNameLabel.getFont().getName(), Font.BOLD, Constants.TITLEFONTSIZE));
        final JLabel quizAuthorLabel = new JLabel("Author: ");
        quizAuthorLabel.setForeground(Color.WHITE);
        final JLabel quizNumQuestionsLabel = new JLabel("Number of Questions: ");
        quizNumQuestionsLabel.setForeground(Color.WHITE);
        quizName = new JLabel();
        quizName.setForeground(Color.WHITE);
        quizName.setFont(new Font(quizNameLabel.getFont().getName(), Font.BOLD, Constants.TITLEFONTSIZE));
        quizAuthor = new JLabel();
        quizAuthor.setForeground(Color.WHITE);
        quizNumQuestions = new JLabel();
        quizNumQuestions.setForeground(Color.WHITE);
        infoPanel.add(nameBox);
        infoPanel.add(authorBox);
        infoPanel.add(numBox);
        nameBox.add(quizNameLabel);
        nameBox.add(quizName);
        authorBox.add(quizAuthorLabel);
        authorBox.add(quizAuthor);
        numBox.add(quizNumQuestionsLabel);
        numBox.add(quizNumQuestions);

        final JPanel buttons = buttonsListenersAndUi();

        assembleFinalPanelHelper(infoPanel, buttons);

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

    @NotNull
    private static Component horizontalSpacer() {
        return Box.createHorizontalStrut(Constants.STRUTSMALLSPACER * 3);
    }

    private void assembleFinalPanelHelper(JPanel infoPanel, JPanel buttons) {

        this.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = createGbc();

        this.addComp(infoPanel, 0, 1, 3, GridBagConstraints.CENTER, gbc);
        this.addComp(buttons, 0, 2, 3, GridBagConstraints.CENTER, gbc);
        this.addComp(horizontalSpacer(), 0, 3, 0, GridBagConstraints.CENTER, gbc);

        this.setBackground(Constants.BGCOLOUR);
    }

    /**
     * Creates a panel with the back and play buttons and sets up listeners for them.
     * @return the buttons panel
     */
    public JPanel buttonsListenersAndUi() {
        final JPanel buttons0 = new JPanel();
        final JButton back = new CustomButton("Back to Main Menu");
        final JButton play = new CustomButton("Play Quiz");

        buttons0.add(Box.createHorizontalGlue());
        buttons0.add(play);
        buttons0.add(horizontalSpacer());
        buttons0.add(back);
        buttons0.add(Box.createHorizontalGlue());
        buttons0.setBackground(Constants.BGCOLOUR);

        play.addActionListener(
                // below is temp code, quizObject should be passed somewhere to be played
                evt -> {
                    if (evt.getSource().equals(play)) {
                        accessQuizController.playAccessedQuiz((RetrievedQuiz) quizObject);
                    }
                }
        );

        back.addActionListener(evt -> accessQuizController.switchToLoggedInView());

        return buttons0;
    }

    public String getViewName() {
        return "accessed quiz info";
    }

    public void setAccessQuizController(AccessQuizController accessQuizController) {
        this.accessQuizController = accessQuizController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final AccessedQuizInfoState state = (AccessedQuizInfoState) evt.getNewValue();
        quizName.setText(state.getQuizName());
        quizAuthor.setText(state.getAuthor());
        quizNumQuestions.setText(String.valueOf(state.getNumQuestions()));
        quizObject = state.getQuizObject();
    }
}
