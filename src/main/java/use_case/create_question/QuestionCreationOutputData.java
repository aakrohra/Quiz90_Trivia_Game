package use_case.create_question;

import entity.PlayerCreatedQuestion;

/**
 * Output data object for question creation.
 */
public class QuestionCreationOutputData {

    private final PlayerCreatedQuestion questionObject;

    public QuestionCreationOutputData(PlayerCreatedQuestion questionObject) {
        this.questionObject = questionObject;
    }

    public PlayerCreatedQuestion getQuestionObject() {
        return questionObject;
    }
}
