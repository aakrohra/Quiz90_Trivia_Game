package entity;

import java.util.ArrayList;
import java.util.List;

import data_access.DBCustomQuizDataAccessObject;

/**
 * An implementation of the Database interface.
 * Entity that represents a quiz database attached to a Player user
 */
public class PlayerQuizDatabase implements Database {
    private User user;
    private List<Quiz> quizList = new ArrayList<>();

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
    public void create() {

    }

    @Override
    public Object getByKey(String key) {
        try {
            final Quiz quiz = quizList.get
        }
    }

    private int getOrd(char chr) {
        if (97 <= chr && chr <= 122) {
            return chr - 97;
        }
        else if (65 <= chr && chr <= 90) {
            return chr - 65;
        }
        else if (48 <= chr && chr <= 57) {
            return chr - 22;
        }
        else {
            return 57-22;
        }
    }

    private Quiz binarySearch(String key, int keyIdx, int lowIdx, int highIdx) {
    }

    @Override
    public List getByTitle(String title) {
        return List.of();
    }

    @Override
    public List getAll() {
        return List.of();
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
