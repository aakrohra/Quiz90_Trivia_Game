
package entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * An implementation of the Database interface.
 * Entity that represents a quiz database attached to a Player user
 */
public class PlayerQuizDatabase implements Database {
    private User user;
    private Map<String, PlayerCreatedQuiz> quizMap = new HashMap<>();
    private Map<String, String> titleToKeyMap = new HashMap<>();

    public PlayerQuizDatabase(User user, Map<String, PlayerCreatedQuiz> quizMap) {
        this.user = user;
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


    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Quiz getByKey(String key) {
        final Quiz quiz = quizMap.get(key);
        return quiz;
    }

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

    @Override
    public Map<String, PlayerCreatedQuiz> getAll() {
        return quizMap;
    }

    @Override
    public int getNumberOfItems() {
        return quizMap.size();
    }
}

