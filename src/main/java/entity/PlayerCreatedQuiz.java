package entity;

import app.Constants;

import java.util.List;
import java.util.Random;

/**
 * Representation of a player created quiz.
 */
public class PlayerCreatedQuiz {
    private final List<PlayerCreatedQuestion> listOfQuestions;
    private final String category;
    private String uniqueKey = "";
    private final User user;
    private final String username;

    public PlayerCreatedQuiz(String category, User user) {
        this.user = user;
        this.username = user.getName();
        this.listOfQuestions = listOfQuestionsMaker();
        this.category = category;
        this.uniqueKey = this.randomKey() + username;
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
     *
     * @return String representation of the list of questions of this trivia quiz.
     */
    public String getListOfQuestions() {
        final StringBuilder result = new StringBuilder();
        for (PlayerCreatedQuestion question : this.listOfQuestions) {
            result.append(question).append("\n");
        }
        return result.toString();
    }

    public String getCategory() {
        return this.category;
    }

    public String getUniqueKey() {
        return this.uniqueKey;
    }

    private List<PlayerCreatedQuestion> listOfQuestionsMaker() {
        // TODO this needs implementation based on API
        return null;
    }
}