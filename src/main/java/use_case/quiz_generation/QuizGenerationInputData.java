package use_case.quiz_generation;

/**
 * The Input Data for the Quiz Generation Use Case.
 */
public class QuizGenerationInputData {

    private final String difficulty;
    private final String category;
    private final int numQuestions;

    public QuizGenerationInputData(String difficulty, String selectedCategory, int selectedNumberOfQuestions) {
        this.difficulty = difficulty;
        this.category = selectedCategory;
        this.numQuestions = selectedNumberOfQuestions;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCategory() {
        return category;
    }

    public int getNumQuestions() {
        return numQuestions;
    }
}
