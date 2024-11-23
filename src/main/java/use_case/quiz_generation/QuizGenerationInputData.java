package use_case.quiz_generation;

/**
 * The Input Data for the Quiz Generation Use Case.
 */
public class QuizGenerationInputData {

    private final String difficulty;
    private final String selectedCategory;
    private final String selectedNumberOfQuestions;

    public QuizGenerationInputData(String difficulty, String selectedCategory, String selectedNumberOfQuestions) {
        this.difficulty = difficulty;
        this.selectedCategory = selectedCategory;
        this.selectedNumberOfQuestions = selectedNumberOfQuestions;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public String getSelectedNumberOfQuestions() {
        return selectedNumberOfQuestions;
    }
}
