package test;

import java.util.UUID;

public class Reservation {

	private String reservationID;
	private String eMail;
	private String tourID;
	private int validity;
	
	public Reservation(){
		this.reservationID = null;
		this.eMail = null;
		this.tourID = null;
		this.validity = 1;
	}
	
	public Reservation(String eMail, String tourID, int validity){
		this.reservationID = UUID.randomUUID().toString();
		this.eMail = eMail;
		this.tourID = tourID;
		this.validity = validity;
	}
	
	public Reservation(String reservationID, String eMail, String tourID, int validity){
		this.reservationID = reservationID;
		this.eMail = eMail;
		this.tourID = tourID;
		this.validity = validity;
	}
	
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getReservationID() {
		return reservationID;
	}
	public void setReservationID(String reservationID) {
		this.reservationID = reservationID;
	}
	public String getTourID() {
		return tourID;
	}
	public void setTourID(String tourID) {
		this.tourID = tourID;
	}
	public int getValidity() {
		return validity;
	}
	public void setValidity(int validity) {
		this.validity = validity;
	}
	public String toString(){
		return "ReservationID: "+this.getReservationID()+"; Reservation validity: "+this.getValidity();
	}
}
