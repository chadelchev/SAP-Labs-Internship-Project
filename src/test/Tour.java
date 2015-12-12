package test;

import java.util.Date;
import java.util.UUID;

public class Tour {

	private String tourID;
	private String destination;
	private String description;
	private double price;
	private Date startDate;
	private Date endDate;
	private short capacity;
	private double rating;
	private int availability;
	private String picture;
	
	public Tour(){
		this.tourID = UUID.randomUUID().toString();
		this.destination = null;
		this.description = null;
		this.price = 0;
		this.startDate = null;
		this.endDate = null;
		this.capacity = 0;
		this.rating = 0;
		this.availability = 1;
	}
	
	public Tour(String destination, String description, double price, Date startDate, Date endDate, short capacity, String picture){
		this();
		this.destination = destination;
		this.description = description;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
		this.capacity = capacity;
		this.picture = picture;
	}
	
	public Tour(String destination, String description, double price, Date startDate, Date endDate, short capacity, double rating, int availability, String picture){
		this(description, destination, price, startDate, endDate, capacity, picture);
		this.rating = rating;
		this.availability = availability;
	}
	
	public Tour(String tourID, String destination, String description, double price, Date startDate, Date endDate, short capacity, double rating, int availability, String picture){
		this.tourID = tourID;
		this.destination = destination;
		this.description = description;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
		this.capacity = capacity;
		this.rating = rating;
		this.availability = availability;
		this.picture = picture;
	}
	
	
	public String getTourID() {
		return tourID;
	}
	public void setTourId(String tourID) {
		this.tourID = tourID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public short getCapacity() {
		return capacity;
	}
	public void setCapacity(short capacity) {
		this.capacity = capacity;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public int getAvailability() {
		return availability;
	}
	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
}
