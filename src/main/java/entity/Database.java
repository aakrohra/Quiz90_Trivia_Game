package entity;

import java.util.Map;

/**
 * Representation of a database in our program.
 * @param <T> is the type of item contained in the database
 */
public interface Database<T> {
    /**
     * Returns user of database.
     * @return the user of database
     */
    User getUser();

    /**
     * Returns an item given a unique key.
     * @param key of item
     * @return item
     */
    T getByKey(String key);

    /**
     * Returns a list of items that contain the given string in the title.
     *
     * @param title of item
     * @return list of items
     */
    Map<String, T> getByTitle(String title);

    /**
     * Returns all items in database.
     *
     * @return list of items
     */
    Map<String, T> getAll();

    /**
     * Returns number of items in database.
     * @return integer of items
     */
    int getNumberOfItems();
}
