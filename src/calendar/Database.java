package calendar;

import java.sql.*;

public class Database {
	
	  public Connection connect = null;
	  private Statement statement = null;
	  //private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;

	  public void openConn() throws Exception {
	      // This will load the MySQL driver, each DB has its own driver
	      Class.forName("com.mysql.jdbc.Driver");
	      // Setup the connection with the DB
	      connect = DriverManager
	          .getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/all_s_gr46_test", "all_s_gr46" , "kaffe");
	 }
	  
	  public void closeConn() {
		    try {
		      if (resultSet != null) {
		        resultSet.close();
		      }

		      if (statement != null) {
		        statement.close();
		      }

		      if (connect != null) {
		        connect.close();
		      }
		    } catch (Exception e) {

		    }
	}
	 
	  public void viewTable(String table) throws Exception {
		  statement = connect.createStatement();
		  resultSet = statement
		          .executeQuery("select * from " + table);
		  //Skrive ut det man har hentet til konsollen
	      while (resultSet.next()) {
              System.out.println(resultSet.getString("fornavn") + " " + resultSet.getString("etternavn"));
          }
	  }
}
