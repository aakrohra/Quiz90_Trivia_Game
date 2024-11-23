package data_access;

import java.util.HashMap;
import java.util.Map;

import entity.TriviaQuestion;
import entity.TriviaResponse;

/**
 * A utility class for interacting with trivia data from an external API.
 * It maps categories to their corresponding IDs and provides a method
 * for fetching trivia questions based on specified parameters.
 */

@SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:SuppressWarnings"})
public class TriviaApp {
    private static final Map<String, Integer> CATEGORY_MAPPING = new HashMap<>();

    static {
        CATEGORY_MAPPING.put("General Knowledge", 9);
        CATEGORY_MAPPING.put("Entertainment: Books", 10);
        CATEGORY_MAPPING.put("Entertainment: Film", 11);
        CATEGORY_MAPPING.put("Entertainment: Music", 12);
        CATEGORY_MAPPING.put("Entertainment: Musicals & Theatres", 13);
        CATEGORY_MAPPING.put("Entertainment: Television", 14);
        CATEGORY_MAPPING.put("Entertainment: Video Games", 15);
        CATEGORY_MAPPING.put("Entertainment: Board Games", 16);
        CATEGORY_MAPPING.put("Science & Nature", 17);
        CATEGORY_MAPPING.put("Science: Computers", 18);
        CATEGORY_MAPPING.put("Science: Mathematics", 19);
        CATEGORY_MAPPING.put("Mythology", 20);
        CATEGORY_MAPPING.put("Sports", 21);
        CATEGORY_MAPPING.put("Geography", 22);
        CATEGORY_MAPPING.put("History", 23);
        CATEGORY_MAPPING.put("Politics", 24);
        CATEGORY_MAPPING.put("Art", 25);
        CATEGORY_MAPPING.put("Celebrities", 26);
        CATEGORY_MAPPING.put("Animals", 27);
        CATEGORY_MAPPING.put("Vehicles", 28);
        CATEGORY_MAPPING.put("Entertainment: Comics", 29);
        CATEGORY_MAPPING.put("Science: Gadgets", 30);
        CATEGORY_MAPPING.put("Entertainment: Anime & Manga", 31);
        CATEGORY_MAPPING.put("Entertainment: Cartoons", 32);
    }

    /**
     * Returns the id of the category to use for retrieving from the API call.
     * @param categoryName String of the category
     * @return Returns the id of its category as a string
     */
    public static int getCategoryId(String categoryName) {
        return CATEGORY_MAPPING.get(categoryName);
    }

    /**
     * Fetches trivia questions from the API based on the provided parameters.
     *
     * @param numQuestions the number of questions to retrieve
     * @param category the name of the category for the trivia questions
     * @param difficulty the difficulty level of the trivia questions (e.g., "easy", "medium", "hard")
     * @return a {@link TriviaResponse} object containing the trivia questions and answers
     * @throws Exception if there is an error during the API call
     */

    public TriviaResponse fetchTrivia(int numQuestions, String category, String difficulty) throws Exception {
        final DBTriviaDataAccessObject triviaDao = new DBTriviaDataAccessObject();

        // Get trivia questions from the API
        System.out.println("Fetching Trivia");
        System.out.println(numQuestions + " " + category + " " + difficulty);
        final TriviaResponse trivia = triviaDao.getTrivia(numQuestions, getCategoryId(category), difficulty);
        for (TriviaQuestion question : trivia.getQuestions()) {
            System.out.println("Question: " + question.getQuestion());
            System.out.println("Correct Answer: " + question.getCorrectAnswer());
            System.out.println("Incorrect Answers: " + String.join(", ", question.getIncorrectAnswers()));
            System.out.println();
        }
        return trivia;
    }
}
