package Datatypes.DataStructures;

import java.util.ArrayList;
import java.util.HashMap;

// I tried all over the carpet 
// :< 
public class Trie {
    private class TrieNode {
        HashMap<Character, TrieNode> nodeMap = new HashMap<>();
        boolean isEndOfWord;
    }

    private TrieNode root;

    private void getAllWords(TrieNode current, StringBuilder sb, ArrayList<String> words) {
        if (current.isEndOfWord) {
            words.add(sb.toString());
        }

        for (char c : current.nodeMap.keySet()) {
            char d = Character.toUpperCase(c);
            sb.append(d);
            getAllWords(current.nodeMap.get(d), sb, words);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    private boolean delete(TrieNode node, String word, int index) {
        if (index == word.length()) {
            if (!node.isEndOfWord) {
                return false;
            }
            node.isEndOfWord = false;
            return node.nodeMap.isEmpty();
        }
        char c = word.charAt(index);
        c = Character.toUpperCase(c);
        if (!node.nodeMap.containsKey(c)) {
            return false;
        }
        TrieNode child = node.nodeMap.get(c);
        boolean shouldDelete = delete(child, word, index + 1);
        if (shouldDelete) {
            node.nodeMap.remove(c);
            return node.nodeMap.isEmpty();
        }
        return false;
    }

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            char d = Character.toUpperCase(c);
            if (!curr.nodeMap.containsKey(d)) {
                curr.nodeMap.put(d, new TrieNode());
            }
            curr = curr.nodeMap.get(d);
        }
        curr.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            char d = Character.toUpperCase(c);
            if (!curr.nodeMap.containsKey(d)) {
                return false;
            }
            curr = curr.nodeMap.get(d);
        }
        return curr.isEndOfWord;
    }

    public boolean startsWith(String prefix) {
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) {
            char d = Character.toUpperCase(c);
            if (!curr.nodeMap.containsKey(d)) {
                return false;
            }
            curr = curr.nodeMap.get(d);
        }
        return true;
    }

    public ArrayList<String> autoComplete(String prefix) {
        ArrayList<String> words = new ArrayList<>();
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) {
            char d = Character.toUpperCase(c);
            if (!curr.nodeMap.containsKey(d)) {
                return words;
            }
            curr = curr.nodeMap.get(d);
        }
        collectWords(curr, new StringBuilder(prefix), words);
        return words;
    }

    private void collectWords(TrieNode node, StringBuilder sb, ArrayList<String> words) {
        if (node.isEndOfWord) {
            words.add(sb.toString());
        }
        for (char c : node.nodeMap.keySet()) {
            char d = Character.toUpperCase(c);
            collectWords(node.nodeMap.get(d), sb.append(d), words);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public void delete(String word) {
        delete(root, word, 0);
    }

    public void clear() {
        root = new TrieNode();
    }

    public ArrayList<String> getAllWords() {
        ArrayList<String> words = new ArrayList<>();
        getAllWords(root, new StringBuilder(), words);
        return words;
    }
}
