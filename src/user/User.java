package user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.StringTokenizer;

import Appointment.Notification;
import calendar.Calendar;
import calendar.Database;

public class User extends Database{

	public String firstname; 
	public String lastname;
	public String username; 
	public String password;
	public String mail; 

	public Calendar personalCalendar; 
	public ArrayList<Group> groups = new ArrayList<Group>();
	public ArrayList<Notification> notifications = new ArrayList<Notification>();

	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	//Opprette bruker-instans av bruker som allerede eksisterer i databasen
	public User(String username) throws Exception {
		if (userNameExists(username)) {
			this.username = username;
			try {
				openConn();
				preparedStatement = connect.prepareStatement("select * from User WHERE Username='"+username+"'");
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					this.firstname = resultSet.getString("First_name");
					this.lastname = resultSet.getString("Last_name");
					this.mail = resultSet.getString("Mail");
					this.password = resultSet.getString("Password");
				}
				preparedStatement = connect.prepareStatement("select GroupID from Group_members WHERE Username=?");
				preparedStatement.setString(1, getUsername());
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					groups.add(new Group(resultSet.getInt("GroupID"),this));
				}
				personalCalendar = new Calendar(this);
			} finally {
				closeConn();
			}
		}
		else System.out.println("Brukernavnet eksisterer ikke!");
	}

	//Opprette bruker-instans av ny bruker
	public User(String firstname, String lastname, String username, String password,String mail) throws Exception {
		setCredencials(firstname,lastname,username,password,mail);
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.mail = mail;
		this.personalCalendar = new Calendar(this,"Personal Calendar");
	}

	//Gettere
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getMail() {
		return mail;
	}
	public Calendar getPersonalCalendar() {
		return personalCalendar;
	}
	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setCredencials(String firstname, String lastname, String username, String password, String mail) throws Exception {
		try {
			if(isValidName(firstname) && isValidName(lastname) && isValidUsername(username) && isValidMail(mail)){

			}else throw new IllegalArgumentException("Either the name or username is invalid");  

			openConn();
			preparedStatement = connect.prepareStatement("insert into User values (?,?, ?, ?, ?)");
			preparedStatement.setString(1,username);
			preparedStatement.setString(2,firstname);
			preparedStatement.setString(3,lastname);
			preparedStatement.setString(4,mail);
			preparedStatement.setString(5,password);
			preparedStatement.executeUpdate();
			//Opprette en personlig kalender
			preparedStatement = connect.prepareStatement("insert into Calendar values (default,?,?,null)");
			preparedStatement.setString(1,"Personlig");
			preparedStatement.setString(2,getUsername());
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}
	}

	public void updateFirstName(String firstname) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("update User set First_name=? where Username=?");
			preparedStatement.setString(1, firstname);
			preparedStatement.setString(2, getUsername());
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}
	}

	public void updateLastName(String firstname) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("update User set Last_name=? where Username=?");
			preparedStatement.setString(1, lastname);
			preparedStatement.setString(2, getUsername());
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}
	}

	public void updatePassword(String password) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("update User set Password=? where Username=?");
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, getUsername());
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}
	}

	public void updateMail(String mail) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("update User set Mail=? where Username=?");
			preparedStatement.setString(1, mail);
			preparedStatement.setString(2, getUsername());
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}
	}

	public boolean userNameExists(String username) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select Username from all_s_gr46_calendar.User WHERE Username= ?");
			preparedStatement.setString(1,username);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} finally {
			closeConn();
		}
		return false;
	}

	public void setPassword(String password) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("UPDATE User SET Password= ? where Username= ?");
			preparedStatement.setString(1,password);
			preparedStatement.setString(2,username);
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}		
		this.password = password;
	}

	public void setMail(String mail) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("UPDATE User SET Mail= ? where Username= ?");
			preparedStatement.setString(1,mail);
			preparedStatement.setString(2,username);
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}		
		this.mail = mail;
	}

	public void addGroup(Group group) throws Exception {
		if (groups.contains(group)) {
			throw new IllegalArgumentException("Allerede medlem i gruppe"); 
		} else {
			try {
				openConn();
				preparedStatement = connect.prepareStatement("insert into Group_members values (?,?)");
				preparedStatement.setString(1, getUsername());
				preparedStatement.setInt(2, group.getGroupID());
				preparedStatement.executeUpdate();
			} finally {
				closeConn();
			}
			groups.add(group); 
		}
	}

	public void removeGroup(Group group) throws Exception {
		if (groups.contains(group)) {
			try {
				openConn();
				preparedStatement = connect.prepareStatement("delete from Group_members where Username=? and GroupID=?");
				preparedStatement.setString(1, getUsername());
				preparedStatement.setInt(2, group.getGroupID());
				preparedStatement.executeUpdate();
			} finally {
				closeConn();
			}
			groups.remove(group); 
		} else {
			throw new IllegalArgumentException("Ikke medlem i gruppe");  
		}
	}

	public void setAllSeen() throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("update Invited set Seen=1 where Reciever=?");
			preparedStatement.setString(1, getUsername());
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}
	}
	private boolean isValidName(String name){
		int index = 0; 
		String lc = name.toLowerCase(); 
		for (int i = 0; i < lc.length(); i++) {
			char c = lc.charAt(i);
			if( c >= 'a' && c<= 'z' || c =='æ' || c == 'ø' || c == 'å'){
				index++; 
			}else return false; 
		}
		if( index < 3){
			return false; 
		}
		return true; 

	}
	private boolean isValidUsername(String name){
		int index = 0; 
		String lc = name.toLowerCase(); 
		for (int i = 0; i < lc.length(); ++i){
			char c = lc.charAt(i); 
			if( c >= 'a' && c<= 'z' || c =='æ' || c == 'ø' || c == 'å' || c >= 0 && c <=9){
				index++; 

			}else return false; 
		}
		if( index < 3){
			return false; 
		}
		return true; 

	}

	private boolean isValidMail(String mail){
		if (mail == null ){
			return false; 
		}
		if (mail.indexOf("@") < 0){
			return false; 
		}
		if (mail.indexOf(".") < 0){
			return false; 
		}
		if (endOfMail(mail) == false){
			return false; 
		}
		if (AtAmount(mail) == false){
			return false; 			
		}
		return true; 

	}
	//Hjelpmetode for å sjekke at det kun er en alfakrøll i en mailadresse
	private boolean AtAmount(String mail){
		int teller = 0; 
		for(int j = 0;j < mail.length(); ++j){
			if(mail.charAt(j) == '@'){
				teller++; 
			}
		}
		if(teller > 1){
			return false; 
		}
		return true; 
	}
	//Hjelpemetode for å sjekke at slutten på mailadressen er innafor	
	private boolean endOfMail(String mail){
		StringTokenizer st = new StringTokenizer(mail,".");
		String lastToken = null;
		while ( st.hasMoreTokens() )
		{
			lastToken = st.nextToken();
		}

		if ( lastToken.length() >= 2 )
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	public static void main(String[] args) throws Exception {
		User s = new User("Petter", "Olsen", "perrols", "22334455", "mail@mail.com"); 
		System.out.println(s.getFirstname()); 
		System.out.println(s.getUsername()); 
	}
}

