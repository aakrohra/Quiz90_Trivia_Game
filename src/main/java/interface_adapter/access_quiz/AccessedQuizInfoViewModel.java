package interface_adapter.access_quiz;

import interface_adapter.ViewModel;

/**
 * The View Model for the Accessed Quiz Info view.
 */
public class AccessedQuizInfoViewModel extends ViewModel<AccessedQuizInfoState> {

    public AccessedQuizInfoViewModel() {
        super("Accessed quiz");
        setState(new AccessedQuizInfoState());
    }
}
