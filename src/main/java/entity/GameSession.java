package entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Representation of the current game session of the program.
 */
public class GameSession {
    private final User lastPlayed;
    private final List<User> users;
    private final Quiz currentQuiz;
    private final Question currentQuestion;
    private final Queue turnBasedQueue;

    public GameSession(List<User> users, Quiz currentQuiz, Question currentQuestion) {
        this.users = users;
        this.lastPlayed = this.users.get(0);
        this.currentQuiz = currentQuiz;
        this.currentQuestion = currentQuestion;
        this.turnBasedQueue = new LinkedList<>();
    }

    public User getLastPlayed() {
        return this.lastPlayed;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public Quiz getCurrentQuiz() {
        return this.currentQuiz;
    }

    public Question getCurrentQuestion() {
        return this.currentQuestion;
    }

    public Queue getTurnBasedQueue() {
        return this.turnBasedQueue;
    }
}
