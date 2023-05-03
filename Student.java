import java.util.ArrayList;

// Object oritented programming is such a drag some times.. 
public class Student {
    private String _name;
    private String _DOB;
    private String _ID;
    private ArrayList<String> schedule;

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getDOB() {
        return _DOB;
    }

    public void setDOB(String DOB) {
        this._DOB = DOB;
    }

    public String getID() {
        return _ID;
    }

    public void setID(String ID) {
        this._ID = ID;
    }

    public ArrayList<String> getSchedule() {
        return schedule;
    }
    public void add_course(String cid) {
        schedule.add(cid);
    }

    public void setSchedule(ArrayList<String> schedule) {
        this.schedule = schedule;
    }
    @Override
    public String toString() {
        return String.format("%s : DOB: %s\n\tID: %d\n\tSchedule: %s",
                _name, _DOB, _ID, schedule.toString());
    }
}