import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class Menu {
    private Trie commandTrie;
    private Catalog CourseCatalog;
    private HashMap<String, Consumer<Void>> function_table;
    Menu(String fileName) {
        commandTrie = new Trie();
        CourseCatalog = new Catalog(fileName);
        function_table = new HashMap<>();
        commandTrie.insert("LOOKUP");
        
        function_table.put("LOOKUP", this::courseLookup);
        while (true) {
            String command = get_input();
            if(function_table.get(command) == null) {
                ArrayList<String> retval = commandTrie.autoComplete(command);
                if(!retval.isEmpty()) {
                    System.out.println("Did you mean?: ");
                    for (String str : retval) {
                        System.out.println(str);
                    }
                }
            } else function_table.get(command).accept(null);
            break;
        }
    }
    void print_table() {
        CourseCatalog.print_catalog();
    }
    void courseLookup(Void v) {
        String id = get_input();
        Course tmp = CourseCatalog.getCourse(id);
        if(tmp != null) {
            System.out.println(tmp);
        }
        else {
            ArrayList<String> retval = CourseCatalog.Course_trie.autoComplete(id);
            if(!retval.isEmpty()) {
                System.out.println("Did you mean?: ");
                for (String str : retval) {
                    System.out.println(str);
                }
            }
            else {
                System.out.println("Course doesn't exist");
            }
        }
    }

    String get_input() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            String input = br.readLine();
            String strUpper = input.toUpperCase();
            return strUpper;
        } catch(IOException e) {
            System.out.println("exiting program..");
            System.exit(1);
        }
        return null;
    }

}
