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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static UserDAO usrDAO;
	public static TourDAO tourDAO;

	private Logger logger = LoggerFactory.getLogger(RegisterServlet.class);

	private DataSource ds = null;
	
	@Override
	public void init() throws ServletException {
		try {
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx
					.lookup("java:comp/env/jdbc/DefaultDB");
		} catch (Exception e) {
			logger.error("Exception in init", e);
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
		Connection connection = null;
		//TODO fix it to be send from the client!
		request.setCharacterEncoding("UTF-8");
		
		try {
			connection = ds.getConnection();
			String eMail = request.getParameter("eMail");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String phone = request.getParameter("phone");
			String location = request.getParameter("location");
			String password = request.getParameter("password");
			String repeatPassword = request.getParameter("secondPassword");
						String role = "user";
			
			if(InputValidation.validateEmail(eMail)){
				if(InputValidation.checkPasswordSpelling(password, repeatPassword)){
					if(!firstName.isEmpty() && !lastName.isEmpty()){
					
						
						User usr = new User(eMail, firstName, lastName,phone,location, password, role);
						
						if(!usrDAO.existsUser(usr)){
							UserDAO.addUser(connection, usr);
							ArrayList<User> registeredUsersList = usrDAO.getAllRecords(connection);
							request.getSession(true).setAttribute("registeredUsersList", registeredUsersList);
							request.getServletContext().getRequestDispatcher("/regse.jsp").forward(request, response);
						}
						else{
							String message = "Такъв потребител вече съществува";
							request.getSession(true).setAttribute("busy", message);
							request.getServletContext().getRequestDispatcher("/userexist.jsp").forward(request, response);
						}
							
					}else{
						String message = "Има непопълнени полета ";
						request.getSession(true).setAttribute("empty", message);
						request.getServletContext().getRequestDispatcher("/emptyBoxes.jsp").forward(request, response);response.getWriter().println("There are empty textboxes.");
					}
				}else{
					String message = "Въведената парола не съвпада";
					request.getSession(true).setAttribute("passwordMiss", message);
					request.getServletContext().getRequestDispatcher("/passwordMiss.jsp").forward(request, response);
				}				
			}else{
				String message = "Въвели сте невалиден е-Мейл";
				request.getSession(true).setAttribute("wrongMail", message);
				request.getServletContext().getRequestDispatcher("/wrongMail.jsp").forward(request, response);
			}
			
			
			
		} catch (SQLException e) {
			response.getWriter().println(e);
			e.printStackTrace(response.getWriter());
		}finally{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


}
