import java.sql.*;
import java.util.ArrayList;

public class SQLiteJDBCDefinitions {

    private static Connection c = null;

    static {
      try {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:dictionary.db");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      }
    }

    public static void insert(Word word) {
      Statement stmt = null;
      String wordStr = word.toString();
      ArrayList<String> defs = word.getDefinitions();
      String sql = "null";
      try {
        stmt = c.createStatement();
        for (int i = 0; i < defs.size(); i++) {
          sql = "INSERT INTO DEFINITIONS (WORD,DEFNUM,DEF) " +
                     "VALUES ('" + wordStr + "', " + (i + 1) + ", '" + 
                      defs.get(i) + "' );"; 
          stmt.executeUpdate(sql);
        }
        stmt.close();
        c.commit();
        c.close();
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        return;
      }
      System.out.println("Records created successfully");
    }

    public static Word select(String wordStr) {
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
          c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          return null;
        }
        System.out.println("Operation done successfully");
        return word;
    }
}