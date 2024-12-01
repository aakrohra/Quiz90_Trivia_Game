package use_case.create_question;

/**
 * Input boundary for custom question creation.
 */
public interface QuestionCreationInputBoundary {

    /**
     * Executes creating the question object.
     * @param questionCreationInputData the input data
     */
    void executeCreateQuestion(QuestionCreationInputData questionCreationInputData);
}
