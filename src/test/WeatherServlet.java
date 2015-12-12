package test;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.sap.core.connectivity.api.DestinationException;
import com.sap.core.connectivity.api.http.HttpDestination;

/**
 * Servlet implementation class WeatherServlet
 */
public class WeatherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WeatherServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		HttpDestination destination;
//		String city = request.getParameter("city");
//		response.setContentType("text/html");
//		response.setCharacterEncoding("UTF-8");
//
//		try {
//
//			// access the HttpDestination for the resource "weather" specified
//			// in the web.xml
//			Context ctx = new InitialContext();
//			destination = (HttpDestination) ctx.lookup("java:comp/env/weather");
//		} catch (NamingException e) {
//			throw new RuntimeException(e);
//		}
//		HttpClient client;
//		try {
//			client = destination.createHttpClient();
//		} catch (DestinationException e) {
//			throw new RuntimeException(e);
//		}
//
//		try {
//			// Create get request
//			if (city == null || city.length() == 0) {
//				response.getWriter().println(
//						"<div>Информация за времето не е налична<br/></div>");
//				return;
//			}
//			HttpGet get = new HttpGet("?city=" + city);
//
//			// Execute the request
//			HttpResponse resp = client.execute(get);
//
//			// Process the response
//			HttpEntity entity = resp.getEntity();
//			String respToString = EntityUtils.toString(entity);
//
//			String formatterResponse = convert(respToString);
//
//			response.getWriter().print(formatterResponse);
//		} catch (Exception e) {
//			response.getWriter().println(
//					"<div>Информация за времето не е налична. Моля проверете дали сте написали града на латиница.<br/></div>");
//		} finally {
//			ClientConnectionManager connectionManager = client
//					.getConnectionManager();
//			if (connectionManager != null) {
//				connectionManager.shutdown();
//			}
//		}
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	}
	

	private static String fahrenheitToCelsius(int fahrenheit) {
		DecimalFormat d = new DecimalFormat("00.00 C");
		double celsius = (fahrenheit - 32) / 1.8;
		return d.format(celsius);
	}
	
	public static String getForecast(String city) {
		HttpDestination destination;

		try {
			// access the HttpDestination for the resource "weather" specified
			// in the web.xml
			Context ctx = new InitialContext();
			destination = (HttpDestination) ctx.lookup("java:comp/env/weather");
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
		HttpClient client;
		try {
			client = destination.createHttpClient();
		} catch (DestinationException e) {
			throw new RuntimeException(e);
		}

		try {
			// Create get request
			if (city == null || city.length() == 0) {
				return	"<div>Информация за времето не е налична<br/></div>";
				
			}
			HttpGet get = new HttpGet("?city=" + URLEncoder.encode(city, "UTF-8"));

			// Execute the request
			HttpResponse resp = client.execute(get);

			// Process the response
			HttpEntity entity = resp.getEntity();
			String respToString = EntityUtils.toString(entity);

			String formatterResponse = convert(respToString);

			return formatterResponse;
		} catch (Exception e) {
			return "<div>Информация за времето не е налична.<br/></div>";
		} finally {
			ClientConnectionManager connectionManager = client
					.getConnectionManager();
			if (connectionManager != null) {
				connectionManager.shutdown();
			}
		}
	}
	
	private static String convert(String respToString) {
		Gson gson = new Gson();
		ForecastResponse forecast = gson.fromJson(respToString,
				ForecastResponse.class);
		String formattedResponse = "<div>Температура: <b>"
				+ fahrenheitToCelsius(forecast.getData().getTemperature())
				+ "</b><br/>";
		formattedResponse += "Влажност: <b>" + forecast.getData().getHumidity()
				+ "&nbsp;%</b><br/>";
		formattedResponse += "Скорост на вятъра: <b>"
				+ forecast.getData().getWind() + "&nbsp;км/ч </b/><br/>";
		formattedResponse += "</div>";
		return formattedResponse;
	}

}