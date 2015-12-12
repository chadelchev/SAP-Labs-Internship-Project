package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


/**
 * Servlet implementation class LogInServlet
 */
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static UserDAO usrDAO;
	public static TourDAO tourDAO;
	public static ReservationDAO reservationDAO;
	DataSource ds = null;
	Connection connection = null;
	@Override
	public void init() throws ServletException {
		try {
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx
					.lookup("java:comp/env/jdbc/DefaultDB");			
		} catch (NamingException e) {
			throw new ServletException(e);
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			connection = ds.getConnection();
			String eMail = request.getParameter("eMail");
			String password = request.getParameter("password");
			
			if(usrDAO.existsUser(eMail)){
				User usr = usrDAO.selectUser(eMail);
						
				if(usrDAO.isPasswordCorrect(eMail, password)){
					
					ArrayList<User> registeredUsersList = usrDAO.getAllRecords(connection);
					request.getSession(true).setAttribute("registeredUsersList", registeredUsersList);
					
					request.getSession(true).setAttribute("user", usr);
					request.getRequestDispatcher("index.jsp").forward(request,
							response);
					
					ArrayList<Reservation> userReservationsList = reservationDAO.getUserReservations(usr);
					
					request.getSession(true).setAttribute("userReservationsList", userReservationsList);
					
			
				}else{
					String message = "Грешно потребителско име или парола";
					request.getSession(true).setAttribute("wrong", message);
					request.getServletContext().getRequestDispatcher("/wronguser.jsp").forward(request, response);
				}
			}else{
				String message = "Няма такъв потребител";
				request.getSession(true).setAttribute("no", message);
				request.getServletContext().getRequestDispatcher("/nosuchuser.jsp").forward(request, response);
			}
			
		
		} catch (SQLException e) {
			String message = "Все още няма създадена таблица USER, необходима е първа регистрация";
			request.getSession(true).setAttribute("table", message);
			request.getServletContext().getRequestDispatcher("/table.jsp").forward(request, response);
			e.printStackTrace();
		}finally{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
