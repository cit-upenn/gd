package Dictionary;

import java.util.HashMap;
import java.util.ArrayList;

public class Trie {
    private TrieNode root;
 
    public Trie() {
        root = new TrieNode();
    }
 
    // Inserts a word into the trie.
    public void insert(String word) {
        HashMap<Character, TrieNode> children = root.children;
 
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
 
            TrieNode t;
            if(children.containsKey(c)){
                    t = children.get(c);
            }else{
                t = new TrieNode(c);
                children.put(c, t);
            }
 
            children = t.children;
 
            //set leaf node
            if(i==word.length()-1)
                t.isLeaf = true;    
        }
    }
 
    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode t = searchNode(word);
 
        if(t != null && t.isLeaf) 
            return true;
        else
            return false;
    }
 
    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        if(searchNode(prefix) == null) 
            return false;
        else
            return true;
    }
 
    private TrieNode searchNode(String str){
        HashMap<Character, TrieNode> children = root.children; 
        TrieNode t = null;
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                children = t.children;
            }else{
                return null;
            }
        }
 
        return t;
    }

    // returns 10 words in the trie tree that starts with the prefix
    public ArrayList<String> getWordsStartsWith(String prefix) {
        ArrayList<String> words = new ArrayList<String>();
        TrieNode t = searchNode(prefix);
        if (t == null) {
            return words;
        }
        searchWordsHelper(words, t, prefix);
        return words;
    }

    private void searchWordsHelper(ArrayList<String> words, TrieNode t, String word) {
        // limit the size of returned list to 10
        if (words.size() >= 20) {
            return;
        }

        if (t.isLeaf == true) {
            words.add(word);
        }
        for (char c : t.children.keySet()) {
            searchWordsHelper(words, t.children.get(c), word + c);
        }
    }
    
    // Tester
    public static void main(String[] args) {
        Trie lexicon = new Trie();
        lexicon.insert("brake");
        lexicon.insert("service");
        System.out.println(lexicon.search("brake"));
        System.out.println(lexicon.search("bra"));
        System.out.println(lexicon.search("serve"));
        System.out.println(lexicon.startsWith("bra"));
        System.out.println(lexicon.startsWith("sea"));
        lexicon.insert("bra");
        lexicon.insert("brave");
        System.out.println(lexicon.search("bra"));
        ArrayList<String> words = lexicon.getWordsStartsWith("bra");
        for (String w : words) {
            System.out.println(w);
        }
    }
}