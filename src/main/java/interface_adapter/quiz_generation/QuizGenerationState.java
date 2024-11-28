package interface_adapter.quiz_generation;

/**
 * The state for the QuizGeneration ViewModel.
 */
public class QuizGenerationState {
    private String selectedCategory = "";
    private String selectedNumberOfQuestions = "";
    private String selectedDifficulty = "";
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "QuizGenerationState{"
                + "selectedCategory='" + selectedCategory + '\''
                + ", selectedNumberOfQuestions='" + selectedNumberOfQuestions + '\''
                + ", selectedDifficulty='" + selectedDifficulty + '\''
                + ", error='" + error + '\''
                + '}';
    }
}
