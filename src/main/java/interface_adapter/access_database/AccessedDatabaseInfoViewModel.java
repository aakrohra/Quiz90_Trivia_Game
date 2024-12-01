package interface_adapter.access_database;

import interface_adapter.ViewModel;

/**
 * The view model for the accessed database view.
 */
public class AccessedDatabaseInfoViewModel extends ViewModel<AccessedDatabaseInfoState> {
    public AccessedDatabaseInfoViewModel() {
        super("access database");
        setState(new AccessedDatabaseInfoState());
    }
}
