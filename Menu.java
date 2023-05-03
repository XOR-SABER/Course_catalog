import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

// Sintax for commands 
// Not case senstive.. 
// No operators, +-/*
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


// : HELP {Command} 
// Prints out the use of that command.. 
// : ADD COURSE 
// Does the add course function.. 
// : ADD COURSE {COURSE ID} {UNITS} {TRANSFER CODE} "COURSE NAME"
// This is around length of 6
// : ADD STUDENT {NAME} {DOB} {ID} 
// This is around length of 5
// : SAVE STUDENTS {File name}
// Saves database to .csv 
// : LOAD STUDENTS {PATH}
// Loads student from .csv from a path
// : DROP STUDENT {ID} 
// Removes a student from the data base
// : PRINT STUDENT REPORT {ID}
// Prints a report of student's progress
// : PRINT STUDENT DATABASE REPORT
// LOOKUP {COURSE ID}
// LOOKUP {STUDENT ID}

public class Menu {
    private Catalog CourseCatalog;
    private Command_table commands;

    Menu(String fileName) {
        commands = new Command_table();
        commands.addVoidCommand("EXIT", this::end_program);
        commands.addVoidCommand("LOOKUP", this::courseLookupVoid);
        commands.addVoidCommand("PRINT CATALOG", this::print_table);
        commands.addStringCommand("PRINT", this::print_dep);
        commands.addStringCommand("LOOKUP", this::courseLookupString);
        commands.addStringCommand("ADD", this::add_object);
        CourseCatalog = new Catalog(fileName);
        while (true) {
            String command = get_input();
            commands.get_command(command);
        }
    }

    void print_table(Void V) {
        CourseCatalog.print_database();
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

    void courseLookupString(String id) {
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

    void end_program(Void v) {
        System.out.println("Exiting program.. ");
        System.exit(0);
    }

    void print_dep(String str) {
        ArrayList<String> all_course_id = CourseCatalog.Course_trie.autoComplete(str);
        if (all_course_id.isEmpty())
            return;
        for (String id : all_course_id) {
            System.out.println(CourseCatalog.getData(id).toString());
        }
    }

    void add_object(String str) {
        switch (str) {
            case "STUDENT":
                System.out.println("S");
                break;
            case "COURSE":
                System.out.println("C"); 
                break;
            default:
                break;
        }
    }

    void clear_screen() {

    }
}
