import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Map.Entry;

public class Catalog {

    private HashMap<String,Course> CourseMap;

    public Trie Course_trie; 

    Catalog(String fileName) {
        CourseMap = new HashMap<>();
        Course_trie = new Trie();
        try {
            File courseFile = new File(fileName);
            Scanner scanner = new Scanner(courseFile);
        while (scanner.hasNextLine()) {
                String current_line = scanner.nextLine();
                if(!current_line.isEmpty()) {
                    Course temp = new Course(current_line);
                    Course_trie.insert(temp.getCourseId());
                    addCourse(temp);
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

    public boolean addCourse(Course currentCourse) {
        if(check(currentCourse.getCourseId())) return false;
        CourseMap.put(currentCourse.getCourseId(), currentCourse);
        return true;
    }

    public boolean check(String id) {
        if(CourseMap.get(id) == null) return false;
        return true;
    }

    public Course getCourse(String id) {
        if(!check(id)) return null;
        return CourseMap.get(id);
    }
    public void print_catalog(){
        for (Entry<String, Course> entry : CourseMap.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
