package interface_adapter.summary;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Summary View.
 */
public class SummaryViewModel extends ViewModel<SummaryState> {

    public SummaryViewModel() {
        super("summary");
        setState(new SummaryState());
    }
}
