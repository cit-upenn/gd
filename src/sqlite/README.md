###Compile and Run

- Download latest version of sqlite-jdbc-(VERSION).jar from sqlite-jdbc repository.
- Add downloaded jar file sqlite-jdbc-(VERSION).jar in your class path, or you can use it along with -classpath option as the example below.

  $javac *.java

  $java -classpath ".:sqlite-jdbc-3.8.11.2.jar" SQLiteTester

###Usage

- **SQLiteJDBCInitializer** is used to establish the database and create tables.
- **SQLiteJDBC** is a database helper class that provides several methods to allow "SELECT", "INSERT", "DELETE", etc. operations for specific purposes.

###Database (at root directory)

database: 
- dictionary.db

tables: 
- definitions (word, defnum, def) - *e.g. ('application', 1, 'a formal request to an authority for something.')*
- learning (word) - *e.g. ('word')*