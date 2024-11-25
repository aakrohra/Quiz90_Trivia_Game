package interface_adapter.local_multiplayer;

import use_case.local_multiplayer.LocalMultiplayerInputBoundary;
import use_case.local_multiplayer.LocalMultiplayerInputData;
import use_case.quiz_generation.QuizGenerationInputData;

/**
 * Controller for the Local Multiplayer Use Case.
 */
public class LocalMultiplayerController {

    private final LocalMultiplayerInputBoundary localMultiplayerInputBoundary;

    public LocalMultiplayerController(LocalMultiplayerInputBoundary localMultiplayerInteractor) {
        this.localMultiplayerInputBoundary = localMultiplayerInteractor;
    }

    /**
     * Executes the action to generate a quiz and play through it.
     * @param difficulty the chosen difficulty of the quiz.
     * @param selectedCategory the chosen category for the quiz.
     * @param selectedNumberOfQuestions the chosen number of questions in the quiz.
     */
    public void execute(int selectedNumberOfQuestions, String selectedCategory, String difficulty) {
        final QuizGenerationInputData multiplayerQuizGenerationInputData = new QuizGenerationInputData(
                selectedNumberOfQuestions, selectedCategory, difficulty);

        localMultiplayerInputBoundary.execute(multiplayerQuizGenerationInputData);
    }

    /**
     * Executes the action to switch to the Local Multiplayer view.
     */
    public void switchToLocalMultiplayerView() {
        localMultiplayerInputBoundary.switchToLocalMultiplayerView();
    }

    /**
     * Executes the action to switch to the Main Menu view.
     */
    public void switchToMainMenuView() {
        localMultiplayerInputBoundary.switchToMainMenuView();
    }
}
