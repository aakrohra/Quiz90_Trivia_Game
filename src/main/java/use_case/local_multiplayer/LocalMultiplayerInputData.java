package use_case.local_multiplayer;

/**
 * Input data for the Local Multiplayer Use Case.
 */
public class LocalMultiplayerInputData {

    private final int numQuestions;
    private final String category;
    private final String difficulty;

    public LocalMultiplayerInputData(int selectedNumberOfQuestions, String selectedCategory, String difficulty) {
        this.numQuestions = selectedNumberOfQuestions;
        this.category = selectedCategory;
        this.difficulty = difficulty;
    }


}
