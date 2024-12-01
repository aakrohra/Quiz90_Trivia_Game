package interface_adapter.access_quiz;

import interface_adapter.ViewModel;

/**
 * The View Model for the Accessed Quiz Info view.
 */
public class AccessedQuizInfoViewModel extends ViewModel<AccessedQuizInfoState> {

    public static final String TITLE_LABEL = "Custom Quiz!";

    public AccessedQuizInfoViewModel() {
        super("accessed quiz info");
        setState(new AccessedQuizInfoState());
    }
}
