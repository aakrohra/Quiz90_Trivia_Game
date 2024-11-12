package use_case.access_quiz;

/**
 * The Input Data for the Access Quiz Use Case.
 */
public class AccessQuizInputData {

    private final String key;

    public AccessQuizInputData(String key) {
        this.key = key;
    }

    String getKey() {
        return key;
    }

}
