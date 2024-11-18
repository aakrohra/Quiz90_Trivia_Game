package interface_adapter.quiz_generation;

/**
 * The state for the QuizGeneration ViewModel.
 */
public class QuizGenerationState {
    private String selectedCategory = "";
    private String selectedNumberOfQuestions = "";
    private String selectedDifficulty = "";

    // Getters
    public String getSelectedCategory() {
        return selectedCategory;
    }

    public String getSelectedNumberOfQuestions() {
        return selectedNumberOfQuestions;
    }

    public String getSelectedDifficulty() {
        return selectedDifficulty;
    }

    // Setters
    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public void setSelectedNumberOfQuestions(String selectedNumberOfQuestions) {
        this.selectedNumberOfQuestions = selectedNumberOfQuestions;
    }

    public void setSelectedDifficulty(String selectedDifficulty) {
        this.selectedDifficulty = selectedDifficulty;
    }

    @Override
    public String toString() {
        return "QuizGenerationState{"
                + "selectedCategory='" + selectedCategory + '\''
                + ", selectedNumberOfQuestions='" + selectedNumberOfQuestions + '\''
                + ", selectedDifficulty='" + selectedDifficulty + '\''
                + '}';
    }
}
