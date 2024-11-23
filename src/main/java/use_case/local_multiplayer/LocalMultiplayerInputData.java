package use_case.local_multiplayer;

/**
 * Input data for the Local Multiplayer Use Case.
 */
public class LocalMultiplayerInputData {

    private final int numPlayers;
    private final String key;

    public LocalMultiplayerInputData(int numPlayers, String key) {
        this.numPlayers = numPlayers;
        this.key = key;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public String getKey() {
        return key;
    }
}
