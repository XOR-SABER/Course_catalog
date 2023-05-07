import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map.Entry;

import Datatypes.CommandTable;
import Datatypes.Course;
import Datatypes.CourseDatabase;
import Datatypes.Student;
import Datatypes.StudentDatabase;
import Datatypes.Course.Transfer;

// Sintax for commands 
// Example 
// : LOOKUP : DONE
// Starts the lookup function for courses 
// : EXIT : DONE 
// Exits the program
// : HELP : DONE 
// Prints out the commands avaliable 
// PRINT CATALOG : DONE
// Prints out the catalog
// PRINT {DEPARTMENT} : DONE
// Prints out all courses in a department..
// LOOKUP {COURSE ID} : DONE 
// : HELP {COMMAND} : DONE 
// Prints out the use of that command.. 
// : ADD STUDENT {NAME} {DOB} {ID} : DONE 
// This is around length of 5
// LOOKUP {STUDENT ID} : DONE
// : ADD COURSE {COURSE ID} {UNITS} {TRANSFER CODE} "COURSE NAME" : DONE
// This is around length of 6
// ENROLL {STUDENT ID} {COURSE ID} : DONE
// : SAVE STUDENTS {File name}  : DONE
// Saves database to .csv
// : LOAD STUDENTS {PATH} : DONE
// Loads student from .csv from a path
// : DROP STUDENT {ID} : DONE
// Removes a student from the data base
// STUDENT_REPORT {ID} : DONE
// Prints a report of student's progress
// : PRINT STUDENT DATABASE REPORT : DONE
// UNENROLL {STUDENT ID} {COURSE ID} : DONE

public class CommandPrompt {
    private CourseDatabase CourseCatalog;
    private StudentDatabase StudentDb;
    private CommandTable commands;
    private String orginFile;

    CommandPrompt(String fileName) {
        CourseCatalog = new CourseDatabase(fileName);
        StudentDb = new StudentDatabase();
        intialize_commands();
        System.out.println(
                "WELCOME TO COURSE CATALOG DATABASE SIMULATOR!\nTRY RUNNING THE HELP COMMAND TO SEE YOUR COMMAND OPTIONS!\n");
    }

    public void run() {
        while (true) {
            String command = get_input();
            commands.get_command(command);
        }
    }

    void intialize_commands() {
        commands = new CommandTable();
        commands.addVoidCommand(
                "LOOKUP", this::courseLookupVoid,
                "LOOKUP: runs the LOOKUP function.\nGood for looking for courses.");
        commands.addStringCommand(
                "EXIT", this::end_program,
                "EXIT: Exits the program.\nAdd save to exit save the database\nExample:\nEXIT\nEXIT SAVE");
        commands.addStringCommand(
                "PRINT", this::print,
                "PRINT: Looks up a object in a database and prints it.\nExample:\nPRINT {COURSE ID}\nPRINT {STUDENT ID}\nPRINT {DEPARTMENT}\nPRINT CATALOG\nPRINT DATABASE");
        commands.addStringCommand(
                "LOOKUP", this::LookupString,
                "LOOKUP: Looks up a object in a database and prints it.\nExample:\nLOOKUP {COURSE ID}\nLOOKUP {STUDENT ID}");
        commands.addStringCommand(
                "STUDENT_REPORT", this::get_report,
                "STUDENT_REPORT: Makes a student report file.\nExample:\nSTUDENT_REPORT {STUDENT ID}\nSTUDENT_REPORT ALL");
        commands.addLineCommand(
                "ADD", this::add_object,
                "ADD: adds a course or student using a function.\nExample:\nADD COURSE {COURSE ID} {UNITS} {TRANSFER CODE} \"COURSE NAME\"\nADD STUDENT {NAME} {DOB} {ID}");
        commands.addLineCommand(
                "ENROLL", this::enroll,
                "ENROLL: Enrolls student to a course.\nadds course into schedule.\nExample:\nENROLL {STUDENT ID} {COURSE ID}");
        commands.addLineCommand(
                "UNENROLL", this::unenroll,
                "UNENROLL: Unenrolls student to a course.\nremoves course into schedule.\nExample:\nUNENROLL {STUDENT ID} {COURSE ID}");
        commands.addLineCommand(
                "SAVE", this::save_db,
                "SAVE: SAVES A DATABASE TO FILE.\nExample:\nSAVE {DB_NAME} {FILENAME}");
        commands.addLineCommand(
                "LOAD", this::load_db,
                "LOAD: LOADS A DATABASE TO FILE.\nExample:\nLOAD {DB_NAME} {FILENAME}");
        commands.addLineCommand(
                "DROP", this::drop_data,
                "DROP: Removes student from a database.\nExample:\nDROP STUDENT {STUDENT ID}");

    }

