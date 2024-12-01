package interface_adapter.create_question;

import use_case.create_question.QuestionCreationInputBoundary;
import use_case.create_question.QuestionCreationInputData;

/**
 * The Controller for the question creation use case.
 */
public class QuestionCreationController {

    private final QuestionCreationInputBoundary questionCreationInteractor;

    public QuestionCreationController(QuestionCreationInputBoundary questionCreationInteractor) {
        this.questionCreationInteractor = questionCreationInteractor;
    }

    /**
     * Executes creating a question object using the given data.
     * @param questionText the question
     * @param answer the answer
     * @param wrong1 wrong answer 1
     * @param wrong2 wrong answer 2
     * @param wrong3 wrong answer 3
     */
    public void executeCreateQuestion(String questionText, String answer, String wrong1, String wrong2, String wrong3) {
        final QuestionCreationInputData questionCreationInputData = new QuestionCreationInputData(questionText, answer, wrong1, wrong2, wrong3);

        questionCreationInteractor.executeCreateQuestion(questionCreationInputData);
    }

}
