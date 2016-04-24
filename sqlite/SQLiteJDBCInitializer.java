import java.sql.*;

public class SQLiteJDBCInitializer
{
  public static void main( String args[] )
  {
    Connection c = null;
    Statement stmt = null;

    try {

      // connect to DB, if DB does not exist, create a new one
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:dictionary.db");
      System.out.println("Opened database successfully");

      // create table
      stmt = c.createStatement();
      String sql = "CREATE TABLE DEFINITIONS " +
                   "(WORD           TEXT    NOT NULL, " +
                   " DEFNUM         INT     NOT NULL, " + 
                   " DEF            TEXT    NOT NULL, " + 
                   " PRIMARY KEY (WORD, DEFNUM) " + 
                   " )"; 
      stmt.executeUpdate(sql);
      System.out.println("Table created successfully");

      stmt.close();
      c.close();   

    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }

  }
}