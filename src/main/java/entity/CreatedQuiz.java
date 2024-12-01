package entity;

import java.util.List;

/**
 * Representation of a player created quiz.
 */
public class CreatedQuiz implements Quiz {
    private final List<PlayerCreatedQuestion> listOfQuestions;
    private final String title;

    public CreatedQuiz(String title, List<PlayerCreatedQuestion> listOfQuestions) {
        this.title = title;
        this.listOfQuestions = listOfQuestions;
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

}
