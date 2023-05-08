package Datatypes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Map.Entry;

import Datatypes.DataStructures.Database;

public class StudentDatabase extends Database<Student> {
    public StudentDatabase() {
    }

    public StudentDatabase(String fileName) {
        super();
        loadFromFile(fileName);
    }

    public void loadFromFile(String fileName) throws IllegalArgumentException {
        try {
            File courseFile = new File(fileName);
            Scanner scanner = new Scanner(courseFile);
            while (scanner.hasNextLine()) {
                String current_line = scanner.nextLine();
                if (!current_line.isEmpty()) {
                    Student tmp = new Student();
                    tmp.fromCSV(current_line);
                    if (check(tmp.getID())) {
                        scanner.close();
                        throw new IllegalArgumentException("ID ALREADY EXISTS");
                    }
                    addData(tmp.getID(), tmp);
                }
            }
            scanner.close();
            System.out.println("Student database Loaded..");
        } catch (FileNotFoundException error) {
            System.out.println("FILE NOT FOUND...");
            error.printStackTrace();
        }
    }

    public void saveToFile(String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName);
            for (Entry<String, Student> entry : Database.entrySet()) {
                writer.println(entry.getValue().toCSV());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save to file: " + fileName);
            e.printStackTrace();
        }
    }
}