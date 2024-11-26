package entity;

import java.util.List;

/**
 * Representation of a player created quiz.
 */
public class PlayerCreatedQuiz implements Quiz {
    private final List<PlayerCreatedQuestion> listOfQuestions;
    private final String title;
    private final String author;

    public PlayerCreatedQuiz(String title, List<PlayerCreatedQuestion> listOfQuestions, String author) {
        this.title = title;
        this.listOfQuestions = listOfQuestions;
        this.author = author;
    }

    /**
     * String representation of the list of questions of this trivia quiz.
     * @return String representation of the list of questions of this trivia quiz.
     */
    public String getListOfQuestions() {
        final StringBuilder result = new StringBuilder();
        for (PlayerCreatedQuestion question : this.listOfQuestions) {
            result.append(question.getQuestionText()).append("\n");
        }
        return result.toString();
    }

    @Override
    public List<? extends Question> getQuestions() {
        return listOfQuestions;
    }

    public String getTitle() {
        return title;
    }

    public int getNumQuestions() {
        return listOfQuestions.size();
    }

    public String getAuthor() {
        return author;
    }
}
