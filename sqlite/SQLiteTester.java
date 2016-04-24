public class SQLiteTester {
    public static void main(String[] args) {
        Word word = new Word("application");
        word.addDefinition("a formal request to an authority for something.");
        word.addDefinition("the action of putting something into operation.");
        SQLiteJDBC.insert(word);

        Word w = SQLiteJDBC.select("application");
        w.print();
    }
}