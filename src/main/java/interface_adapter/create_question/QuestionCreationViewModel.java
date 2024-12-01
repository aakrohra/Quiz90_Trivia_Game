package interface_adapter.create_question;

import interface_adapter.ViewModel;

/**
 * The view model for question creation.
 */
public class QuestionCreationViewModel extends ViewModel<QuestionCreationState> {
    public static final String TITLE_LABEL = "Quiz Creation!";

    public QuestionCreationViewModel() {
        super("question creation");
        setState(new QuestionCreationState());
    }
}
