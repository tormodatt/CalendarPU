package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import calendar.Database;

public class UserView extends Database{
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> userNames = new ArrayList<String>(); 

	public UserView() throws Exception{

		try {
			openConn();
			preparedStatement = connect.prepareStatement("SELECT Username, First_name, Last_name FROM User ORDER BY First_name");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				names.add(resultSet.getString("First_name") + " " + resultSet.getString("Last_name"));  
				userNames.add(resultSet.getString("Username"));
			}
		} finally {
			closeConn();
		}
		for (int i = 0; i < names.size(); i++) {
			System.out.println((i+1) + ". "+ names.get(i));
		}
	}
	
	public ArrayList<String> getUserNames() {
		return userNames;
	}
}
