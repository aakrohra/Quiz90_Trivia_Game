package entity;

import java.util.ArrayList;

/**
 * Factory for creating RegisteredPlayer objects.
 */
public class RegisteredPlayerFactory implements PlayerFactory {
    @Override
    public User create(String name, String password) {
        return new CommonUser(name, password, 0, 0, new ArrayList<>());
    }
}
