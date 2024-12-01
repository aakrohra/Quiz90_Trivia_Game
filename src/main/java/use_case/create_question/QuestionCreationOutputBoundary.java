package use_case.create_question;

/**
 * The interface for question creation output boundary.
 */
public interface QuestionCreationOutputBoundary {

    /**
     * Prepares a success view with the given output data.
     * @param outputData the output data
     */
    void prepareSuccessView(QuestionCreationOutputData outputData);
}
