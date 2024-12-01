
package entity;

import java.util.*;

/**
 * An implementation of the Database interface.
 * Entity that represents a quiz database attached to a Player user
 */
public class PlayerQuizDatabase implements Database {
    private Map<String, RetrievedQuiz> quizMap;
    private Map<String, String> titleToKeyMap = new HashMap<>();

    public PlayerQuizDatabase(Map<String, RetrievedQuiz> quizMap) {
        this.quizMap = quizMap;
        this.titleToKeyMap = this.titleToKeyMapBuilder();
    }

    private Map<String, String> titleToKeyMapBuilder() {
        final Map<String, String> titleToKeyMap = new HashMap<>();
        final Iterator<String> keys = quizMap.keySet().iterator();
        for (RetrievedQuiz quiz : quizMap.values()) {
            titleToKeyMap.put(quiz.getTitle(), keys.next());
        }
        return titleToKeyMap;
    }

    /**
     * Returns a quiz that matches the given key.
     * @param key of quiz
     * @return quiz object
     */
    @Override
    public RetrievedQuiz getByKey(String key) {
        final RetrievedQuiz quiz = quizMap.get(key);
        return quiz;
    }

    @Override
    public Map<String, RetrievedQuiz> getByTitle(String title) {
        final Map<String, RetrievedQuiz> quizzes = new HashMap<>();
        final String str = title.toLowerCase().replaceAll("\\s+", "");
        int i = 0;
        List<String> keys = new ArrayList<>(quizMap.keySet());
        for (RetrievedQuiz quiz : quizMap.values()) {
            final String quizTitle = quiz.getTitle().toLowerCase().replaceAll("\\s+", "");
            if (quizTitle.contains(str)) {
                final String key = keys.get(i);
                quizzes.put(key, quiz);
            }
            i++;
        }
        return quizzes;
    }

    /**
     * Returns all quiz objects for a given user keyed to quiz key.
     * @return map of quiz objects
     */
    @Override
    public Map<String, RetrievedQuiz> getAll() {
        return quizMap;
    }

    @Override
    public Map<String, String> getTitleByKey() {
        return titleToKeyMap;
    }

    /**
     * Returns number of quizzes for a user.
     * @return number of quizzes
     */
    @Override
    public int getNumberOfItems() {
        return quizMap.size();
    }
}

