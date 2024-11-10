package entity;

/**
 * Factory for creating players.
 */
public interface PlayerFactory {
    /**
     * Creates a new Player.
     * @param name the name of the new player
     * @param password the password of the new player
     * @return the new player
     */
    User create(String name, String password);

}

