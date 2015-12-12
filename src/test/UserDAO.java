package test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {
	public static final String USERS_TABLE = "USER1";
	private static Logger logger = LoggerFactory.getLogger(UserDAO.class);

	private DataSource dataSource;

	public UserDAO(DataSource newDataSource) throws SQLException {
		setDataSource(newDataSource);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource newDataSource) throws SQLException {
		this.dataSource = newDataSource;
	}

	public void checkUsersTable() throws SQLException {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			if (!existsUsersTable(connection)) {
				// dropUsersTable();
				createUsersTable(connection);
			}
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	private boolean existsUsersTable(Connection connection) throws SQLException {
		// DatabaseMetaData meta = connection.getMetaData();
		// ResultSet rs = meta.getTables(null, null, USERS_TABLE, null);
		// logger.error("v existsUsersTable");
		// while (rs.next()) {
		// String name = rs.getString("TABLE_NAME");
		// logger.error("name: "+name);
		// if (name.equals(USERS_TABLE)) {
		// return true;
		// }
		// }
		try {
			getAllRecords(connection);
			logger.error("table exists");
			return true;
		} catch (Exception e) {
			logger.error("table does not exist");	
			return false;
		}
	}

	public static void createUsersTable(Connection connection)
			throws SQLException {
		String sql = "CREATE TABLE " + USERS_TABLE
				+ "(EMAIL VARCHAR(255) PRIMARY KEY, "
				+ "FIRSTNAME VARCHAR(255) NOT NULL, "
				+ "LASTNAME VARCHAR(255) NOT NULL, "
				+ "PASSWORD VARCHAR(255) NOT NULL, "
				+ "PHONE VARCHAR(255) NOT NULL, "
				+ "LOCATION VARCHAR(255)NOT NULL, "
				+ "ROLE VARCHAR(15) NOT NULL)";

		PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.executeUpdate();
				User usr = new User("admin@Smarty.com", "Martin", "Teliyski", "admin", "admin","admin","admin");
				UserDAO.addUser(connection, usr);
		logger.error("Uspeshno syzdavane na tablica");
	}

	public static void dropUsersTable(Connection connection)
			throws SQLException {
		String sql = "DROP TABLE " + USERS_TABLE;

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.executeUpdate();
	}

	public boolean existsUser(User usr) throws SQLException {

		boolean result = existsUser(usr.geteMail());
		return result;

	}

	public boolean existsUser(String eMail) throws SQLException {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			String sql = "SELECT * FROM " + USERS_TABLE + " WHERE EMAIL='"+ eMail + "'";
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public boolean isPasswordCorrect(String eMail, String password)
			throws SQLException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			User usr = selectUser(eMail);
			if (null != usr && usr.getPassword().equals(password)) {
				return true;
			}
			return false;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
	public static void addUser(Connection connection, User usr) throws SQLException {
		String sql = "INSERT INTO " + USERS_TABLE
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, usr.geteMail());
		stmt.setString(2, usr.getFirstName());
		stmt.setString(3, usr.getLastName());
		stmt.setString(4, usr.getPhone());
		stmt.setString(5, usr.getLocation());
		stmt.setString(6, usr.getPassword());
		stmt.setString(7, usr.getRole());

		stmt.execute();
		logger.error("Uspeshno dobavqne na user" + usr.geteMail());
}

public User selectUser(String eMail) throws SQLException {

	Connection connection = null;

	try {
		connection = dataSource.getConnection();

		String sql = "SELECT * FROM " + USERS_TABLE + " WHERE EMAIL='"
				+ eMail + "'";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			User usr = new User(rs.getString(1), rs.getString(2),
					rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),rs.getString(7));
			return usr;
		} else {
			return null;
		}
	} finally {
		if (connection != null) {
			connection.close();
		}
	}
}

public ArrayList<User> getAllRecords(Connection connection)
		throws SQLException {

	String sql = "SELECT * FROM " + USERS_TABLE;
	PreparedStatement stmt = connection.prepareStatement(sql);
	ResultSet rs = stmt.executeQuery();

	ArrayList<User> list = new ArrayList<User>();
	while (rs.next()) {
		User us = new User();
		us.seteMail(rs.getString(1));
		us.setFirstName(rs.getString(2));
		us.setLastName(rs.getString(3));
		us.setPhone(rs.getString(4));
		us.setLocation(rs.getString(5));
		us.setPassword(rs.getString(6));
		us.setRole(rs.getString(7));
		list.add(us);
	}
	return list;
}
}
