package use_case.access_quiz;

import entity.Quiz;

/**
 * Output data for the Access Quiz use case.
 */
public class AccessQuizOutputData {

    private final boolean useCaseFailed;
    private final String quizName;
    private final int numQuestions;
    private final Quiz quizObject;

    public AccessQuizOutputData(boolean useCaseFailed, String quizName, int numQuestions, Quiz quizObject) {
        this.useCaseFailed = useCaseFailed;
        this.quizName = quizName;
        this.numQuestions = numQuestions;
        this.quizObject = quizObject;
    }

    public String getQuizName() {
        return quizName;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public Quiz getQuizObject() {
        return quizObject;
    }
}
