package interface_adapter.access_database;

import entity.User;
import use_case.access_database.AccessDatabaseInputBoundary;
import use_case.access_database.AccessDatabaseInputData;

public class AccessDatabaseController {

    private final AccessDatabaseInputBoundary accessDatabaseInteractor;

    public AccessDatabaseController(AccessDatabaseInputBoundary accessDatabaseInteractor) {
        this.accessDatabaseInteractor = accessDatabaseInteractor;
    }

    public void execute(User user) {
        final AccessDatabaseInputData accessDatabaseInputData = new AccessDatabaseInputData(user);
        accessDatabaseInteractor.execute(accessDatabaseInputData);
    }

    public void switchToMainMenuView() {
        accessDatabaseInteractor.switchToMainMenuView();
    }
}
