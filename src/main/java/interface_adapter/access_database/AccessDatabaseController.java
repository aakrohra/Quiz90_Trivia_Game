package interface_adapter.access_database;

import entity.User;
import use_case.access_database.AccessDatabaseInputBoundary;
import use_case.access_database.AccessDatabaseInputData;

/**
 * The controller for handling the interaction between the Access Database use case
 * and the user interface.
 */
public class AccessDatabaseController {

    private final AccessDatabaseInputBoundary accessDatabaseInteractor;

    public AccessDatabaseController(AccessDatabaseInputBoundary accessDatabaseInteractor) {
        this.accessDatabaseInteractor = accessDatabaseInteractor;
    }

    /**
     * Executes the use case for accessing the database with the provided user data.
     * @param user the user whose data is being accessed in the database
     */
    public void execute(User user) {
        final AccessDatabaseInputData accessDatabaseInputData = new AccessDatabaseInputData(user);
        accessDatabaseInteractor.execute(accessDatabaseInputData);
    }
}
