package interface_adapter.playthrough;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Playthrough View.
 */
public class PlaythroughViewModel extends ViewModel<PlaythroughState> {

    public static final String NEXT_BUTTON_LABEL = "Next Question";
    public static final String CORRECT_ANSWER_MESSAGE = "Correct!";
    public static final String INCORRECT_ANSWER_MESSAGE = "Incorrect.";

    public PlaythroughViewModel() {
        super("playthrough");
        setState(new PlaythroughState());
    }

    // TODO: If we don't need / use any of the functions below we can delete them
    // TODO: I added them as a more convenient way to use State functions.

    /**
     * Updates the selected answer in the state.
     * @param selectedAnswer The selected answer to set.
     */
    public void setSelectedAnswer(String selectedAnswer) {
        final PlaythroughState currentState = getState();
        currentState.setSelectedAnswer(selectedAnswer);
        setState(currentState);
    }

    /**
     * Moves to the next question in the quiz.
     * If there are no more questions, retains the current state.
     */
    public void advanceToNextQuestion() {
        final PlaythroughState currentState = getState();
        currentState.advanceToNextQuestion();
        setState(currentState);
    }

    /**
     * Retrieves the current question text.
     * @return The text of the current question or null if no quiz is set.
     */
    public String getCurrentQuestionText() {
        final PlaythroughState currentState = getState();
        if (currentState.getCurrentQuestion() != null) {
            return currentState.getCurrentQuestion().getQuestionText();
        }
        return null;
    }

    /**
     * Retrieves the answer options for the current question.
     * @return A string of answer options or null if no quiz is set.
     */
    public String getAnswerOptions() {
        final PlaythroughState currentState = getState();
        if (currentState.getCurrentQuestion() != null) {
            return currentState.getCurrentQuestion().getAnswerOptions();
        }
        return null;
    }
}