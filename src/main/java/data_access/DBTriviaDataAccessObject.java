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
import entity.TriviaResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A Data Access Object (DAO) that handles retrieving trivia questions from an external API.
 */
public class DBTriviaDataAccessObject {

    private static final int SUCCESS_CODE = 0;
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "response_code";
    private static final Map<String, String> HTMLENTITIES = new HashMap<>();

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

    /**
     * Retrieves a list of trivia questions from the external API.
     *
     * @param amount The number of trivia questions to retrieve.
     * @param difficulty The difficulty level of the trivia questions.
     * @param categoryID The ID of the category of the trivia questions.
     * @return A TriviaResponse containing the list of TriviaQuestion objects.
     * @throws Exception If an error occurs during the API request or parsing the response.
     */

    public TriviaResponse getTrivia(int amount, int categoryID, String difficulty) throws Exception {
        // Construct URL with the given parameters
        final String urlString =
                String.format("https://opentdb.com/api.php?amount=%d&category=%d&difficulty=%s&type=multiple",
                        amount, categoryID, difficulty);

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
                return new TriviaResponse(triviaQuestions);
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

