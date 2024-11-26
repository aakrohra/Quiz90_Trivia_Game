package interface_adapter.summary;

import entity.Quiz;
import java.util.HashMap;
import java.util.Map;
import kotlin.Pair;

/**
 * The state for the Summary View Model.
 */
public class SummaryState {
    private Quiz quiz;
    private int numberOfCorrectAnswers;
    private Map<Integer, Pair<String, Boolean>> playerInfo = new HashMap<>();

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setPlayerInfo(Map<Integer, Pair<String, Boolean>> playerInfo) {
        this.playerInfo = playerInfo;
    }

    public Map<Integer, Pair<String, Boolean>> getPlayerInfo() {
        return playerInfo;
    }

    public void setNumberOfCorrectAnswers(int numberOfCorrectAnswers) {
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
    }

    public int getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }

    public int getNumberOfQuestions() {
        return quiz.getQuestions().size();
    }

    /**
     * Updates player information for a specific question.
     *
     * @param questionIndex The index of the question.
     * @param playerAnswer  The player's answer.
     * @param isCorrect     Whether the player's answer was correct.
     */
    public void updatePlayerInfo(int questionIndex, String playerAnswer, boolean isCorrect) {
        playerInfo.put(questionIndex, new Pair<>(playerAnswer, isCorrect));
    }

    @Override
    public String toString() {
        return "SummaryState{"
                + "quiz=" + quiz
                + ", numberOfCorrectAnswers=" + numberOfCorrectAnswers
                + ", playerInfo=" + playerInfo
                + '}';
    }
}
