package use_case;

import data_access.DBCustomQuizDataAccessObject;
import entity.*;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.access_database.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessDatabaseInteractorTest {

    private AccessDatabaseInteractor accessDatabaseInteractor;
    private AccessDatabaseOutputBoundary accessDatabasePresenter;
    private AccessDatabaseUserDataAccessInterface accessDatabaseUserDataAccessInterface;

    @BeforeEach
    public void setup() {
        accessDatabaseUserDataAccessInterface = new AccessDatabaseUserDataAccessInterface() {
            @Override
            public Map<String, RetrievedQuiz> getAllUserQuizzes(String username) {
                Map<String, RetrievedQuiz> userQuizzes = new HashMap<>();
                CommonUserFactory commonUserFactory = new CommonUserFactory();
                User user = commonUserFactory.create("testDatabase", "testDatabase");
                String quizTitle = "Test1";
                List<PlayerCreatedQuestion> listOfQuestions = new ArrayList<>();
                List<String> answerOptions = new ArrayList<>();
                answerOptions.add("1");
                answerOptions.add("2");
                answerOptions.add("3");
                answerOptions.add("4");
                listOfQuestions.add(new PlayerCreatedQuestion("Test1", answerOptions, "1"));
                String key = "biS4UVnqojtestDatabase";
                RetrievedQuiz quiz = new RetrievedQuiz(quizTitle, listOfQuestions, "testDatabase");
                userQuizzes.put(key, quiz);
                return userQuizzes;
            }

            /**
             * @param user the given user
             * @return
             */
            @Override
            public JSONObject getUserInfo(User user) {
                return null;
            }

            @Override
            public boolean existsByName(String username) {
                return true;
            }
        };

        accessDatabasePresenter = new AccessDatabaseOutputBoundary() {
            @Override
            public void prepareSuccessView(AccessDatabaseOutputData data) {
                data.getQuizDatabase();
            }

            @Override
            public void switchToMainMenuView() {

            }

            @Override
            public void preparePlaythroughView(Quiz quiz) {

            }

            @Override
            public void switchToCreateQuestionView(String username) {

            }

            @Override
            public void prepareFailView(String error) {
            }
        };
    }

    @Test
    public void testExecuteUserExists() {
        AccessDatabaseUserDataAccessInterface testDAO = new DBCustomQuizDataAccessObject();
        AccessDatabaseInputData testID = new AccessDatabaseInputData("testDatabase");
        AccessDatabaseInteractor accessDatabaseInteractor = new AccessDatabaseInteractor(testDAO, accessDatabasePresenter);
        accessDatabaseInteractor.execute(testID);

    }
}
