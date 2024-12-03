package interface_adapter.local_multiplayer_summary;

import java.util.HashMap;
import java.util.Map;

import entity.Quiz;
import kotlin.Pair;

/**
 * The state for the Summary View Model.
 */
public class LocalMultiplayerSummaryState {
    private Quiz quiz;
    private Map<Integer, Pair<String, Boolean>> playerOneInfo = new HashMap<>();
    private Map<Integer, Pair<String, Boolean>> playerTwoInfo = new HashMap<>();
    private Integer[] numMapCorrect;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setPlayerOneInfo(Map<Integer, Pair<String, Boolean>> playerOneInfo) {
        this.playerOneInfo = playerOneInfo;
    }

    public Map<Integer, Pair<String, Boolean>> getPlayerOneInfo() {
        return playerOneInfo;
    }

    public void setPlayerTwoInfo(Map<Integer, Pair<String, Boolean>> playerTwoInfo) {
        this.playerTwoInfo = playerTwoInfo;
    }

    public Map<Integer, Pair<String, Boolean>> getPlayerTwoInfo() {
        return playerTwoInfo;
    }

    public int getNumberOfQuestions() {
        return quiz.getQuestions().size();
    }

    public Integer[] getNumMapCorrect() {
        return numMapCorrect;
    }

    public void setNumMapCorrect(Integer[] numMapCorrect) {
        this.numMapCorrect = numMapCorrect;
    }
}
