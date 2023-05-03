import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Catalog extends Database<Course> {
    public Trie Course_trie;

    Catalog(String fileName) {
        Database = new HashMap<>();
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
            System.out.println("A file error occured...");
            error.printStackTrace();
            // No file exit the program..
            System.exit(1);
        }
    }
}
