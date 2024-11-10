package data_access;

import entity.TriviaResponse;
import entity.TriviaQuestion;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBTriviaDataAccessObject {

    private static final int SUCCESS_CODE = 0;
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "response_code";
    private static final String MESSAGE = "message";

    public DBTriviaDataAccessObject() {
        // Constructor doesn't need any initialization for this case
    }

    public TriviaResponse getTrivia(int amount, String difficulty) throws Exception {
        // Construct URL with the given parameters
        final String urlString =
                String.format("https://opentdb.com/api.php?amount=%d&difficulty=%s&type=multiple", amount, difficulty);

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(urlString)
                .addHeader("Content-Type", CONTENT_TYPE_JSON)
                .build();

        try {
            // Make the HTTP request to the API
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            // Check if the response code indicates success
            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                // Parse the trivia questions from the response body
                final List<TriviaQuestion> triviaQuestions = parseTriviaQuestions(responseBody.getJSONArray("results"));
                return new TriviaResponse(triviaQuestions);
            }
            else {
                // If response code is not success, throw an exception
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException e) {
            throw new RuntimeException("Error fetching or parsing trivia data", e);
        }
    }

    private List<TriviaQuestion> parseTriviaQuestions(JSONArray results) throws JSONException {
        // This method parses the trivia questions into a list of TriviaQuestion objects
        final List<TriviaQuestion> triviaQuestions = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            final JSONObject questionObj = results.getJSONObject(i);

            // Extract the relevant data for each trivia question
            final String questionText = questionObj.getString("question");
            final String correctAnswer = questionObj.getString("correct_answer");

            // Extract incorrect answers (which is an array)
            final List<String> incorrectAnswers = parseIncorrectAnswers(questionObj.getJSONArray("incorrect_answers"));

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
            incorrectAnswers.add(incorrectAnswersArray.getString(i));
        }
        return incorrectAnswers;
    }

}

