package interface_adapter.create_quiz;

import entity.CreatedQuiz;

/**
 * The state for the quiz creation view model.
 */
public class QuizCreationState {

    private CreatedQuiz quizObject;
    private String key;

    public CreatedQuiz getQuizObject() {
        return quizObject;
    }

    public void setQuizObject(CreatedQuiz quizObject) {
        this.quizObject = quizObject;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
