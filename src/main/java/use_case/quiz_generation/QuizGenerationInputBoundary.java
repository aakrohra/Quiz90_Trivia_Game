package use_case.quiz_generation;

/**
 * Input Boundary for actions related to quiz generation.
 */
public interface QuizGenerationInputBoundary {

    /**
     * Executes the action to switch to the Quiz Generation view.
     */
    void switchToQuizGenerationView();

    /**
     * Executes the action to switch to the Main Menu view.
     */
    void switchToMainMenuView();

    /**
     * Executes the quiz generation process using the provided input data.
     *
     * @param quizData The input data containing the number of questions, category,
     *                 and difficulty level for the quiz generation.
     */
    void execute(QuizGenerationInputData quizData);
}
