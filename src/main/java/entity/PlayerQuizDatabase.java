
package entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data_access.DBCustomQuizDataAccessObject;

/**
 * An implementation of the Database interface.
 * Entity that represents a quiz database attached to a Player user
 */
public class PlayerQuizDatabase implements Database {
    private User user;
    private Map<String, Quiz> quizList = new HashMap<>();

    public PlayerQuizDatabase(User user) {
        this.user = user;
        final DBCustomQuizDataAccessObject quizDataAccessObject = new DBCustomQuizDataAccessObject();
        quizList = quizDataAccessObject.getAllUserQuizzes(user);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void createItem() {

    }

    @Override
    public Quiz getByKey(String key) {
        final Quiz quiz = quizList.get(key);
        return quiz;
    }

    private int getOrd(char chr) {
        final int result;
        if (97 <= chr && chr <= 122) {
            result = chr - 97;
        }
        else if (65 <= chr && chr <= 90) {
            result = chr - 65;
        }
        else if (48 <= chr && chr <= 57) {
            result = chr - 22;
        }
        else {
            result = 57 - 22;
        }
        return result;
    }

    private Quiz binarySearch(String key, int keyIdx, int lowIdx, int highIdx) {
        return quizList.get(key);
        }

    @Override
    public List getByTitle(String title) {
        return List.of();
    }

    @Override
    public Map<String, Quiz> getAll() {
        return quizList;
    }

    @Override
    public void update(String key, Object updatedItem) {

    }

    @Override
    public void delete(String key) {

    }

    @Override
    public int getNumberOfItems() {
        return quizList.size();
    }
}

