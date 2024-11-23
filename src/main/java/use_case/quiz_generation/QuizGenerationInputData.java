package use_case.quiz_generation;

/**
 * The Input Data for the Quiz Generation Use Case.
 */
public class QuizGenerationInputData {

    private final int numQuestions;
    private final String category;
    private final String difficulty;

    public QuizGenerationInputData(int selectedNumberOfQuestions, String selectedCategory, String difficulty) {
        this.numQuestions = selectedNumberOfQuestions;
        this.category = selectedCategory;
        this.difficulty = difficulty;
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
