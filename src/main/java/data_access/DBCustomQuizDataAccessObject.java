package data_access;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.Constants;
import entity.*;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import use_case.access_database.AccessDatabaseUserDataAccessInterface;
import use_case.access_quiz.AccessQuizDataAccessInterface;
import use_case.create_quiz.CreateQuizDataAccessInterface;

/**
 * The DAO for accessing custom quiz data.
 */
public class DBCustomQuizDataAccessObject implements AccessQuizDataAccessInterface,
        AccessDatabaseUserDataAccessInterface,
        CreateQuizDataAccessInterface {

    public static final String CORRECT = "correct";
    public static final String OPTIONS = "options";
    public static final String QUESTION_TEXT = "questionText";
    public static final String TITLE = "title";
    public static final String INFO1 = "info";
    public static final String USER = "user";
    public static final String QUESTIONS = "questions";
    public static final String PUT = "PUT";

    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
    private static final String INFO = "info";
    private static final int SUCCESS_CODE = 200;
    private static final String API_INFO_CALL = "http://vm003.teach.cs.toronto.edu:20112/user?username=%s";
    private static final String API_USER_EXISTS_CALL =
            "http://vm003.teach.cs.toronto.edu:20112/checkIfUserExists?username=%s";

    /**
     * Get the username of the user who created the quiz with the given key.
     * @param key the given key
     * @return the username of the creator
     */
    public String getKeyUser(String key) {
        final StringBuilder keyUser = new StringBuilder();
        for (int i = 0; i < key.length(); i++) {
            if (i >= Constants.RANDOMKEYSIZE) {
                keyUser.append(key.charAt(i));
            }
        }
        return keyUser.toString();
    }

    @Override
    public boolean existsByKey(String key) {
        final String keyUser = this.getKeyUser(key);

        boolean keyExists = false;

        // checks if user keyUser exists
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format(API_USER_EXISTS_CALL, keyUser))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();

        // checks if data exists within key "key" in "info" part of database for keyUser
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                final Request requestUser = new Request.Builder()
                        .url(String.format(API_INFO_CALL, keyUser))
                        .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                        .build();

                final Response responseUser = client.newCall(requestUser).execute();
                final JSONObject responseBodyUser = new JSONObject(responseUser.body().string());

                final JSONObject userJSONObject = responseBodyUser.getJSONObject(USER);
                final JSONObject data = userJSONObject.getJSONObject(INFO);
                keyExists = data.has(key);
            }
        }
        catch (final IOException | JSONException ex) {
            keyExists = false;
        }
        return keyExists;
    }

    @Override
    public boolean existsByName(String username) {

        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final Request request = new Request.Builder()
                .url(String.format(API_USER_EXISTS_CALL,
                        username))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            return responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE;
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public JSONObject getQuizFromKey(String key) throws RuntimeException {
        final String keyUser = this.getKeyUser(key);

        // API call for keyUser's info
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format(API_INFO_CALL, keyUser))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();

        // checks if data exists within key "key" in "info" part of database for keyUser
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            final JSONObject userJSONObject = responseBody.getJSONObject(USER);
            final JSONObject data = userJSONObject.getJSONObject(INFO);
            return data.getJSONObject(key);
        }
        catch (final IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns all quiz objects associated with the given user mapped to their key.
     * @param username the given username
     * @return map with key, quiz pairing
     * @throws RuntimeException if there is an issue
     */
    @Override
    public Map<String, RetrievedQuiz> getAllUserQuizzes(String username) throws RuntimeException {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format(API_INFO_CALL,
                        username))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            final JSONObject userJSONObject = responseBody.getJSONObject(USER);
            final JSONObject data = userJSONObject.getJSONObject(INFO);
            final Map<String, RetrievedQuiz> userQuizzes = new HashMap<>();
            final RetrievedQuizFactory retrievedQuizFactory = new RetrievedQuizFactory();
            final Iterator<String> keys = data.keys();
            while (keys.hasNext()) {
                final String key = keys.next();
                final JSONObject quizData = data.getJSONObject(key);
                final RetrievedQuiz quiz = retrievedQuizFactory.create(quizData, key);
                userQuizzes.put(key, quiz);
            }
            return userQuizzes;
        }
        catch (final IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Gets user info including username, password, and quizzes and returns as a JSONObject.
     * @param user the given user
     * @return username, password, and quizzes as JSONObject in the following format:
     *     {
     *       "username": "user",
     *       "password": "pass",
     *       "info": {
     *         "key1": {
     *           "title": "title1",
     *           "questions": [
     *             {
     *               "questionText": "qqqqq",
     *               "options": ["o1", "o2", "o3", "o4"],
     *               "correct": "o2"
     *             },
     *             ...
     *           ]
     *         },
     *         ...
     *       }
     *     }
     * @throws RuntimeException if there is an issue
     */
    @Override
    public JSONObject getUserInfo(User user) {
        final String username = user.getName();
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format(API_INFO_CALL, username))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                final JSONObject userJSONObject = responseBody.getJSONObject(USER);
                final JSONObject data = userJSONObject.getJSONObject(INFO1);
                final JSONObject userInfo = new JSONObject();
                userInfo.put(USERNAME, username);
                userInfo.put(PASSWORD, user.getPassword());
                userInfo.put(INFO, data);
                return userInfo;
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public JSONObject quizObjectToJSONObject(CreatedQuiz quiz) {
        final JSONObject quizJSONObject = new JSONObject();
        quizJSONObject.put(TITLE, quiz.getTitle());
        final List<PlayerCreatedQuestion> questions = (List<PlayerCreatedQuestion>) quiz.getQuestions();
        final JSONArray questionsArray = new JSONArray();
        for (final PlayerCreatedQuestion question : questions) {
            final JSONObject questionObject = new JSONObject();
            questionObject.put(QUESTION_TEXT, question.getQuestionText());
            final JSONArray optionsArray = new JSONArray(question.getAnswerOptionsList());
            questionObject.put(OPTIONS, optionsArray);
            questionObject.put(CORRECT, question.getCorrectAnswer());
            questionsArray.put(questionObject);
        }
        quizJSONObject.put(QUESTIONS, questionsArray);
        return quizJSONObject;
    }

    /**
     * Helper function to generate a random key of RANDOMKEYSIZE in alphanumeric characters.
     * @return unique string
     */
    private String randomKey() {
        final int keyLength = Constants.RANDOMKEYSIZE;
        final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final StringBuilder key = new StringBuilder();
        final Random random = new Random();
        for (int i = 0; i < keyLength; i++) {
            final int index = random.nextInt(chars.length());
            key.append(chars.charAt(index));
        }
        return key.toString();
    }

    /**
     * Adds quiz to the GradeAPI database to the given user and returns key of new quiz.
     * @param quizObject the given quiz
     * @param user the given user
     * @return key of quiz added to the database
     */
    @Override
    public String addQuiz(JSONObject quizObject, User user) {
        final JSONObject currentUserInfo = getUserInfo(user);
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        String key = "";
        boolean exists = true;
        while (exists) {
            key = randomKey() + user.getName();
            if (!existsByKey(key)) {
                exists = false;
            }
        }
        currentUserInfo.getJSONObject(INFO).put(key, quizObject);
        final RequestBody body = RequestBody.create(currentUserInfo.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/modifyUserInfo")
                .method(PUT, body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                return key;
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
}
