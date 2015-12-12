package test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementing a mail example which shows how to use the connectivity service APIs to send e-mail.
 * The example provides a simple UI to compose an e-mail message and send it. The post method uses
 * the connectivity service and the javax.mail API to send the e-mail.
 */
public class MailServlet extends HttpServlet {

    @Resource(name = "mail/Session") 
    private Session mailSession; 

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MailServlet.class);

    /** {@inheritDoc} */
    @Override
    protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
    	request.setCharacterEncoding("UTF-8");
        Transport transport = null;
        try {
            // Parse form parameters
            String from = request.getParameter("fromaddress");
            String to = request.getParameter("toaddress");
            String subjectText = request.getParameter("subjecttext");
            String mailText = request.getParameter("mailtext");
            mailText+="<br>"+ "Този мейл беше изпратен от: " + from;
            if ( to.isEmpty()) {
                throw new RuntimeException("Form parameters To may not be empty!");
            }

            // Construct message from parameters
            MimeMessage mimeMessage = new MimeMessage(mailSession);
            InternetAddress[] fromAddress = InternetAddress.parse(from);
            InternetAddress[] toAddresses = InternetAddress.parse(to);
            mimeMessage.setFrom(fromAddress[0]);
            mimeMessage.setRecipients(RecipientType.TO, toAddresses);
            mimeMessage.setSubject(subjectText, "UTF-8");
            MimeMultipart multiPart = new MimeMultipart("alternative");
            MimeBodyPart part = new MimeBodyPart();
            part.setText(mailText, "utf-8", "plain");
            multiPart.addBodyPart(part);
            mimeMessage.setContent(multiPart);

            // Send mail
            transport = mailSession.getTransport();
            transport.connect();
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

            // Confirm mail sending
            String message = "Писмото е изпратено успешно. Очаквайте да се свържем с вас.";
			request.getSession(true).setAttribute("mailSend", message);
			request.getServletContext().getRequestDispatcher("/mailSend.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.error("Mail operation failed", e);
            throw new ServletException(e);
        } finally {
            // Close transport layer
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    throw new ServletException(e);
                }
            }
        }
    }
}