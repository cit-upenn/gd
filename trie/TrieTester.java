import java.util.ArrayList;

public class TrieTester {
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