package use_case.create_quiz;

import data_access.DBCustomQuizDataAccessObject;
import entity.CreatedQuiz;
import entity.User;
import interface_adapter.logged_in.LoggedInState;
import org.json.JSONObject;

/**
 * The interactor for the quiz creation use case.
 */
public class QuizCreationInteractor implements QuizCreationInputBoundary {

    private final QuizCreationOutputBoundary quizCreationOutputBoundary;
    private final CreateQuizDataAccessInterface customQuizDAO;
    private final GetUserDataAccessInterface getUserDAO;
    private final DBCustomQuizDataAccessObject dao = new DBCustomQuizDataAccessObject();

    public QuizCreationInteractor(CreateQuizDataAccessInterface customQuizDAO,
                                  GetUserDataAccessInterface getUserDAO,
                                  QuizCreationOutputBoundary quizCreationOutputBoundary) {
        this.customQuizDAO = customQuizDAO;
        this.getUserDAO = getUserDAO;
        this.quizCreationOutputBoundary = quizCreationOutputBoundary;
    }

    @Override
    public void executeCreateQuiz(QuizCreationInputData inputData) {
        final CreatedQuiz quizObject = new CreatedQuiz(inputData.getTitle(), inputData.getQuestions());
        final JSONObject jsonData = customQuizDAO.quizObjectToJSONObject(quizObject);

        // this needs to be changed to get currently logged in user somehow somewhere
        final String key = customQuizDAO.addQuiz(jsonData, getUserDAO.get(inputData.getUsername()));

        final QuizCreationOutputData outputData = new QuizCreationOutputData(quizObject, key);
        quizCreationOutputBoundary.prepareSuccessView(outputData);
    }

}
