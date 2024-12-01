package use_case.create_question;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import entity.PlayerCreatedQuestionFactory;

/**
 * The interactor for the question creation use case.
 */
public class QuestionCreationInteractor implements QuestionCreationInputBoundary {

    private final QuestionCreationOutputBoundary questionCreationPresenter;
    private final PlayerCreatedQuestionFactory questionFactory = new PlayerCreatedQuestionFactory();

    public QuestionCreationInteractor(QuestionCreationOutputBoundary questionCreationOutputBoundary) {
        this.questionCreationPresenter = questionCreationOutputBoundary;
    }

    @Override
    public void executeCreateQuestion(QuestionCreationInputData questionCreationInputData) {
        final JSONObject questionJSONObject = new JSONObject();
        questionJSONObject.put("questionText", questionCreationInputData.getQuestionText());

        final List<String> options = new ArrayList<>();
        options.add(questionCreationInputData.getWrong1());
        options.add(questionCreationInputData.getWrong2());
        options.add(questionCreationInputData.getWrong3());
        questionJSONObject.put("options", options);

        questionJSONObject.put("correct", questionCreationInputData.getAnswer());

        final QuestionCreationOutputData outputData = new QuestionCreationOutputData(
                questionFactory.create(questionJSONObject));
        questionCreationPresenter.prepareSuccessView(outputData);
    }
}
