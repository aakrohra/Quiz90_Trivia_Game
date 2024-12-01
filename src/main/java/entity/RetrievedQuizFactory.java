package entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import app.Constants;

/**
 * Creates a new PlayerCreatedQuiz object.
 */
public class RetrievedQuizFactory implements QuizFactory {

    private final PlayerCreatedQuestionFactory questionFactory = new PlayerCreatedQuestionFactory();

    @Override
    public RetrievedQuiz create(JSONObject quizData, String key) {

        // stores all questions into a JSON Array
        final JSONArray questionsJSONArray = quizData.getJSONArray("questions");

        // instantiate a list of player created questions to be added to
        final List<PlayerCreatedQuestion> questionsList = new ArrayList<>();

        // loop over each question in the JSON array, turn it into a Player Created Question, then add it to the list
        for (int i = 0; i < questionsJSONArray.length(); i++) {
            questionsList.add(questionFactory.create(questionsJSONArray.getJSONObject(i)));
        }

        // get author name from end of given key
        final StringBuilder author = new StringBuilder();
        for (int i = Constants.RANDOMKEYSIZE; i < key.length(); i++) {
            author.append(key.charAt(i));
        }

        // return quiz object with title from JSON input, list of questions generated, and author derived from key
        return new RetrievedQuiz(quizData.getString("title"), questionsList, author.toString());

    }
}
