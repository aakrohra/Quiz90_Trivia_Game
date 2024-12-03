package use_case;

import data_access.DBCustomQuizDataAccessObject;
import entity.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.junit.Test;
import use_case.access_database.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AccessDatabaseInteractorTest {

    public Map<String, RetrievedQuiz> exampleQuizMap() {
        RetrievedQuiz quiz = exampleQuiz();
        Map<String, RetrievedQuiz> quizzes = new HashMap<>();
        quizzes.put("keyTest", quiz);
        return quizzes;
    }

    private static RetrievedQuiz exampleQuiz() {
        String quizTitle = "snoopy";
        List<PlayerCreatedQuestion> listOfQuestions = new ArrayList<>();
        List<String> answerOptions = new ArrayList<>();
        answerOptions.add("1");
        answerOptions.add("2");
        answerOptions.add("3");
        answerOptions.add("4");
        listOfQuestions.add(new PlayerCreatedQuestion("Question", answerOptions, "1"));
        String author = "testDatabase";
        RetrievedQuiz quiz = new RetrievedQuiz(quizTitle, listOfQuestions, author);
        return quiz;
    }

    public PlayerQuizDatabase exampleDatabase() {
        return new PlayerQuizDatabase(exampleQuizMap());
    }

    @Test
    public void testExecuteAccessDatabaseFromUserSuccess() {

        AccessDatabaseUserDataAccessInterface customDatabaseDAO = new AccessDatabaseUserDataAccessInterface() {
            @Override
            public Map<String, RetrievedQuiz> getAllUserQuizzes(String username) {
                return exampleQuizMap();
            }

            @Override
            public JSONObject getUserInfo(User user) {
                return null;
            }

            @Override
            public boolean existsByName(String username) { return true; }
        };

        AccessDatabaseOutputBoundary customDatabaseOB = new AccessDatabaseOutputBoundary() {
            @Override
            public void prepareSuccessView(AccessDatabaseOutputData data) {
                PlayerQuizDatabase database = data.getQuizDatabase();
                PlayerQuizDatabase correctDatabase = exampleDatabase();
                RetrievedQuiz retrievedQuiz = database.getAll().get("keyTest");
                RetrievedQuiz correctRetrievedQuiz = correctDatabase.getAll().get("keyTest");
                assertEquals(retrievedQuiz.getTitle(), correctRetrievedQuiz.getTitle());
                assertEquals(retrievedQuiz.getAuthor(), correctRetrievedQuiz.getAuthor());
                assertEquals(retrievedQuiz.getNumQuestions(), correctRetrievedQuiz.getNumQuestions());
            }

            @Override
            public void switchToMainMenuView() {
                fail("Use case main menu not expected.");
            }

            @Override
            public void preparePlaythroughView(RetrievedQuiz quiz) {
                fail("Use case playthrough not expected.");
            }

            @Override
            public void switchToCreateQuestionView(String username) {
                fail("Use case createQuestion not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case fail not expected.");
            }
        };

        AccessDatabaseInteractor accessDatabaseInteractor = new AccessDatabaseInteractor(customDatabaseDAO, customDatabaseOB);
        accessDatabaseInteractor.execute(new AccessDatabaseInputData("testDatabase"));
    }

    @Test
    public void testExecuteAccessDatabaseFromUserFailure() {
        AccessDatabaseUserDataAccessInterface customDatabaseDAO = new AccessDatabaseUserDataAccessInterface() {
            @Override
            public Map<String, RetrievedQuiz> getAllUserQuizzes(String username) {
                return exampleQuizMap();
            }

            @Override
            public JSONObject getUserInfo(User user) { return null; }

            @Override
            public boolean existsByName(String username) { return false; }
        };

        AccessDatabaseOutputBoundary customDatabaseOB = new AccessDatabaseOutputBoundary() {
            @Override
            public void prepareSuccessView(AccessDatabaseOutputData data) {
                fail("Use case prepareSuccessView not expected.");
            }

            @Override
            public void switchToMainMenuView() {
                fail("Use case main menu not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals(error, "There is no user named: testDatabaseThisUserDoesNotExist");
            }

            @Override
            public void switchToCreateQuestionView(String username) {
                fail("Use case createQuestion not expected.");
            }

            @Override
            public void preparePlaythroughView(RetrievedQuiz quiz) {
                fail("Use case playthrough not expected.");
            }
        };

        AccessDatabaseInteractor accessDatabaseInteractor = new AccessDatabaseInteractor(customDatabaseDAO, customDatabaseOB);
        accessDatabaseInteractor.execute(new AccessDatabaseInputData("testDatabaseThisUserDoesNotExist"));

    }

    @Test
    public void testSwitchToMainMenuView() {
        AccessDatabaseUserDataAccessInterface customDatabaseDAO = new AccessDatabaseUserDataAccessInterface() {
            @Override
            public Map<String, RetrievedQuiz> getAllUserQuizzes(String username) {
                return exampleQuizMap();
            }

            @Override
            public JSONObject getUserInfo(User user) { return null; }

            @Override
            public boolean existsByName(String username) { return true; }
        };

        AccessDatabaseOutputBoundary customDatabaseOB = new AccessDatabaseOutputBoundary() {
            @Override
            public void prepareSuccessView(AccessDatabaseOutputData data) {
                fail("Use case prepareSuccessView not expected.");
            }

            @Override
            public void switchToMainMenuView() {
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case prepareFailView not expected.");
            }

            @Override
            public void switchToCreateQuestionView(String username) {
                fail("Use case createQuestion not expected.");
            }

            @Override
            public void preparePlaythroughView(RetrievedQuiz quiz) {
                fail("Use case playthrough not expected.");
            }
        };

        AccessDatabaseInteractor accessDatabaseInteractor = new AccessDatabaseInteractor(customDatabaseDAO, customDatabaseOB);
        accessDatabaseInteractor.switchToMainMenuView();
    }

    @Test
    public void testSwitchToPlaythroughView() {
        AccessDatabaseUserDataAccessInterface customDatabaseDAO = new AccessDatabaseUserDataAccessInterface() {
            @Override
            public Map<String, RetrievedQuiz> getAllUserQuizzes(String username) {
                return exampleQuizMap();
            }

            @Override
            public JSONObject getUserInfo(User user) { return null; }

            @Override
            public boolean existsByName(String username) { return true; }
        };

        AccessDatabaseOutputBoundary customDatabaseOB = new AccessDatabaseOutputBoundary() {
            @Override
            public void prepareSuccessView(AccessDatabaseOutputData data) {
                fail("Use case prepareSuccessView not expected.");
            }

            @Override
            public void switchToMainMenuView() {
                fail("Use case main menu not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case prepareFailView not expected.");
            }

            @Override
            public void switchToCreateQuestionView(String username) {
                fail("Use case createQuestion not expected.");
            }

            @Override
            public void preparePlaythroughView(RetrievedQuiz quiz) {
                RetrievedQuiz correctQuiz = exampleQuiz();
                assertEquals(quiz.getTitle(), correctQuiz.getTitle());
                assertEquals(quiz.getAuthor(), correctQuiz.getAuthor());
                assertEquals(quiz.getNumQuestions(), correctQuiz.getNumQuestions());
            }
        };

        AccessDatabaseInteractor accessDatabaseInteractor = new AccessDatabaseInteractor(customDatabaseDAO, customDatabaseOB);
        accessDatabaseInteractor.switchToPlaythroughView(exampleQuiz());
    }

    @Test
    public void testSwitchToCreateQuestionView() {
        AccessDatabaseUserDataAccessInterface customDatabaseDAO = new AccessDatabaseUserDataAccessInterface() {
            @Override
            public Map<String, RetrievedQuiz> getAllUserQuizzes(String username) {
                return exampleQuizMap();
            }

            @Override
            public JSONObject getUserInfo(User user) { return null; }

            @Override
            public boolean existsByName(String username) { return true; }
        };

        AccessDatabaseOutputBoundary customDatabaseOB = new AccessDatabaseOutputBoundary() {
            @Override
            public void prepareSuccessView(AccessDatabaseOutputData data) {
                fail("Use case prepareSuccessView not expected.");
            }

            @Override
            public void switchToMainMenuView() {
                fail("Use case main menu not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case prepareFailView not expected.");
            }

            @Override
            public void switchToCreateQuestionView(String username) {
                assertEquals(username, "testDatabase");
            }

            @Override
            public void preparePlaythroughView(RetrievedQuiz quiz) {
                fail("Use case preparePlaythroughView not expected.");
            }
        };

        AccessDatabaseInteractor accessDatabaseInteractor = new AccessDatabaseInteractor(customDatabaseDAO, customDatabaseOB);
        accessDatabaseInteractor.switchToCreateQuestionView("testDatabase");
    }

    @Test
    public void testUpdateUsernameSuccess() {
        AccessDatabaseUserDataAccessInterface customDatabaseDAO = new AccessDatabaseUserDataAccessInterface() {
            @Override
            public Map<String, RetrievedQuiz> getAllUserQuizzes(String username) {
                return exampleQuizMap();
            }

            @Override
            public JSONObject getUserInfo(User user) { return null; }

            @Override
            public boolean existsByName(String username) { return true; }
        };

        AccessDatabaseOutputBoundary customDatabaseOB = new AccessDatabaseOutputBoundary() {
            @Override
            public void prepareSuccessView(AccessDatabaseOutputData data) {
            }

            @Override
            public void switchToMainMenuView() {
                fail("Use case main menu not expected.");
            }

            @Override
            public void preparePlaythroughView(RetrievedQuiz quiz) {
                fail("Use case playthrough not expected.");
            }

            @Override
            public void switchToCreateQuestionView(String username) {
                fail("Use case createQuestion not expected.");
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case fail not expected.");
            }
        };

        AccessDatabaseInteractor accessDatabaseInteractor = new AccessDatabaseInteractor(customDatabaseDAO, customDatabaseOB);
        accessDatabaseInteractor.updateDatabase("testDatabase");
    }

    @Test
    public void testUpdateUsernameFail() {
        AccessDatabaseUserDataAccessInterface customDatabaseDAO = new AccessDatabaseUserDataAccessInterface() {
            @Override
            public Map<String, RetrievedQuiz> getAllUserQuizzes(String username) {
                return exampleQuizMap();
            }

            @Override
            public JSONObject getUserInfo(User user) { return null; }

            @Override
            public boolean existsByName(String username) { return false; }
        };

        AccessDatabaseOutputBoundary customDatabaseOB = new AccessDatabaseOutputBoundary() {
            @Override
            public void prepareSuccessView(AccessDatabaseOutputData data) {
                fail("Use case prepareSuccessView not expected.");
            }

            @Override
            public void switchToMainMenuView() {
                fail("Use case main menu not expected.");
            }

            @Override
            public void preparePlaythroughView(RetrievedQuiz quiz) {
                fail("Use case playthrough not expected.");
            }

            @Override
            public void switchToCreateQuestionView(String username) {
                fail("Use case createQuestion not expected.");
            }

            @Override
            public void prepareFailView(String error) {
            }
        };

        AccessDatabaseInteractor accessDatabaseInteractor = new AccessDatabaseInteractor(customDatabaseDAO, customDatabaseOB);
        accessDatabaseInteractor.updateDatabase("testDatabaseThisUserDoesNotExist");
    }

}
