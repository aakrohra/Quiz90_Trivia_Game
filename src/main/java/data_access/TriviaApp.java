package data_access;

import entity.TriviaQuestion;
import entity.TriviaResponse;

/**
 * The main entry point of the Trivia application that retrieves trivia questions from the API
 * and prints them along with their correct and incorrect answers.
 */
public class TriviaApp {
    public static void main(String[] args) throws Exception {
        final DBTriviaDataAccessObject triviaDao = new DBTriviaDataAccessObject();

        // Get trivia questions from the API
        final TriviaResponse trivia = triviaDao.getTrivia(3, "medium");

        // Print the trivia questions and answers
        for (TriviaQuestion question : trivia.getQuestions()) {
            System.out.println("Question: " + question.getQuestion());
            System.out.println("Correct Answer: " + question.getCorrectAnswer());
            System.out.println("Incorrect Answers: " + String.join(", ", question.getIncorrectAnswers()));
            System.out.println();
        }
    }
}
