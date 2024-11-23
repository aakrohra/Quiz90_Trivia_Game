package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.TriviaQuestion;
import entity.TriviaQuiz;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import use_case.quiz_generation.QuizGenerationInputData;

/**
 * A Data Access Object (DAO) that handles retrieving trivia questions from an external API.
 */
public class DBTriviaDataAccessObject {

    private static final int SUCCESS_CODE = 0;
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "response_code";
    private static final Map<String, String> HTMLENTITIES = new HashMap<>();
    private static final Map<String, Integer> CATEGORY_MAPPING = new HashMap<>();

    static {
        HTMLENTITIES.put("&lt;", "<");
        HTMLENTITIES.put("&gt;", ">");
        HTMLENTITIES.put("&quot;", "\"");
        HTMLENTITIES.put("&ldquo;", "\"");
        HTMLENTITIES.put("&rdquo;", "\"");
        HTMLENTITIES.put("&apos;", "'");
        HTMLENTITIES.put("&#039;", "'");
        HTMLENTITIES.put("&rsquo;", "'");
        HTMLENTITIES.put("&lsquo;", "'");
        HTMLENTITIES.put("&amp;", "&");
        // Add more entities if there are any more that you find
        // Could also find a Java Library that does this automatically
    }

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
        CATEGORY_MAPPING.put("Entertainment: Japanese Anime & Manga", 31);
        CATEGORY_MAPPING.put("Entertainment: Animations & Cartoons", 32);
    }

    /**
     * Returns the id of the category to use for retrieving from the API call.
     * @param categoryName String of the category
     * @return Returns the id of its category as a string
     */
    private static int getCategoryId(String categoryName) {
        return CATEGORY_MAPPING.get(categoryName);
    }

    /**
     * Retrieves a list of trivia questions from the external API.
     *
     * @param quizData Contains the category, difficulty, and number of questions which are required to make
     *                 an API call.
     * @return A TriviaResponse containing the list of TriviaQuestion objects.
     * @throws Exception If an error occurs during the API request or parsing the response.
     */

    public TriviaQuiz getTrivia(QuizGenerationInputData quizData) throws Exception {

        // Construct URL with the given parameters
        final String urlString =
                String.format("https://opentdb.com/api.php?amount=%d&category=%d&difficulty=%s&type=multiple",
                        quizData.getNumQuestions(), getCategoryId(quizData.getCategory()),
                        quizData.getDifficulty());

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(urlString)
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            System.out.println(urlString);

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {

                final List<TriviaQuestion> triviaQuestions =
                        parseTriviaQuestions(responseBody.getJSONArray("results"));

                return new TriviaQuiz(triviaQuestions);
            }
            else {
                throw new RuntimeException();
            }
        }
        catch (IOException | JSONException ex) {
            throw new Exception(ex);
        }
    }

    /**
     * Decodes HTML entities in a string.
     *
     * @param encodedText The encoded text containing HTML entities.
     * @return The decoded string.
     */
    public static String decoder(String encodedText) {
        String decodedText = encodedText;
        for (Map.Entry<String, String> entry : HTMLENTITIES.entrySet()) {
            decodedText = decodedText.replace(entry.getKey(), entry.getValue());
        }
        return decodedText;
    }

    private List<TriviaQuestion> parseTriviaQuestions(JSONArray results) throws JSONException {
        final List<TriviaQuestion> triviaQuestions = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            final JSONObject questionObj = results.getJSONObject(i);

            final String questionText = decoder(questionObj.getString("question"));
            final String correctAnswer = decoder(questionObj.getString("correct_answer"));

            final List<String> incorrectAnswers =
                    parseIncorrectAnswers(questionObj.getJSONArray("incorrect_answers"));

            // Create a TriviaQuestion object and add it to the list
            final TriviaQuestion triviaQuestion = new TriviaQuestion(questionText, correctAnswer, incorrectAnswers);
            triviaQuestions.add(triviaQuestion);
        }
        return triviaQuestions;
    }

    private List<String> parseIncorrectAnswers(JSONArray incorrectAnswersArray) throws JSONException {
        // Converts the incorrect answers JSON array into a List of Strings
        final List<String> incorrectAnswers = new ArrayList<>();
        for (int i = 0; i < incorrectAnswersArray.length(); i++) {
            incorrectAnswers.add(decoder(incorrectAnswersArray.getString(i)));
        }
        return incorrectAnswers;
    }

}

