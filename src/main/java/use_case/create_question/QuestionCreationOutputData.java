package use_case.create_question;

import entity.PlayerCreatedQuestion;

public class QuestionCreationOutputData {

    private final PlayerCreatedQuestion questionObject;

    public QuestionCreationOutputData(PlayerCreatedQuestion questionObject) {
        this.questionObject = questionObject;
    }

    public PlayerCreatedQuestion getQuestionObject() {
        return questionObject;
    }
}
