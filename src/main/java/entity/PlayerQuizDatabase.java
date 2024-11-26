
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

    /**
     * Returns a map of quizzes that contains a substring title in their titles.
     * @param title substring to search for
     * @return map of quizzes keyed to their key
     */
    //    private int getOrd(char chr) {
    //        final int result;
    //        if (97 <= chr && chr <= 122) {
    //            result = chr - 97;
    //        }
    //        else if (65 <= chr && chr <= 90) {
    //            result = chr - 65;
    //        }
    //        else if (48 <= chr && chr <= 57) {
    //            result = chr - 22;
    //        }
    //        else {
    //            result = 57 - 22;
    //        }
    //        return result;
    //    }

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

    /**
     * Returns number of quizzes for a user.
     * @return number of quizzes
     */
    @Override
    public int getNumberOfItems() {
        return quizMap.size();
    }
}

