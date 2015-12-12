package test;

import java.io.Serializable;

import com.google.gson.Gson;

public class ForecastResponse implements Serializable {
	@Override
	public String toString() {
		return "ForecastResponse [apiVersion=" + apiVersion + ", forecastData="
				+ data + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String apiVersion = "";
	
	private Data data = null;
	

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data {
		@Override
		public String toString() {
			return "Data [location=" + location + ", temperature="
					+ temperature + ", skytext=" + skytext + ", humidity="
					+ humidity + ", wind=" + wind + ", day=" + day + "]";
		}
		private String location = "";
		private int temperature = 0;
		private String skytext = "";
		private String humidity = "";
		private String wind = "";
		private String day = "";
		
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public int getTemperature() {
			return temperature;
		}
		public void setTemperature(int temperature) {
			this.temperature = temperature;
		}
		public String getSkytext() {
			return skytext;
		}
		public void setSkytext(String skytext) {
			this.skytext = skytext;
		}
		public String getHumidity() {
			return humidity;
		}
		public void setHumidity(String humidity) {
			this.humidity = humidity;
		}
		public String getWind() {
			return wind;
		}
		public void setWind(String wind) {
			this.wind = wind;
		}
		public String getDay() {
			return day;
		}
		public void setDay(String day) {
			this.day = day;
		}

	}
	
	public static void main(String[] args) {
		//Test za parsvane na GSON response-a ot dali-vali
		String json = "{" +
				"\"apiVersion\":\"1.0\", " + 
		   "\"data\":{" + 
			  "\"location\":\"Pernik\"," + 
			  "\"temperature\":\"39\"," + 
			  "\"skytext\":\"Clear\"," + 
			  "\"humidity\":\"55\"," + 
			  "\"wind\":\"0 mph\"," + 
			  "\"date\":\"2012-12-12\"," + 
			  "\"day\":\"Wednesday\"" + 
		   "}}";
		ForecastResponse forecast= new Gson().fromJson(json, ForecastResponse.class);
		System.out.println(forecast);
	}
}
