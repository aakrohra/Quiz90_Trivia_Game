package entity;

import java.util.List;

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
     * Creates a new item for the database.
     */
    void create();

    /**
     * Returns an item given a unique key.
     * @param key of item
     * @return item
     */
    T getByKey(String key);

    /**
     * Returns a list of items that contain the given string in the title.
     * @param title of item
     * @return list of items
     */
    List<T> getByTitle(String title);

    /**
     * Returns all items in database.
     * @return list of items
     */
    List<T> getAll();

    /**
     * Updates a given item given the unique key and the updated item.
     * @param key of item to be updated
     * @param updatedItem to update with
     */
    void update(String key, T updatedItem);

    /**
     * Deletes a given item given the unique key.
     * @param key of item to be deleted
     */
    void delete(String key);

    /**
     * Returns number of items in database.
     * @return integer of items
     */
    int getNumberOfItems();
}