    void courseLookupVoid(Void v) {
        System.out.print("Please enter a courseID");
        String id = get_input();
        Course tmp = CourseCatalog.getData(id);
        if (tmp != null) {
            System.out.println(tmp);
        } else {
            ArrayList<String> retval = CourseCatalog.Course_trie.autoComplete(id);
            if (!retval.isEmpty()) {
                System.out.println("Did you mean?: ");
                for (String str : retval) {
                    System.out.println(str);
                }
                courseLookupVoid(v);
            } else {
                System.out.println("Course doesn't exist");
            }
        }
    }

    void LookupString(String id) {
        Student temp = StudentDb.getData(id);
        if (temp != null) {
            System.out.println(temp);
            return;
        }
        Course tmp = CourseCatalog.getData(id);
        if (tmp != null) {
            System.out.println(tmp);
        } else {
            ArrayList<String> retval = CourseCatalog.Course_trie.autoComplete(id);
            if (!retval.isEmpty()) {
                System.out.println("Did you mean?: ");
                for (String str : retval) {
                    System.out.println(str);
                }
                courseLookupVoid(null);
            } else {
                System.out.println("Course doesn't exist");
            }
        }
    }

    String get_input() {
        System.out.print(": ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String input = br.readLine();
            String strUpper = input.toUpperCase();
            return strUpper;
        } catch (IOException e) {
            System.out.println("exiting program..");
            System.exit(1);
        }
        return null;
    }

    void end_program(String v) {
        if (v.equals("SAVE")) {
            String[] arr = { "", "STUDENTS", orginFile };
            save_db(arr);
        }
        System.out.println("Exiting program.. ");
        System.exit(0);
    }

    void print(String str) {
        Student temp = StudentDb.getData(str);
        if (temp != null) {
            System.out.println(temp);
            return;
        } else if (str.equals("CATALOG")) {
            CourseCatalog.printDatabase();
            return;
        } else if (str.equals("DATABASE")) {
            StudentDb.printDatabase();
            return;
        }

        ArrayList<String> all_course_id = CourseCatalog.Course_trie.autoComplete(str);
        if (all_course_id.isEmpty())
            return;
        for (String id : all_course_id) {
            System.out.println(CourseCatalog.getData(id).toString());
        }
    }

    void add_object(String str[]) {
        switch (str[1]) {
            case "STUDENT":
                if (str.length == 5)
                    add_student(str);
                break;
            case "COURSE":
                if (str.length == 6)
                    add_course(str);
                break;
            default:
                // Its your fault for being dumb..
                System.out.println("Unknown command use \"HELP\" to look at avaliable commands.");
                break;
        }
    }

    void add_student(String str[]) {
        Student tmp = new Student();
        if (Character.isDigit(str[2].charAt(0))) {
            System.out.println("INVAILD NAME: NO DIGITS ALLOWED");
            return;
        }
        if (Character.isAlphabetic(str[3].charAt(0))) {
            System.out.println("INVAILD DOB: NO CHARS ALLOWED");
            return;
        }
        tmp.setName(str[2]);
        tmp.setDOB(str[3]);
        tmp.setID(str[4]);
        StudentDb.addData(tmp.getID(), tmp);
    }

    void add_course(String str[]) {
        Course tmp = new Course();
        if (Character.isDigit(str[2].charAt(0))) {
            System.out.println("INVAILD ID: NO DIGITS ALLOWED");
            return;
        }
        if (Character.isAlphabetic(str[3].charAt(0))) {
            System.out.println("INVAILD UINTS: NO CHARS ALLOWED");
            return;
        }
        if (Character.isAlphabetic(str[4].charAt(0))) {
            System.out.println("INVAILD TRANSFER CODE: NO CHARS ALLOWED");
            return;
        }
        tmp.setCourseId(str[2]);
        tmp.setUnits(Float.parseFloat(str[3]));
        switch (Integer.parseInt(str[4])) {
            case 0:
                tmp.setTransferability(Transfer.NON_TRANSFERABLE);
                break;
            case 1:
                tmp.setTransferability(Transfer.CSU_TRANSFERABLE);
                break;
            case 2:
                tmp.setTransferability(Transfer.UC_TRANSFERABLE);
                break;
            case 3:
                tmp.setTransferability(Transfer.U_TRANSFERABLE);
                break;
            default:
                break;
        }
        if (Integer.parseInt(str[4]) > 3) {
            System.out.println(str[4] + ": TRANSFER CODE DOESN'T EXIST");
            return;
        }
        if (str[5].charAt(0) != '\"') {
            System.out.println("NO QUOTATIONS IN COURSE NAME");
            return;
        }
        String name = str[5].replaceAll("\"", "");
        tmp.setCourseName(name);
        CourseCatalog.addData(tmp.getCourseId(), tmp);
    }

