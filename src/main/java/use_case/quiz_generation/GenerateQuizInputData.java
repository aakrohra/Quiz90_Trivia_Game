package use_case.quiz_generation;

/**
 * The Input Data for the Quiz Generation Use Case.
 */
public class QuizGenerationInputData {

    private final String category;
    private final int numberOfQuestions;
    private final String difficulty;

    public QuizGenerationInputData(String category, int numberOfQuestions, String difficulty) {
        this.category = category;
        this.numberOfQuestions = numberOfQuestions;
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
