package test;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.ecm.api.EcmService;
import com.sap.ecm.api.RepositoryOptions;
import com.sap.ecm.api.RepositoryOptions.Visibility;
import com.tu.sofia.summerpractice.Image;

/**
 * Servlet implementation class StartupServlet
 */
public class StartupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger logger = LoggerFactory.getLogger(StartupServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StartupServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx
					.lookup("java:comp/env/jdbc/DefaultDB");
			Session documentServiceSession = prepareDocumentServiceForUsage(ctx);
			AddTourServlet.openCmisSession = documentServiceSession;
			Image.openCmisSession = documentServiceSession;
			
			UserDAO usrDAO = new UserDAO(ds);
			usrDAO.checkUsersTable();
			TourDAO tourDAO = new TourDAO(ds);
			tourDAO.checkToursTable();
			ReservationDAO reservationDAO = new ReservationDAO(ds);
			reservationDAO.checkReservationsTable();

			RegisterServlet.tourDAO = tourDAO;
			AddTourServlet.tourDAO = tourDAO;
			LogInServlet.tourDAO = tourDAO;
			ReservationServlet.tourDAO = tourDAO;
			CancelationServlet.tourDAO = tourDAO;

			RegisterServlet.usrDAO = usrDAO;
			LogInServlet.usrDAO = usrDAO;
			ReservationServlet.usrDAO = usrDAO;
			CancelationServlet.usrDAO = usrDAO;

			ReservationServlet.reservationDAO = reservationDAO;
			LogInServlet.reservationDAO = reservationDAO;
			CancelationServlet.reservationDAO = reservationDAO;

		} catch (Exception e) {
			logger.error("Seriozen problem!!!", e);
		}
	}

	private Session prepareDocumentServiceForUsage(InitialContext ctx)
			throws NamingException {

		String uniqueName = "com.foo.MyRepository";
		// Use a secret key only known to your application (min. 10 chars)
		String secretKey = "my_super_secret_key_123";
		String lookupName = "java:comp/env/" + "EcmService";
		Session openCmisSession = null;
		EcmService ecmSvc;
		ecmSvc = (EcmService) ctx.lookup(lookupName);
		try {
			openCmisSession = ecmSvc.connect(uniqueName, secretKey);
		} catch (CmisObjectNotFoundException e) {
			RepositoryOptions options = new RepositoryOptions();
			options.setUniqueName(uniqueName);
			options.setRepositoryKey(secretKey);
			options.setVisibility(Visibility.PROTECTED);
			ecmSvc.createRepository(options);
			openCmisSession = ecmSvc.connect(uniqueName, secretKey);
		}
		return openCmisSession;
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
