package use_case;

import data_access.DBCustomQuizDataAccessObject;
import entity.PlayerCreatedQuiz;
import entity.PlayerCreatedQuizFactory;
import org.json.JSONObject;
import org.junit.Test;
import use_case.access_quiz.*;

import static org.junit.Assert.assertEquals;

public class AccessQuizInteractorTest {

    @Test
    public void testExecuteAccessQuizFromKeySuccess() {

        AccessQuizDataAccessInterface customQuizDAO = new AccessQuizDataAccessInterface() {
            @Override
            public boolean existsByKey(String key) {
                return true;
            }

            @Override
            public JSONObject getQuizFromKey(String key) {
                return new JSONObject("{\"title\": \"titleTest\", \"questions\": [{\"questionText\": \"questionText1\", \"options\": [\"o1\", \"o2\", \"o3\", \"o4\"], \"correct\": \"o2\"}, {\"questionText\": \"question222!!!\", \"options\": [\"oo1\", \"oo2\", \"oo3\", \"oo4\"], \"correct\": \"oo4\"}]}");
            }
        };



        AccessQuizOutputBoundary accessQuizOB = new AccessQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(AccessQuizOutputData outputData) {
                PlayerCreatedQuizFactory quizFac = new PlayerCreatedQuizFactory();
                PlayerCreatedQuiz quizObject = quizFac.create(new JSONObject("{\"title\": \"titleTest\", \"questions\": [{\"questionText\": \"questionText1\", \"options\": [\"o1\", \"o2\", \"o3\", \"o4\"], \"correct\": \"o2\"}, {\"questionText\": \"question222!!!\", \"options\": [\"oo1\", \"oo2\", \"oo3\", \"oo4\"], \"correct\": \"oo4\"}]}"), "0123456789aak");
                AccessQuizOutputData correctOD = new AccessQuizOutputData(false, quizObject);
                assertEquals(correctOD.getQuizName(), outputData.getQuizName());
                assertEquals(correctOD.getAuthor(), outputData.getAuthor());
                assertEquals(correctOD.getNumQuestions(), outputData.getNumQuestions());
            }

            @Override
            public void prepareFailView(String error) {

            }

            @Override
            public void switchToLoggedInView() {

            }

            @Override
            public void playAccessedQuiz(PlayerCreatedQuiz quizObject) {

            }
        };

        AccessQuizInteractor accessQuizInteractor = new AccessQuizInteractor(customQuizDAO, accessQuizOB);
        accessQuizInteractor.execute(new AccessQuizInputData("0123456789aak"));
    }
}
