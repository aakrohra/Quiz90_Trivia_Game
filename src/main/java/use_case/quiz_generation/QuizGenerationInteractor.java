package use_case.quiz_generation;

import entity.Question;
import entity.TriviaQuestion;
import entity.TriviaQuiz;

/**
 * Interactor for the Quiz Generation Use Case.
 */
public class QuizGenerationInteractor implements QuizGenerationInputBoundary {

    private final QuizGenerationOutputBoundary quizGenerationPresenter;
    private final QuizGenerationDataAccessInterface triviaDataAccessObject;

    public QuizGenerationInteractor(QuizGenerationOutputBoundary quizGenerationPresenter,
                                    QuizGenerationDataAccessInterface triviaDataAccessObject) {
        this.quizGenerationPresenter = quizGenerationPresenter;
        this.triviaDataAccessObject = triviaDataAccessObject;
    }

    /**
     * Executes the action to switch to the Quiz Generation view.
     */
    @Override
    public void switchToQuizGenerationView() {
        quizGenerationPresenter.switchToQuizGenerationView();
    }

    /**
     * Executes the action to switch to the Main Menu view.
     */
    @Override
    public void switchToMainMenuView() {
        quizGenerationPresenter.switchToMainMenuView();
    }

    /**
     * Executes the quiz generation process using the provided input data.
     *
     * @param quizData The input data containing the number of questions, category,
     *                 and difficulty level for the quiz generation.
     */
    @Override
    public void execute(QuizGenerationInputData quizData) {
        try {
            // Attempt to fetch trivia questions
            final TriviaQuiz trivia = triviaDataAccessObject.getTrivia(quizData);

            // Log trivia details
            System.out.println("Fetching Trivia");
            System.out.println(
                    "Number of Questions: " + quizData.getNumQuestions()
                            + ", Category: " + quizData.getCategory()
                            + ", Difficulty: " + quizData.getDifficulty());
            for (Question question : trivia.getQuestions()) {
                System.out.println("Question: " + question.getQuestionText());
                System.out.println("Correct Answer: " + question.getCorrectAnswer());
                System.out.println("Incorrect Answers: " + String.join(", ", question.getIncorrectAnswers()));
                System.out.println();
            }

            // Prepare success view
            quizGenerationPresenter.prepareSuccessView(trivia);
        } catch (Exception ex) {
            // Handle the failure case
            quizGenerationPresenter.prepareFailView(ex.getMessage());
        }
    }

}
