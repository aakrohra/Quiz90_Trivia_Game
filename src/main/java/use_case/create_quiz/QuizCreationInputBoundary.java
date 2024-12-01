package use_case.create_quiz;

/**
 * The input boundary for quiz creation.
 */
public interface QuizCreationInputBoundary {

    /**
     * Executes creating a quiz.
     * @param quizCreationInputData the input data
     */
    void executeCreateQuiz(QuizCreationInputData quizCreationInputData);
}
