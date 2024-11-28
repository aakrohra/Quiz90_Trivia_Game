package use_case.local_multiplayer;

import entity.TriviaQuiz;

/**
 * Output Boundary interface for the Local Multiplayer Use Case.
 */
public interface LocalMultiplayerOutputBoundary {

    // TODO all use cases need both success and fail views

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
     * Prepares the fail view.
     */
    void prepareFailView();
}
