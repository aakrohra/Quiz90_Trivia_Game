package interface_adapter.create_question;

import entity.PlayerCreatedQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the question creation view model.
 */
public class QuestionCreationState {

    private String questionText = "";
    private String correctAnswer = "";
    private String wrongAnswer1 = "";
    private String wrongAnswer2 = "";
    private String wrongAnswer3 = "";

    private List<PlayerCreatedQuestion> questionsSoFar;

    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public String getWrongAnswer3() {
        return wrongAnswer3;
    }

    public List<PlayerCreatedQuestion> getQuestionsSoFar() {
        return questionsSoFar;
    }

    /**
     * Adds to the questions created so far in this state.
     * @param playerCreatedQuestion the question to be added.
     */
    public void addToQuestionsSoFar(PlayerCreatedQuestion playerCreatedQuestion) {
        if (questionsSoFar == null) {
            questionsSoFar = new ArrayList<>();
        }
        questionsSoFar.add(playerCreatedQuestion);
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }

    public void setWrongAnswer3(String wrongAnswer3) {
        this.wrongAnswer3 = wrongAnswer3;
    }

}
