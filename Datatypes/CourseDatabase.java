package Datatypes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Datatypes.DataStructures.Database;
import Datatypes.DataStructures.Trie;

public class CourseDatabase extends Database<Course> {
    public Trie Course_trie;

    public CourseDatabase(String fileName) {
        super();
        Course_trie = new Trie();
        try {
            File courseFile = new File(fileName);
            Scanner scanner = new Scanner(courseFile);
            while (scanner.hasNextLine()) {
                String current_line = scanner.nextLine();
                if (!current_line.isEmpty()) {
                    Course temp = new Course(current_line);
                    Course_trie.insert(temp.getCourseId());
                    addData(temp.getCourseId(), temp);
                }
            }
            scanner.close();
        } catch (FileNotFoundException error) {
            System.out.println("FILE NOT FOUND!");
            error.printStackTrace();
            // No file exit the program..
            System.exit(1);
        }
    }

    @Override
    public boolean addData(String id, Course new_data) {
        Database.put(id, new_data);
        Course_trie.insert(id);
        return true;
    }
}