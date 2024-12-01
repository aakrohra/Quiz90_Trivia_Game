package view;

import interface_adapter.create_quiz.QuizCreationViewModel;

import javax.swing.*;

/**
 * The view for the created quiz (summary and key).
 */
public class CreatedQuizView extends JPanel {

    private QuizCreationViewModel quizCreationViewModel;

    public CreatedQuizView(QuizCreationViewModel quizCreationViewModel) {
        this.quizCreationViewModel = quizCreationViewModel;
    }

    public String getViewName() {
        return "quiz creation";
    }
}
