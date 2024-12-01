package interface_adapter.create_quiz;

import java.util.List;

import entity.PlayerCreatedQuestion;
import use_case.create_quiz.QuizCreationInputBoundary;
import use_case.create_quiz.QuizCreationInputData;

/**
 * The controller for the quiz creation and summary use case.
 */
public class QuizCreationController {

    private final QuizCreationInputBoundary quizCreationInputBoundary;

    public QuizCreationController(QuizCreationInputBoundary quizCreationInputBoundary) {
        this.quizCreationInputBoundary = quizCreationInputBoundary;
    }

    /**
     * Executes creating a quiz and showing a summary of it.
     * @param questions the list of questions to be used
     * @param title the title of the quiz
     * @param username the current username
     */
    public void executeCreateQuiz(List<PlayerCreatedQuestion> questions, String title, String username) {
        final QuizCreationInputData inputData = new QuizCreationInputData(questions, title, username);
        quizCreationInputBoundary.executeCreateQuiz(inputData);
    }

}
