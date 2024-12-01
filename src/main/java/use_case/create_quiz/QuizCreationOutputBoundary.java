package use_case.create_quiz;

/**
 * The interface for the quiz creation output boundary.
 */
public interface QuizCreationOutputBoundary {

    /**
     * Prepares a success view for the quiz creation use case.
     * @param outputData the data object with the quiz object
     */
    void prepareSuccessView(QuizCreationOutputData outputData);
}
