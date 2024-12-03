package interface_adapter.local_multiplayer_playthrough;

import entity.Question;
import entity.Quiz;
import kotlin.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * The state for the LocalMultiplayerPlaythrough View Model.
 */
public class LocalMultiplayerPlaythroughState {
    private Quiz quiz;
    private int currentQuestionIndex;
    private String selectedAnswer = "";
    private Boolean currentPlayerIsOne = true;
    private Map<Integer, Pair<String, Boolean>> playerOneInfo = new HashMap<>();
    private Integer[] numMapCorrect = {0, 0};
    private Map<Integer, Pair<String, Boolean>> playerTwoInfo = new HashMap<>();

    /**
     * Sets the current quiz and resets the state for a new quiz.
     *
     * @param quiz The quiz to set.
     */
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        // Reset the state for a new quiz
        this.currentQuestionIndex = 0;
        this.selectedAnswer = "";
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    /**
     * Gets the current question from the quiz.
     *
     * @return The current question, or null if the quiz is not set or no more questions.
     */
    public Question getCurrentQuestion() {
        if (quiz == null || currentQuestionIndex < 0 || currentQuestionIndex >= quiz.getQuestions().size()) {
            System.out.println("Error index out of bounds.");
        }
        return quiz.getQuestions().get(currentQuestionIndex);
    }

    @Override
    public String toString() {
        return "LocalMultiplayerPlaythroughState{"
                + "quiz=" + quiz
                + ", currentQuestionIndex=" + currentQuestionIndex
                + ", selectedAnswer='" + selectedAnswer + '\''
                + '}';
    }

    public Boolean getCurrentPlayerIsOne() {
        return currentPlayerIsOne;
    }

    public void setCurrentPlayerIsOne(boolean currentPlayerIsOne) {
        this.currentPlayerIsOne = currentPlayerIsOne;
    }

    /**
     * Clears player one info map.
     */
    public void clearPlayerOneInfo() {
        playerOneInfo.clear();
    }

    public Map<Integer, Pair<String, Boolean>> getPlayerOneInfo() {
        return playerOneInfo;
    }

    public void setPlayerOneInfo(Integer playerOneInfoInt, Pair<String, Boolean> playerOneInfoMore) {
        this.playerOneInfo.put(playerOneInfoInt, playerOneInfoMore);
    }

    public Integer[] getNumMapCorrect() {
        return numMapCorrect;
    }

    public void setNumMapCorrect(Integer[] numMapCorrect) {
        this.numMapCorrect = numMapCorrect;
    }

    /**
     * Clears player two info map.
     */
    public void clearPlayerTwoInfo() {
        playerTwoInfo.clear();
    }

    public Map<Integer, Pair<String, Boolean>> getPlayerTwoInfo() {
        return playerTwoInfo;
    }

    public void setPlayerTwoInfo(Integer playerTwoInfoInt, Pair<String, Boolean> playerTwoInfoMore) {
        this.playerTwoInfo.put(playerTwoInfoInt, playerTwoInfoMore);
    }
}
