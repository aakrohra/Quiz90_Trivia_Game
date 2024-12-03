package use_case.local_multiplayer;

import entity.TriviaQuiz;
import interface_adapter.local_multiplayer_playthrough.LocalMultiplayerPlaythroughState;

/**
 * Output Boundary interface for the Local Multiplayer Use Case.
 */
public interface LocalMultiplayerOutputBoundary {

    /**
     * Prepares trivia quiz.
     * @param triviaQuiz The trivia quiz to be played.
     */
    void prepareQuiz(TriviaQuiz triviaQuiz);

    /**
     * Prepares the view to switch to the Local Multiplayer screen.
     */
    void switchToLocalMultiplayerView();

    /**
     * Prepares the view to switch to the Main Menu view.
     */
    void switchToMainMenuView();

    /**
     * Prepares the fail view for creating a quiz.
     * @param errorMessage The error message that shows up on the fail view.
     */
    void prepareFailView(String errorMessage);

    /**
     * Executes the action to go to the next question in the quiz.
     * @param state The local multiplayer playthrough state.
     */
    void nextQuestion(LocalMultiplayerPlaythroughState state);

}
