package Datatypes.DataStructures;

import java.util.HashMap;
import java.util.Map.Entry;

// This is a custom database data structure
// It uses a hashmap to get O(1) Search, Insert, and Deletion
public class Database<T> {
    // Protected because we need access the hashmap sometimes..
    protected HashMap<String, T> Database;

    // Default constructor
    public Database() {
        Database = new HashMap<>();
    }

    // Adds a object/data to the database, using a {Key, object}
    public boolean addData(String id, T new_data) {
        Database.put(id, new_data);
        return true;
    }

    // Checks if an object/data with the specified key exists in the database
    public boolean check(String id) {
        if (Database.get(id) == null)
            return false;
        return true;
    }

    // Returns the object/data with the specified key from the database
    // Returns null if the key does not exist in the database
    public T getData(String id) {
        if (!check(id))
            return null;
        return Database.get(id);
    }

    // Prints all objects/data in the database
    public void printDatabase() {
        for (Entry<String, T> entry : Database.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    // Removes the object/data with the specified key from the database
    // Returns false if the key does not exist in the database
    public boolean removeData(String id) {
        if (!check(id))
            return false;
        Database.remove(id);
        return true;
    }

    // Returns the entire database as a hashmap
    public HashMap<String, T> getDB() {
        return Database;
    }
}