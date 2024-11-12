package entity;

import java.util.List;

/**
 * Representation of a player created quiz.
 */
public class PlayerCreatedQuiz implements Quiz {
    private final String title;
    private final List<PlayerCreatedQuestion> listOfQuestions;

    public PlayerCreatedQuiz(String title, List<PlayerCreatedQuestion> listOfQuestions) {
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
            result.append(question).append("\n");
        }
        return result.toString();
    }

    public String getTitle() {
        return title;
    }

    public int getNumQuestions() {
        return listOfQuestions.size();
    }
}
