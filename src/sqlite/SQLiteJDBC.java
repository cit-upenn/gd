package sqlite;
import java.sql.*;
import java.util.ArrayList;

/**
 * SQLite helper class that deals with database operations.
 * @author Qingxiao Dong
 */
public class SQLiteJDBC {

    private static Connection c = null;

    /**
     * Static code block that opens database connection.
     */
    static {
      try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:dictionary.db");
        c.setAutoCommit(true);
        System.out.println("Opened database successfully");
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      }
    }

    /**
     * Given a word, the method inserts tuples into the DEFINITIONS table.
     * @param word the word that is going to be inserted
     */
    public static void insertIntoDictionary(Word word) {
      Statement stmt = null;
      String wordStr = word.toString();
      ArrayList<String> defs = word.getDefinitions();
      String sql = "";
      try {
        stmt = c.createStatement();
        for (int i = 0; i < defs.size(); i++) {
          sql = "INSERT INTO DEFINITIONS (WORD,DEFNUM,DEF) " +
                        "VALUES ('" + wordStr + "', " + (i + 1) + ", '" + 
                        defs.get(i) + "' );"; 
          stmt.executeUpdate(sql);
        }
        stmt.close();
      } catch ( Exception e ) {
        System.out.println(sql);
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        return;
      }
    }

    /**
     * The method selects propriate tuples from the DEFINITIONS table that matches
     * the word string and convert them to a single Word object.
     * @param wordStr the word's string
     * @return a Word object that contains the definitions
     */
    public static Word selectFromDictionary(String wordStr) {
        Statement stmt = null;
        Word word = new Word(wordStr);
        try {
          stmt = c.createStatement();
          ResultSet rs = stmt.executeQuery( "SELECT * FROM DEFINITIONS WHERE WORD = '" + 
            wordStr + "';" );
          while ( rs.next() ) {
            String def = rs.getString("def");
            word.addDefinition(def);
          }
          rs.close();
          stmt.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          return null;
        }
        return word;
    }

    /**
     * The method add a word to the LEARNING table.
     * @param word the Word object to be added
     */
    public static void addWordToLearn(Word word) {
      Statement stmt = null;
      String wordStr = word.toString();
      addWordToLearn(wordStr);
    }

    /**
     * The method add a word to the LEARNING table.
     * @param wordStr the word string to be added
     */
    public static void addWordToLearn(String wordStr) {
      Statement stmt = null;
      try {
        stmt = c.createStatement();
        String sql = "INSERT INTO LEARNING (WORD) " +
                      "VALUES ('" + wordStr + "' );"; 
        stmt.executeUpdate(sql);
        stmt.close();
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        return;
      }
    }

    /**
     * The method removes a word from the LEARNING table.
     * @param word the Word object to be removed
     */
    public static void removeWordToLearn(Word word) {
      Statement stmt = null;
      String wordStr = word.toString();
      removeWordToLearn(wordStr);
    }

    /**
     * The method removes a word from the LEARNING table
     * @param wordStr the word string to be removed
     */
    public static void removeWordToLearn(String wordStr) {
      Statement stmt = null;
      try {
        stmt = c.createStatement();
        String sql = "DELETE FROM LEARNING WHERE WORD = '" + wordStr + "';"; 
        stmt.executeUpdate(sql);
        stmt.close();
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        return;
      }
    }

    /**
     * The method returns all the Words (word and their definitions) that 
     * are in both DEFINITIONS and LEARNING table
     * @return all the Words (word and their definitions) that are in both DEFINITIONS and LEARNING table
     */
    public static ArrayList<String> getWordsToLearn() {
      Statement stmt = null;
      ArrayList<String> words = new ArrayList<String>();
      try {
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM LEARNING;" );
        while ( rs.next() ) {
          String wordStr = rs.getString("word");
          words.add(wordStr);
        }
        rs.close();
        stmt.close();
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        return null;
      }
      return words;
    }
    
    public static boolean hasWordToLearn(String wordStr) {
        Statement stmt = null;
        boolean found = false;
        try {
          stmt = c.createStatement();
          ResultSet rs = stmt.executeQuery( "SELECT * FROM LEARNING WHERE WORD = '" + 
            wordStr + "';" );
          if (rs.next()) {
        	  found = true;
          }
          rs.close();
          stmt.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          return found;
        }
        return found;
      }
}