package entity;

import java.util.List;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name;
    private final String password;
    private final int currentScore;
    private final int highScore;
    // Note that keys are not the entire question as one line.
    private final List<String> createdQuestionsKeys;

    public CommonUser(String name, String password, int currentScore, int highScore,
                      List<String> createdQuestionsKeys) {
        this.name = name;
        this.password = password;
        this.currentScore = currentScore;
        this.highScore = highScore;
        this.createdQuestionsKeys = createdQuestionsKeys;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int getCurrentScore() {
        return currentScore;
    }

    @Override
    public int getHighScore() {
        return highScore;
    }
}
