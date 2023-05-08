package Datatypes;

import Datatypes.DataStructures.Trie;

import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashMap;

public class CommandTable {
    // Storing functions in a dictionary of functions for quick access..
    private TreeMap<String, String> help_map;
    private HashMap<String, Consumer<Void>> void_function_table;
    private HashMap<String, Consumer<String>> string_function_table;
    private HashMap<String, Consumer<String[]>> line_function_table;
    // private HashMap<String, tooltip> help_map;
    private Trie commandTrie;

    public CommandTable() {
        commandTrie = new Trie();
        help_map = new TreeMap<>();
        void_function_table = new HashMap<>();
        string_function_table = new HashMap<>();
        line_function_table = new HashMap<>();

        // intialize help here..
        addVoidCommand(
                "HELP", this::help,
                "Try HELP {command names}.");
        addStringCommand(
                "HELP", this::help,
                "Try HELP {command names}.");
    }

    // Adds commands that have a void parameter
    public void addVoidCommand(String command_name, Consumer<Void> func, String tool_tip) {
        commandTrie.insert(command_name);
        void_function_table.put(command_name, func);
        help_map.put(command_name, tool_tip);
    }

    // Adds commands that have a string parameter
    public void addStringCommand(String command_name, Consumer<String> func, String tool_tip) {
        commandTrie.insert(command_name);
        string_function_table.put(command_name, func);
        help_map.put(command_name, tool_tip);
    }

    // Adds commands that have a string parameter
    public void addLineCommand(String command_name, Consumer<String[]> func, String tool_tip) {
        commandTrie.insert(command_name);
        line_function_table.put(command_name, func);
        help_map.put(command_name, tool_tip);
    }

    public void get_command(String command) {
        // One word commands
        String parsed_command[] = command.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        if (parsed_command.length == 1) {
            parsed_command[0] = parsed_command[0].toUpperCase();
            if (void_function_table.get(parsed_command[0]) != null) {
                void_function_table.get(parsed_command[0]).accept(null);
                return;
            }
        }
        // Two word commands..
        else if (parsed_command.length == 2) {
            parsed_command[0] = parsed_command[0].toUpperCase();
            parsed_command[1] = parsed_command[1].toUpperCase();
            if (string_function_table.get(parsed_command[0]) != null) {
                string_function_table.get(parsed_command[0]).accept(parsed_command[1]);
                return;
            }
        } else if (parsed_command.length > 2) {
            parsed_command[0] = parsed_command[0].toUpperCase();
            parsed_command[1] = parsed_command[1].toUpperCase();
            if (line_function_table.get(parsed_command[0]) != null) {
                line_function_table.get(parsed_command[0]).accept(parsed_command);
                return;
            }
        }
        // This command doesn't make sense do you have another command in mind?
        commandAutoComplete(command);
    }

    public void commandAutoComplete(String command) {
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

    // Void help command..
    void help(Void v) {
        System.out.println("These are the commands avaliable: Try HELP {command names}!");
        ArrayList<String> retval = commandTrie.getAllWords();
        for (String str : retval) {
            System.out.println("\t" + str);
        }
    }

    // String help command..
    void help(String str) {
        // Add did you mean here..
        if (help_map.get(str) == null) {
            commandAutoComplete(str);
            return;
        }
        System.out.println(help_map.get(str));
    }
}