import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.HashMap;

public class Command_table {
    // Storing functions in a dictionary of functions for quick access..
    private HashMap<String, Consumer<Void>> void_function_table;
    private HashMap<String, Consumer<String>> string_function_table;
    private Trie commandTrie;

    Command_table() {
        commandTrie = new Trie();
        void_function_table = new HashMap<>();
        string_function_table = new HashMap<>();

        // intialize help here..
        addVoidCommand("HELP", this::help);
    }

    // Adds commands that have a void parameter
    void addVoidCommand(String command_name, Consumer<Void> func) {
        commandTrie.insert(command_name);
        void_function_table.put(command_name, func);
    }

    // Adds commands that have a string parameter
    void addStringCommand(String command_name, Consumer<String> func) {
        commandTrie.insert(command_name);
        string_function_table.put(command_name, func);
    }

    void get_command(String command) {
        // One liner commands..
        if (void_function_table.get(command) != null) {
            void_function_table.get(command).accept(null);
            return;
        }
        // One word commands
        String parsed_command[] = command.split(" ");
        if (parsed_command.length == 1) {
            if (void_function_table.get(command) != null) {
                void_function_table.get(command).accept(null);
                return;
            }
        }
        // Two word commands..
        else if (parsed_command.length == 2) {
            if (string_function_table.get(parsed_command[0]) != null) {
                string_function_table.get(parsed_command[0]).accept(parsed_command[1]);
                return;
            }
        }
        // This command doesn't make sense do you have another command in mind?
        command_auto_complete(command);

    }

    void command_auto_complete(String command) {
        ArrayList<String> retval = commandTrie.autoComplete(command);
        // If not empty then there's probably a mistake..
        if (!retval.isEmpty()) {
            System.out.println("Did you mean?: ");
            for (String str : retval) {
                System.out.println(str);
            }
        }
        // Its your fault for being dumb..
        System.out.println("Unknown command use \"HELP\" to look at avaliable commands.");
    }

    void help(Void v) {
        System.out.println("These are the commands avaliable: ");
        ArrayList<String> retval = commandTrie.getAllWords();
        for (String str : retval) {
            System.out.println(": " + str);
        }
    }
}
