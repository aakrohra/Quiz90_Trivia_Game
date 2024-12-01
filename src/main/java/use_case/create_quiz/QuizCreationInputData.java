package use_case.create_quiz;

import java.util.List;

import entity.PlayerCreatedQuestion;

/**
 * The input data object for the quiz creation use case.
 */
public class QuizCreationInputData {

    private List<PlayerCreatedQuestion> questions;
    private String title;
    private String username;

    public QuizCreationInputData(List<PlayerCreatedQuestion> questions, String title, String username) {
        this.questions = questions;
        this.title = title;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