    void enroll(String[] star) {
        Student temp = StudentDb.getData(star[1]);
        if (temp == null)
            return;
        Course tmp = CourseCatalog.getData(star[2]);
        if (tmp == null) {
            ArrayList<String> retval = CourseCatalog.Course_trie.autoComplete(star[2]);
            if (!retval.isEmpty()) {
                System.out.println("Did you mean?: ");
                for (String str : retval)
                    System.out.println(str);
            } else
                System.out.println("Course doesn't exist");
            return;
        }
        temp.add_course(tmp.getCourseId());
        StudentDb.addData(temp.getID(), temp);
    }

    void save_db(String[] str) {
        switch (str[1]) {
            case "STUDENTS":
                StudentDb.save_to_file(str[2]);
                System.out.println("Student database saved..");
                break;
            case "COURSES":
                System.out.println("Courses aren't supported..");
                break;
            default:
                // Its your fault for being dumb..
                System.out.println("Unknown command use \"HELP\" to look at avaliable commands.");
                break;
        }
        return;
    }

    void load_db(String[] str) {
        switch (str[1]) {
            case "STUDENTS":
                orginFile = str[2];
                StudentDb.loadFromFile(str[2]);
                System.out.println("Student database Loaded..");
                break;
            case "COURSES":
                System.out.println("Courses aren't supported..");
                break;
            default:
                // Its your fault for being dumb..
                System.out.println("Unknown command use \"HELP\" to look at avaliable commands.");
                break;
        }
        return;
    }

    void drop_data(String[] str) {
        switch (str[1]) {
            case "STUDENT":
                StudentDb.removeData(str[2]);
                break;
            case "COURSE":
                System.out.println("Courses aren't supported..");
                break;
            default:
                // Its your fault for being dumb..
                System.out.println("Unknown command use \"HELP\" to look at avaliable commands.");
                break;
        }
        return;
    }

    void get_report(String str) {
        if (!StudentDb.check(str)) {
            System.out.println("Unknown student..");
            return;
        } else if (str.equals("ALL")) {
            for (Entry<String, Student> entry : StudentDb.getDB().entrySet()) {
                make_report(entry.getValue());
            }
        } else {
            Student tmpStudent = StudentDb.getData(str);
            make_report(tmpStudent);
        }
    }

    void make_report(Student tmpStudent) {
        StringBuffer buff = new StringBuffer();
        buff.append(tmpStudent.toReport() + "\n\n");
        float Total = 0.0f;
        for (String s : tmpStudent.getSchedule()) {
            Course tmpCourse = CourseCatalog.getData(s);
            Total += tmpCourse.getUnits();
            buff.append(tmpCourse.toString() + "\n\n");
        }
        buff.append("TOTAL UNITS: " + Float.toString(Total));
        try {
            String fileName = "reports/" + tmpStudent.getName().toLowerCase() + "_report.txt";
            PrintWriter writer = new PrintWriter(fileName);
            writer.write(buff.toString());
            writer.close();
            System.out.println("Report saved to file: " + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save report to file: " + e.getMessage());
        }
    }

    void unenroll(String[] star) {
        Student temp = StudentDb.getData(star[1]);
        if (temp == null)
            return;
        Course tmp = CourseCatalog.getData(star[2]);
        if (tmp == null) {
            ArrayList<String> retval = CourseCatalog.Course_trie.autoComplete(star[2]);
            if (!retval.isEmpty()) {
                System.out.println("Did you mean?: ");
                for (String str : retval)
                    System.out.println(str);
            } else
                System.out.println("Course doesn't exist");
            return;
        }
        temp.remove_course(tmp.getCourseId());
        StudentDb.addData(temp.getID(), temp);
    }
}
