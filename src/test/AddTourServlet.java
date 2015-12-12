package test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisNameConstraintViolationException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.ecm.api.EcmService;
import com.sap.ecm.api.RepositoryOptions;
import com.sap.ecm.api.RepositoryOptions.Visibility;

/**
 * Servlet implementation class AddTourServlet
 */

@MultipartConfig(location = "")
public class AddTourServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static UserDAO usrDAO;
	public static TourDAO tourDAO;
	public static Session openCmisSession;

	DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

	private Logger logger = LoggerFactory.getLogger(RegisterServlet.class);

	private DataSource ds = null;
	Connection connection = null;

	@Override
	public void init() throws ServletException {
		try {
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
		} catch (Exception e) {
			logger.error("Exception in init", e);
		}
	}

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

		String destination = request.getParameter("destination");
		String description = request.getParameter("description");
		String priceStr = request.getParameter("price");
		String capacityStr = request.getParameter("capacity");
		double price = 0;
		short capacity = 0;

		String startDateDay = request.getParameter("startDateDay");
		String startDateMonth = request.getParameter("startDateMonth");
		String startDateYear = request.getParameter("startDateYear");

		Part filePart = request.getPart("picture");
		String fileName = getFileName(filePart);

		Date startDate = null;

		if (InputValidation.isValidDay(startDateDay)
				&& InputValidation.isValidMonth(startDateMonth)
				&& InputValidation.isValidYear(startDateYear)) {
			logger.error("Validna pyrva data.");
			String startDateStr = startDateDay + "-" + startDateMonth + "-"
					+ startDateYear;
			try {
				startDate = formatter.parse(startDateStr);
			} catch (ParseException e) {
				logger.error("Exception pri parse na data.");
				e.printStackTrace();
			}
		} else {
			logger.error("Nevalidna data.");
		}

		String endDateDay = request.getParameter("endDateDay");
		String endDateMonth = request.getParameter("endDateMonth");
		String endDateYear = request.getParameter("endDateYear");

		Date endDate = null;

		if (InputValidation.isValidDay(endDateDay)
				&& InputValidation.isValidMonth(endDateMonth)
				&& InputValidation.isValidYear(endDateYear)) {
			logger.error("Validna vtora data.");
			String endDateStr = endDateDay + "-" + endDateMonth + "-"
					+ endDateYear;
			try {
				endDate = formatter.parse(endDateStr);
			} catch (ParseException e) {
				logger.error("Exception pri parse na data.");
				e.printStackTrace();
			}
		} else {
			logger.error("Nevalidna data.");
		}

		if (!destination.isEmpty() && !description.isEmpty()
				&& !priceStr.isEmpty() && !capacityStr.isEmpty()) {
			logger.error("Nqma prazni poleta");
			if (InputValidation.isDouble(priceStr)) {
				logger.error("Nevalidna cena");
				if (InputValidation.isNumber(capacityStr)) {
					logger.error("Nevaliden limit.");
					price = Double.parseDouble(priceStr);
					capacity = Short.parseShort(capacityStr);

					Tour tour = new Tour(destination, description, price,
							startDate, endDate, capacity, fileName);

					logger.error("Checking if tour exists.");

					try {
						connection = ds.getConnection();
						if (!tourDAO.existsTour(tour)) {
							logger.error("Tour does not exist.");
							logger.error("Trying to add tour.");
							tourDAO.addTour(tour);
							saveImage(filePart, fileName);
							logger.error("Tour added, trying to get all records.");

							ArrayList<Tour> toursList = tourDAO
									.getAllAvailableTours();
							request.getSession(true).setAttribute("toursList",
									toursList);

							request.getServletContext()
									.getRequestDispatcher("/destinations.jsp")
									.forward(request, response);
						}
					} catch (SQLException e) {
						response.getWriter().println(e);
						e.printStackTrace();
					} finally {
						if (connection != null) {
							try {
								connection.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

				} else {
					// invalid capacity
					response.getWriter().println("invalid capacity");
				}
			} else {
				// Invalid Money
				response.getWriter().println("Invalid Money");
			}
		} else {
			response.getWriter().println("There are empty textboxes");
		}

	}

	private String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}

	private void saveImage(Part filePart, String fileName) throws IOException,
			ServletException {
		try {
			Folder root = openCmisSession.getRootFolder();
			// create a new folder
			Map<String, String> newFolderProps = new HashMap<String, String>();
			newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
			newFolderProps.put(PropertyIds.NAME, "SapHANANeo");
			try {
				root.createFolder(newFolderProps);
			} catch (CmisNameConstraintViolationException e) {
			}

			// create a new file in the root folder
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
			properties.put(PropertyIds.NAME, fileName);

			InputStream stream = filePart.getInputStream();
			// TODO Check filePart.getSize() is it right one??!!
			ContentStream contentStream = openCmisSession.getObjectFactory()
					.createContentStream(fileName, filePart.getSize(),
							"image/jpeg", stream);
			try {
				root.createDocument(properties, contentStream,
						VersioningState.NONE);
			} catch (CmisNameConstraintViolationException e) {
				// Document exists already, nothing to do
			}
			logger.debug("File [" + fileName
					+ "] saved into document repository.");

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
