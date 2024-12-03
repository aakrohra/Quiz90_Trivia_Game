package use_case.local_multiplayer;

import entity.*;
import interface_adapter.local_multiplayer_playthrough.LocalMultiplayerPlaythroughState;
import org.junit.Test;
import use_case.quiz_generation.QuizGenerationDataAccessInterface;
import use_case.quiz_generation.QuizGenerationInputData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LocalMultiplayerInteractorTest {
    @Test
    public void testExecuteGenerateQuizSuccess() {

        // Test input and outputs of interactor
        QuizGenerationDataAccessInterface generatedQuizDAO = new QuizGenerationDataAccessInterface() {
            @Override
            public TriviaQuiz getTrivia(QuizGenerationInputData quizData) throws Exception {
                List<TriviaQuestion> listOfTriviaQuestions = new ArrayList<>();
                for (int i = 1; i <= 5; i++) {
                    List<String> incorrectAnswers = new ArrayList<>();
                    for (int j = 2; j < 5; j++) {
                        incorrectAnswers.add("Question0");
                    }
                    listOfTriviaQuestions.add(new TriviaQuestion("Question" + i, "Answer" + i, incorrectAnswers));
                }
                return new TriviaQuiz(listOfTriviaQuestions);
            }
        };

        LocalMultiplayerOutputBoundary localMultiplayerOB = new LocalMultiplayerOutputBoundary() {
            @Override
            public void prepareQuiz(TriviaQuiz triviaQuiz) {
                TriviaQuiz triviaQuiz1;
                try {
                    QuizGenerationInputData quizGenerationInputData = new QuizGenerationInputData(5, "Animals", "Easy");
                    triviaQuiz1 = generatedQuizDAO.getTrivia(quizGenerationInputData);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                assertEquals(triviaQuiz.getListOfQuestions(), triviaQuiz1.getListOfQuestions());
                assertEquals(triviaQuiz.getQuestions(), triviaQuiz1.getQuestions());
            }

            @Override
            public void switchToLocalMultiplayerView() {
                // not needed for this test

            }

            @Override
            public void switchToMainMenuView() {
                // not needed for this test

            }

            @Override
            public void prepareFailView(String errorMessage) {
                // not needed for this test

            }

            @Override
            public void nextQuestion(LocalMultiplayerPlaythroughState state) {
                // not needed for this test
            }

        };

        LocalMultiplayerInteractor localMultiplayerInteractor = new LocalMultiplayerInteractor(localMultiplayerOB, generatedQuizDAO);
        localMultiplayerInteractor.execute(new QuizGenerationInputData(5, "Animals", "Easy"));
    }

}
