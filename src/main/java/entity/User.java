package entity;

/**
 * The representation of a user in our program.
 */
public interface User {

    /**
     * Returns the username of the user.
     * @return the username of the user.
     */
    String getName();

    /**
     * Returns the password of the user.
     * @return the password of the user.
     */
    String getPassword();

    /**
     * Returns the current score of the user.
     * @return the current score of the user.
     */
    int getCurrentScore();

    /**
     * Returns the high score of the user.
     * @return the high score of the user.
     */
    int getHighScore();

}
