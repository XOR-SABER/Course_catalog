import java.util.HashMap;
import java.util.Map.Entry;

public class Database<T> {
    protected HashMap<String, T> Database;

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

    public void print_database() {
        for (Entry<String, T> entry : Database.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
