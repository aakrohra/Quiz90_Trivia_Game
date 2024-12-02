package interface_adapter.local_multiplayer_playthrough;

import entity.Question;
import entity.Quiz;

/**
 * The state for the LocalMultiplayerPlaythrough View Model.
 */
public class LocalMultiplayerPlaythroughState {
    private Quiz quiz;
    private int currentQuestionIndex;
    private int numberOfCorrectAnswers;
    private String selectedAnswer = "";
    private Boolean currentPlayerIsOne = true;

    /**
     * Sets the current quiz and resets the state for a new quiz.
     *
     * @param quiz The quiz to set.
     */
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        // Reset the state for a new quiz
        this.currentQuestionIndex = 0;
        this.numberOfCorrectAnswers = 0;
        this.selectedAnswer = "";
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    /**
     * Adds one two the total number of correct answers.
     * Should be used each time a question is answers correctly
     */
    public void updateNumberOfCorrectAnswers() {
        this.numberOfCorrectAnswers++;
    }

    public int getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
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

    /**
     * Advances to the next question in the quiz, if available.
     */
    public void advanceToNextQuestion() {
        if (quiz != null && currentQuestionIndex < quiz.getQuestions().size() - 1) {
            currentQuestionIndex++;
            selectedAnswer = "";
        }
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
}
