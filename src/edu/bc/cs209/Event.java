package edu.bc.cs209;

import java.util.Scanner;

public class Event {

	private int id;
	private String type; // TODO define an enum
	private String date; // TODO should be a java.util.Date, not a String
	private String address;
	private String sector; // TODO define an enum
	private String zone; // TODO define an enum
	
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("Type: " + type);

		try (Scanner dateScanner = new Scanner(date)) {
			String date = dateScanner.next();
			String time = dateScanner.next();
			sb.append("\nDate: " + date);
			sb.append("\nTime: " + time);
		}

		sb.append("\nAddress: " + address);
		sb.append("\nSector: " + sector);
		sb.append("\nZone: " + zone);
		sb.append("\n");
		return sb.toString();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}

}
