package Datatypes.DataStructures;

import java.util.HashMap;
import java.util.Map.Entry;

public class Database<T> {
    protected HashMap<String, T> Database;

    public Database() {
        Database = new HashMap<>();
    }

    public boolean addData(String id, T new_data) {
        Database.put(id, new_data);
        return true;
    }

    public boolean check(String id) {
        if (Database.get(id) == null)
            return false;
        return true;
    }

    public T getData(String id) {
        if (!check(id))
            return null;
        return Database.get(id);
    }

    public void printDatabase() {
        for (Entry<String, T> entry : Database.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public boolean removeData(String id) {
        if (!check(id))
            return false;
        Database.remove(id);
        return true;
    }

    public HashMap<String, T> getDB() {
        return Database;
    }

}
