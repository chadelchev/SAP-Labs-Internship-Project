package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReservationDAO {
	public static final String RESERVATIONS_TABLE = "RESERVATIONS";
	private static Logger logger = LoggerFactory
			.getLogger(ReservationDAO.class);

	private DataSource dataSource;

	public ReservationDAO(DataSource newDataSource) throws SQLException {
		setDataSource(newDataSource);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource newDataSource) throws SQLException {
		this.dataSource = newDataSource;
	}

	public void checkReservationsTable() throws SQLException {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			if (!existsReservationsTable(connection)) {
				createReservationsTable(connection);
			}
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	private void createReservationsTable(Connection connection)
			throws SQLException {
		String sql = "CREATE TABLE " + RESERVATIONS_TABLE
				+ "(RESERVATIONSID VARCHAR(255) PRIMARY KEY, "
				+ "EMAIL VARCHAR(255) NOT NULL, "
				+ "TOURID VARCHAR(255) NOT NULL, " + "VALIDITY INT NOT NULL)";

		logger.error("Trying to create RESERVATIONS table.");
		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.executeUpdate();
		logger.error("Uspeshno syzdavane na RESERVATIONS tablica");

	}

	private boolean existsReservationsTable(Connection connection) {
		try {
			getAllReservations(connection);
			logger.error("RESERVATIOMS table exists.");
			return true;
		} catch (Exception e) {
			logger.error("RESERVATIOMS table does not exist");
			return false;
		}
	}

	public static void dropToursTable(Connection connection)
			throws SQLException {
		String sql = "DROP TABLE " + RESERVATIONS_TABLE;

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.executeUpdate();
	}

	public boolean existsReservation(Reservation reservation)
			throws SQLException {

		boolean result = existsReservation(reservation.getTourID());
		return result;
	}

	public boolean existsReservation(String reservationID) throws SQLException {

		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			String sql = "SELECT * FROM " + RESERVATIONS_TABLE
					+ " WHERE RESERVATIONSID='" + reservationID + "'";
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

	public void addReservation(Reservation reservation) throws SQLException {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			String sql = "INSERT INTO " + RESERVATIONS_TABLE
					+ " VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, reservation.getReservationID());
			stmt.setString(2, reservation.geteMail());
			stmt.setString(3, reservation.getTourID());
			stmt.setInt(4, reservation.getValidity());

			stmt.execute();
			logger.error("Uspeshno dobavqne na Reservation"
					+ reservation.getReservationID());
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
	
	public Reservation selectReservationByTour(String tourID)
			throws SQLException {

		Connection connection = null;

		try {
			connection = dataSource.getConnection();

			String sql = "SELECT * FROM " + RESERVATIONS_TABLE
					+ " WHERE TOURID='" + tourID + "'";
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Reservation reservation = new Reservation(rs.getString(1),
						rs.getString(2), rs.getString(3), rs.getInt(4));
				return reservation;
			} else {
				return null;
			}
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public Reservation selectReservation(String reservationID)
			throws SQLException {

		Connection connection = null;

		try {
			connection = dataSource.getConnection();

			String sql = "SELECT * FROM " + RESERVATIONS_TABLE
					+ " WHERE RESERVATIONSID='" + reservationID + "'";
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Reservation reservation = new Reservation(rs.getString(1),
						rs.getString(2), rs.getString(3), rs.getInt(4));
				return reservation;
			} else {
				return null;
			}
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public void cancelReservation(String reservationID) throws SQLException {
		Connection connection = null;
		try{
			
			logger.error("Trying to cancel reservation with ID: "+reservationID);
			connection = dataSource.getConnection();
			String sql = "UPDATE " + RESERVATIONS_TABLE
				+ " SET VALIDITY=0 WHERE RESERVATIONSID='"
				+ reservationID + "'";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();
			logger.error("Cancelation over.");
			}finally{
				if (connection !=null && !connection.isClosed()) {
					connection.close();
				}
		}

	}

	public ArrayList<Reservation> getAllReservations(Connection connection)
			throws SQLException {

		String sql = "SELECT * FROM " + RESERVATIONS_TABLE+ " WHERE VALIDITY=1";
		logger.error("Trying to select all reservations.");
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		logger.error("All reservations selected.");
		ArrayList<Reservation> list = new ArrayList<Reservation>();
		while (rs.next()) {
			Reservation reservation = new Reservation();
			reservation.setReservationID(rs.getString(1));
			reservation.seteMail(rs.getString(2));
			reservation.setTourID(rs.getString(3));
			reservation.setValidity(rs.getInt(4));

			list.add(reservation);
		}
		return list;
	}

	public ArrayList<Reservation> getUserReservations(User usr)
			throws SQLException {
		Connection connection = null;
		ArrayList<Reservation> list = new ArrayList<Reservation>();
		try{
			connection = dataSource.getConnection();
			String sql = "SELECT * FROM " + RESERVATIONS_TABLE +" WHERE EMAIL='"+usr.geteMail()+"' AND VALIDITY=1";
			logger.error("Trying to select all reservations.");
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			logger.error("All reservations selected.");
			
			while (rs.next()) {
				Reservation reservation = new Reservation();
				reservation.setReservationID(rs.getString(1));
				reservation.seteMail(rs.getString(2));
				reservation.setTourID(rs.getString(3));
				reservation.setValidity(rs.getInt(4));
			
				list.add(reservation);
			}
		}finally{
			if (connection !=null && !connection.isClosed()) {
				connection.close();
			}
		}
		
		return list;
	}

}
