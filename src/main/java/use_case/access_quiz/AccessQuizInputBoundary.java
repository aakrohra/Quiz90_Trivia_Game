package use_case.access_quiz;

/**
 * Input Boundary for actions which are related to accessing a quiz.
 */
public interface AccessQuizInputBoundary {

    /**
     * Executes the access quiz use case using the relevant input data.
     * @param accessQuizInputData the input data
     */
    void execute(AccessQuizInputData accessQuizInputData);
}
