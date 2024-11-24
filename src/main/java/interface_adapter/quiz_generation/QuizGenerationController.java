package interface_adapter.quiz_generation;

import use_case.quiz_generation.QuizGenerationInputBoundary;
import use_case.quiz_generation.QuizGenerationInputData;

/**
 * Controller for the Quiz Generation Use Case.
 */
public class QuizGenerationController {

    private final QuizGenerationInputBoundary quizGenerationInteractor;

    public QuizGenerationController(QuizGenerationInputBoundary quizGenerationInteractor) {
        this.quizGenerationInteractor = quizGenerationInteractor;
    }

    /**
     * Executes the quiz generation use case.
     *
     * @param selectedNumberOfQuestions The number of questions to include in the quiz.
     * @param selectedCategory The category of the quiz.
     * @param difficulty The difficulty level of the quiz (e.g., "easy", "medium", "hard").
     */
    public void execute(int selectedNumberOfQuestions, String selectedCategory, String difficulty) {
        final QuizGenerationInputData quizData = new QuizGenerationInputData(
                selectedNumberOfQuestions, selectedCategory, difficulty);

        quizGenerationInteractor.execute(quizData);
    }

    /**
     * Executes the action to switch to the Quiz Generation view.
     */
    public void switchToQuizGenerationView() {
        quizGenerationInteractor.switchToQuizGenerationView();
    }

    /**
     * Executes the action to switch to the Main Menu view.
     */
    public void switchToMainMenuView() {
        quizGenerationInteractor.switchToMainMenuView();
    }
}
