package use_case;

import entity.*;
import org.json.JSONObject;
import org.junit.Test;
import use_case.access_database.AccessDatabaseOutputBoundary;
import use_case.access_database.AccessDatabaseOutputData;
import use_case.access_database.AccessDatabaseUserDataAccessInterface;
import use_case.access_quiz.AccessQuizDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessDatabaseInteractorTest {

    @Test
    public void testExecuteAccessDatabaseFromLoggedInUser() {

        AccessDatabaseUserDataAccessInterface customQuizDAO = new AccessDatabaseUserDataAccessInterface() {
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

            @Override
            public boolean existsByName(String username) {
                return true;
            }
        };

        AccessDatabaseOutputBoundary accessDatabaseOutputBoundary = new AccessDatabaseOutputBoundary() {
            @Override
            public void prepareSuccessView(AccessDatabaseOutputData data) {
                RetrievedQuizFactory retrievedQuizFactory = new RetrievedQuizFactory();
                // RetrievedQuiz quizObject = retrievedQuizFactory.create(new )
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
        }
    }
}
