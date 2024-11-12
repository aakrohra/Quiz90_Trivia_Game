package entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Creates a new PlayerCreatedQuiz object.
 */
public class PlayerCreatedQuizFactory implements QuizFactory {

    private final PlayerCreatedQuestionFactory questionFactory = new PlayerCreatedQuestionFactory();

    @Override
    public PlayerCreatedQuiz create(JSONObject quizData) {

        // stores all questions into a JSON Array
        final JSONArray questionsJSONArray = quizData.getJSONArray("questions");

        // instantiate a list of player created questions to be added to
        final List<PlayerCreatedQuestion> questionsList = new ArrayList<>();

        // loop over each question in the JSON array, turn it into a Player Created Question, then add it to the list
        for (int i = 0; i < questionsJSONArray.length(); i++) {
            questionsList.add(questionFactory.create(questionsJSONArray.getJSONObject(i)));
        }

        // return quiz object with title from JSON input and list of questions generated
        return new PlayerCreatedQuiz(quizData.getString("title"), questionsList);

    }
}
