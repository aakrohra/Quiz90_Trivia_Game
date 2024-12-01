package use_case.create_quiz;

import entity.PlayerCreatedQuestion;

import java.util.List;

/**
 * The input data object for the quiz creation use case.
 */
public class QuizCreationInputData {

    private List<PlayerCreatedQuestion> questions;
    private String title;

    public QuizCreationInputData(List<PlayerCreatedQuestion> questions, String title) {
        this.questions = questions;
        this.title = title;
    }

    public List<PlayerCreatedQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<PlayerCreatedQuestion> questions) {
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
