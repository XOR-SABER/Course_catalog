package Datatypes;

import java.util.ArrayList;

// Object oritented programming is such a drag some times.. 
public class Student {
    private String _name;
    private String _DOB;
    private String _ID;
    private ArrayList<String> schedule;

    public Student() {
        schedule = new ArrayList<>();
    }

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

    public boolean remove_course(String cid) {
        return schedule.remove(cid);
    }

    public void setSchedule(ArrayList<String> schedule) {
        this.schedule = schedule;
    }

    public String toCSV() {
        return String.format("%s,%s,%s,%s",
                _name, _DOB, _ID, String.join(";", schedule));
    }

    public void fromCSV(String csvString) {
        String[] fields = csvString.split(",");
        if (fields.length > 4)
            throw new IllegalArgumentException("INVAILD CSV STRING: " + csvString);
        if (Character.isDigit(fields[0].charAt(0))) {
            System.out.println("INVAILD NAME: NO DIGITS ALLOWED");
            return;
        }
        if (Character.isAlphabetic(fields[1].charAt(0))) {
            System.out.println("INVAILD DOB: NO CHARS ALLOWED");
            return;
        }
        _name = fields[0];
        _DOB = fields[1];
        _ID = fields[2];
        if (fields.length == 4) {
            String[] courses = fields[3].split(";");
            schedule.clear();
            for (String cid : courses)
                schedule.add(cid);
        }
    }

    @Override
    public String toString() {
        return String.format("%s : DOB: %s\n\tID: %s\n\tSchedule: %s",
                _name, _DOB, _ID, schedule.toString());
    }

    public String toReport() {
        return String.format("%s : DOB: %s\n\tID: %s",
                _name, _DOB, _ID);
    }
}