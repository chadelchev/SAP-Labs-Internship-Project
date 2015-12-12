package com.tu.sofia.summerpractice;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Image extends HttpServlet {

	private Logger logger = LoggerFactory.getLogger(Image.class);
	private static final long serialVersionUID = 1L;
	public static Session openCmisSession = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Image() {
		super();
	}

	/**
	 * @see Servlet#destroy()
	 */
	// TODO: should close any sessions!
	public void destroy() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String fileName = request.getParameter("picture");
			if (fileName == null || fileName.length() == 0) {
				return;
			}
			Folder root = openCmisSession.getRootFolder();
			ItemIterable<CmisObject> children = root.getChildren();
			for (CmisObject o : children) {
				if (fileName.equalsIgnoreCase(o.getName())) {
					sendPictureToClient(response, o);
					return;
				}
			}
			// TODO this should not be really an error condition, so may be
			// logged with severity of warning.
			logger.error("File " + fileName
					+ "] does not exist in the document repository.");
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				response.flushBuffer();
			} catch (Exception e) {

			}
		}
	}

	private void sendPictureToClient(HttpServletResponse response, CmisObject o)
			throws IOException {
		Document doc = (Document) o;
		InputStream imageStream = doc.getContentStream().getStream();
		response.setContentLength((int) doc.getContentStreamLength());
		IOUtils.copy(imageStream, response.getOutputStream());
	}
}