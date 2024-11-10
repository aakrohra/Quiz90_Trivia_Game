package entity;

/**
 * An implementation of the Player interface.
 * Entity that represents a Guest user.
 */
public class GuestUser implements User {
    private final String username;
    // TODO Change this line depending on implementation of Guest user?
    //      Idk how it works with login API -> Albert look at probably
    // private final String password = null;
    private final int currentScore;

    public GuestUser() {
        this.username = "Guest";
        this.currentScore = 0;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getPassword() {
        return "none";
    }

    @Override
    public int getCurrentScore() {
        return this.currentScore;
    }

    @Override
    public int getHighScore() {
        System.out.print("Guest players do not have a high score.");
        return 0;

    }

}
