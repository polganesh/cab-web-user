package com.gp.cabbooking.model;

import java.util.Date;

public class SearchModel {

	private int pickup;

	//private Date pickupDate;
	private String pickDate;

	private int pickupHour;

	private int pickupMinute;

	public SearchModel() {
		// TODO Auto-generated constructor stub
	}

	public int getPickup() {
		return pickup;
	}

	public void setPickup(int pickup) {
		this.pickup = pickup;
	}



	public String getPickDate() {
		return pickDate;
	}

	public void setPickDate(String pickDate) {
		this.pickDate = pickDate;
	}

	public int getPickupHour() {
		return pickupHour;
	}

	public void setPickupHour(int pickupHour) {
		this.pickupHour = pickupHour;
	}

	public int getPickupMinute() {
		return pickupMinute;
	}

	public void setPickupMinute(int pickupMinute) {
		this.pickupMinute = pickupMinute;
	}

}
