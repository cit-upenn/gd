import java.util.ArrayList;

public class Word {

    private String word;
    private ArrayList<String> definitions;

    public Word(String word) {
        this.word = word;
        definitions = new ArrayList<String>();
    }

    public String toString() {
        return word;
    }

    public void addDefinition(String def) {
        definitions.add(def);
    }

    public ArrayList<String> getDefinitions() {
        return new ArrayList<String>(definitions);
    }

    public void print() {
        System.out.println(word);
        for (int i = 0; i < definitions.size(); i++) {
            System.out.println((i + 1) + ". " + definitions.get(i));            
        }

    }

}