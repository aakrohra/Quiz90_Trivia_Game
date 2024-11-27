
package entity;

import java.util.*;

/**
 * An implementation of the Database interface.
 * Entity that represents a quiz database attached to a Player user
 */
public class PlayerQuizDatabase implements Database {
    private Map<String, PlayerCreatedQuiz> quizMap;
    private Map<String, String> titleToKeyMap = new HashMap<>();

    public PlayerQuizDatabase(Map<String, PlayerCreatedQuiz> quizMap) {
        this.quizMap = quizMap;
        this.titleToKeyMap = this.titleToKeyMapBuilder();
    }

    private Map<String, String> titleToKeyMapBuilder() {
        final Map<String, String> titleToKeyMap = new HashMap<>();
        final Iterator<String> keys = quizMap.keySet().iterator();
        for (PlayerCreatedQuiz quiz : quizMap.values()) {
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
    public Quiz getByKey(String key) {
        final Quiz quiz = quizMap.get(key);
        return quiz;
    }

    @Override
    public Map<String, PlayerCreatedQuiz> getByTitle(String title) {
        final Map<String, PlayerCreatedQuiz> quizzes = new HashMap<>();
        final String str = title.toLowerCase().replaceAll("\\s+", "");
        for (PlayerCreatedQuiz quiz : quizMap.values()) {
            final String quizTitle = quiz.getTitle().toLowerCase().replaceAll("\\s+", "");
            if (quizTitle.contains(str)) {
                final String key = titleToKeyMap.get(quizTitle);
                quizzes.put(key, quiz);
            }
        }
        return quizzes;
    }

    /**
     * Returns all quiz objects for a given user keyed to quiz key.
     * @return map of quiz objects
     */
    @Override
    public Map<String, PlayerCreatedQuiz> getAll() {
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

