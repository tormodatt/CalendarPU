package user;

public class test {
	
	package user;

	import java.util.ArrayList;

	import Appointment.Room;
	import calendar.Database;

	import java.sql.*;

	import user.User;


		
		private String name; 
		private ArrayList<User> members; 
		private User leader;
		private Group mainGroup; 
		private int groupID;
		private ArrayList<Group> subGroups; 
		
		private PreparedStatement preparedStatement = null;
		private ResultSet resultSet = null;
		
		
		
		public Group(String name, User leader) throws Exception {
			try {
				openConn();
				preparedStatement = connect.prepareStatement("insert into Group values (default,?,?)");
				preparedStatement.setString(1,name);
				preparedStatement.setString(2,leader.getUsername());
				} finally {
					closeConn();
				}
				this.groupID = getDBGroupID();
				this.name = name; 
				this.leader = leader;
		}
		
		public Group(int groupID) throws Exception {
			if (groupIDExists(groupID)) {
				try { 
					openConn(); 
					preparedStatement = connect.prepareStatement("select * from Group WHERE GroupID = ?"); 
					preparedStatement.setInt(1,groupID);
					resultSet = preparedStatement.executeQuery(); 
					while (resultSet.next()) {
						this.groupID = resultSet.getInt("GroupID"); 
						this.name = resultSet.getString("Name"); 

					}
					preparedStatement = connect.prepareStatement("select Name from Group WHERE groupID=?");
					preparedStatement.setInt(1,groupID);
					resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						members.add(new User(resultSet.getString("Owner")));
					}
				} finally {
					closeConn();
				}
				this.groupID = groupID;
			}
			else System.out.println("Gruppen eksisterer ikke!");
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
		
		
		public Group getMainGroups() {
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

		
	}


}
