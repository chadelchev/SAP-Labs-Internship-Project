package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TourDAO {
	public static final String TOURS_TABLE = "TOURS";
	private static Logger logger = LoggerFactory.getLogger(TourDAO.class);

	private DataSource dataSource;

	public TourDAO(DataSource newDataSource) throws SQLException {
		setDataSource(newDataSource);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource newDataSource) throws SQLException {
		this.dataSource = newDataSource;
	}

	public void checkToursTable() throws SQLException {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			if (!existsToursTable()) {
				createToursTable(connection);
			}
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	private boolean existsToursTable() throws SQLException {

		try {
			getAllAvailableTours();
			logger.error("TOURS table exists.");
			return true;
		} catch (Exception e) {
			logger.error("TOURS table does not exist");
			return false;
		}
	}

	private void createToursTable(Connection connection) throws SQLException {

		String sql = "CREATE TABLE " + TOURS_TABLE
				+ "(TOURID VARCHAR(255) PRIMARY KEY, "
				+ "DESTINATION VARCHAR(255) NOT NULL, "
				+ "DESCRIPTION VARCHAR(255) NOT NULL, "
				+ "PRICE DOUBLE NOT NULL," + "STARTDATE DATE NOT NULL, "
				+ "ENDDATE DATE NOT NULL, " + "CAPACITY SMALLINT NOT NULL, "
				+ "RATING DOUBLE NOT NULL, " + "AVAILABILITY INT NOT NULL, "
				+ "PICTURE VARCHAR(255))";

		logger.error("Trying to create TOURS table.");
		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.executeUpdate();
		logger.error("Uspeshno syzdavane na TOURS tablica");

	}

	public static void dropToursTable(Connection connection)
			throws SQLException {
		String sql = "DROP TABLE " + TOURS_TABLE;

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.executeUpdate();
	}

	public boolean existsTour(Tour tour) throws SQLException {

		boolean result = existsTour(tour.getTourID());
		return result;

	}

	public boolean existsTour(String tourID) throws SQLException {

		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			String sql = "SELECT * FROM " + TOURS_TABLE + " WHERE TOURID='"
					+ tourID + "'";
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

	public void addTour(Tour tour) throws SQLException {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			String sql = "INSERT INTO " + TOURS_TABLE
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, tour.getTourID());
			stmt.setString(2, tour.getDestination());
			stmt.setString(3, tour.getDescription());
			stmt.setDouble(4, tour.getPrice());
			stmt.setDate(5, new java.sql.Date(tour.getStartDate().getTime()));
			stmt.setDate(6, new java.sql.Date(tour.getEndDate().getTime()));
			stmt.setShort(7, tour.getCapacity());
			stmt.setDouble(8, tour.getRating());
			stmt.setInt(9, tour.getAvailability());
			stmt.setString(10, tour.getPicture());

			stmt.execute();
			logger.error("Uspeshno dobavqne na Tour" + tour.getTourID() + ", "
					+ tour.getDestination() + "picture " + tour.getPicture());
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public Tour selectTour(String tourID) throws SQLException {

		Connection connection = null;

		try {
			connection = dataSource.getConnection();

			String sql = "SELECT * FROM " + TOURS_TABLE + " WHERE TOURID='"
					+ tourID + "'";
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Tour tour = new Tour(rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getDouble(4), rs.getDate(5),
						rs.getDate(6), rs.getShort(7), rs.getDouble(8),
						rs.getInt(9), rs.getString(10));
				return tour;
			} else {
				return null;
			}
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public void cancelTour(String tourID)
			throws SQLException {
		Connection connection = null;
		try{
			connection = dataSource.getConnection();
			String sql = "UPDATE " + TOURS_TABLE
				+ " SET AVAILABILITY=0 WHERE TOURID='" + tourID
				+ "'";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();
		}finally{
			
		}
	}
	
	public void decrementCapacity(String tourID)
			throws SQLException {
		Connection connection = null;
		logger.error("Atempting to decrement capacity.");
		try{
			connection = dataSource.getConnection();
			Tour tour = selectTour(tourID);
			logger.error("Tour object created.ID= "+tourID);
			int capacity = tour.getCapacity();
			capacity = capacity-1;
			logger.error("New capacity variable = "+ capacity);
			
			if(capacity>0){
				String sql = "UPDATE "+TOURS_TABLE+" SET CAPACITY = "+capacity+" WHERE TOURID='"+tourID+"'";
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.executeUpdate();
				logger.error("Query successful.");
			}else{
				this.cancelTour(tourID);
			}
			
		}finally{
			if (connection !=null && !connection.isClosed()) {
				connection.close();
			}
		}


	}

	public ArrayList<Tour> getAllAvailableTours() throws SQLException {
		Connection connection = null;
		ArrayList<Tour> list = new ArrayList<Tour>();
		try {
			connection = dataSource.getConnection();
			String sql = "SELECT * FROM " + TOURS_TABLE
					+ " WHERE AVAILABILITY=1";
			logger.error("Trying to select all tours.");
			
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			logger.error("All tours selected.");
			while (rs.next()) {
				Tour tour = new Tour();
				tour.setTourId(rs.getString(1));
				tour.setDestination(rs.getString(2));
				tour.setDescription(rs.getString(3));
				tour.setPrice(rs.getDouble(4));
				tour.setStartDate(rs.getDate(5));
				tour.setEndDate(rs.getDate(6));
				tour.setCapacity(rs.getShort(7));
				tour.setRating(rs.getDouble(8));
				tour.setAvailability(rs.getInt(9));
				tour.setPicture(rs.getString(10));

				list.add(tour);
			}
		} finally {
			if (connection !=null && !connection.isClosed()) {
				connection.close();
			}
		}
		return list;
	}

}
