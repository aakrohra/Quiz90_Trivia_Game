package use_case.access_database;

public interface AccessDatabaseOutputBoundary {
    void prepareSuccessView(AccessDatabaseOutputData outputData);

    void prepareFailView(String errorMessage);
}
