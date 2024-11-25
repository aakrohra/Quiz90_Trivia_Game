package interface_adapter.access_database;

import interface_adapter.ViewModel;

public class AccessedDatabaseInfoViewModel extends ViewModel<AccessedDatabaseInfoState> {
    public AccessedDatabaseInfoViewModel() {
        super("access database");
        setState(new AccessedDatabaseInfoState());
    }
}
