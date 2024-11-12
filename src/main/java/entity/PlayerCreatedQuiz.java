package entity;

import java.util.List;
import java.util.Random;

import app.Constants;

/**
 * Representation of a player created quiz.
 */
public class PlayerCreatedQuiz implements Quiz {
    private final List<PlayerCreatedQuestion> listOfQuestions;
    private String uniqueKey = "";
    private final User user;
    private final String username;
    private final String title;

    public PlayerCreatedQuiz(User user, String title, List<PlayerCreatedQuestion> listOfQuestions) {
        this.user = user;
        this.username = user.getName();
        this.uniqueKey = this.randomKey() + username;
        this.title = title;
        this.listOfQuestions = listOfQuestions;
    }

    private String randomKey() {
        final int keyLength = Constants.RANDOMKEYSIZE;
        final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final StringBuilder key = new StringBuilder();
        final Random random = new Random();
        for (int i = 0; i < keyLength; i++) {
            final int index = random.nextInt(chars.length());
            key.append(chars.charAt(index));
        }
        return key.toString();
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
