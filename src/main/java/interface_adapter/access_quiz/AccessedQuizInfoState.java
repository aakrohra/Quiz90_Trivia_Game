package interface_adapter.access_quiz;

import entity.Quiz;

/**
 * The state information regarding the accessed quiz.
 */
public class AccessedQuizInfoState {
    private String quizName = "";
    private int numQuestions;
    private Quiz quizObject;

    public AccessedQuizInfoState(AccessedQuizInfoState copy) {
        quizName = copy.quizName;
        numQuestions = copy.numQuestions;
        quizObject = copy.quizObject;
    }

    public AccessedQuizInfoState() {

    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public Quiz getQuizObject() {
        return quizObject;
    }

    public void setQuizObject(Quiz quizObject) {
        this.quizObject = quizObject;
    }

}
