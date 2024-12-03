package use_case;

import entity.*;
import interface_adapter.local_multiplayer_playthrough.LocalMultiplayerPlaythroughState;
import org.junit.Test;
import use_case.local_multiplayer.LocalMultiplayerInteractor;
import use_case.local_multiplayer.LocalMultiplayerOutputBoundary;
import use_case.quiz_generation.QuizGenerationDataAccessInterface;
import use_case.quiz_generation.QuizGenerationInputData;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;
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
                TriviaQuiz correctOutputTriviaQuiz;
                try {
                    List<TriviaQuestion> listOfTriviaQuestions = new ArrayList<>();
                    for (int i = 1; i <= 5; i++) {
                        List<String> incorrectAnswers = new ArrayList<>();
                        for (int j = 2; j < 5; j++) {
                            incorrectAnswers.add("Question0");
                        }
                        listOfTriviaQuestions.add(new TriviaQuestion("Question" + i, "Answer" + i, incorrectAnswers));
                    }
                    correctOutputTriviaQuiz = new TriviaQuiz(listOfTriviaQuestions);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                for (int i = 0; i < triviaQuiz.getQuestions().size(); i++) {
                    assertEquals(triviaQuiz.getQuestions().get(i).getQuestionText(),
                            correctOutputTriviaQuiz.getQuestions().get(i).getQuestionText());
                    assertEquals(triviaQuiz.getQuestions().get(i).getCorrectAnswer(),
                            correctOutputTriviaQuiz.getQuestions().get(i).getCorrectAnswer());
                    assertEquals(triviaQuiz.getQuestions().get(i).getAnswerOptions(),
                            correctOutputTriviaQuiz.getQuestions().get(i).getAnswerOptions());
                    assertEquals(triviaQuiz.getQuestions().get(i).getIncorrectAnswers().get(0),
                            correctOutputTriviaQuiz.getQuestions().get(i).getIncorrectAnswers().get(0));
                    assertEquals(triviaQuiz.getQuestions().get(i).getIncorrectAnswers().get(1),
                            correctOutputTriviaQuiz.getQuestions().get(i).getIncorrectAnswers().get(1));
                    assertEquals(triviaQuiz.getQuestions().get(i).getIncorrectAnswers().get(2),
                            correctOutputTriviaQuiz.getQuestions().get(i).getIncorrectAnswers().get(2));
                }
            }

            @Override
            public void switchToLocalMultiplayerView() {
                fail("Not expected.");

            }

            @Override
            public void switchToMainMenuView() {
                fail("Not expected.");

            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Not expected.");

            }

            @Override
            public void nextQuestion(LocalMultiplayerPlaythroughState state) {
                fail("Not expected.");
            }

        };

        LocalMultiplayerInteractor localMultiplayerInteractor = new LocalMultiplayerInteractor(localMultiplayerOB, generatedQuizDAO);
        localMultiplayerInteractor.execute(new QuizGenerationInputData(5, "Animals", "Easy"));
    }

    @Test
    public void testExecuteGenerateQuizFailure() {
        QuizGenerationDataAccessInterface generatedQuizDAO = new QuizGenerationDataAccessInterface() {
            @Override
            public TriviaQuiz getTrivia(QuizGenerationInputData quizData) throws Exception {
                throw new Exception("Fail view preparing");
            }
        };

        LocalMultiplayerOutputBoundary localMultiplayerOB = new LocalMultiplayerOutputBoundary() {
            @Override
            public void prepareQuiz(TriviaQuiz triviaQuiz) {
                fail("Not expected.");
            }

            @Override
            public void switchToLocalMultiplayerView() {
                fail("Not expected.");

            }

            @Override
            public void switchToMainMenuView() {
                fail("Not expected.");

            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Fail view preparing", errorMessage);
            }

            @Override
            public void nextQuestion(LocalMultiplayerPlaythroughState state) {
                fail("Not expected.");
            }
        };

        LocalMultiplayerInteractor localMultiplayerInteractor = new LocalMultiplayerInteractor(localMultiplayerOB, generatedQuizDAO);
        localMultiplayerInteractor.execute(new QuizGenerationInputData(5, "Animals", "Easy"));
    }

    @Test
    public void testSwitchToLocalMultiplayerView() {
        QuizGenerationDataAccessInterface generatedQuizDAO = new QuizGenerationDataAccessInterface() {
            @Override
            public TriviaQuiz getTrivia(QuizGenerationInputData quizData) throws Exception {
                return null;
            }
        };

        LocalMultiplayerOutputBoundary localMultiplayerOB = new LocalMultiplayerOutputBoundary() {
            @Override
            public void prepareQuiz(TriviaQuiz triviaQuiz) {
                fail("Not expected.");
            }

            @Override
            public void switchToLocalMultiplayerView() {
                // This runs
            }

            @Override
            public void switchToMainMenuView() {
                fail("Not expected.");

            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Not expected.");
            }

            @Override
            public void nextQuestion(LocalMultiplayerPlaythroughState state) {
                fail("Not expected.");
            }
        };

        LocalMultiplayerInteractor localMultiplayerInteractor = new LocalMultiplayerInteractor(localMultiplayerOB, generatedQuizDAO);
        localMultiplayerInteractor.switchToLocalMultiplayerView();
    }

    @Test
    public void testSwitchToMainMenuView() {
        QuizGenerationDataAccessInterface generatedQuizDAO = new QuizGenerationDataAccessInterface() {
            @Override
            public TriviaQuiz getTrivia(QuizGenerationInputData quizData) throws Exception {
                return null;
            }
        };

        LocalMultiplayerOutputBoundary localMultiplayerOB = new LocalMultiplayerOutputBoundary() {
            @Override
            public void prepareQuiz(TriviaQuiz triviaQuiz) {
                fail("Not expected.");
            }

            @Override
            public void switchToLocalMultiplayerView() {
                fail("Not expected.");
            }

            @Override
            public void switchToMainMenuView() {
                // this runs
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Not expected.");
            }

            @Override
            public void nextQuestion(LocalMultiplayerPlaythroughState state) {
                fail("Not expected.");
            }
        };

        LocalMultiplayerInteractor localMultiplayerInteractor = new LocalMultiplayerInteractor(localMultiplayerOB, generatedQuizDAO);
        localMultiplayerInteractor.switchToMainMenuView();
    }

    @Test
    public void testNextQuestion() {
        QuizGenerationDataAccessInterface generatedQuizDAO = new QuizGenerationDataAccessInterface() {
            @Override
            public TriviaQuiz getTrivia(QuizGenerationInputData quizData) throws Exception {
                return null;
            }
        };

        LocalMultiplayerOutputBoundary localMultiplayerOB = new LocalMultiplayerOutputBoundary() {
            @Override
            public void prepareQuiz(TriviaQuiz triviaQuiz) {
                fail("Not expected.");
            }

            @Override
            public void switchToLocalMultiplayerView() {
                fail("Not expected.");
            }

            @Override
            public void switchToMainMenuView() {
                fail("Not expected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Not expected.");
            }

            @Override
            public void nextQuestion(LocalMultiplayerPlaythroughState state) {
                // this runs
            }
        };

        LocalMultiplayerInteractor localMultiplayerInteractor = new LocalMultiplayerInteractor(localMultiplayerOB, generatedQuizDAO);
        localMultiplayerInteractor.nextQuestion(new LocalMultiplayerPlaythroughState());
    }
}
