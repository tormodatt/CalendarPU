package user;

import java.util.ArrayList;

import calendar.Calendar;
import calendar.Database;

import java.sql.*;

import user.User;


public class Group extends Database{
	
	private String name; 
	private ArrayList<User> members; 
	private User leader;
	private Group mainGroup; 
	private int groupID;
	private ArrayList<Group> subGroups; 
	private Calendar calendar; 
	
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public Group(int groupID) throws Exception {
		if (groupIDExists(groupID)) {
			try {
				openConn();
				preparedStatement = connect.prepareStatement("select * from Group where GroupID=?");
				preparedStatement.setInt(1,groupID);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					this.name = resultSet.getString("Name");
					this.leader = new User(resultSet.getString("Leader"));
				}
				preparedStatement = connect.prepareStatement("select * from Group_relation where Sub_group=?");
				preparedStatement.setInt(1,groupID);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					this.mainGroup = new Group(resultSet.getInt("Super_group"));
				}
				preparedStatement = connect.prepareStatement("select * from Group_relation where Super_group=?");
				preparedStatement.setInt(1,groupID);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					this.subGroups.add(new Group(resultSet.getInt("Sub_group")));
				}
			} finally {
				closeConn();
			}
			this.calendar = new Calendar(this, name + " calendar"); //Lager evt. en metode senere, setCalendar(), tar inn navn fra bruker
		} else {
			 System.out.println("Group does not exist!");
		}
		
		
	}
	
	public Group(String name, User leader) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Group values (default,?,?)");
			preparedStatement.setString(1,name);
			preparedStatement.setString(2,leader.getUsername());
			resultSet = preparedStatement.executeQuery();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				this.groupID = resultSet.getInt("GroupID");
			} 
		} finally {
				closeConn();
			}
			this.groupID = getDBGroupID();
			this.name = name; 
			this.leader = leader;
			this.calendar = new Calendar(this, name + " calendar"); //Lager evt. en metode senere, setCalendar(), tar inn navn fra bruker
	}
	
	
	private boolean groupIDExists(int groupID) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select GroupID from Group WHERE groupID=?");
			preparedStatement.setInt(1,groupID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return true;
			}
		} finally {
			closeConn();
		}
		return false;
	}

	private int getDBGroupID() throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("select GroupID from Group WHERE GroupID=?");
			preparedStatement.setInt(1,groupID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return resultSet.getInt("GroupID");
			}
		} finally {
			closeConn();
		}
		return groupID;
	}
	
	public int getGroupID() {
		return groupID;
	}

	public void setName(String name) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Group (Name) values (?)");
			preparedStatement.setString(1,name);
			preparedStatement.executeUpdate();
			} finally {
			closeConn();
			}
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setLeader(User leader) throws Exception{
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Group (Leader) values (?)");
			preparedStatement.setString(1,leader.getUsername());
			preparedStatement.executeUpdate();
			} finally {
			closeConn();
			}
		this.leader = leader;
	}
	
	public User getLeader() {
		return this.leader; 
	}
	
	public void addMember(User user) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Group_members (Username) values (?)");
			preparedStatement.setString(1,user.getUsername());
			preparedStatement.executeUpdate();
		} finally {
			closeConn();	
		}
		this.members.add(user);
	}
	
	
	public void removeMember(User user) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("delete from Group_members (Username) values (?)");
			preparedStatement.setString(1,user.getUsername());
			preparedStatement.executeUpdate();
		} finally {
			closeConn();	
		}
		this.members.remove(user);
	}
	
	public ArrayList<User> getMembers() {
		return this.members;
	}

	
	public void setMainGroup(Group group) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("UPDATE Group_relation SET Super_group = ? where GroupID= ?");
			preparedStatement.setInt(1,group.getGroupID());
			preparedStatement.setInt(2,this.groupID);
			preparedStatement.executeUpdate();
		} finally {
			closeConn();
		}		
		this.mainGroup = group;
	}
	
	
	public Group getMainGroup() {
		return mainGroup;
	}


	public void addSubGroup(Group group) throws Exception {
		try {
			openConn();
			preparedStatement = connect.prepareStatement("insert into Group_relation (Sub_group) values (?)");
			preparedStatement.setInt(1,group.getGroupID());
			preparedStatement.executeUpdate();
		} finally {
			closeConn();	
		}
		this.subGroups.add(group);
	}
	
	public ArrayList<Group> getSubGroups() { 
		return this.subGroups;
	}
	
	
	public Calendar getCalendar() {
		return this.calendar; 
	}
	
	
}
