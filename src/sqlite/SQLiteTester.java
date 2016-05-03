package sqlite;
/**
 * Tester class for testing SQLiteJDBC methods.
 * @author Qingxiao Dong
 */
public class SQLiteTester {

    /**
     * The main class.
     * @param args runtime arguments
     */
    public static void main(String[] args) {

         /** test insert into dictionary  */
        // Word word = new Word("connection");
        // word.addDefinition("a relationship in which a person, thing, or idea is linked or associated with something else.");
        // word.addDefinition("a supplier of narcotics.");
        // SQLiteJDBC.insertIntoDictionary(word);

         /** test select from dictionary */
        // Word w = SQLiteJDBC.selectFromDictionary("main");
        // w.print();
        // SQLiteJDBC.selectFromDictionary("application").print();

         /** test add word to learn and get words to learn */
        // SQLiteJDBC.addWordToLearn("application");
        // SQLiteJDBC.addWordToLearn(word);
        // ArrayList<Word> words = SQLiteJDBC.getWordsToLearn();
        // for (Word wd : words) {
        //     wd.print();
        // }

         /** test remove word to learn and get words to learn */
        // SQLiteJDBC.removeWordToLearn(word);
        // words = SQLiteJDBC.getWordsToLearn();
        // for (Word wd : words) {
        //     wd.print();
        // }
        // SQLiteJDBC.removeWordToLearn("application");
        // words = SQLiteJDBC.getWordsToLearn();
        // for (Word wd : words) {
        //     wd.print();
        // }
    	
        SQLiteJDBC.selectFromDictionary("A").print();

    }
}