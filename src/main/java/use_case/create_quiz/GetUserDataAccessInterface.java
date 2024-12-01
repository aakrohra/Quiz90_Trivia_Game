package use_case.create_quiz;

import entity.User;

/**
 * The interface for getting required user data from API.
 */
public interface GetUserDataAccessInterface {

    /**
     * Gets the user object for the given username.
     * @param username the username
     * @return the User object
     */
    User get(String username);
}
