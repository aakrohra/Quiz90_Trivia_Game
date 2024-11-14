package entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Factory for creating PlayerCreatedQuestion objects.
 */
public class PlayerCreatedQuestionFactory implements QuestionFactory {

    @Override
    public PlayerCreatedQuestion create(JSONObject questionData) {
        final String questionText = questionData.getString("questionText");

        final JSONArray optionsJSONArray = questionData.getJSONArray("options");
        final List<String> options = new ArrayList<>();
        for (int i = 0; i < optionsJSONArray.length(); i++) {
            options.add(optionsJSONArray.getString(i));
        }

        final String correct = questionData.getString("correct");

        return new PlayerCreatedQuestion(questionText, options, correct);
    }
}
