package use_case.create_quiz;

import entity.CreatedQuiz;

/**
 * The output data for quiz creation.
 */
public class QuizCreationOutputData {
    private final CreatedQuiz quizObject;
    private final String key;

    public QuizCreationOutputData(CreatedQuiz quizObject, String key) {
        this.quizObject = quizObject;
        this.key = key;
    }

    public CreatedQuiz getQuizObject() {
        return quizObject;
    }

    public String getKey() {
        return key;
    }
}
