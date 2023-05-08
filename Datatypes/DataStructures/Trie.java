package Datatypes.DataStructures;

import java.util.ArrayList;
import java.util.HashMap;

// I tried all over the carpet 
// :< 
// This class implements the Trie data structure
// It is used to store a set of strings and provides a quick and efficient way to search and retrieve them.
public class Trie {
    // Inner class for Trie node which stores:
    // 1. A map of child nodes to next characters
    // 2. A boolean flag indicating if it's the end of a word
    private class TrieNode {
        HashMap<Character, TrieNode> nodeMap = new HashMap<>();
        boolean isEndOfWord;
    }

    // The root of the Trie data structure
    private TrieNode root;

    // Recursive function to get all the words in the trie
    // Time Complexity: O(n), where n is the number of nodes in the trie
    private void getAllWords(TrieNode current, StringBuilder sb, ArrayList<String> words) {
        // If current node is the end of a word, add the word to the list of words
        if (current.isEndOfWord) {
            words.add(sb.toString());
        }

        // Traverse all the child nodes of the current node
        for (char c : current.nodeMap.keySet()) {
            char d = Character.toUpperCase(c);
            sb.append(d);
            // Recursively call the function for each child node
            getAllWords(current.nodeMap.get(c), sb, words);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    // Deletes a word from the trie data structure
    // Returns true if the word was successfully deleted, false otherwise
    // Time Complexity: O(m), where m is the length of the word being deleted
    private boolean delete(TrieNode node, String word, int index) {
        // If we've reached the end of the word
        if (index == word.length()) {
            // If this node is not marked as the end of a word, then the word is not in the
            // trie
            if (!node.isEndOfWord) {
                return false;
            }
            // Mark this node as not the end of a word and check if this node has any
            // children nodes
            node.isEndOfWord = false;
            return node.nodeMap.isEmpty();
        }
        // Get the character at the current index and convert it to uppercase
        char c = word.charAt(index);
        c = Character.toUpperCase(c);
        // If the current node does not have a child node with the current character,
        // then the word is not in the trie
        if (!node.nodeMap.containsKey(c)) {
            return false;
        }
        // Recursively call the delete function on the child node with the next
        // character in the word
        TrieNode child = node.nodeMap.get(c);
        boolean shouldDelete = delete(child, word, index + 1);
        // If the child node should be deleted and has no children nodes, remove it from
        // this node's nodeMap
        if (shouldDelete) {
            node.nodeMap.remove(c);
            return node.nodeMap.isEmpty();
        }
        // If the child node should not be deleted or has children nodes, do not remove
        // it from this node's nodeMap
        return false;
    }

    // Constructor that initializes the root node of the trie
    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie
    // Time Complexity: O(m), where m is the length of the word being inserted
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

    // Searches for a word in the trie
    // Time Complexity: O(m), where m is the length of the word being searched
    // Returns true if the word is in the trie, false otherwise
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

    // Searches for a prefix in the trie
    // Time Complexity: O(m), where m is the length of the prefix being searched
    // Returns true if the prefix is in the trie, false otherwise
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

    // Given a prefix, returns an ArrayList of all the words in the trie that have
    // the given prefix.
    // Time complexity: O(m + k) where m is the length of the prefix and k is the
    // number of words that have the prefix
    // Returns an ArrayList of all the words that have the given prefix
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

    // Collects all the words from the given TrieNode in the trie that have been
    // added so far
    // Time Complexity: O(m * n), where m is the number of characters in the longest
    // word in the trie and n is the number of words in the trie
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

    // Deletes a word from the trie
    // Time Complexity: O(m), where m is the length of the word being deleted
    public void delete(String word) {
        delete(root, word, 0);
    }

    // Clears all the words from the trie
    // Time Complexity: O(1)
    public void clear() {
        root = new TrieNode();
    }

    // Gets all the words from the trie that have been added so far
    // Time Complexity: O(m * n), where m is the number of characters in the longest
    // word in the trie and n is the number of words in the trie
    public ArrayList<String> getAllWords() {
        ArrayList<String> words = new ArrayList<>();
        getAllWords(root, new StringBuilder(), words);
        return words;
    }
}
